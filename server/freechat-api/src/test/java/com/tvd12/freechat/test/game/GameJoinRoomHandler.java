package com.tvd12.freechat.test.game;

import static com.tvd12.freechat.test.game.GameCommands.JOIN_ROOM;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.handler.ChatClientRequestHandler;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyRequestListener(JOIN_ROOM)
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
