package com.phone.api.domain;

import com.phone.api.web.rest.TestUtil;
import org.junit.Test;

import static com.phone.api.domain.CustomerTestSamples.getCustomerSample1;
import static com.phone.api.domain.CustomerTestSamples.getCustomerSample2;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = getCustomerSample1();
        Customer customer2 = new Customer();
        assertThat(customer1).isNotEqualTo(customer2);

        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }
}
