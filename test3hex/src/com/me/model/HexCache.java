package com.me.model;

import com.badlogic.gdx.Gdx;

public class HexCache {
	
	// Start Position of group of sprites
	private int startRowIndice;
	public int getStartRowIndice()
	{
		return startRowIndice;
	}
	private int startColIndice;
	public int getStartcolIndice()
	{
		return startColIndice;
	}
	private int endRowIndice;
	public int getEndRowIndice()
	{
		return endRowIndice;
	}
	private int endColIndice;
	public int getEndColIndice()
	{
		return endColIndice;
	}
	
	// Size
	private int rows;
	public int getRows()
	{
		return rows;
	}
	private int cols;
	public int getCols()
	{
		return cols;
	}
	
	// SpriteCacheIndex
	private int spriteCacheIndex;
	public int getSpriteCacheIndex()
	{
		return spriteCacheIndex;
	}
	public void setSpriteCacheIndex(int spriteCacheIndexParam)
	{
		this.spriteCacheIndex = spriteCacheIndexParam;
	}
	public void disposeSpriteCacheIndex()
	{
		this.spriteCacheIndex = -1;
	}
	public Boolean isInCache()
	{
		if (spriteCacheIndex == -1)
		{
			return false;
		}
		return true;
	}
	
	public boolean isToRender;
	
	
	// Init
	public HexCache(int rowIndice, int columnIndice, int rowsParam, int colsParam)
	{
		this.startRowIndice = rowIndice;
		this.startColIndice	= columnIndice;
		this.rows = rowsParam;
		this.spriteCacheIndex = -1;
		this.cols = colsParam;
		this.endRowIndice = this.startRowIndice + this.rows;
		this.endColIndice = this.startColIndice + this.cols;
		this.isToRender = false;
		
    	Gdx.app.log("Init HexCache", "startRowIndice = " + String.valueOf(startRowIndice) + 
    			" endRowIndice = " + String.valueOf(endRowIndice) + 
    			" startColIndice = " + String.valueOf(startColIndice) +
    			" endColIndice = " + String.valueOf(endColIndice));

	}
	
}