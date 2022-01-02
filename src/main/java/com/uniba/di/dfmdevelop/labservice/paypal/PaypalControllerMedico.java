package com.uniba.di.dfmdevelop.labservice.paypal;

import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaypalControllerMedico {

    @Autowired
    PaypalService paymentService;

    public static final String SUCCESS_URL = "medico/payment/success";
    public static final String CANCEL_URL = "medico/payment/cancel";

    @PostMapping("/pay_medico")
    public String payment(@ModelAttribute("payment") Payment p_payment) {
        p_payment.setCurrency("EUR");
        p_payment.setMethod("paypal");
        p_payment.setIntent("SALE");
        p_payment.setDescription("Prenotazione tampone");

        try {
            com.paypal.api.payments.Payment payment = paymentService.createPayment(
                    p_payment.getPrice(),
                    p_payment.getCurrency(),
                    p_payment.getMethod(),
                    p_payment.getIntent(),
                    p_payment.getDescription(),
                    "https://localhost:8443/" + CANCEL_URL,
                    "https://localhost:8443/" + SUCCESS_URL);

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        } catch(PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/"+CANCEL_URL;
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return CANCEL_URL;
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            com.paypal.api.payments.Payment payment = paymentService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return SUCCESS_URL;
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/"+CANCEL_URL;
    }
}