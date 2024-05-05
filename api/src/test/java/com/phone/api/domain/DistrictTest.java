package com.phone.api.domain;

import static com.phone.api.domain.DistrictTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phone.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DistrictTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(District.class);
        District district1 = getDistrictSample1();
        District district2 = new District();
        assertThat(district1).isNotEqualTo(district2);

        district2.setId(district1.getId());
        assertThat(district1).isEqualTo(district2);

        district2 = getDistrictSample2();
        assertThat(district1).isNotEqualTo(district2);
    }
}

