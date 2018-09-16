/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package pixengine.test;

import com.majoolwip.engine.gfx.Image;
import com.majoolwip.engine.gfx.PixFont;
import com.majoolwip.engine.gfx.PixFontGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;


public class PixFontGeneratorTest {
	@Test
	public void genArial() {
		Image i = PixFontGenerator.genFontImage("ubuntu", 18, Font.PLAIN);
		PixFont font = new PixFont(i);
		Image image = font.getChar('a');
		Assert.assertNotEquals(0, image.getWidth());
	}
}
