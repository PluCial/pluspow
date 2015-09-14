package com.pluspow.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TransContentsServiceTest extends AppEngineTestCase {

    private TextResService service = new TextResService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
