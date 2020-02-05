package vn.team.freechat.test.game;

import static vn.team.freechat.test.game.GameCommands.MOVE_SHIP;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;

import lombok.Setter;
import vn.team.freechat.handler.ChatClientRequestHandler;

@Setter
@EzyPrototype
@EzyArrayBinding(
		write = false,
		indexes = {"x", "y", "z"})
@EzyClientRequestListener(MOVE_SHIP)
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
