package dev.engine.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import dev.engine.entities.Entity;
import dev.engine.entities.components.SpriteComponent;
import dev.engine.graphics.Bitmap;
import dev.engine.graphics.RenderContext;
import dev.engine.spatial.AABB;
import dev.engine.spatial.QuadTree;

public class Scene {
	
	private QuadTree tree;
	
	public Scene() {
		tree = new QuadTree(new AABB(-2, -2, 2, 2), 8);
		
		tree.add(new Entity(-0.5f, -0.5f, 0.5f, 0.5f).addComponent(new SpriteComponent(new Bitmap("/test.png"), 0.0f)));
	}
	
	public void update(Input input, float delta) {
		Set<Entity> entities = tree.queryRange(new AABB(-4, -4, 4, 4), new HashSet<Entity>());
		
		Iterator it = entities.iterator();
		while(it.hasNext()) {
			Entity current = (Entity)it.next();
			
			float startX = current.getX();
			float startY = current.getY();
			
			current.update(input, delta);
			
			if(startX != current.getX() || startY != current.getY()) {
				tree.remove(current);
				current.updateAABB();
				
				//handleCollisions(current);
				
				tree.add(current);
			}
		}
	}
	
	public void render(RenderContext target) {
		target.clearScreen(255, 0, 0, 0);
		
		Set<Entity> renderableEntities = tree.queryRange(target.getRenderArea(), new TreeSet<Entity>());
		
		Iterator it = renderableEntities.iterator();
		while(it.hasNext()) {
			Entity current = (Entity)it.next();
			current.render(target);
		}
		
		target.generateLightMap();
		target.applyLightMap();
	}
}
