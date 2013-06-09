package com.me.view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.model.Hex;
import com.me.model.HexCache;
import com.me.model.HexWorld;

public class WorldCacheRenderer {

	private HexWorld world;
	private OrthographicCamera cam;
    private SpriteCache spriteCache;
    private ArrayList<HexCache> hexCaches = new ArrayList<HexCache>();
    private final static int UNIT_BY_CACHE = 32;
    
    private TextureRegion hexTextureEven = new TextureRegion();
    private TextureRegion hexTextureOdd = new TextureRegion();

    /*
     * Generate the HexCaches data
     */
    public WorldCacheRenderer(HexWorld hexWorld)
    {
    	this.world = hexWorld;
        spriteCache = new SpriteCache((int)(hexWorld.getRows() * hexWorld.getCols()), false);
        
        // Generate all hexCache
        int rowCacheCount = (int)world.getRows() / UNIT_BY_CACHE;
        int colCacheCount = (int)world.getCols() / UNIT_BY_CACHE;
        for (int row = 0; row < rowCacheCount; row++) {
			for (int col = 0; col < colCacheCount; col++) {
				HexCache hexCache = new HexCache(row * UNIT_BY_CACHE, col * UNIT_BY_CACHE, UNIT_BY_CACHE, UNIT_BY_CACHE);
				hexCaches.add(hexCache);
			}
		}
		// Load images
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/textures/textures.pack"));
        hexTextureEven = atlas.findRegion("hex_even");
        hexTextureOdd  = atlas.findRegion("hex_odd");
    }
    /*
     * Set camera
     */
    public void setCamera(OrthographicCamera camParam)
    {
    	this.cam = camParam;
    }
    
    /*
     * Create cache data
     */
    public void manageWorldCache(int camColIndex, int camRowIndex, int camCols, int camRows)
    {
    	// Each hexCache objects
		for (int cache_index = 0; cache_index < hexCaches.size(); cache_index++) {
			HexCache hexCache = this.hexCaches.get(cache_index);
			boolean releaseCache = true;
			
			// Each row to show
			outerloop: for (int row = camRowIndex; row < camRows; row++) {
				// Each col to show
				for (int col = camColIndex; col < camCols; col++) {

					if (row >= hexCache.getStartRowIndice() && row < hexCache.getEndRowIndice() && col >= hexCache.getStartcolIndice() && col < hexCache.getEndColIndice())
					{
						if (!hexCache.isInCache())
						{
							releaseCache = false;
							// Set cache
							this.hexCaches.get(cache_index).setSpriteCacheIndex(setCache(hexCache));
							this.hexCaches.get(cache_index).isToRender = true;
					        break outerloop;
						}
					}
					
				}
			}
			
			if (releaseCache)
			{
				//this.hexCaches.get(cache_index).disposeSpriteCacheIndex();
			}
		}
    }
    
    /*
     * Create one cache
     */
    public int setCache(HexCache hexCache)
    {
    	spriteCache.beginCache();
    	
    	for (int row = hexCache.getStartRowIndice(); row < hexCache.getEndRowIndice(); row++) {
			for (int col = hexCache.getStartcolIndice(); col < hexCache.getEndColIndice(); col++) {
				// Get hex
				Hex hex = world.getHex(row, col);
				// Add to cache with good pixel position
				addToCache(hex);
			}
		}
    		
		return spriteCache.endCache();
    	
    }
    
    /*
     * Add on hextiles to cache
     */
	public void addToCache(Hex hex)
	{
		if (hex.getRowIndice() % 2 == 0)
		{
			spriteCache.add(hexTextureEven, hex.getPositionX(), hex.getPositionY(), world.getHexWidth(), world.getHexHeight());
		}
		else
		{
			spriteCache.add(hexTextureOdd, hex.getPositionX(), hex.getPositionY(), world.getHexWidth(), world.getHexHeight());
		}
	}
	
	/*
	 * Render all caches
	 */
    public void render()
    {
    	spriteCache.setProjectionMatrix(cam.combined);
	    spriteCache.begin();  
    	for (HexCache hexCache : hexCaches) {
			if (hexCache.isToRender)
			{
				spriteCache.draw(hexCache.getSpriteCacheIndex());   
			}
		}
        spriteCache.end(); 
    }
    
    /*
     * Dispose
     */
    public void dispose()
    {
    	spriteCache.dispose();
    }
}
