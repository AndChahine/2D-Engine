package dev.engine.graphics;

import java.util.Arrays;

public class Bitmap {

	private int width;
	private int height;
	private int[] pixels;

	public Bitmap(int width, int height) {
		this(width, height, new int[width * height]);
	}

	public Bitmap(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	public void clearScreen(int a, int r, int g, int b) {
		Arrays.fill(pixels, toARGB(a, r, g, b));
	}

	//TODO: implement z-depth for sorting alpha blending
	public void drawPixel(int x, int y, int a, int r, int g, int b) {
		int index = (x + y * width);
		int argb;
		
		if(a < 255) {
			// normalize argb from 0.0 - 1.0
			float a1 = a / 255.0f;
			float r1 = r / 255.0f;
			float g1 = g / 255.0f;
			float b1 = b / 255.0f;
			
			float a2 = ((pixels[index] >> 24) & 0xFF) / 255.0f;
			float r2 = ((pixels[index] >> 16) & 0xFF) / 255.0f;
			float g2 = ((pixels[index] >> 8) & 0xFF) / 255.0f;
			float b2 = ((pixels[index] >> 0) & 0xFF) / 255.0f;
			
			// multiply components by their alpha
			r1 *= a1;
			g1 *= a1;
			b1 *= a1;
			
			r2 *= a2;
			g2 *= a2;
			b2 *= a2;
			
			// calculate alpha blending
			float newA = a1 + a2 * (1 - a1);
			float newR = r1 + r2 * (1 - a1);
			float newG = g1 + g2 * (1 - a1);
			float newB = b1 + b2 * (1 - a1);
			
			// convert back to range 0 - 255 and combine components into a single int
			newA *= 255;
			newR *= 255;
			newG *= 255;
			newB *= 255;
			
			argb = toARGB((int)newA, (int)newR, (int)newG, (int)newB);
		}else {
			argb = toARGB(a, r, g, b);
		}
		
		pixels[index] = argb;
	}

	protected int toARGB(int a, int r, int g, int b) {
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
}
