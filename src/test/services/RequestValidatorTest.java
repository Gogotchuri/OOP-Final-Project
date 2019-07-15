
package test.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import services.RequestValidator;
import test.Tester;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestValidatorTest extends Tester {

    @Mock
    HttpServletRequest req;

    @Mock
    HttpServletResponse resp;

    private Map<String, List<String>> rules;

    @Before
    public void setUp() throws Exception {
        rules = new HashMap<>();
    }

    @Test
    public void failed() {
        //Validation rules
        rules.put("email", Arrays.asList("required", "type:email"));
        rules.put("id", Arrays.asList("required", "type:integer", "max_num:15"));


        //Validation rules
        rules.put("email", Arrays.asList("required", "type:email"));
        rules.put("id", Arrays.asList("required", "type:integer", "max_num:15"));

        assertTrue(true);

        //implement me
    }

}
