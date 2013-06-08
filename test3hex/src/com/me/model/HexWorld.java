package com.me.model;

import com.badlogic.gdx.Gdx;
import com.me.utils.*;

public class HexWorld {
	
	// Hexes
	private Hex[][] hexes;
	public Hex[][] getHexes()
	{
		return hexes;		
	}
	public Hex getHex(int row, int col)
	{
		return hexes[row][col];
	}
	
	// Map width
	private	float width;
	public float getWidth()
	{
		return width;
	}
	
	// Height Map
	private	float height;
	public float getHeight()
	{
		return height;
	}

	// Hexes indices
	private int rows;
	public float getRows()
	{
		return rows;
	}
	
	private int cols;
	public float getCols()
	{
		return cols;
	}

	// Hex sizes
	private float side;
	public float getSide()
	{
		return side;
	}
	
	private float hexWidth;
	public float getHexWidth()
	{
		return hexWidth;
	}
	
	private float hexHeight;
	public float getHexHeight()
	{
		return hexHeight;
	}
	
	// Init
	public HexWorld(int worldRows, int worldCols, float hexSide)
	{
		// World Parameters
		this.rows = worldRows;
		this.cols = worldCols;
		hexes = new Hex[rows][cols];
		
		// Hexes parameters
		this.side = hexSide;
		
		// World Size
		this.hexWidth = HexMath.getWidth(side, HexOrientation.POINT);
		this.hexHeight = HexMath.getHeight(side, HexOrientation.POINT);
		this.width = (cols * hexWidth) + hexWidth;
		this.height = rows * hexHeight;
	}
	
	public void generateHexes()
	{
		// Hex Params
		final float h = HexMath.getH(side, HexOrientation.POINT);
		final float r = HexMath.getR(side, HexOrientation.POINT);

		final float hexWidth  = HexMath.getWidth(side, HexOrientation.POINT);
		final float hexHeight = HexMath.getHeight(side, HexOrientation.POINT);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				
				String type = new String();
				// Calcul pixel position
				float pixelX;
				if (row % 2 == 0)
				{
					pixelX = col * 2 * r;
					type = "Even";
				}
				else
				{
					pixelX = (col * 2 * r) + r;
					type = "odd";
				}
				float pixelY = row * (h + side);
				
				//Gdx.app.log(type + " Hex values : ", "row ="+ String.valueOf(row) + " Col =" + String.valueOf(col) + " PixelX =" + String.valueOf(pixelX)+ " PixelY =" + String.valueOf(pixelY));

				Hex newHex = new Hex(pixelX, pixelY, side, row, col, hexWidth, hexHeight);
				hexes[row][col] = newHex;
			}
		}
		
		// Procedural generation
	}
}
