package com.pluspow.controller.spot;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SelectLangControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/spot/selectLang");
        SelectLangController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/spot/selectLang.jsp"));
    }
}
