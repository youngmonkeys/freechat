package com.tvd12.freechat.test.game;

import static com.tvd12.freechat.test.game.GameCommands.MOVE_SHIP;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.freechat.handler.ChatClientRequestHandler;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyArrayBinding(
		write = false,
		indexes = {"x", "y", "z"})
@EzyRequestListener(MOVE_SHIP)
public class GameMoveShipHandler 
		extends ChatClientRequestHandler
		implements EzyDataBinding {

	private double x;
	private double y;
	private double z;
	
	private VirtualWorld virtualWorld = VirtualWorld.getInstance();

	@Override
	protected void execute() throws EzyBadRequestException {
		String username = user.getName();
		VirtualRoom room = virtualWorld.getRoom(username);
		Ship ship = room.getShip();
		ship.move(new Vec3(x, y, z));
	}
	
}
