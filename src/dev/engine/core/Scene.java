package dev.engine.core;

import dev.engine.graphics.Bitmap;
import dev.engine.graphics.LightMap;
import dev.engine.graphics.RenderContext;

public class Scene {

	private Bitmap bitmap;
	
	public Scene() {
		bitmap = new Bitmap("/knight.png");
	}
	
	public void update(float delta) {
	}
	
	public void render(RenderContext target) {
		target.clearScreen(255, 0, 0, 0);
		
		target.fillCircle(300, 300, 150, 255, 255, 255, 255);
		target.fillCircle(500, 300, 150, 255, 255, 255, 255);
		target.drawImage(bitmap, -0.5f, -0.5f, 0.5f, 0.5f);
		
		target.generateLightMap();
		target.applyLightMap();
	}
}
