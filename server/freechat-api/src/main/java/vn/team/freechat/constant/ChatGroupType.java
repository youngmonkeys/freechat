package vn.team.freechat.constant;

import com.tvd12.ezyfox.constant.EzyConstant;
import com.tvd12.ezyfox.util.EzyEnums;

import lombok.Getter;

public enum ChatGroupType implements EzyConstant {

	TWO(1),
	MULTIPLE(2);
	
	@Getter
	private final int id;
	
	private ChatGroupType(int id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return toString();
	}
	
	public static ChatGroupType valueOf(int id) {
		return EzyEnums.valueOf(values(), id);
	}
}
