package com.salespipeline.application.views.login;

import com.salespipeline.application.data.service.AuthService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.List;
import java.util.Map;
//Activation Page
@PageTitle("Thank you!")
@Route("activate")
public class ActivationView extends Composite implements BeforeEnterObserver {

    private VerticalLayout layout;

    private final AuthService authService;

    public ActivationView(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        try {//Activation code clicked?
            Map<String, List<String>> params = event.getLocation().getQueryParameters().getParameters();
            String code = params.get("code").get(0);
            authService.activate(code);
            layout.add(
                    new Text("Account activated."), //YES
                    new RouterLink("Login? This way!", LoginView.class)
            );
        } catch (AuthService.AuthException e) {
            layout.add(new Text("Nice try. Invalid Link.")); //NO
        }
    }

    @Override
    protected Component initContent() {
        layout = new VerticalLayout();
        return layout;
    }
}
