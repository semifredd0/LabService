package com.uniba.di.dfmdevelop.labservice.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.uniba.di.dfmdevelop.labservice.LabServiceApplication;
import com.uniba.di.dfmdevelop.labservice.paypal.PaypalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabServiceApplication.class)
public class PaymentServiceTest {

    @Autowired
    PaypalService paymentService;

    @Test
    public void creatingPayment() throws PayPalRESTException {

        Payment newPayment = paymentService.createPayment(12.50,
                                                    "EUR",
                                                    "PAYPAL",
                                                      "SALE",
                                                    null,
                                                    "http://localhost:8443/pay/cancel",
                                                    "http://localhost:8443/pay/success");

        assertTrue(newPayment != null);
    }

    /*@Test
    public void executionOfAPayment() throws PayPalRESTException {

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setPayerId("TEST");
        payerInfo.setBirthDate("25/12/2000");
        payerInfo.setEmail("lab_lecce@labservice.it");

        Payer payer = new Payer();
        payer.setPayerInfo(payerInfo);

        Payment newPayment = paymentService.createPayment(12.50,
                "EUR",
                "PAYPAL",
                "SALE",
                null,
                "http://localhost:8443/pay/cancel",
                "http://localhost:8443/pay/success");

        newPayment.setState("AUTHORIZED");

        Payment paymentExecute = paymentService.executePayment(newPayment.getId(), payerInfo.getPayerId());

        assertTrue(paymentExecute != null);
    }*/
}
