package dev.engine.graphics;

import dev.engine.util.MathUtils;

public class RenderContext extends Bitmap {
	
	//TODO: put drawing functions from here to bitmap
	//      to make drawing primitives to bitmaps easier
	
	//TODO: abstract lighting
	
	private Bitmap lightMap;
	private float intensity = 1.0f;
	
	public RenderContext(int width, int height, int[] pixels) {
		super(width, height, pixels);
		
		lightMap = new Bitmap(width, height);
	}
	
	public void applyLightMap() {
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				int index = x + y * getWidth();
				
				int pixel = getPixels()[index];
				int light = lightMap.getPixels()[index];
				
				float oldR = ((pixel >> 16) & 0xFF) / 255.0f;
				float oldG = ((pixel >> 8) & 0xFF) / 255.0f;
				float oldB = ((pixel >> 0) & 0xFF) / 255.0f;
				
				float newR = ((light >> 16) & 0xFF) / 255.0f;
				float newG = ((light >> 8) & 0xFF) / 255.0f;
				float newB = ((light >> 0) & 0xFF) / 255.0f;
				
				// combining light source color with unlit pixel color using multiplicative blending
				int r1 = (int) ((oldR * newR) * 255);
				int g1 = (int) ((oldG * newG) * 255);
				int b1 = (int) ((oldB * newB) * 255);
				
				drawPixel(x, y, 255, r1, g1, b1);
			}
		}
	}
	
	//TODO: refactor and optimize loop
	//TODO: add attenuation to lighting
	public void generateLightMap(float xCenter, float yCenter, float radius, int a, int r, int g, int b) {
		lightMap.clearScreen(255, 30, 30, 30);
		
		for(int j = (int) -radius; j < radius; j++) {
			
			int height = (int)Math.sqrt(radius * radius - j * j);
			for(int i = -height; i < height; i++) {
				
				//TODO: fix bounds checking
				if(i + xCenter < 0 || i + xCenter >= lightMap.getWidth() || j + yCenter < 0 || j + yCenter >= lightMap.getHeight()) {
					continue;
				}
				
				int index = (int) ((i + xCenter) + (j + yCenter) * lightMap.getWidth());
				
				int oldPixel = lightMap.getPixels()[index];
				float oldR = ((oldPixel >> 16) & 0xFF) / 255.0f;
				float oldG = ((oldPixel >> 8) & 0xFF) / 255.0f;
				float oldB = ((oldPixel >> 0) & 0xFF) / 255.0f;
				
				float lightR = (float) r / 255.0f;
				float lightG = (float) g / 255.0f;
				float lightB = (float) b / 255.0f;
				
				// adding lightsources to lightmap and combining using additive blending
				float newR = ((oldR + lightR) * intensity);
				float newG = ((oldG + lightG) * intensity);
				float newB = ((oldB + lightB) * intensity);
				
				// after adding up all the light, run values through tone-mapping function, color / (color + 1)
				// to reduce values back to the range 0.0 - 1.0 as the color approaches positive infinity
				int newR2 = (int)((newR / (newR + 1)) * 255.0f);
				int newG2 = (int)((newG / (newG + 1)) * 255.0f);
				int newB2 = (int)((newB / (newB + 1)) * 255.0f);
				
				lightMap.drawPixel((int)(i + xCenter), (int)(j + yCenter), 255, newR2, newG2, newB2);
			}
		}
	}
	
	public void drawImage(Bitmap bitmap, float xStart, float yStart, float xEnd, float yEnd) {
		float aspect = (float)getWidth() / (float)getHeight();
		
		float scaledXStart = xStart / aspect;
		float scaledXEnd = xEnd / aspect;
		yStart /= aspect;
		yEnd /= aspect;
		
		float halfWidth = getWidth() / 2.0f;
		float halfHeight = getHeight() / 2.0f;
		
		float imageXStart = 0.0f;
		float imageYStart = 0.0f;
		float imageYStep = 1.0f / (((yEnd * halfHeight) + halfHeight) - ((yStart * halfHeight) + halfHeight));
		float imageXStep = 1.0f / (((scaledXEnd * halfWidth) + halfWidth) - ((scaledXStart * halfWidth) + halfWidth));
		
		if(xStart < -1.0f) {
			imageXStart = -((scaledXStart + 1.0f) / (scaledXEnd - scaledXStart));
			scaledXStart = -1.0f;
		}
		if(xStart > 1.0f) {
			imageXStart = -((scaledXStart + 1.0f) / (scaledXEnd - scaledXStart));
			scaledXStart = 1.0f;
		}
		if(yStart < -1.0f) {
			imageYStart = -((yStart + 1.0f) / (yEnd - yStart));
			yStart = -1.0f;
		}
		if(yStart > 1.0f) {
			imageYStart = -((yStart + 1.0f) / (yEnd - yStart));
			yStart = 1.0f;
		}
		
		scaledXEnd = MathUtils.clamp(scaledXEnd, -1.0f, 1.0f);
		yEnd = MathUtils.clamp(yEnd, -1.0f, 1.0f);
		
		scaledXStart = (scaledXStart * halfWidth) + halfWidth;
		yStart = (yStart * halfHeight) + halfHeight;
		scaledXEnd = (scaledXEnd * halfWidth) + halfWidth;
		yEnd = (yEnd * halfHeight) + halfHeight;
		
		float srcY = imageYStart;
		for(int j = (int) yStart; j < yEnd; j++) {
			float srcX = imageXStart;
			for(int i = (int) scaledXStart; i < scaledXEnd; i++) {
				bitmap.copyNearest(this, i, j, srcX, srcY);
				srcX += imageXStep;
			}
			srcY += imageYStep;
		}
	}
	
	public void drawLine(float xStart, float yStart, float xEnd, float yEnd, int a, int r, int g, int b) {
		float dx = Math.abs(xEnd - xStart);
		float sx = xStart < xEnd ? 1 : -1;
		float dy = -Math.abs(yEnd - yStart);
		float sy = yStart < yEnd ? 1 : -1;
		float err = dx + dy;
		
		while(true) {
			drawPixel((int)xStart, (int)yStart, a, r, g, b);
			
			if(xStart == xEnd && yStart == yEnd) {
				break;
			}
			
			float e2 = 2 * err;
			
			if(e2 >= dy) {
				err += dy;
				xStart += sx;
			}
			
			if(e2 <= dx) {
				err += dx;
				yStart += sy;
			}
		}
	}
	
	public void drawCircle(int xCenter, int yCenter, int radius, int a, int r, int g, int b) {
		double i;
		double angle; 
		double x1; 
		double y1;
		
		for(i = 0; i < 360; i += 0.1) {
			angle = i;
			x1 = radius * Math.cos(angle * Math.PI / 180.0);
			y1 = radius * Math.sin(angle * Math.PI / 180.0);
			
			drawPixel((int)(xCenter + x1), (int)(yCenter + y1), a, r, g, b);
		}
	}
	
	public void fillCircle(int xCenter, int yCenter, int radius, int a, int r, int g, int b) {		
		for(int j = -radius; j < radius; j++) {
			int height = (int)Math.sqrt(radius * radius - j * j);
			
			for(int i = -height; i < height; i++) {
				drawPixel(i + xCenter, j + yCenter, a, r, g, b);
			}
		}
	}
}
