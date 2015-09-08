package com.pluspow.dao;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class OfficeHoursDaoTest extends AppEngineTestCase {

    private OfficeHoursDao dao = new OfficeHoursDao();

    @Test
    public void test() throws Exception {
        assertThat(dao, is(notNullValue()));
    }
}
