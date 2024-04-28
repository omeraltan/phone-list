package com.phone.api.annotation;

import com.phone.api.domain.Customer;
import com.phone.api.repository.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Customer customer = customerRepository.findByEmail(value);
        if (customer == null) {
            return true;
        }
        return false;
    }
}
