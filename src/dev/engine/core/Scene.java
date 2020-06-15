package dev.engine.core;

import dev.engine.graphics.Bitmap;
import dev.engine.graphics.RenderContext;

public class Scene {

	private Bitmap bitmap;
	private float x = 400;
	private float y = 300;
	private float timer = 0;
	
	public Scene() {
		bitmap = new Bitmap("/knight.png");
	}
	
	public void update(float delta) {
		// testing light
		timer += delta;
		x += (float) Math.cos(timer) / 5;
		y += (float) Math.sin(timer) / 5;
	}
	
	public void render(RenderContext target) {
		target.clearScreen(255, 0, 0, 0);
		
		target.fillCircle(300, 300, 150, 255, 255, 255, 255);
		target.fillCircle(500, 300, 150, 255, 255, 255, 255);
		target.drawImage(bitmap, -0.5f, -0.5f, 0.5f, 0.5f);
		
		target.generateLightMap(x, 300, 100, 255, 255, 10, 10);
		
		target.applyLightMap();
	}
}
