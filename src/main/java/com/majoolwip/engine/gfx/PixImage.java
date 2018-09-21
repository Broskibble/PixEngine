/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine.gfx;

import com.majoolwip.engine.Pix;
import com.majoolwip.engine.util.PixLogger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PixImage {
	private int[] pixels;
	private int width, height;

	public PixImage(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = image.getRGB(0, 0, width, height, null, 0, width);
		} catch (IOException e) {
			Pix.getLogger().error(e.getMessage());
			System.exit(1);
		}
	}

	public PixImage(int[] pixel, int width, int height) {
		this.pixels = pixel;
		this.width = width;
		this.height = height;
	}

	public void saveImage(String path, String format) throws IOException {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, width, height, pixels, 0, width);
		ImageIO.write(image, format, new File(path));
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
