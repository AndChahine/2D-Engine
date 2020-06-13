/*
 * Copyright 2020 Andrew Chahine
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.engine.core;

import dev.engine.graphics.Display;

public class Main {

	public static void main(String[] args) {
		Display display = new Display(800, 600);
		Scene scene = new Scene();
		
		int frames = 0;
		double unprocessedTime = 0.0;
		double frameCounterTime = 0;
		double frameTime = 1.0 / 1000.0;
		long previousTime = System.nanoTime();
		String fpsString = "0 ms per frame (0 fps)";
		
		//TODO: implement linear interpolated rendering
		while (true) {
			boolean render = false;
			
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			
			unprocessedTime += passedTime / 1000000000.0;
			frameCounterTime += passedTime / 1000000000.0;
			
			if(frameCounterTime >= 1.0) {
				fpsString = (1000.0 / frames) + " ms per frame (" + frames + " fps)";
				
				System.out.println(fpsString);
				
				frames = 0;
				frameCounterTime = 0.0;
			}
			
			while(unprocessedTime > frameTime) {
				render = true;
				
				// update game
				scene.update((float)frameTime);
				
				unprocessedTime -= frameTime;
			}
			
			if(render) {
				frames++;
				// render game
				scene.render(display.getRenderContext());
				display.render();
			}else {
				try {
					Thread.sleep(1);
				}catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
