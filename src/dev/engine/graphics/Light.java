package dev.engine.graphics;

public class Light {

	public static final float DEFAULT_INTENSITY = 1000.0f;
	
	private float x;
	private float y;
	private float radius;
	private float invRadiusSqr;
	
	private float r;
	private float g;
	private float b;
	
	public Light(float x, float y, float radius, float constant, float linear, float quadratic, float r, float g, float b) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.r = r;
		this.g = g;
		this.b = b;
		
		this.invRadiusSqr = 1.0f / (radius * radius);
	}
	
	public float getInvRadiusSqr() {
		return invRadiusSqr;
	}
	
	public float getR() {
		return r;
	}
	
	public float getG() {
		return g;
	}
	
	public float getB() {
		return b;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getRadius() {
		return radius;
	}
}
