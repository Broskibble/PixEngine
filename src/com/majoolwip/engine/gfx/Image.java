/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
	private int[] pixels;
	private int pWidth, pHeight;

	public Image(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			pWidth = image.getWidth();
			pHeight = image.getHeight();
			pixels = image.getRGB(0, 0, pWidth, pHeight, null, 0, pWidth);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public int getpWidth() {
		return pWidth;
	}

	public void setpWidth(int pWidth) {
		this.pWidth = pWidth;
	}

	public int getpHeight() {
		return pHeight;
	}

	public void setpHeight(int pHeight) {
		this.pHeight = pHeight;
	}
}
