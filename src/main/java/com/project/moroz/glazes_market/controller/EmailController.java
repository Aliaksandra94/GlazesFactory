package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.TestEmailConstant;
import com.project.moroz.glazes_market.entity.Order;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.service.interfaces.OrderService;
import com.project.moroz.glazes_market.service.interfaces.SolvencyService;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
@Controller
public class EmailController {
    private JavaMailSender mailSender;
    private UserService userService;
    private OrderService orderService;
    private SolvencyService solvencyService;
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setSolvencyService(SolvencyService solvencyService) {
        this.solvencyService = solvencyService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userPage/orderHistory/order{id}/enterEmail")
    public String getEmailPage(@PathVariable("id") int id, HttpServletRequest request, Model model) throws MessagingException {
        Order order = orderService.returnOrderById(id);
        User user = order.getUser();
        if (request.isUserInRole("ROLE_SELLER") || request.isUserInRole("ROLE_ADMIN")) {
            if (user.getEmail() != null && !(user.getEmail().equals(""))) {
                model.addAttribute("user", user);
                model.addAttribute("order", order);
                return sendHtmlEmail(id, request, model);
            } else {
                return "redirect:/userPage/orderHistory";
            }
        } else if (request.isUserInRole("ROLE_USER")) {
            model.addAttribute("user", user);
            model.addAttribute("order", order);
            return "users/requestEmailPage";
        } else {
            return "redirect:/userPage/orderHistory";
        }
    }

    @PostMapping("/userPage/orderHistory/order{id}/enterEmail")
    public String sentEmail(@PathVariable("id") int id, HttpServletRequest request, Model model,
                            @RequestParam("email") String email,
                            @ModelAttribute("order") Order order) throws MessagingException {
        if (email.equals("")) {
            String errorMessage = messageSource.getMessage("error.emptyField", new Object[]{"error.emptyField"}, LocaleContextHolder.getLocale());
            model.addAttribute("msg", errorMessage);
            order = orderService.returnOrderById(id);
            model.addAttribute("order", order);
            User user = order.getUser();
            model.addAttribute("user", user);
            return "users/requestEmailPage";
        }
        order = orderService.returnOrderById(id);
        model.addAttribute("order", order);
        User user = Utils.getUserInSession(request);
        userService.updateUsersetEmail(user.getId(), email);
        user = userService.returnUserById(user.getId());
        model.addAttribute("user", user);
        sendHtmlEmail(order.getId(), request, model);
        return "users/orderEmailSendConfirmation";
    }


    @ResponseBody
    @RequestMapping("/userPage/orderHistory/{id}/sendEmail")
    public String sendHtmlEmail(@PathVariable("id") int id, HttpServletRequest request, Model model) throws MessagingException {
        Order order = orderService.returnOrderById(id);
        User user = order.getUser();
        MimeMessage message = mailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        String picture = TestEmailConstant.picture;
        String htmlMsg = null;
        if (user.getSolvency().equals(solvencyService.returnSolvencyByID(1))) {
            htmlMsg = "<h2> Good day, " + user.getName() + "! </h2>\n" +
                    "<h3>Thank you for placing the order. <br> " +
                    "Your order No " + order.getOrderNumber() + " dd " + order.getOrderDate() +
                    " is ready for shipment and payment. <br>" +
                    "<br><br>" +
                    "Order summary: <br>" +
                    order.getOrderItems().size() + " products for the amount of " +
                    order.getAmount() + "." +
                    "<br><br>" +
                    "Remind You, don't forget to pay for the order No " + order.getOrderNumber() + " before shipment." +
                    "<br><br>" +
                    "To get more info, please, visit our web-site: <a href=\"http://localhost:8080\">Link</a>" +
                    "<br><br>" +
                    picture +
                    "Thank you for choosing us!</h3>";
        } else if (user.getSolvency().equals(solvencyService.returnSolvencyByID(2))) {
            htmlMsg = "<h2> Good day, " + user.getName() + "! </h2>\n" +
                    "<h3>Thank you for placing the order. <br> " +
                    "Your order No" + order.getOrderNumber() + " dd " + order.getOrderDate() +
                    " is ready for shipment. <br>" +
                    "<br><br>" +
                    "Order summary: <br>" +
                    order.getOrderItems().size() + " products for the amount of " +
                    order.getAmount() + "." +
                    "<br><br>" +
                    "Remind You, don't forget to pay for the order No " + order.getOrderNumber() +
                    " no later than 30 days after shipment." +
                    "<br><br>" +
                    "To get more info, please, visit our web-site: <a href=\"http://localhost:8080\">ChoCoCom</a>" +
                    "<br><br>" +
                    "<img src='https://i.postimg.cc/FHQXzrtp/logo.png' border='0' alt='logo'/>" +
                    "Thank you for choosing us!</h3>";
        }
        message.setContent(htmlMsg, "text/html");
        helper.setTo(user.getEmail());
        helper.setSubject("ChoCoCom. About the order's readiness.");
        this.mailSender.send(message);
        return "users/orderEmailSendConfirmation";
    }
}

