package com.me.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.me.utils.*;

public class HexWorld {
	
	// Hexes
	private int[][]	groundHexes;
	private ArrayList<Hex> loadedHexes = new ArrayList<Hex>();
	//private Hex[][]	livingHexes;
	public ArrayList<Hex> getLoadedHexes()
	{
		return loadedHexes;		
	}
	public Hex getLoadedHex(int row, int col)
	{
		for (Hex hex : this.loadedHexes) {
			if (row == hex.getRowIndice() && col == hex.getColumnIndice())
			{
				return hex;
			}
		}
		return null;
	}
	
	public int[][]getGroundHexes()
	{
		return groundHexes;
	}
	public int getGroundHex(int row, int col)
	{
		return groundHexes[col][row];
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
		groundHexes = new int[cols][rows];
		// Hexes parameters
		this.side = hexSide;
		
		// World Size
		this.hexWidth = HexMath.getWidth(side, HexOrientation.POINT);
		this.hexHeight = HexMath.getHeight(side, HexOrientation.POINT);
		this.width = (cols * hexWidth) + hexWidth;
		this.height = rows * hexHeight;
	}
	
	public void tempGroundGeneration()
	{
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				groundHexes[col][row] = HexGroundType.type.PLAIN.ordinal();
			}
		}
	}
	
	
	/*
	 * NOTE : Finally, manageLoaded Hexes is not usefull. Clean loadHexes array and rewrite all is perfect for perf and for simplicity / fiability.
	 */
	// Note : Note to load to list all loaded hex in the two loop ?
	public void manageLoadedHexes(int minCol, int maxCol, int minRow, int maxRow)
	{
		// Hex Params
		final float h = HexMath.getH(side, HexOrientation.POINT);
		final float r = HexMath.getR(side, HexOrientation.POINT);

		final float hexWidth  = HexMath.getWidth(side, HexOrientation.POINT);
		final float hexHeight = HexMath.getHeight(side, HexOrientation.POINT);
		
		// Reset loaded Hex
		for (Hex hex : this.loadedHexes) {
			hex.setIsLoaded(false); // Check if that work
		}
		
		// Manage loaded Hex
		for (int col = minCol; col < maxCol; col++) {
			for (int row = minRow; row < maxRow; row++) {
				
				if (row > 0 && row < getRows() && col > 0 && col < getCols())
				{
					Hex hex = getLoadedHex(row, col);
					if (hex != null)
					{
						hex.setIsLoaded(true); // ?
					}
					else
					{
						// Calcul pixel position
						float pixelX;
						if (row % 2 == 0)
						{
							pixelX = col * 2 * r;
						}
						else
						{
							pixelX = (col * 2 * r) + r;
						}
						float pixelY = row * (h + side);
						Hex newHex = new Hex(pixelX, pixelY, side, row, col, hexWidth, hexHeight);
						this.loadedHexes.add(newHex);
					}
				}
			}
		}
		
		// Remove old Hex
		ArrayList<Integer> hexToRemove = new ArrayList<Integer>();
		
		for (Hex hex : this.loadedHexes) {
			if(!hex.getIsLoaded())
			{
				hexToRemove.add(this.loadedHexes.indexOf(hex));
			}
		}
		for (Integer hexIndex : hexToRemove) {
			this.loadedHexes.remove(hexIndex);
		}
	}
	
	public void manageLoadedHexes2(int minCol, int maxCol, int minRow, int maxRow, int previousMinCol, int previousMaxCol, int previousMinRow, int previousMaxRow)
	{
		// Hex Params
		final float h = HexMath.getH(side, HexOrientation.POINT);
		final float r = HexMath.getR(side, HexOrientation.POINT);
		
		// New tiles
		for (int col = minCol; col <= maxCol; col++) {
			for (int row = minRow; row <= maxRow; row++) {
				if (!(col >= previousMinCol && col <= previousMaxCol && row >= previousMinRow && row <= previousMaxRow)) // Check if we need to include max values
				{
					if (row > 0 && row < getRows() && col > 0 && col < getCols())
						createLoadedHex(col,row,r,h);
				}
			}
		}
		
		// Remove old tiles
//		for (int col = previousMinCol; col <= previousMaxCol; col++) {
//			for (int row = previousMinRow; row <= previousMaxRow; row++) {
//				if (!(col >= minCol && col <= maxCol && row >= minRow && row <= maxRow)) // Check if we need to include max values
//				{
//					Hex hex = getLoadedHex(row, col);
//					if (hex != null)
//						this.loadedHexes.remove(hex);
//				}
//			}
//		}
		ArrayList<Integer> hexToRemove = new ArrayList<Integer>();

		for (Hex hex : this.loadedHexes) {
			if (!(hex.getColumnIndice() >= minCol && hex.getColumnIndice() <= maxCol && hex.getRowIndice() >= minRow && hex.getRowIndice() <= maxRow)) // Check if we need to include max values
			{
				hexToRemove.add(this.loadedHexes.indexOf(hex));
			}
		}

		for (Integer hexIndex : hexToRemove) {
			this.loadedHexes.remove(hexIndex);
		}
		Gdx.app.log("COUNT ", String.valueOf(this.loadedHexes.size()));
	}
	
	public void generateLoadHexes(int minCol, int maxCol, int minRow, int maxRow)
	{
		this.loadedHexes.clear();
		// Hex Params
		final float h = HexMath.getH(side, HexOrientation.POINT);
		final float r = HexMath.getR(side, HexOrientation.POINT);
		
		// New tiles
		for (int col = minCol; col <= maxCol; col++) {
			for (int row = minRow; row <= maxRow; row++) {
				if (row > 0 && row < getRows() && col > 0 && col < getCols())
					createLoadedHex(col,row,r,h);
			}
		}
	}
	
	public void createLoadedHex(int col, int row, float r, float h)
	{
		// Calcul pixel position
		float pixelX;
		if (row % 2 == 0)
		{
			pixelX = col * 2 * r;
		}
		else
		{
			pixelX = (col * 2 * r) + r;
		}
		float pixelY = row * (h + side);
		Hex newHex = new Hex(pixelX, pixelY, side, row, col, hexWidth, hexHeight);
		this.loadedHexes.add(newHex);
	}

}
