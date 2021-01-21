package com.salespipeline.application.views.opportunities;

import com.salespipeline.application.data.entity.Opportunity;
import com.salespipeline.application.data.service.OpportunityService;
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

@Route(value = "opportunites", layout = MainView.class)
@PageTitle("Opportunities")
@CssImport("./styles/views/opportunities/opportunities-view.css")
public class OpportunitiesView extends Div {
//Opportunites Tabelle anlegen
    private Grid<Opportunity> grid = new Grid<>(Opportunity.class, false);

    private TextField ido;
    private TextField idc;
    private TextField title;
    private TextField category;
    private TextField rating;
    private TextField probability;
    private TextField revenue;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Opportunity> binder;

    private Opportunity opportunity;

    public OpportunitiesView(@Autowired OpportunityService opportunityService) {
        setId("opportunities-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("ido").setAutoWidth(true);
        grid.addColumn("idc").setAutoWidth(true);
        grid.addColumn("title").setAutoWidth(true);
        grid.addColumn("category").setAutoWidth(true);
        grid.addColumn("rating").setAutoWidth(true);
        grid.addColumn("probability").setAutoWidth(true);
        grid.addColumn("revenue").setAutoWidth(true);
        grid.setItems(query -> opportunityService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // Row auswählen
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Opportunity> opportunityFromBackend = opportunityService.get(event.getValue().getId());
                // refresh
                if (opportunityFromBackend.isPresent()) {
                    populateForm(opportunityFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure
        binder = new BeanValidationBinder<>(Opportunity.class);
//Error Message beachten!
        // Bind fields.
        binder.forField(ido).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("ido");
        binder.forField(idc).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("idc");
        binder.forField(rating).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("rating");
        binder.forField(probability).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("probability");
        binder.forField(revenue).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("revenue");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.opportunity == null) {
                    this.opportunity = new Opportunity();
                }
                binder.writeBean(this.opportunity);

                opportunityService.update(this.opportunity);
                clearForm();
                refreshGrid();
                Notification.show("Opportunity details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the opportunity details.");
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
        ido = new TextField("Ido");
        idc = new TextField("Idc");
        title = new TextField("Title");
        category = new TextField("Category");
        rating = new TextField("Rating");
        probability = new TextField("Probability");
        revenue = new TextField("Revenue");
        Component[] fields = new Component[]{ido, idc, title, category, rating, probability, revenue};

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

    private void populateForm(Opportunity value) {
        this.opportunity = value;
        binder.readBean(this.opportunity);

    }
}
//Gleich wie bei Customers