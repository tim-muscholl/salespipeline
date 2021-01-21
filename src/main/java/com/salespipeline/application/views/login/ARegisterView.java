package com.salespipeline.application.views.login;


import com.salespipeline.application.data.service.AuthService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Register as Admin")
@Route("ARegister")

public class ARegisterView extends Composite {
    private  final AuthService authService;

    public ARegisterView(AuthService authService) {this.authService = authService;}

    @Override
    protected Component initContent() {
        TextField username = new TextField("E-Mail or Username");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Confirm password");
        return new VerticalLayout(
                new H2("Register as Admin"),
                username,
                password1,
                password2,
                new Button("Send", event -> ARegisterView(
                        username.getValue(),
                        password1.getValue(),
                        password2.getValue()
                ))
        );

    }
//Register as Admin
    private void ARegisterView(String username, String password1, String password2) {
        if (username.trim().isEmpty()) {
            Notification.show("Enter a username");
        } else if (password1.isEmpty()) {
            Notification.show("Enter a password");
        } else if (!password1.equals(password2)) {
            Notification.show("Passwords don't match");
        } else {
            authService.adminregister(username, password1); //adminregister als neue Funktion im AuthService: Unterschied: Legt die Rolle als Admin fest!
            Notification.show("Please check your email!"); //Check your mail - wenn auf Mail umgeleitet wird.
        }
    }
}
