package com.tvd12.freechat.common.data;

import java.io.Serializable;
import java.util.Date;

import com.tvd12.ezyfox.io.EzyDates;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatData implements Serializable {
	private static final long serialVersionUID = -1053536008550958384L;

	private Date creationDate = new Date();
	private Date lastReadDate = new Date();
	private Date lastModifiedDate = new Date();
	private int day = EzyDates.formatAsInteger(new Date());
	
}
