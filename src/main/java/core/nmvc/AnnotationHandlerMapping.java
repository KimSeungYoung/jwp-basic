package core.nmvc;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        Set<Method> methods = getRequestMappingMethods(controllers.keySet());
        for (Method method : methods) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            logger.debug("register handlerExecution : url is {}, method is {}", rm.value(), method);
            handlerExecutions.put(createHandlerKey(rm), createHandlerExecutioin(controllers, method));
        }

    }

    private HandlerExecution createHandlerExecutioin(Map<Class<?>, Object> controllers, Method method) {
        return new HandlerExecution(controllers.get(method.getDeclaringClass()), method);
    }

    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllersKeySet) {
        Set<Method> methods = Sets.newHashSet();
        for (Class<?> clazz : controllersKeySet) {
            methods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return methods;
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }

    @Override
    public Optional<HandlerExecution> getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        logger.debug("requestUri : {}, requestMethod : {}", requestUri, rm);
        return Optional.ofNullable(handlerExecutions.get(new HandlerKey(requestUri, rm)));
    }
}
