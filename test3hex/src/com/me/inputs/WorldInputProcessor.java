package com.me.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class WorldInputProcessor implements InputProcessor {
	private OrthographicCamera cam;
	private float dragOldX;
	private float dragOldY;

	public WorldInputProcessor(OrthographicCamera camParam)
	{
		dragOldX = -999999;
		dragOldY = -999999;
		this.cam = camParam;
	}
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		// Drag left
		if (screenX < dragOldX)
		{
            if (cam.position.x > 0 - 5)
            	cam.translate(-1, 0, 0);
		}
		// Drag right
		else if(screenX > dragOldX)
		{
			cam.translate(1, 0, 0);
		}
		// Drag up
		if (screenY < dragOldY)
		{

			cam.translate(0, 1, 0);
		}
		// Drag down
		else if(screenY > dragOldY)
		{

            if (cam.position.y >  0 - 5)
            	cam.translate(0, -1, 0);
		}
		if (dragOldX == -999999)
		{
			dragOldX = screenX;
			dragOldY = screenY;
		}
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
