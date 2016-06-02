package co.insecurity.iiam.rest.controller;

import co.insecurity.iiam.rest.domain.Alerts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @ModelAttribute Alerts alerts,
            RedirectAttributes redirectAttrs,
            Model model) {
        LOG.debug("in login() view");
        if (error != null)
            alerts.addAlert("Invalid username or password!",
                    Alerts.AlertType.DANGER);
        if (logout != null) {
            if (!(isAuthenticatedAnonymously())) {
                alerts.addAlert("You have not been logged out!",
                        Alerts.AlertType.WARNING);
                redirectAttrs.addFlashAttribute(alerts);
                return "redirect:/";
            } else
                alerts.addAlert("You have been successfully logged out!",
                        Alerts.AlertType.SUCCESS);
        }
        return "/login";
    }
}
