package next.controller.user;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static core.annotation.RequestMethod.GET;

@Controller
public class UserFormController {
    private static final Logger logger = LoggerFactory.getLogger(UserFormController.class);

    @RequestMapping(value = "/users/form", method = GET)
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug(">> Execute userForm controller!");
        return new ModelAndView(new JspView("/user/form.jsp"));
    }
}
