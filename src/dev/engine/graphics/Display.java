package dev.engine.graphics;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Display {
	
	private JFrame frame;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	private BufferedImage screenBuffer;
	private RenderContext target;
	private int[] pixels;

	public Display(int width, int height) {
		
		Dimension size = new Dimension(width, height);
		canvas = new Canvas();
		canvas.setPreferredSize(size);
		canvas.setMinimumSize(size);
		canvas.setMaximumSize(size);
		
		screenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) screenBuffer.getRaster().getDataBuffer()).getData();
		target = new RenderContext(width, height, pixels);
		
		frame = new JFrame("Bitmap Testing");
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(1);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public void render() {
		g.drawImage(screenBuffer, 0, 0, screenBuffer.getWidth(), screenBuffer.getHeight(), null);
		bs.show();
	}
	
	public RenderContext getRenderContext() {
		return target;
	}
}