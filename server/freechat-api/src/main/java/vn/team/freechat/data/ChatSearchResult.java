package vn.team.freechat.data;

import java.util.List;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@EzyObjectBinding
public class ChatSearchResult {
	
	@EzyValue("1")
	private List<ChatGroup> listGroup;
	
	@EzyValue("2")
	private String userName;
}
