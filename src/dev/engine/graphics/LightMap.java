package dev.engine.graphics;

import dev.engine.util.MathUtils;

public class LightMap extends Bitmap{
	
	public LightMap(int width, int height) {
		super(width, height);
	}
	
	public void generateLightMap(Light light) {
		// clear to ambient light
		this.clearScreen(255, 30, 30, 30);
		
		int radius = (int) light.getRadius();
		
		for(int j = -radius; j <= radius; j++) {
			for(int i = -radius; i <= radius; i++) {
				
				int index = (int)(i + light.getX()) + (int)(j + light.getY()) * this.getWidth();
				
				int oldPixel = this.getPixels()[index];
				float oldR = ((oldPixel >> 16) & 0xFF) / 255.0f;
				float oldG = ((oldPixel >> 8) & 0xFF) / 255.0f;
				float oldB = ((oldPixel >> 0) & 0xFF) / 255.0f;
				
				float lightR = (float) light.getR();
				float lightG = (float) light.getG();
				float lightB = (float) light.getB();
				
				float dist = MathUtils.distance(i + light.getX(), j + light.getY(), light.getX(), light.getY());
				
				float brightness = Math.max((1.0f / (dist * dist)) - light.getInvRadiusSqr(), 0.0f);
				
				// adding lightsources to lightmap and combining using additive blending
				float newR = ((oldR + lightR) * brightness * Light.DEFAULT_INTENSITY);
				float newG = ((oldG + lightG) * brightness * Light.DEFAULT_INTENSITY);
				float newB = ((oldB + lightB) * brightness * Light.DEFAULT_INTENSITY);
				
				// after adding up all the light, run values through tone-mapping function, color / (color + 1),
				// to reduce values back to the range 0.0 - 1.0 as the color approaches positive infinity
				int newR2 = (int)((newR / (newR + 1)) * 255.0f);
				int newG2 = (int)((newG / (newG + 1)) * 255.0f);
				int newB2 = (int)((newB / (newB + 1)) * 255.0f);
				
				// clamp color minimum to ambient lighting i.e. 30
				newR2 = Math.max(newR2, 30);
				newG2 = Math.max(newG2, 30);
				newB2 = Math.max(newB2, 30);
				
				this.drawPixel((int)(i + light.getX()), (int)(j + light.getY()), 255, newR2, newG2, newB2);
			}
		}
	}
}
