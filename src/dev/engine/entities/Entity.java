package dev.engine.entities;

import java.util.ArrayList;
import java.util.List;

import dev.engine.core.Input;
import dev.engine.graphics.RenderContext;
import dev.engine.spatial.AABB;

public class Entity implements Comparable<Entity> {

	private float x;
	private float y;
	private float renderLayer;
	private AABB aabb;
	private List<EntityComponent> components;
	
	public Entity(float minX, float minY, float maxX, float maxY) {
		components = new ArrayList<EntityComponent>();
		aabb = new AABB(minX, minY, maxX, maxY);
		x = aabb.getCenterX();
		y = aabb.getCenterY();
		renderLayer = 0.0f;
	}
	
	public void update(Input input, float delta) {
		for(int i = 0; i < components.size(); i++) {
			components.get(i).update(input, delta);
		}
	}
	
	public void render(RenderContext target) {
		for(int i = 0; i < components.size(); i++) {
			components.get(i).render(target);
		}
	}
	
	public Entity addComponent(EntityComponent component) {
		component.setEntity(this);
		component.onAdd();
		components.add(component);
		return this;
	}
	
	public void updateAABB() {
		float deltaX = x - aabb.getCenterX();
		float deltaY = y - aabb.getCenterY();
		
		float minX = aabb.getMinX() + deltaX;
		float minY = aabb.getMinY() + deltaY;
		float maxX = aabb.getMaxX() + deltaX;
		float maxY = aabb.getMaxY() + deltaY;
		
		aabb = new AABB(minX, minY, maxX, maxY);
	}
	
	@Override
	public int compareTo(Entity e) {
		
		final int BEFORE = -1;
		final int AFTER = 1;
		final int EQUAL = 0;
		
		if(this == e) {
			return EQUAL;
		}
		
		if(renderLayer < e.renderLayer) {
			return AFTER;
		}
		
		return BEFORE;
	}
	
	public boolean intersectAABB(Entity other) {
		return aabb.intersectAABB(other.getAABB());
	}
	
	public EntityComponent getComponent(String name) {
		for(int i = 0; i < components.size(); i++) {
			
			EntityComponent current = components.get(i);
			if(components.get(i).getName().equals(name)) {
				return current;
			}
		}
		
		return null;
	}
	
	public void setRenderLayer(float renderLayer) {
		this.renderLayer = renderLayer;
	}
	
	public AABB getAABB() {
		return aabb;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
