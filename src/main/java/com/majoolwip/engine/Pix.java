/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.gfx.PixFont;
import com.majoolwip.engine.gfx.Pixel;
import com.majoolwip.engine.util.PixLogger;
import com.majoolwip.engine.util.PixSettings;
import com.majoolwip.engine.util.PixUtils;
import com.sun.management.GarbageCollectionNotificationInfo;


public class Pix {
	private static final PixLogger logger = new PixLogger();

	private static Game game;
	private static PixSettings settings;
	private static Window window;
	private static Renderer renderer;
	private static Input input;

	private static volatile boolean running = false;

	public Pix(Game game, PixSettings settings) {
		Pix.setGame(game);
		Pix.setSettings(settings);
	}

	public void start() {
		if (running)
			return;
		run();
	}

	public static void stop() {
		Pix.running = false;
	}

	private static void init() {
		window = new Window(settings);
		renderer = new Renderer(window);
		input = new Input(window);
		game.init();
		Pix.running = true;
	}

	private void run() {
		Pix.init();

		double firstTime;
		double lastTime = System.nanoTime() / 1e9d;
		double passedTime;
		double unprocessedTime = 0;
		double frameTime = 0;
		int frames = 0;
		int fps = 0;

		boolean render;


		while(Pix.isRunning()) {
			firstTime = System.nanoTime() / 1e9d;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			frameTime += passedTime;
			render = false;
			double cap = Pix.getSettings().isLockFPS() ? Pix.getSettings().getUpdateCap() : passedTime;
			while(unprocessedTime >= cap && unprocessedTime != 0) {
				unprocessedTime -= cap;
				getGame().getState().update((float)cap);
				render = true;
			}

			if(frameTime >= 1.0) {
				frameTime -= frameTime;
				fps = frames;
				frames = 0;
			}

			if(render) {
				renderer.clear();
				getGame().getState().render(renderer);
				if(Pix.getSettings().isDebug())
					renderDebug(fps);
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		cleanUp();
	}

	private static void cleanUp() {
		window.dispose();
	}

	// Temporary until a UI system is implemented.
	private void renderDebug(int fps) {
		renderer.setAlphaMod(0.5f);
		PixFont f = PixFont.STANDARD;
		renderer.draw2DString("FPS: " + fps, 0, 0, PixFont.LEFT);
		renderer.draw2DString("MouseX: " + input.getMouseX(),  0, f.getMaxHeight(), PixFont.LEFT);
		renderer.draw2DString("MouseY: " + input.getMouseY(), 0, f.getMaxHeight() * 2, PixFont.LEFT);

		int twothirds = (int) (Pix.getSettings().getWidth() * (3f / 4f));

		renderer.draw2DString("Memory Usage", twothirds, 0, PixFont.CENTER);
		double memRatio = (double)PixUtils.getUsedMemory() / PixUtils.getTotalMemory();
		int barWidth = (int) (Pix.getSettings().getWidth() / 3f);
		renderer.draw2DFillRect((int) (twothirds - barWidth / 2f), f.getMaxHeight(),
								 barWidth, f.getMaxHeight(), Pixel.GREEN);
		renderer.draw2DFillRect((int) (twothirds - barWidth / 2f), f.getMaxHeight(),
				(int) (barWidth * memRatio), f.getMaxHeight(), Pixel.RED);

		renderer.setColorOverlay(Pixel.RED);
		renderer.draw2DString("Used: " + PixUtils.getUsedMemory() / 1048576 + " Mib", twothirds, f.getMaxHeight() * 2, PixFont.CENTER);
		renderer.setColorOverlay(Pixel.GREEN);
		renderer.draw2DString("Free: " + (PixUtils.getTotalMemory() - PixUtils.getUsedMemory()) / 1048576  + " Mib", twothirds, f.getMaxHeight() * 3, PixFont.CENTER);
		renderer.setColorOverlay(Pixel.WHITE);
		renderer.draw2DString("Max: " + Runtime.getRuntime().maxMemory() / 1048576 + " Mib", twothirds, f.getMaxHeight() * 4, PixFont.CENTER);
		renderer.setAlphaMod(1f);
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

	public static Renderer getRenderer() {
		return renderer;
	}

	private static boolean isRunning() {
		return running;
	}

	public static PixLogger getLogger() {
		return logger;
	}

	public static Input getInput() {
		return input;
	}
}
