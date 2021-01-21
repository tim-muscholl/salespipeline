package com.salespipeline.application.data.service;

import com.salespipeline.application.data.entity.Role;
import com.salespipeline.application.data.entity.User;
import com.salespipeline.application.views.admin.AdminView;
import com.salespipeline.application.views.customers.CustomersView;
import com.salespipeline.application.views.home.HomeView;
import com.salespipeline.application.views.logout.LogoutView;
import com.salespipeline.application.views.main.MainView;
import com.salespipeline.application.views.opportunities.OpportunitiesView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//Login Backend
@Service
public class AuthService {
//Authorized Route -> Je nach Rolle werden andere Routes freigegeben
    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {

    }

    public class AuthException extends Exception {

    }

    private final UserRepository userRepository;
    private final MailSender mailSender;

    public AuthService(UserRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public void authenticate(String username, String password) throws AuthException {
        User user = userRepository.getByUsername(username);
             user.setActive(true);
        if (user != null && user.checkPassword(password) && user.isActive()) { //muss existieren und aktiv sein
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRole()); //je nach Rolle Routes freischalten
        } else { //ist nicht aktiviert oder Passwort falsch
            throw new AuthException();
        }
    }

    private void createRoutes(Role role) {
        getAuthorizedRoutes(role).stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute( //Gilt nur für eine Session, anstatt für die ganze Application
                                route.route, route.view, MainView.class));
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        var routes = new ArrayList<AuthorizedRoute>();
//Was wird dem Nutzer angezeigt:
        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("customers" , "Customers", CustomersView.class));
            routes.add(new AuthorizedRoute("opportunities", "Opportunities", OpportunitiesView.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
//Wenn Admin:

        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("customers" , "Customers", CustomersView.class));
            routes.add(new AuthorizedRoute("opportunities", "Opportunities", OpportunitiesView.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        }

        return routes;
    }

    public void register(String email, String password) {
        User user = userRepository.save(new User(email, password, Role.USER));
        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@salespipeline.com"); //absenderadresse
        message.setSubject("Salespipeline - Please confirm your account"); //Betreff
        message.setText( "Welcome! " + user.getUsername() +"," + " this is your activationcode. Please click the link to confirm your mailadress: " + text); //Persönliche Nachricht
        message.setTo(email);
        mailSender.send(message);


      // System.out.println("http://localhost:8080/activate?code=" + user.getActivationCode());
    }       //Activation code: Kann auch ein anderer Link angezeigt werden bzw. mailto:
//Prüfen ob active
    public void activate(String activationCode) throws AuthException {
        User user = userRepository.getByActivationCode(activationCode);
        if (user != null) {
            user.setActive(true);
            userRepository.save(user); //-> User wird erst nach der Aktivierung gespeichert.
        } else {
            throw new AuthException();
        }
    } //Neuen Admin registrieren
    public void adminregister(String email, String password) {
        User user = userRepository.save(new User(email, password, Role.ADMIN)); //Rolle als Admin festgelegt.
        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@salespipeline.com");
        message.setSubject("Salespipeline - Please confirm your Admin account");
        message.setText("Welcome! " + user.getUsername() + "," + " this is your activationcode. Please click the link to confirm your mailadress: " + text); //Persönliche Nachricht
        message.setTo(email);
        mailSender.send(message);

    }
}
