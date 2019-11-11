package vn.team.freechat.test.game;

import static vn.team.freechat.constant.GameCommands.JOIN_ROOM;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;

import lombok.Setter;
import vn.team.freechat.handler.ChatClientRequestHandler;

@Setter
@EzyPrototype
@EzyClientRequestListener(JOIN_ROOM)
public class GameJoinRoomHandler 
		extends ChatClientRequestHandler {

	private VirtualWorld virtualWorld = VirtualWorld.getInstance();

	@Override
	protected void execute() throws EzyBadRequestException {
		String username = user.getName();
		VirtualRoom room = virtualWorld.getRoom(username);
		room.setResponseFactory(responseFactory);
		room.reset();
		response(room);
		logger.info("room has added: " + username);
	}
	
	private void response(VirtualRoom room) {
		responseFactory.newObjectResponse()
			.command(JOIN_ROOM)
			.session(session)
			.data(room)
			.execute();
	}
	
}
