package core.nmvc;

import core.mvc.ModelAndView;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;
    private MockHttpServletResponse response;

    @Before
    public void setup() throws Exception {
        handlerMapping = new AnnotationHandlerMapping("core.nmvc", "next.controller");
        handlerMapping.initialize();

        response = new MockHttpServletResponse();
    }

    @Test
    public void list() throws Exception {
        executeTestWith("GET", "/users");
        assertEquals("/users/list.jsp", response.getForwardedUrl());
    }

    @Test
    public void show() throws Exception {
        executeTestWith("GET", "/users/show");
        assertEquals("/users/show.jsp", response.getForwardedUrl());
    }

    @Test
    public void create() throws Exception {
        executeTestWith("POST", "/users");
        assertEquals("/users", response.getRedirectedUrl());
    }

    @Test
    public void home() throws Exception {
        executeTestWith("GET", "/");
        assertEquals("home.jsp", response.getForwardedUrl());
    }

    @Test
    public void userForm() throws Exception {
        executeTestWith("GET", "/users/form");
        assertEquals("/user/form.jsp", response.getForwardedUrl());
    }

    @Test
    public void loginForm() throws Exception {
        executeTestWith("GET", "/users/loginForm");
        assertEquals("/user/login.jsp", response.getForwardedUrl());
    }

    private void executeTestWith(String requestMethod, String requestUrl) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest(requestMethod, requestUrl);
        HandlerExecution execution = handlerMapping.getHandler(request);
        ModelAndView mav = execution.handle(request, response);
        mav.getView().render(mav.getModel(), request, response);
    }
}