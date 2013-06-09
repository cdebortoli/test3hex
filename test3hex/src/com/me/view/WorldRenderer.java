package com.me.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.inputs.WorldInputProcessor;
import com.me.model.Hex;
import com.me.model.HexWorld;
import com.me.utils.HexMath;
import com.me.utils.HexOrientation;

public class WorldRenderer {

	private OrthographicCamera worldCam;
	private HexWorld world;
	private FPSLogger fpsLogger = new FPSLogger();
	private WorldInputProcessor inputProcessor;
	
	// Cam values
    float rotationSpeed = 0.5f;
    static final int VIEWPORT_WIDTH_UNITS  = 4;
    static final int VIEWPORT_HEIGHT_UNITS = 4;
    
    // Rander objects
    private TextureRegion hexTextureEven = new TextureRegion();
    private TextureRegion hexTextureOdd = new TextureRegion();
    private SpriteBatch spriteBatch;
	float colCalcul;
	float rowCalcul;
	float h;
	float r;
	int sprite_margin;
	
	public WorldRenderer(HexWorld worldParam)
	{
		this.world = worldParam;
		
		// Init
        spriteBatch = new SpriteBatch();

		// Load images
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/textures/textures.pack"));
        hexTextureEven = atlas.findRegion("hex_even");
        hexTextureOdd  = atlas.findRegion("hex_odd");

		h = HexMath.getH(world.getSide(), HexOrientation.POINT);
		r = HexMath.getR(world.getSide(), HexOrientation.POINT);
        colCalcul = 2 * r;
        rowCalcul = h + world.getSide();	
		sprite_margin = 4;
	}
	
	 public void render() {
		 	spriteBatch.setProjectionMatrix(worldCam.combined);
			// Handle input of the camera
			//handleInput();
			
			spriteBatch.begin();
			
			// World Rendering		
			float min_pixel_x = (worldCam.position.x - ((worldCam.viewportWidth * worldCam.zoom) / 2) - sprite_margin) ;
			float max_pixel_x = (worldCam.position.x + ((worldCam.viewportWidth * worldCam.zoom) / 2) + sprite_margin) ;
			float min_pixel_y = (worldCam.position.y - ((worldCam.viewportHeight * worldCam.zoom) / 2) - sprite_margin) ;
			float max_pixel_y = (worldCam.position.y + ((worldCam.viewportHeight * worldCam.zoom) / 2) + sprite_margin) ;
			
			int min_col = (int) (min_pixel_x / colCalcul);
			int max_col = (int) (max_pixel_x / colCalcul);
			int min_row = (int) (min_pixel_y / rowCalcul);
			int max_row = (int) (max_pixel_y / rowCalcul);
			
			for (int i = min_col; i < max_col; i++) {
				for (int j = min_row; j < max_row; j++) {

					if (j > 0 && j < world.getRows() && i > 0 && i < world.getCols())
						renderHex(world.getHex(j, i));
				}
			}

			
			spriteBatch.end();
			
			fpsLogger.log();
			worldCam.update();
	 }
	 
	 public void renderHex(Hex hex)
	 {
			if (hex.getRowIndice() % 2 == 0)
			{
				spriteBatch.draw(hexTextureEven, hex.getPositionX(), hex.getPositionY(), world.getHexWidth(), world.getHexHeight());
			}
			else
			{
				spriteBatch.draw(hexTextureOdd, hex.getPositionX(), hex.getPositionY(), world.getHexWidth(), world.getHexHeight());
			}
	 }
	 
	 public void resize(int width, int height)
	 {
	        float aspectRatio = (float) width / (float) height;
	        worldCam = new OrthographicCamera(VIEWPORT_WIDTH_UNITS * aspectRatio, VIEWPORT_HEIGHT_UNITS);
	        worldCam.translate((VIEWPORT_WIDTH_UNITS * aspectRatio)/2, VIEWPORT_HEIGHT_UNITS/2, 0);
			inputProcessor = new WorldInputProcessor(worldCam);
			Gdx.input.setInputProcessor(inputProcessor);
	 }
	 
	 public void dispose()
	 {
		 spriteBatch.dispose();
	 }
	 
//	  private void handleInput() {
//	        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
//	        	if(worldCam.zoom < 1000)
//	        		worldCam.zoom += 0.5;        
//	        	Gdx.app.log("ZOOM", String.valueOf(worldCam.zoom));
//	        }
//	        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
//	        	if(worldCam.zoom > 0.5)
//	        		worldCam.zoom -= 0.5;
//	        	Gdx.app.log("ZOOM", String.valueOf(worldCam.zoom));
//	        }
//	        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//	                if (worldCam.position.x > 0 - 5)
//	                	worldCam.translate(-1, 0, 0);
//	        }
//	        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//	                if (worldCam.position.x <  world.getWidth() + 5)
//	                	worldCam.translate(1, 0, 0);
//	        }
//	        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//	                if (worldCam.position.y >  0 - 5)
//	                	worldCam.translate(0, -1, 0);
//	        }
//	        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
//	                if (worldCam.position.y <  world.getHeight() + 5)
//	                	worldCam.translate(0, 1, 0);
//	        }
//	        if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
//	        	worldCam.rotate(-rotationSpeed, 0, 0, 1);
//	        }
//	        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
//	        	worldCam.rotate(rotationSpeed, 0, 0, 1);
//	        }
//	}
}
