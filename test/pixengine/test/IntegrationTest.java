/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package pixengine.test;

import com.majoolwip.engine.Game;
import com.majoolwip.engine.Pix;
import com.majoolwip.engine.Renderer;
import com.majoolwip.engine.gfx.PixFont;
import com.majoolwip.engine.util.PixSettings;
import com.majoolwip.engine.util.State;
import org.junit.Test;

public class IntegrationTest {

	private class TestState extends State {

		@Override
		public void update(float dt) {
		}

		@Override
		public void render(Renderer r) {
		}
	}

	private class TestGame extends Game {

		@Override
		public void init() {
			this.setState(new TestState());
		}

		@Override
		public void dispose() {

		}
	}

	@Test
	public void fullRun() {
		Pix pix = new Pix(new TestGame(), new PixSettings());
		pix.start();
	}
}
