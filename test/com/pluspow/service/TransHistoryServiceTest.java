package com.pluspow.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TransHistoryServiceTest extends AppEngineTestCase {

    private TransHistoryService service = new TransHistoryService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
