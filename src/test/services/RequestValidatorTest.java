
package test.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import test.Tester;

import javax.servlet.http.HttpServletRequest;

public class RequestValidatorTest extends Tester { // TODO: Ilia
    @Before
    public void setUp() throws Exception {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void failed() {

    }

}
