package dev.engine.graphics;

import dev.engine.util.Utils;

public class RenderContext extends Bitmap {

	public RenderContext(int width, int height, int[] pixels) {
		super(width, height, pixels);
	}
	
	public void drawImage(Bitmap bitmap, float xStart, float yStart, float xEnd, float yEnd) {
		float aspect = (float)getWidth() / (float)getHeight();
		
		float scaledXStart = xStart / aspect;
		float scaledXEnd = xEnd / aspect;
		
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
		
		scaledXEnd = Utils.clamp(scaledXEnd, -1.0f, 1.0f);
		yEnd = Utils.clamp(yEnd, -1.0f, 1.0f);
		
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
