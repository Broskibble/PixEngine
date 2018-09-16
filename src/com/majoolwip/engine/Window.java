/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.util.PixSettings;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window implements WindowListener {

	private PixSettings settings;
	private Frame frame;
	private BufferedImage image;
	private BufferStrategy bufferStrategy;
	private Graphics graphics;
	private Insets insets;

	public Window(PixSettings settings) {
		this.settings = settings;
		image = new BufferedImage(settings.getWidth(), settings.getHeight(), BufferedImage.TYPE_INT_RGB);

		frame = new Frame(settings.getTitle());
		insets = frame.getInsets();
		frame.setSize((int)(settings.getWidth() * settings.getScale()) + insets.left + insets.right,
				      (int)(settings.getHeight() * settings.getScale()) + insets.top + insets.bottom);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.createBufferStrategy(2);
		bufferStrategy = frame.getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();

		frame.addWindowListener(this);
		frame.requestFocus();
	}

	public void update() {
		insets = frame.getInsets();
		if(frame.getWidth() != insets.left + insets.right + (int)(image.getWidth() * 2)) {
			frame.setSize(insets.left + insets.right + (int)(image.getWidth() * 2), frame.getHeight());
		}
		if(frame.getHeight() != insets.top + insets.bottom + (int)(image.getHeight() * 2)) {
			frame.setSize(frame.getWidth(), insets.top + insets.bottom + (int) (image.getHeight() * 2));
		}
		do {
			do {
				graphics = bufferStrategy.getDrawGraphics();
				graphics.drawImage(image, insets.left,
										  insets.top,
						                  (int)(settings.getWidth() * settings.getScale()),
						                  (int)(image.getHeight() * settings.getScale()),
						     null);
				graphics.dispose();
			} while (bufferStrategy.contentsRestored());
			bufferStrategy.show();
		} while (bufferStrategy.contentsLost());
	}

	public void dispose() {
		frame.setVisible(false);
		frame.dispose();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void windowOpened(WindowEvent windowEvent) {

	}

	@Override
	public void windowClosing(WindowEvent windowEvent) {
		Pix.stop();
	}

	@Override
	public void windowClosed(WindowEvent windowEvent) {

	}

	@Override
	public void windowIconified(WindowEvent windowEvent) {

	}

	@Override
	public void windowDeiconified(WindowEvent windowEvent) {

	}

	@Override
	public void windowActivated(WindowEvent windowEvent) {

	}

	@Override
	public void windowDeactivated(WindowEvent windowEvent) {

	}
}
