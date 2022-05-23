package com.tvd12.freechat.common.entity;

import com.tvd12.ezyfox.io.EzyDates;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ChatEntity {
    private Date creationDate = new Date();
    private Date lastReadDate = new Date();
    private Date lastModifiedDate = new Date();
    private int day = EzyDates.formatAsInteger(new Date());
}
