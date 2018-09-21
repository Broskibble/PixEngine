/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine;

import com.majoolwip.engine.util.State;

public abstract class Game {
	private State state;

	public abstract void init();
	public abstract void dispose();

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
