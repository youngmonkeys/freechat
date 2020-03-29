package com.tvd12.freechat.test.game;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.util.EzyEquals;
import com.tvd12.ezyfox.util.EzyHashCodes;

@EzyArrayBinding
public class Vec2 {

	public float x;
	public float y;
	
	public static final Vec2 ZERO = new Vec2();
	
	public Vec2() {
		this(0.0F, 0.0F);
	}
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void multiple(float a) {
		this.x *= a;
		this.y *= a;
	}
	
	public Vec2 multipleNew(float a) {
		return new Vec2(this.x * a, this.y * a);
	}
	
	@Override
	public boolean equals(Object obj) {
		return new EzyEquals<Vec2>()
				.function(t -> t.x)
				.function(t -> t.y)
				.isEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return new EzyHashCodes()
				.append(x, y)
				.toHashCode();
	}
}
