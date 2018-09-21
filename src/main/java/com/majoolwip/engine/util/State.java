/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine.util;

import com.majoolwip.engine.Renderer;

public abstract class State {
	public abstract void update(float dt);
	public abstract void render(Renderer r);
}
