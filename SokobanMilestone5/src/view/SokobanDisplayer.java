package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SokobanDisplayer extends Canvas 
{
	private char[][] levelData;
	private int cRow; //levelData.length
	private int cCol; //levelData[0].length
	private StringProperty wallFileName;
	private StringProperty boxFileName;
	private StringProperty targetFileName;
	private StringProperty floorFileName;
	private StringProperty playerLeftFileName;
	private StringProperty playerRightFileName;
	private StringProperty playerUpFileName;
	private StringProperty playerDownFileName;
	private String direction;
	
	public SokobanDisplayer() 
	{
		this.direction = "init";
		this.cRow = 0;
		this.cCol = 0;
		this.wallFileName = new SimpleStringProperty();
		this.boxFileName = new SimpleStringProperty();
		this.targetFileName = new SimpleStringProperty();	
		this.floorFileName = new SimpleStringProperty();
		this.playerLeftFileName = new SimpleStringProperty();
		this.playerRightFileName = new SimpleStringProperty();
		this.playerUpFileName = new SimpleStringProperty();
		this.playerDownFileName = new SimpleStringProperty();
	}
	
 	public void setDirection(String command)
 	{
 		String[]directionStr = command.split(" ");
 		if(directionStr.length < 2)
 			return;
 		this.direction = directionStr[1];
 	}
	
 	public String getDirection()
 	{
 		switch (this.direction) 
 		{
		case "init":
			return getPlayerRightFileName();
		case "left":
			return getPlayerLeftFileName();
		case "right":
			return getPlayerRightFileName();
		case "up":
			return getPlayerUpFileName();
		case "down":
			return getPlayerDownFileName();
		default:
			return getPlayerRightFileName();
		}
 	}
	
	public void redraw()
	{
		if(this.levelData != null)
		{
			double W = getWidth(); //The canvas width
			double H = getHeight(); //The canvas height
			double w = W / Math.max(this.cCol,this.cRow);
			double  h = H / Math.max(this.cCol, this.cRow);

			GraphicsContext gc = this.getGraphicsContext2D();
						
			Image box = null;
			Image wall = null;
			Image target = null;
			Image floor = null;
			Image playerDirection = null;
			
			try 
			{
				box = new Image(new FileInputStream(getBoxFileName()));
				wall = new Image(new FileInputStream(getWallFileName()));
				target = new Image(new FileInputStream(getTargetFileName()));
				floor = new Image(new FileInputStream(getFloorFileName()));	
				playerDirection = new Image(new FileInputStream(getDirection()));
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			
			gc.clearRect(0, 0, W, H);
						
			for(int i=0; i<cRow; i++)
			{
				for(int j=0; j<cCol; j++)
				{
					char c = levelData[i][j];
					switch (c) 
					{
						case 'A':
							gc.drawImage(playerDirection, j*w, i*h, w, h);
						break;
						case '@':
							gc.drawImage(box, j*w, i*h, w, h);
						break;
						case '#':
							gc.drawImage(wall, j*w, i*h, w, h);
						break;
						case 'o':
							gc.drawImage(target, j*w, i*h, w, h);
						break;
						case ' ':
							gc.drawImage(floor, j*w, i*h, w, h);
						break;
					default:
						gc.setFill(Color.BLUE);
						break;
					}
				}
			}
		}
	}

	public void initCanvas()
	{
		GraphicsContext pacbanGraphics = this.getGraphicsContext2D();
		Image pacban = null;
		try 
		{
			pacban = new Image(new FileInputStream("./resources/Images/openpac.jpg"));
			pacbanGraphics.drawImage(pacban, 180, 220, this.getWidth()-350, this.getHeight()-350);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setLevelData(char[][] arr) 
	{
		this.cRow = arr.length;
		this.cCol = arr[0].length;
		this.levelData = arr;
		redraw();
	}
		
	public char[][] getLevelData() 
	{
		return levelData;
	}
	
	public String getWallFileName() 
	{
		return this.wallFileName.get();
	}

	public String getBoxFileName() 
	{
		return this.boxFileName.get();
	}

	public String getTargetFileName() 
	{
		return this.targetFileName.get();
	}

	public String getFloorFileName()
	{
		return this.floorFileName.get();
	}

	public void setWallFileName(String wallFileName) 
	{
		this.wallFileName.set(wallFileName);
	}

	public void setBoxFileName(String boxFileName) 
	{
		this.boxFileName.set(boxFileName);
	}

	public void setTargetFileName(String targetFileName) 
	{
		this.targetFileName.set(targetFileName);
	}
	
	public void setFloorFileName(String floorFileName)
	{
		this.floorFileName.set(floorFileName);
	}
	
	public void setPlayerLeftFileName(String playerLeftFileName)
	{
		this.playerLeftFileName.set(playerLeftFileName);
	}
	
	public String getPlayerLeftFileName()
	{
		return this.playerLeftFileName.get();
	}
	
	public void setPlayerRightFileName(String playerRightFileName)
	{
		this.playerRightFileName.set(playerRightFileName);
	}
	
	public String getPlayerRightFileName()
	{
		return this.playerRightFileName.get();
	}
	
	public void setPlayerUpFileName(String playerUpFileName)
	{
		this.playerUpFileName.set(playerUpFileName);
	}
	
	public String getPlayerUpFileName()
	{
		return this.playerUpFileName.get();
	}
	
	public void setPlayerDownFileName(String playerDownFileName)
	{
		this.playerDownFileName.set(playerDownFileName);
	}
	
	public String getPlayerDownFileName()
	{
		return this.playerDownFileName.get();
	}
}
