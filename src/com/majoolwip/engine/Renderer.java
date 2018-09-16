/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.gfx.Image;

import java.awt.image.DataBufferInt;

public class Renderer {

	private int[] pixels;
	private int pWidth, pHeight;

	private int clearColor = 0xff000000;

	public Renderer(Window window) {
		this.pWidth = window.getImage().getWidth();
		this.pHeight = window.getImage().getHeight();
		this.pixels = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
	}

	public void clearPixels() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = clearColor;
		}
	}

	public void setPixel(int x, int y, int value) {
		if(x < 0 || x >= pWidth || y < 0 || y >= pHeight)
			return;

		pixels[x + y * pWidth] = value;
	}

	public void drawRect(int x, int y, int width, int height, int color) {
		for(int i = x; i < width; i++) {
			setPixel(i, y, color);
			setPixel(i, y + height - 1, color);
		}

		for(int i = y; i < height; i++) {
			setPixel(x, i, color);
			setPixel(x + width - 1, i, color);
		}
	}

	public void drawFillRect(int x, int y, int width, int height, int color) {
		for(; y < height; y++) {
			for(; x < width; x++) {
				setPixel(x, y, color);
			}
		}
	}

	public void drawImage(int x, int y, Image image) {
		for(; y < image.getpHeight(); y++) {
			for (; x < image.getpWidth(); x++) {
				setPixel(x, y, image.getPixels()[x + y * image.getpWidth()]);
			}
		}
	}

	public int getClearColor() {
		return clearColor;
	}

	public void setClearColor(int clearColor) {
		this.clearColor = clearColor;
	}

}
