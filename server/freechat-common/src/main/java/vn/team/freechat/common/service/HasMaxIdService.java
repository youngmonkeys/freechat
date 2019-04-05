package vn.team.freechat.common.service;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.database.service.EzyMaxIdService;

import lombok.Setter;

@Setter
public class HasMaxIdService {

	@EzyAutoBind
	protected EzyMaxIdService maxIdService;
	
	protected long newId(String key) {
		return maxIdService.incrementAndGet(key);
	}
	
}
