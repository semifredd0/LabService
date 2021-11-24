package com.uniba.di.dfmdevelop.labservice.controller;

import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;
import com.uniba.di.dfmdevelop.labservice.model.Payment;
import com.uniba.di.dfmdevelop.labservice.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaypalController {
    
    @Autowired
    PaypalService paymentService;
    
    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @PostMapping("/pay")
    public String payment(@ModelAttribute("payment") Payment p_payment) {
        try {
            com.paypal.api.payments.Payment payment = paymentService.createPayment(
                    p_payment.getPrice(),
                    p_payment.getCurrency(),
                    p_payment.getMethod(),
                    p_payment.getIntent(),
                    p_payment.getDescription(),
                    "http://localhost:8443/" + CANCEL_URL,
                    "http://localhost:8443/" + SUCCESS_URL);

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        } catch(PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/index"; // Modificare il link
    }

    @GetMapping(value = CANCEL_URL) // Implementare pagina cancel
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL) // Implementare pagina success
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            com.paypal.api.payments.Payment payment = paymentService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/index"; // Modificare il link
    }
}