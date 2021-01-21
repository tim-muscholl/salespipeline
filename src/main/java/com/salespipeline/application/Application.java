package com.salespipeline.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.vaadin.artur.helpers.LaunchUtil;


/**
 * The entry point of the Spring Boot application.
 *
 * Use the * and some desktop browsers.
 *
 */
@SpringBootApplication //Springboot:run
@PWA(name = "Salespipeline", shortName = "Salespipeline")

public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args)); //Start Browser im Dev Mode
       // LaunchUtil.launchBrowser(SpringApplication.run(Application.class, args)); //Browsestart in normalem Mode
//Session wird gestartet, sobald die Application läuft und das Frontend fertig compiled ist. Localhost öffnet sich automatisch.
    }

}

//Application ausführen um Projekt auf dem localhost zu starten