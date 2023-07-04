package com.ekene.paymentconfig.controller;

import com.ekene.paymentconfig.PaymentService;
import com.ekene.paymentconfig.dto.PaymentRequest;
import com.google.gson.Gson;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Flow;

///import static spark.Spark.get;

@RestController
@RequestMapping("api/payment")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @GetMapping("/pay")
    public Map<String, String> pay (PaymentRequest paymentRequest) throws StripeException {
        PaymentIntent intent = PaymentIntent.create(paymentService.
                paymentIntentCreateParams(paymentRequest));
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("client_secret", intent.getClientSecret());
        return stringMap;
    }



}
