package com.me.test3hex;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.me.model.HexWorld;
import com.me.view.WorldRenderer;

public class test3hex extends Game {

    // Model
    private HexWorld world;
    private WorldRenderer worldRenderer;


    static final int WORLD_WIDTH_UNITS  = 5000;
    static final int WORLD_HEIGHT_UNITS = 5000;
    static final float HEX_SIDE_UNIT	= (float) 0.57735026919; // To have a unit to 1 ( cos(30)/0.50 )
    
	@Override
	public void create() {
		
		world = new HexWorld(WORLD_WIDTH_UNITS, WORLD_HEIGHT_UNITS, HEX_SIDE_UNIT);
		worldRenderer = new WorldRenderer(world);
		world.generateHexes();
	}

	@Override
	public void dispose() {
		worldRenderer.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);


		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
}