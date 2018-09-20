/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.util.PixSettings;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private Window window;

	private final int NUM_KEYS = 256;
	private final int NUM_BUTTONS = 5;

	private final boolean[] keys = new boolean[NUM_KEYS];
	private final boolean[] keysLast = new boolean[NUM_KEYS];

	private final boolean[] buttons = new boolean[NUM_BUTTONS];
	private final boolean[] buttonsLast = new boolean[NUM_BUTTONS];

	private int mouseX, mouseY, prevMouseX, prevMouseY, mouseDX, mouseDY;
	private char typed = 0;
	private int wheel = 0, prevWheel = 0;

	public Input(Window window) {
		this.window = window;
		window.getFrame().addKeyListener(this);
		window.getFrame().addMouseListener(this);
		window.getFrame().addMouseMotionListener(this);
		window.getFrame().addMouseWheelListener(this);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
		typed = keyEvent.getKeyChar();
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() < NUM_KEYS) {
			keys[keyEvent.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() < NUM_KEYS) {
			keys[keyEvent.getKeyCode()] = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		if(mouseEvent.getButton() < NUM_BUTTONS) {
			buttons[mouseEvent.getButton()] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		if(mouseEvent.getButton() < NUM_BUTTONS) {
			buttons[mouseEvent.getButton()] = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		mouseMoved(mouseEvent);
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		int x = mouseEvent.getX();
		mouseX = (int) (((x - window.getInsets().left) /
				 (float)(window.getFrame().getWidth() - window.getInsets().left - window.getInsets().right)) *
					    (window.getImage().getWidth()));
		int y = mouseEvent.getY();
		mouseY = (int) (((y - window.getInsets().top) /
				 (float)(window.getFrame().getHeight() - window.getInsets().top - window.getInsets().bottom)) *
					    (window.getImage().getHeight()));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
		wheel = mouseWheelEvent.getWheelRotation();
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getMouseDX() {
		return mouseDX;
	}

	public int getMouseDY() {
		return mouseDY;
	}

	public char getTyped() {
		return typed;
	}
}
