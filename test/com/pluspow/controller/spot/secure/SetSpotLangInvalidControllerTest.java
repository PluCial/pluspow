package com.pluspow.controller.spot.secure;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SetSpotLangInvalidControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/spot/secure/setSpotLangInvalid");
        SetLangInvalidController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
