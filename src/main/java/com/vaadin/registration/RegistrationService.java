package com.vaadin.registration;

import com.vaadin.registration.domain.Registration;
import com.vaadin.registration.domain.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A service class for the UI to access backend services.
 */
@Service
class RegistrationService {
    
    @Autowired
    private RegistrationRepository repository;

    public List<String> getSizes() {
        return Arrays.asList("Small", "Medium", "Large", "Extra Large", "XXL");
    }

    public void register(Registration registration) {
        repository.save(registration);
    }

}
