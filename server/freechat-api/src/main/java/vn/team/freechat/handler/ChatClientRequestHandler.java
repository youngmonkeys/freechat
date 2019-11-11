package vn.team.freechat.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.function.EzyHandler;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyAppContextAware;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.entity.EzySessionAware;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.entity.EzyUserAware;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;

import lombok.Setter;

@Setter
public abstract class ChatClientRequestHandler
		extends EzyLoggable
		implements 
			EzyHandler,
			EzyAppContextAware, 
			EzySessionAware, 
			EzyUserAware {

	protected EzyUser user;
	protected EzySession session;
	protected EzyAppContext appContext;
	
	@EzyAutoBind("appResponseFactory")
	protected EzyResponseFactory responseFactory;
	
	@Override
	public final void handle() {
		preExecute();
		execute();
		postExecute();
	}
	
	protected void preExecute() {} 
	
	protected abstract void execute() throws EzyBadRequestException;
	
	protected void postExecute() {}
	
}
