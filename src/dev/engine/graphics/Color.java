package dev.engine.graphics;

public class Color {

	private float a;
	private float r;
	private float g;
	private float b;
	
	public Color(int argb) {
		this(((argb >> 24) & 0xFF), ((argb >> 16) & 0xFF), ((argb >> 8) & 0xFF), (argb & 0xFF));
	}
	
	public Color(int a, int r, int g, int b) {
		this(a / 255.0f, r / 255.0f, g / 255.0f, b / 255.0f);
	}
	
	public Color(float a, float r, float g, float b) {
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Combines the red, green, blue, and alpha values of a color into a single integer.
	 * 
	 * @param a The alpha value of the color in the range 0 - 255.
	 * @param r The red value of the color in the range 0 - 255.
	 * @param g The green value of the color in the range 0 - 255.
	 * @param b The blue value of the color in the range 0 - 255.
	 *  
	 * @return The combined red, green, blue, and alpha values.
	 */
	public static int toARGB(int a, int r, int g, int b) {
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
}
