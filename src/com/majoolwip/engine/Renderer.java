/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.gfx.PixImage;
import com.majoolwip.engine.gfx.PixFont;
import com.majoolwip.engine.gfx.Pixel;

import java.awt.image.DataBufferInt;

public class Renderer {

	private PixFont font = PixFont.STANDARD;

	private int[] pixels;
	private int pWidth, pHeight;

	private float alphaMod = 1f;
	private int colorOverlay = Pixel.WHITE;
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

		float alpha = Pixel.getAlpha(value) - (1 - alphaMod);
		if(colorOverlay != Pixel.WHITE) {
			value = Pixel.overlayColor(value, colorOverlay);
		}

		if(alpha == 1) {
			pixels[x + y * pWidth] = value;
		} else if(alpha != 0) {
			pixels[x + y * pWidth] = Pixel.alphaBlend(pixels[x + y * pWidth],
													  value);
		}

	}

	public void drawString(String text, int offX, int offY, int justified) {
		int unicode;
		int offset = 0;
		if(justified == PixFont.RIGHT) {
			offset -= font.getStringWidth(text);
		} else if(justified == PixFont.CENTER) {
			offset -= font.getStringWidth(text) / 2;
		}
		for (int i = 0; i < text.length(); i++) {
			unicode = text.codePointAt(i);
			drawImage(offX + offset, offY, font.getChar(unicode));
			offset += font.getChar(unicode).getWidth();
		}
	}

	public void drawRect(int x, int y, int width, int height, int color) {
		for(int i = x; i < x + width; i++) {
			setPixel(i, y, color);
			setPixel(i, y + height - 1, color);
		}

		for(int i = y; i < y + height; i++) {
			setPixel(x, i, color);
			setPixel(x + width - 1, i, color);
		}
	}

	public void drawFillRect(int x, int y, int width, int height, int color) {
		for(int j = y ; j < y + height; j++) {
			for(int i = x; i < x + width; i++) {
				setPixel(i, j, color);
			}
		}
	}

	public void drawImage(int x, int y, PixImage pixImage) {
		for(int j = y; j < y + pixImage.getHeight(); j++) {
			for (int i = x; i < x + pixImage.getWidth(); i++) {
				setPixel(i, j, pixImage.getPixels()[(i - x) + (j - y) * pixImage.getWidth()]);
			}
		}
	}

	/**
	 * Sets the color that the screen is set to at the beginning of each frame.
	 * @param clearColor
	 */
	public void setClearColor(int clearColor) {
		this.clearColor = clearColor;
	}

	/**
	 * Sets the modfication to the alpha channel that is rendered.
	 * alphaMod == 1 is no change, alphaMod == 0 is fully transparent.
	 * @param alphaMod
	 */
	public void setAlphaMod(float alphaMod) {
		this.alphaMod = alphaMod;
	}

	public void setColorOverlay(int color) {
		this.colorOverlay = color;
	}
}
