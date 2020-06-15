package dev.engine.util;

public class MathUtils {

	public static float clamp(float val, float min, float max) {
		float v = val;
		
		if(v < min) {
			v = min;
		}else if(v > max) {
			v = max;
		}
		
		return v;
	}
	
	public static float distance(float x1, float y1, float x2, float y2) {
		float ac = Math.abs(y2 - y1);
		float cb = Math.abs(x2 - x1);
		
		return (float) Math.hypot(ac, cb);
	}
}
