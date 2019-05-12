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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.registration.domain.Registration;
import com.vaadin.registration.domain.RegistrationRepository;

@Route
public class ListRegistrationsView extends VerticalLayout {
    
    private final transient RegistrationRepository repo;
    private final Grid<Registration> registrations = new Grid<>(Registration.class);
    
    public ListRegistrationsView(RegistrationRepository repo) {
        this.repo = repo;
        // Build the layout
        H1 heading = new H1("List of submitted registrations");
        Button update = new Button(VaadinIcon.REFRESH.create());
        RouterLink orderView = new RouterLink("Submit new registration", MainView.class);
        add(heading, update, registrations, orderView);
        
        registrations.setColumns("name", "email", "shirtSize");
        registrations.addComponentColumn(order -> {
            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
            deleteBtn.addClickListener(e -> {
                repo.delete(order);
                listOrders();
            });
            return deleteBtn;
        });
        listOrders();
        
        update.addClickListener(e -> listOrders());
        
    }

    public void listOrders() {
        registrations.setItems(repo.findAll());
    }
    
}
