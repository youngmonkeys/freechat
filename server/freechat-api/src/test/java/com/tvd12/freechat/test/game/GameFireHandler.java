package com.tvd12.freechat.test.game;

import static com.tvd12.freechat.test.game.GameCommands.FIRE;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.handler.ChatClientRequestHandler;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyRequestListener(FIRE)
public class GameFireHandler 
		extends ChatClientRequestHandler {

	private VirtualWorld virtualWorld = VirtualWorld.getInstance();

	@Override
	protected void execute() throws EzyBadRequestException {
		String username = user.getName();
		VirtualRoom room = virtualWorld.getRoom(username);
		Bullet bullet = room.addBullet();
		response(bullet);
	}
	
	private void response(Bullet bullet) {
		responseFactory.newObjectResponse()
			.command(FIRE)
			.session(session)
			.data(bullet)
			.execute();
	}
	
}
