package dev.engine.util;

public class Utils {

	public static float clamp(float val, float min, float max) {
		float v = val;
		
		if(v < min) {
			v = min;
		}else if(v > max) {
			v = max;
		}
		
		return v;
	}
}
