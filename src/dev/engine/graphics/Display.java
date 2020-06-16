package dev.engine.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import dev.engine.core.Input;

public class Display {

	private BufferStrategy bs;
	private Graphics g;

	private BufferedImage screenBuffer;
	private RenderContext target;
	
	private Input input;

	public Display(int width, int height) {

		Dimension size = new Dimension(width, height);
		Canvas canvas = new Canvas();
		canvas.setPreferredSize(size);
		canvas.setMinimumSize(size);
		canvas.setMaximumSize(size);
		
		input = new Input();
		canvas.addKeyListener(input);
		canvas.addMouseListener(input);
		canvas.addMouseMotionListener(input);
		canvas.addMouseWheelListener(input);

		screenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		int[] pixels = ((DataBufferInt) screenBuffer.getRaster().getDataBuffer()).getData();
		target = new RenderContext(width, height, pixels);

		JFrame frame = new JFrame("2D Engine");
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
	
	public Input getInput() {
		return input;
	}
}