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

	public void drawPixel(int x, int y, int a, int r, int g, int b) {
		int argb = toARGB(a, r, g, b);

		int index = (x + y * width);
		pixels[index] = argb;
	}

	protected int toARGB(int a, int r, int g, int b) {
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
}
