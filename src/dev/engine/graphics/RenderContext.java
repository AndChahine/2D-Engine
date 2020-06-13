package dev.engine.graphics;

public class RenderContext extends Bitmap {

	public RenderContext(int width, int height, int[] pixels) {
		super(width, height, pixels);
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
	
	public void drawCircle(int x, int y, int radius, int a, int r, int b, int g) {
		double i, angle, x1, y1;
		
		for(i = 0; i < 360; i += 0.1) {
			angle = i;
			x1 = radius * Math.cos(angle * Math.PI / 180.0);
			y1 = radius * Math.sin(angle * Math.PI / 180.0);
			
			drawPixel((int)(x + x1), (int)(y + y1), a, r, g, b);
		}
	}
	
	public void fillCircle(int x, int y, int radius, int a, int r, int b, int g) {		
		for(int i = -radius; i < radius; i++) {
			int height = (int)Math.sqrt(radius * radius - i * i);
			
			for(int j = -height; j < height; j++) {
				drawPixel(i + x, j + y, a, r, g, b);
			}
		}
	}
}
