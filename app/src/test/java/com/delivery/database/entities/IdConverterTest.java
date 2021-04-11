package com.delivery.database.entities;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.TestEntityGenerator;
import com.delivery.core.domain.Identity;
import org.junit.Test;

public class IdConverterTest {

    @Test
    public void convertIdShouldReturnNumberWhenIsNotNullOrEmpty() {
        // given
        Long number = TestEntityGenerator.randomId();

        // when
        Long actual = IdConverter.convertId(new Identity(number));

        // then
        assertThat(actual).isEqualTo(number);
    }

    @Test
    public void convertIdShouldReturnNullWhenInputIsNull() {
        // when
        Long actual = IdConverter.convertId(null);

        // then
        assertThat(actual).isNull();
    }

    @Test
    public void convertIdShouldReturnNullWhenInputIsIdentityEmpty() {
        // given
        Identity id = Identity.nothing();

        // when
        Long actual = IdConverter.convertId(id);

        // then
        assertThat(actual).isNull();
    }
}