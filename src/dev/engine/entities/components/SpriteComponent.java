package dev.engine.entities.components;

import dev.engine.core.Input;
import dev.engine.entities.EntityComponent;
import dev.engine.graphics.Bitmap;
import dev.engine.graphics.RenderContext;

public class SpriteComponent extends EntityComponent {

public static final String NAME = "SpriteComponent";
	
	private Bitmap[] frames;
	private float[] frameTimes;
	private int[] nextFrames;
	private int currentFrame;
	private float renderLayer;
	private float currentFrameTime;
	
	public SpriteComponent(Bitmap sprite, float renderLayer) {
		this(new Bitmap[] {sprite}, 0.0f, renderLayer);
	}
	
	public SpriteComponent(Bitmap[] frames, float frameTime, float renderLayer) {
		super(NAME);
		
		float[] frameTimes = new float[frames.length];
		int[] nextFrames = new int[frames.length];
		
		for(int i = 0; i < frames.length; i++) {
			frameTimes[i] = frameTime;
			nextFrames[i] = i + 1;
		}
		
		nextFrames[frames.length - 1] = 0;
		
		init(frames, frameTimes, nextFrames, renderLayer);
	}
	
	public SpriteComponent(Bitmap[] frames, float[] frameTimes, int[] nextFrames, float renderLayer) {
		super(NAME);
		init(frames, frameTimes, nextFrames, renderLayer);
	}
	
	private final void init(Bitmap[] frames, float[] frameTimes, int[] nextFrames, float renderLayer) {
		this.frames = frames;
		this.frameTimes = frameTimes;
		this.nextFrames = nextFrames;
		this.renderLayer = renderLayer;
		this.currentFrame = 0;
		this.currentFrameTime = 0.0f;
	}
	
	@Override
	public void onAdd() {
		getEntity().setRenderLayer(renderLayer);
	}
	
	@Override
	public void update(Input input, float delta) {
		currentFrameTime += delta;
		while(frameTimes[currentFrame] != 0 && currentFrameTime > frameTimes[currentFrame]) {
			currentFrameTime -= frameTimes[currentFrame];
			int nextFrame = nextFrames[currentFrame];
			currentFrame = nextFrame;
		}
	}

	@Override
	public void render(RenderContext target) {
		target.drawImage(frames[currentFrame], getEntity().getAABB().getMinX(), getEntity().getAABB().getMinY(), getEntity().getAABB().getMaxX(), getEntity().getAABB().getMaxY());
	}
}
