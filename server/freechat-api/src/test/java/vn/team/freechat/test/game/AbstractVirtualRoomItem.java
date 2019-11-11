package vn.team.freechat.test.game;

import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyResettable;

import lombok.Getter;

@Getter
public abstract class AbstractVirtualRoomItem
		extends EzyLoggable
		implements VirtualRoomItem, EzyResettable {

	@EzyValue
	protected Object id = ID_GENTER.incrementAndGet();
	@EzyValue
	protected double width;
	@EzyValue
	protected double height;
	@EzyValue
	protected Vec3 position;
	@EzyValue
	protected boolean valid = true;
	
	private static final AtomicLong ID_GENTER = new AtomicLong();
	
	@Override
	public void destroy() {
		this.valid = false;
	}
	
	@Override
	public void reset() {
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("(")
				.append("id: ").append(id).append(", ")
				.append("position: ").append(position).append(", ")
				.append("valid: ").append(valid)
				.append(")")
				.toString();
	}
	
}
