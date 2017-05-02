package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static core.annotation.RequestMethod.GET;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private QuestionDao questionDao = QuestionDao.getInstance();

    @RequestMapping(value = "/", method = GET)
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug(">> Execute home controller!");
        return new ModelAndView(new JspView("home.jsp")).addObject("questions", questionDao.findAll());
    }
}
