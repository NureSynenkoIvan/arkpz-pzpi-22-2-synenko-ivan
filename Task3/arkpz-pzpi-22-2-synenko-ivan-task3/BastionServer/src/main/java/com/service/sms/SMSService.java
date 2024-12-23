package com.service.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SMSService {
    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String VERIFICATION_PHONE_NUMBER = "+12184844316";

    public SMSService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }



    public void sendSMS(String phoneNumber, String sms) {
        validatePhoneNumber(phoneNumber);

        Message message = Message
                .creator(new com.twilio.type.PhoneNumber("+" + phoneNumber),
                        new com.twilio.type.PhoneNumber(VERIFICATION_PHONE_NUMBER),
                        sms)
                .create();

        System.out.println(message.getBody());
    }

    private static void validatePhoneNumber(String phoneNumber) {

    }
}
