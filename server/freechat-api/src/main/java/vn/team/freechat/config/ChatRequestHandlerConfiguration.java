package vn.team.freechat.config;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfiguration;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.command.EzyAppSetup;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.support.controller.EzyUserRequestAppPrototypeController;

@EzyConfiguration
public class ChatRequestHandlerConfiguration 
		extends EzyLoggable 
		implements EzyBeanConfig {

	private final EzyAppContext appContext;
	private final EzyBeanContext beanContext;

	@EzyAutoBind({ "appContext", "beanContext" })
	public ChatRequestHandlerConfiguration(
			EzyAppContext appContext, EzyBeanContext beanContext) {
		this.appContext = appContext;
		this.beanContext = beanContext;
	}

	@Override
	public void config() {
		addUserRequestController();
	}

	private void addUserRequestController() {
		appContext
			.get(EzyAppSetup.class)
			.setRequestController(newUserRequestController());
	}

	private EzyUserRequestAppPrototypeController newUserRequestController() {
		return EzyUserRequestAppPrototypeController.builder()
				.beanContext(beanContext)
				.build();
	}

}
