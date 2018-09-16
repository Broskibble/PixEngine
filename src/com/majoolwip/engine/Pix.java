/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.util.PixSettings;

public class Pix implements Runnable {

	private static Game game;
	private static PixSettings settings;
	private static Window window;
	private static Renderer renderer;

	private static volatile boolean running = false;

	public Pix(Game game, PixSettings settings) {
		Pix.setGame(game);
		Pix.setSettings(settings);
	}

	public void start() {
		if (running)
			return;

		Thread thread = new Thread(this, "PIXENGINE");
		thread.start();
	}

	public static void stop() {
		setRunning(false);
	}

	private static void init() {
		window = new Window(settings);
		renderer = new Renderer(window);
		game.init();
	}

	@Override
	public void run() {
		Pix.init();
		Pix.setRunning(true);

		double firstTime;
		double lastTime = System.nanoTime() / 1e9f;
		double passedTime;
		double unprocessedTime = 0;
		boolean render;
		while(running) {
			firstTime = System.nanoTime() / 1e9f;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			render = false;
			while(unprocessedTime >= settings.getUpdateCap()) {
				unprocessedTime -= settings.getUpdateCap();
				game.getState().update((float)settings.getUpdateCap());
				render = true;
			}

			if(render) {
				renderer.clearPixels();
				game.getState().render(renderer);
				window.update();
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
			}
		}
		cleanUp();
	}

	private void cleanUp() {
		window.dispose();
	}

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game game) {
		Pix.game = game;
	}

	public static PixSettings getSettings() {
		return settings;
	}

	public static void setSettings(PixSettings settings) {
		Pix.settings = settings;
	}

	public static Window getWindow() {
		return window;
	}

	public static void setWindow(Window window) {
		Pix.window = window;
	}

	public static Renderer getRenderer() {
		return renderer;
	}

	public static void setRenderer(Renderer renderer) {
		Pix.renderer = renderer;
	}

	private static synchronized void setRunning(boolean value) {
		running = value;
	}
}
