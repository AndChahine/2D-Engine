package dev.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

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
	
	public Bitmap(String fileName) {
		try {
			BufferedImage image = ImageIO.read(getClass().getResource(fileName));
			
			this.width = image.getWidth();
			this.height = image.getHeight();
			
			this.pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearScreen(int a, int r, int g, int b) {
		Arrays.fill(pixels, toARGB(a, r, g, b));
	}

	//TODO: implement z-depth for sorting alpha blending
	public void drawPixel(int x, int y, int a, int r, int g, int b) {
		
		// check if pixel is in bounds of bitmap
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}
		
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
	
	// does nearest filtering when drawing bitmap with render context
	public void copyNearest(Bitmap dest, int destX, int destY, float srcXFloat, float srcYFloat) {
		int srcX = (int)(srcXFloat * (getWidth() - 1));
		int srcY = (int)(srcYFloat * (getHeight() - 1));
		
		int srcIndex = (srcX + srcY * getWidth());
		
		// check if pixel is in bounds
		if(srcX < 0 || srcX >= width || srcY < 0 || srcY >= height) {
			return;
		}
		
		int a = (this.pixels[srcIndex] >> 24) & 0xFF;
		int r = (this.pixels[srcIndex] >> 16) & 0xFF;
		int g = (this.pixels[srcIndex] >> 8) & 0xFF;
		int b = (this.pixels[srcIndex] >> 0) & 0xFF;
		
		dest.drawPixel(destX, destY, a, r, g, b);
	}

	public int toARGB(int a, int r, int g, int b) {
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
