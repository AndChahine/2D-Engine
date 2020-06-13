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
import dev.engine.graphics.RenderContext;

public class Main {

	public static void main(String[] args) {
		Display display = new Display(800, 600);
		RenderContext target = display.getRenderContext();

		while (true) {
			target.clearScreen(255, 0, 0, 0);

			display.render();
		}
	}
}
