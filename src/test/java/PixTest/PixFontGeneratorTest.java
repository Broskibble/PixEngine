/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package PixTest;import com.majoolwip.engine.gfx.PixImage;
import com.majoolwip.engine.gfx.PixFont;
import com.majoolwip.engine.util.PixFontGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;


public class PixFontGeneratorTest {
	@Test
	public void genArial() {
		PixImage i = PixFontGenerator.genFontImage("ubuntu", 18, Font.PLAIN);
		PixFont font = new PixFont(i);
		PixImage pixImage = font.getChar('a');
		Assert.assertNotEquals(0, pixImage.getWidth());
	}
}
