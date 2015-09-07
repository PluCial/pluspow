package com.pluspow.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PlanServiceTest extends AppEngineTestCase {

    private SpotPayPlanService service = new SpotPayPlanService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
