package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface HandlerMapping<T> {
    Optional<T> getHandler(HttpServletRequest request);
}
