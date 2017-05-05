package core.nmvc;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {

    private Method method;
    private Object object;

    public HandlerExecution(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView)method.invoke(object, request, response);
    }
}
