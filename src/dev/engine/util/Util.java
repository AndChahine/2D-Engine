package dev.engine.util;

public class Util {

	public static float clamp(float val, float min, float max) {
		if(val < min) {
			val = min;
		}else if(val > max) {
			val = max;
		}
		
		return val;
	}
}
