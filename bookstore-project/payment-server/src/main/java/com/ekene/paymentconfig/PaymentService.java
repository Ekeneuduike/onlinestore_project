package com.ekene.paymentconfig;

import com.ekene.paymentconfig.dto.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    // Set your secret key. Remember to switch to your live secret key in production.
// See your keys here: https://dashboard.stripe.com/apikeys
    //Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";
    //Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";
    public PaymentIntentCreateParams paymentIntentCreateParams(PaymentRequest paymentRequest){
        Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";
        return  new PaymentIntentCreateParams.Builder()
                .setAmount(100*100L)
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();
    }
}
