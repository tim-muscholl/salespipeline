package com.salespipeline.application.views.customers;

import com.salespipeline.application.data.entity.Customers;
import com.salespipeline.application.data.service.CustomersService;
import com.salespipeline.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Route(value = "customers", layout = MainView.class)
@PageTitle("Customers")
@CssImport("./styles/views/customers/customers-view.css")
public class CustomersView extends Div {
//Customer Tabelle anlegen
    private Grid<Customers> grid = new Grid<>(Customers.class, false);

    private TextField idc;
   // private TextField password;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField jobtitle;
    private TextField businessphone;
    private TextField mobilephone;
    private TextField adress;
    private TextField city;
    private TextField state;
    private TextField country;
    private TextField zip;
//Buttons für neue Anlage
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Customers> binder;
//Entity
    private Customers customers;

    public CustomersView(@Autowired CustomersService customersService) {
        setId("customers-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("idc").setAutoWidth(true);
      //  grid.addColumn("password").setAutoWidth(true);
        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("jobtitle").setAutoWidth(true);
        grid.addColumn("businessphone").setAutoWidth(true);
        grid.addColumn("mobilephone").setAutoWidth(true);
        grid.addColumn("adress").setAutoWidth(true);
        grid.addColumn("city").setAutoWidth(true);
        grid.addColumn("state").setAutoWidth(true);
        grid.addColumn("country").setAutoWidth(true);
        grid.addColumn("zip").setAutoWidth(true);
        grid.setItems(query -> customersService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Customers> customersFromBackend = customersService.get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (customersFromBackend.isPresent()) {
                    populateForm(customersFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Customers.class);

        // Bind fields
        binder.forField(idc).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("idc");
        //binder.forField(password).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
             //   .bind("password");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.customers == null) {
                    this.customers = new Customers();
                }
                binder.writeBean(this.customers);

                customersService.update(this.customers);
                clearForm();
                refreshGrid();
                Notification.show("Customers details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the customers details.");
            }
        });

    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        idc = new TextField("Idc");
       // password = new TextField("Password");
        firstName = new TextField("First Name");
        lastName = new TextField("Last Name");
        email = new TextField("Email");
        jobtitle = new TextField("Jobtitle");
        businessphone = new TextField("Businessphone");
        mobilephone = new TextField("Mobilephone");
        adress = new TextField("Adress");
        city = new TextField("City");
        state = new TextField("State");
        country = new TextField("Country");
        zip = new TextField("Zip");
        Component[] fields = new Component[]{idc, /*password*/ firstName, lastName, email, jobtitle, businessphone,
                mobilephone, adress, city, state, country, zip};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Customers value) {
        this.customers = value;
        binder.readBean(this.customers);

    }
}
