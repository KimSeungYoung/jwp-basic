package core.nmvc;

import core.mvc.ModelAndView;
import next.model.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;
    private MockHttpServletResponse response;
    private boolean isLogin;

    @Before
    public void setup() throws Exception {
        handlerMapping = new AnnotationHandlerMapping( "next.controller");
        handlerMapping.initialize();
        response = new MockHttpServletResponse();
        isLogin = false;
    }

    @Ignore
    @Test
    public void list() throws Exception {
        executeTestWith("GET", "/users");
        assertEquals("/users/list.jsp", response.getForwardedUrl());
    }

    @Ignore
    @Test
    public void show() throws Exception {
        executeTestWith("GET", "/users/show");
        assertEquals("/users/show.jsp", response.getForwardedUrl());
    }

    @Ignore
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

    @Test
    public void listUserLogined() throws Exception {
        login();
        executeTestWith("GET", "/users");
        assertEquals("/user/list.jsp", response.getForwardedUrl());
    }

    @Test
    public void listUserNotLogined() throws Exception {
        executeTestWith("GET", "/users");
        assertEquals("/users/loginForm", response.getRedirectedUrl());
    }

    private void executeTestWith(String requestMethod, String requestUrl) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest(requestMethod, requestUrl);
        if(isLogin) {
            HttpSession session = request.getSession();
            session.setAttribute("user", new User("sykim", "1234", "김승영", "sykim@woowahan.com"));
        }
        HandlerExecution execution = handlerMapping.getHandler(request);
        ModelAndView mav = execution.handle(request, response);
        mav.getView().render(mav.getModel(), request, response);
    }

    private void login() {
        isLogin = true;
    }
}