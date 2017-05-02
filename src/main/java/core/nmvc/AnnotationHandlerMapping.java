package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() throws Exception {
        scanMethodsAnnotated();
    }

    private void scanMethodsAnnotated() throws InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> annotatedClass : annotated) {
            logger.debug("annotated class : {}", annotatedClass.getName());
            Method[] methods = annotatedClass.getMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    String value = requestMapping.value();
                    RequestMethod requestMethod = requestMapping.method();
                    logger.debug("method name : {}, class's instance : {}", method.getName(), annotatedClass.newInstance());
                    handlerExecutions.put(new HandlerKey(value, requestMethod), new HandlerExecution(method, annotatedClass.newInstance()));
                }
            }
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
