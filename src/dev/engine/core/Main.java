package dev.engine.core;
import dev.engine.graphics.Display;
import dev.engine.graphics.RenderContext;

public class Main {

	public static void main(String[] args) {
		Display display = new Display(800, 600);
		RenderContext target = display.getRenderContext();
		
		while(true) {
			target.clear(255, 0, 0, 0);
			
			target.drawPixel(100, 100, 255, 255, 255, 255);
			
			display.render();
		}
	}
}
