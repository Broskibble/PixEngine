/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine.gfx;

import java.awt.*;
import java.util.ArrayList;

public class PixFont {
	public static final PixFont STANDARD = new PixFont(PixFontGenerator.genFontImage("ubuntu", 18, Font.PLAIN));

	private Image[] charImages;

	public PixFont(String path) {
		this(new Image(path));
	}

	public PixFont(Image image) {
		int start = 0;
		ArrayList<Image> tempImages = new ArrayList<>();
		for (int i = 0; i < image.getWidth(); i++) {
			if (image.getPixels()[i] == 0xff0000ff) {
				start = i;
			} else if (image.getPixels()[i] == 0xffffff00) {
				int width = i - start;
				int[] p = new int[width * (image.getHeight() - 1)];
				for (int y = 0; y < image.getHeight() - 1; y++) {
					for (int x = start; x < start + width; x++) {
						p[(x - start) + y * width] = image.getPixels()[x + (y + 1) * image.getWidth()];
					}
				}
				tempImages.add(new Image(p, width, image.getHeight() - 1));
			}
		}
		charImages = new Image[tempImages.size()];
		tempImages.toArray(charImages);
	}

	public Image getChar(int unicode) {
		if (unicode > charImages.length || unicode < 0) {
			return charImages[0];
		} else {
			return charImages[unicode];
		}
	}
}
