package com.pluspow.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class OfficeHoursTest extends AppEngineTestCase {

    private OfficeHours model = new OfficeHours();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
