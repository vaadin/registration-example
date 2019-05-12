
package com.vaadin.registration.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Empty JpaRepository is enough for a simple crud.
 */
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    
}
