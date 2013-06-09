package com.me.model;

// Need to be very light for ground tiles
public class Hex {
	
	// Position
	private float x;
	public float getPositionX()
	{
		return x;
	}
	private float y;
	public float getPositionY()
	{
		return y;
	}
	
	// Indice (row and col)
	private int rowArrayIndice;
	public int getRowIndice()
	{
		return (int)rowArrayIndice;
	}
	private int colArrayIndice;
	public int getColumnIndice()
	{
		return (int)colArrayIndice;
	}
	
	private Boolean isLoaded;
	public Boolean getIsLoaded()
	{
		return isLoaded;
	}
	public void setIsLoaded(Boolean isLoadedParam)
	{
		this.isLoaded = isLoadedParam;
	}
	
	// Init
	public Hex(float hexX, float hexY, float side, int rowIndice, int columnIndice, float hexWidth, float hexHeight)
	{
		this.x = hexX;
		this.y = hexY;
		this.rowArrayIndice = rowIndice;
		this.colArrayIndice = columnIndice;
		this.isLoaded = true;
	}
	
}