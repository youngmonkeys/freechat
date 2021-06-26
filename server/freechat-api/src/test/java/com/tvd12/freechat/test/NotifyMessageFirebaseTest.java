package com.tvd12.freechat.test;

import com.tvd12.freechat.entity.FirebaseClient;
import com.tvd12.freechat.entity.NotifyMessage;

import java.util.HashMap;
import java.util.Map;

public class NotifyMessageFirebaseTest {

    private static final String token = "AAAAlT06_8w:APA91bH7jM9TXt8zKSV4rcU70VYUM7LPGaSHE3CH_tj3fNLL-ZY3RDHflw3UWV0cQC5sSJOTsC3nv7Offry54-yu8PfT8msmDImCxcBJ6g76_68qMFB5-ks-WjIe3wf8SnSCMvpFZ17W";
    public static void main(String[] args) {
        FirebaseClient fclient = new FirebaseClient();

        NotifyMessage notifyMessage = new NotifyMessage();
        notifyMessage.setBody("this is body");
        notifyMessage.setTitle("this is title");
        notifyMessage.setImageURL("https://ibb.co/R703kxf");
        Map<String,String> bodyTest = new HashMap<>();
        bodyTest.put("test","body test");
        notifyMessage.setData(bodyTest);

        fclient.notify(token,notifyMessage);
    }
}
