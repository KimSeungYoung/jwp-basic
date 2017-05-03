package next.controller.user;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static core.annotation.RequestMethod.GET;

@Controller
public class ListUserController {
    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);
    private UserDao userDao = UserDao.getInstance();

    @RequestMapping(value = "/users", method = GET)
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            logger.debug(">> Need to login!");
            return new ModelAndView(new JspView("redirect:/users/loginForm"));
        }

        logger.debug(">> Execute listUserController!");
        return new ModelAndView(new JspView("/user/list.jsp")).addObject("users", userDao.findAll());
    }
}
