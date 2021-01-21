package com.salespipeline.application.data.generator;

import com.salespipeline.application.data.entity.*;
import com.salespipeline.application.data.service.CustomersRepository;
import com.salespipeline.application.data.service.OpportunityRepository;
import com.salespipeline.application.data.service.PersonRepository;
import com.salespipeline.application.data.service.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {
//Generator für Beispieldaten
    @Bean
    public CommandLineRunner loadData(OpportunityRepository opportunityRepository,
                                      CustomersRepository customersRepository, PersonRepository PersonRepository, UserRepository UserRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (opportunityRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Daten generieren");

            //Folgendes wird in der realen Anwendung natürlich weggelassen. Nur den letzten Teil in dem admin und user angelegt werden nicht!
            //Es können darüber hinaus auch weitere selbsterstellte Rollen belegt werden (Developer, etc.)

            logger.info("... Hundert Opportunities erstellen...");
            ExampleDataGenerator<Opportunity> opportunityRepositoryGenerator = new ExampleDataGenerator<>(
                    Opportunity.class);
            opportunityRepositoryGenerator.setData(Opportunity::setId, DataType.ID);
            opportunityRepositoryGenerator.setData(Opportunity::setIdo, DataType.NUMBER_UP_TO_1000);
            opportunityRepositoryGenerator.setData(Opportunity::setIdc, DataType.NUMBER_UP_TO_1000);
            opportunityRepositoryGenerator.setData(Opportunity::setTitle, DataType.WORD);
            opportunityRepositoryGenerator.setData(Opportunity::setCategory, DataType.WORD);
            opportunityRepositoryGenerator.setData(Opportunity::setRating, DataType.NUMBER_UP_TO_100);
            opportunityRepositoryGenerator.setData(Opportunity::setProbability, DataType.NUMBER_UP_TO_100);
            opportunityRepositoryGenerator.setData(Opportunity::setRevenue, DataType.NUMBER_UP_TO_1000);
            opportunityRepository.saveAll(opportunityRepositoryGenerator.create(100, seed));

            logger.info("... Tim's cooler Code erstellt 100 coole Kunden...");
            ExampleDataGenerator<Customers> customersRepositoryGenerator = new ExampleDataGenerator<>(Customers.class);
            customersRepositoryGenerator.setData(Customers::setId, DataType.ID);
            customersRepositoryGenerator.setData(Customers::setIdc, DataType.NUMBER_UP_TO_1000);
            customersRepositoryGenerator.setData(Customers::setFirstName, DataType.FIRST_NAME);
            customersRepositoryGenerator.setData(Customers::setLastName, DataType.LAST_NAME);
            customersRepositoryGenerator.setData(Customers::setEmail, DataType.EMAIL);
            customersRepositoryGenerator.setData(Customers::setJobtitle, DataType.WORD);
            customersRepositoryGenerator.setData(Customers::setBusinessphone, DataType.PHONE_NUMBER);
            customersRepositoryGenerator.setData(Customers::setMobilephone, DataType.PHONE_NUMBER);
            customersRepositoryGenerator.setData(Customers::setAdress, DataType.ADDRESS);
            customersRepositoryGenerator.setData(Customers::setCity, DataType.CITY);
            customersRepositoryGenerator.setData(Customers::setState, DataType.STATE);
            customersRepositoryGenerator.setData(Customers::setCountry, DataType.COUNTRY);
            customersRepositoryGenerator.setData(Customers::setZip, DataType.ZIP_CODE);
            customersRepository.saveAll(customersRepositoryGenerator.create(100, seed));

            logger.info("Daten erstellt. Hat geklappt.");

            logger.info("... hundert Personen erstellen...");
            ExampleDataGenerator<Person> personRepositoryGenerator = new ExampleDataGenerator<>(Person.class);
            personRepositoryGenerator.setData(Person::setId, DataType.ID);
            personRepositoryGenerator.setData(Person::setFirstName, DataType.FIRST_NAME);
            personRepositoryGenerator.setData(Person::setLastName, DataType.LAST_NAME);
            personRepositoryGenerator.setData(Person::setEmail, DataType.EMAIL);
            personRepositoryGenerator.setData(Person::setPhone, DataType.PHONE_NUMBER);
            PersonRepository.saveAll(personRepositoryGenerator.create(100, seed));

            //Rollen anlegen -> sind immer aktiv und müssen nicht aktiviert werden.

            UserRepository.save(new User("user", "u", Role.USER));
            UserRepository.save(new User("admin", "a", Role.ADMIN));
            UserRepository.save(new User ("employee", "e", Role.USER));
            UserRepository.save(new User("tim", "t", Role.ADMIN));
            UserRepository.save(new User("valentin", "v", Role.ADMIN));

            logger.info("Daten generiert. Erfolreich abgeschlossen.");
        };

    }

}