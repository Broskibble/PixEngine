/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.gfx.Image;
import com.majoolwip.engine.gfx.PixFont;

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

	public void drawString(String text, PixFont font, int offX, int offY, int color) {
		int unicode;
		int offset = 0;
		for (int i = 0; i < text.length(); i++) {
			unicode = text.codePointAt(i);
			drawImage(offX + offset, offY, font.getChar(unicode));
			offset += font.getChar(unicode).getWidth();
		}
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
		for(int j = y; j < y + image.getHeight(); j++) {
			for (int i = x; i < x + image.getWidth(); i++) {
				setPixel(i, j, image.getPixels()[(i - x) + (j - y) * image.getWidth()]);
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
