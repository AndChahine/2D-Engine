package dev.engine.entities;

import dev.engine.core.Input;
import dev.engine.graphics.RenderContext;

public class EntityComponent {

	private Entity entity;
	private String name;
	
	public EntityComponent(String name) {
		this.name = name;
	}
	
	public void onAdd() {}
	public void update(Input input, float delta) {}
	public void render(RenderContext target) {}

	public String getName() {
		return name;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
