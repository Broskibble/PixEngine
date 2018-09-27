/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine.gfx;

import com.majoolwip.engine.util.PixFontGenerator;

import java.awt.*;
import java.util.ArrayList;

public class PixFont {

	public static final PixFont STANDARD = new PixFont("src/main/res/fonts/sfont.png");

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int CENTER = 2;

	private PixImage[] charPixImages;
	private int height;
	public PixFont(String path) {
		this(new PixImage(path));
	}

	public PixFont(PixImage pixImage) {
		int start = 0;
		ArrayList<PixImage> tempPixImages = new ArrayList<>();
		for (int i = 0; i < pixImage.getWidth(); i++) {
			if (pixImage.getPixels()[i] == 0xff0000ff) {
				start = i;
			} else if (pixImage.getPixels()[i] == 0xffffff00) {
				int width = i - start;
				int[] p = new int[width * (pixImage.getHeight() - 1)];
				for (int y = 0; y < pixImage.getHeight() - 1; y++) {
					for (int x = start; x < start + width; x++) {
						p[(x - start) + y * width] = pixImage.getPixels()[x + (y + 1) * pixImage.getWidth()];
					}
				}
				tempPixImages.add(new PixImage(p, width, pixImage.getHeight() - 1));
			}
		}
		charPixImages = new PixImage[tempPixImages.size()];
		tempPixImages.toArray(charPixImages);
		height = getChar(0).getHeight();
	}

	public PixImage getChar(int unicode) {
		if (unicode > charPixImages.length || unicode < 0) {
			return charPixImages[0];
		} else {
			return charPixImages[unicode];
		}
	}

	public int getStringWidth(String text) {
		int res = 0;
		for(int i = 0; i < text.length(); i++) {
			res += getChar(text.codePointAt(i)).getWidth();
		}
		return res;
	}

	public int getMaxHeight() {
		return height;
	}
}
