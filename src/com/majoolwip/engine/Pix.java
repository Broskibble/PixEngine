/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.gfx.PixFont;
import com.majoolwip.engine.gfx.Pixel;
import com.majoolwip.engine.util.PixLogger;
import com.majoolwip.engine.util.PixSettings;
import com.majoolwip.engine.util.PixUtils;

import java.awt.*;
import java.lang.management.MemoryUsage;

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
		double lastTime = System.nanoTime() / 1e9f;
		double passedTime;
		double unprocessedTime = 0;
		double frameTime = 0;
		int frames = 0;
		int fps = 0;

		boolean render;
		while(Pix.isRunning()) {
			firstTime = System.nanoTime() / 1e9f;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			frameTime += passedTime;
			render = false;
			while(unprocessedTime >= settings.getUpdateCap()) {
				unprocessedTime -= settings.getUpdateCap();
				getGame().getState().update((float)settings.getUpdateCap());
				render = true;
			}

			if(frameTime >= 1.0) {
				frameTime -= frameTime;
				fps = frames;
				frames = 0;
			}

			if(render) {
				renderer.clearPixels();
				getGame().getState().render(renderer);
				if(Pix.getSettings().isDebug())
					renderDebug(fps);
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) { /* purposely left blank */ }
			}
		}
		cleanUp();
	}

	private static void cleanUp() {
		window.dispose();
	}

	private void renderDebug(int fps) {
		PixFont f = PixFont.STANDARD;
		renderer.drawString("FPS: " + fps, 0, f.getMaxHeight() * 0, PixFont.LEFT);
		renderer.drawString("MouseX: " + input.getMouseX(),  0, f.getMaxHeight() * 1, PixFont.LEFT);
		renderer.drawString("MouseY: " + input.getMouseY(), 0, f.getMaxHeight() * 2, PixFont.LEFT);

		renderer.drawString("Memory Usage", (int) (Pix.getSettings().getWidth() * (2f / 3f)), 0, PixFont.CENTER);
		double memRatio = (double)PixUtils.getUsedMemory() / PixUtils.getTotalMemory();
		int barWidth = (int) (Pix.getSettings().getWidth() / 3f);
		renderer.drawFillRect((int) (Pix.getSettings().getWidth() * (2f/3f) - barWidth / 2f), f.getMaxHeight(),
								 barWidth, 10, Pixel.GREEN);
		renderer.drawFillRect((int) (Pix.getSettings().getWidth() * (2f/3f) - barWidth / 2f), f.getMaxHeight(),
				(int) (barWidth * memRatio), 10, Pixel.RED);

		renderer.setPixel((int) (Pix.getSettings().getWidth() * (2f/3f)), 1, Pixel.YELLOW);
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
