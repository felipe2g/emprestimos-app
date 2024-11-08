package com.felipe2g.notificacao.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfiguration {

    @Value(value = "${twilio.account_sid}")
    private String accountSID;

    @Value(value = "${twilio.auth_token}")
    private String accountAuthToken;

    public void sendSMS(String to, String message) {
        Twilio.init(accountSID, accountAuthToken);

        String from = "+17029797401";

        Message.creator(new PhoneNumber(to), new PhoneNumber(from), message).create();
    }
}
