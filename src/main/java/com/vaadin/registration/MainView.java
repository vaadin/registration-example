/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.registration.domain.Registration;

@StyleSheet("frontend://src/styles.css")
@Route
public class MainView extends VerticalLayout {

    private transient RegistrationService service;

    private Binder<Registration> binder = new BeanValidationBinder<>(Registration.class);

    private TextField name = new TextField("Name");
    private EmailField email = new EmailField("Email");
    private ComboBox<String> shirtSize = new ComboBox<>("T-shirt size");

    public MainView(RegistrationService service) {
        this.service = service;

        // Build the layout
        H1 heading = new H1("Register to win a Vaadin }> iPad!");
        Button submit = new Button("Register to win!");
        setDefaultHorizontalComponentAlignment(FlexLayout.Alignment.CENTER);
        RouterLink listOrders = new RouterLink("View registrations", ListRegistrationsView.class);
        add(
                heading,
                name,
                email,
                shirtSize,
                submit,
                listOrders
        );

        // configure components
        shirtSize.setItems(service.getSizes());

        submit.addClickListener(e -> {
            submitOrder();
            String msg = String.format(
                    "Thank you %s, your registration was submitted!",
                    binder.getBean().getName());
            Notification.show(msg, 3000, Notification.Position.MIDDLE);
            init();
        });


        // Add keyboard shortcut
        submit.addClickShortcut(Key.ENTER);

        // Bind fields from this UI class to domain object using naming convention
        binder.bindInstanceFields(this);
        // enable save button only if the bean is valid
        binder.addStatusChangeListener(e -> submit.setEnabled(binder.isValid()));

        init();
    }

    private void submitOrder() {
        service.register(binder.getBean());
    }

    private void init() {
        binder.setBean(new Registration());
    }

}
