package com.tvd12.freechat.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class NotifyMessage {

    private String title;
    private String body;
    private String imageURL;
    private Map<String,String> data;

}
