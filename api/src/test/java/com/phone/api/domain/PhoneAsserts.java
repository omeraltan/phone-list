package com.phone.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPhoneAllPropertiesEquals(Phone expected, Phone actual) {
        assertPhoneAutoGeneratedPropertiesEquals(expected, actual);
        assertPhoneAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    private static void assertPhoneAllUpdatablePropertiesEquals(Phone expected, Phone actual) {
        assertPhoneUpdatableFieldsEquals(expected, actual);
        assertPhoneUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    private static void assertPhoneAutoGeneratedPropertiesEquals(Phone expected, Phone actual) {
        assertThat(expected)
            .as("Verify Customer auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    private static void assertPhoneUpdatableFieldsEquals(Phone expected, Phone actual) {
        assertThat(expected)
            .as("Verify Phone relevant properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()))
            .satisfies(e -> assertThat(e.getPhoneNumber()).as("check phone number").isEqualTo(actual.getPhoneNumber()))
            .satisfies(e -> assertThat(e.getCustomer()).as("check customer").isEqualTo(actual.getCustomer()));
    }

    private static void assertPhoneUpdatableRelationshipsEquals(Phone expected, Phone actual) {
    }

}
