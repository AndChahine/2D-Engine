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
		return (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	
	public static float saturate(float val) {
		return clamp(val, 0.0f, 1.0f);
	}
}
