package com.tvd12.freechat.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.Map;

@Getter
@Builder
@ToString
public class NotifyMessage {

    private String title;
    private String body;
    private String imageURL;
    private Map<String,String> data;
}
