package com.phone.api.domain;

import com.phone.api.web.rest.TestUtil;
import org.junit.Test;


import static com.phone.api.domain.PhoneTestSamples.getPhoneSample1;
import static com.phone.api.domain.PhoneTestSamples.getPhoneSample2;
import static org.assertj.core.api.Assertions.assertThat;

public class PhoneTest {
    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phone.class);
        Phone phone1 = getPhoneSample1();
        Phone phone2 = new Phone();
        assertThat(phone1).isNotEqualTo(phone2);

        phone2.setId(phone1.getId());
        assertThat(phone1).isEqualTo(phone2);

        phone2 = getPhoneSample2();
        assertThat(phone1).isNotEqualTo(phone2);
    }
}
