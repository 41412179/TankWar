package cn.edu.snnu.client;
/**
 * 
 * @author Haodong Guo
 *
 */
public class Map {
	int x;
	int y;
	int width;
	int height;
	WallType wallType;
	
	private static Map[][] maps = {
		{
			new Map(0, 100, 1, 6, WallType.ICE), new Map(0, 400, 2, 2, WallType.STEEL),
			new Map(0, 450, 1, 5, WallType.FOREST), new Map(50, 50, 5, 1, WallType.ICE),
			new Map(50, 100, 2, 10, WallType.BRICK), new Map(50, 350, 5, 1, WallType.ICE),
			new Map(50, 475, 2, 7, WallType.BRICK), new Map(50, 650, 2, 1, WallType.FOREST),
			new Map(100, 300, 4, 2, WallType.BRICK), new Map(100, 400, 2, 1, WallType.STEEL),
			new Map(150, 400, 2, 1, WallType.BRICK), new Map(150, 475, 2, 7, WallType.BRICK),
			new Map(200, 550, 2, 2, WallType.STEEL), new Map(250, 100, 2, 6, WallType.BRICK),
			new Map(250, 425, 2, 6, WallType.BRICK), new Map(275, 625, 4, 1, WallType.BRICK),
			new Map(275, 650, 1, 2, WallType.BRICK), new Map(300, 650, 1, 1, WallType.FORT),
			new Map(350, 650, 1, 2, WallType.BRICK), new Map(300, 200, 2, 2, WallType.BRICK),
			new Map(300, 350, 2, 2, WallType.STEEL), new Map(300, 450, 2, 2, WallType.BRICK),
			new Map(350, 50, 5, 1, WallType.ICE), new Map(350, 100, 2, 10, WallType.BRICK),
			new Map(350, 350, 6, 1, WallType.ICE), new Map(350, 425, 2, 6, WallType.BRICK),
			new Map(400, 550, 2, 2, WallType.STEEL), new Map(450, 100, 6, 2, WallType.BRICK),
			new Map(450, 400, 2, 1, WallType.BRICK), new Map(450, 475, 2, 7, WallType.BRICK),
			new Map(500, 400, 2, 1, WallType.STEEL), new Map(500, 650, 2, 1, WallType.FOREST),
			new Map(550, 150, 2, 8, WallType.BRICK), new Map(550, 475, 2, 7, WallType.BRICK),
			new Map(600, 100, 1, 6, WallType.ICE), new Map(600, 400, 2, 2, WallType.STEEL),
			new Map(600, 450, 1, 4, WallType.FOREST)
		},
		{
			new Map(0, 100, 1, 6, WallType.ICE), new Map(0, 400, 2, 2, WallType.STEEL),
			new Map(0, 450, 1, 5, WallType.FOREST), new Map(50, 50, 5, 1, WallType.ICE),
			new Map(50, 100, 2, 10, WallType.BRICK), new Map(50, 350, 5, 1, WallType.ICE),
			new Map(50, 475, 2, 7, WallType.BRICK), new Map(50, 650, 2, 1, WallType.FOREST),
			new Map(100, 400, 2, 1, WallType.STEEL), new Map(150, 100, 2, 6, WallType.BRICK),
			new Map(150, 300, 6, 2, WallType.BRICK), new Map(150, 400, 2, 1, WallType.BRICK),
			new Map(150, 475, 2, 7, WallType.BRICK), new Map(200, 100, 4, 2, WallType.BRICK),
			new Map(200, 200, 2, 2, WallType.BRICK), new Map(200, 550, 2, 2, WallType.STEEL),
			new Map(250, 150, 2, 6, WallType.BRICK), new Map(250, 425, 2, 6, WallType.BRICK),
			new Map(275, 625, 4, 1, WallType.BRICK), new Map(275, 650, 1, 2, WallType.BRICK),
			new Map(300, 650, 1, 1, WallType.FORT), new Map(350, 650, 1, 2, WallType.BRICK),
			new Map(300, 350, 2, 2, WallType.STEEL), new Map(300, 450, 2, 2, WallType.BRICK),
			new Map(350, 50, 5, 1, WallType.ICE), new Map(350, 100, 2, 10, WallType.BRICK),
			new Map(350, 350, 6, 1, WallType.ICE), new Map(350, 425, 2, 6, WallType.BRICK),
			new Map(400, 100, 4, 2, WallType.BRICK), new Map(400, 300, 4, 2, WallType.BRICK),
			new Map(400, 550, 2, 2, WallType.STEEL), new Map(450, 150, 2, 6, WallType.BRICK),
			new Map(450, 400, 2, 1, WallType.BRICK), new Map(450, 475, 2, 7, WallType.BRICK),
			new Map(500, 400, 2, 1, WallType.STEEL), new Map(500, 650, 2, 1, WallType.FOREST),
			new Map(550, 100, 2, 10, WallType.BRICK), new Map(550, 475, 2, 7, WallType.BRICK),
			new Map(600, 100, 1, 6, WallType.ICE), new Map(600, 400, 2, 2, WallType.STEEL),
			new Map(600, 450, 1, 4, WallType.FOREST)
		},
		{
			new Map(0, 100, 1, 5, WallType.FOREST), new Map(0, 350, 10, 2, WallType.BRICK),
			new Map(0, 450, 2, 2, WallType.STEEL), new Map(0, 500, 1, 1, WallType.SEA),
			new Map(0, 550, 1, 1, WallType.FOREST), new Map(0, 600, 1, 2, WallType.SEA),		
			new Map(50, 100, 2, 4, WallType.BRICK), new Map(50, 300, 1, 1, WallType.FOREST),
			new Map(50, 450, 2, 10, WallType.BRICK), new Map(100, 450, 1, 1, WallType.SEA),		
			new Map(150, 50, 2, 4, WallType.STEEL), new Map(150, 150, 3, 1, WallType.FOREST),
			new Map(150, 200, 2, 6, WallType.BRICK), new Map(150, 400, 2, 4, WallType.STEEL),
			new Map(150, 500, 2, 4, WallType.BRICK), new Map(150, 650, 2, 2, WallType.BRICK),
			new Map(200, 300, 4, 2, WallType.BRICK), new Map(200, 400, 1, 1, WallType.FOREST),
			new Map(250, 250, 2, 2, WallType.BRICK), new Map(250, 350, 2, 1, WallType.FOREST),
			new Map(250, 400, 2, 8, WallType.BRICK), new Map(275, 625, 4, 1, WallType.BRICK),
			new Map(275, 650, 1, 2, WallType.BRICK), new Map(300, 650, 1, 1, WallType.FORT),
			new Map(350, 650, 1, 2, WallType.BRICK), new Map(300, 200, 4, 2, WallType.BRICK),
			new Map(300, 250, 2, 2, WallType.STEEL), new Map(300, 500, 2, 4, WallType.BRICK),
			new Map(350, 50, 2, 2, WallType.STEEL), new Map(350, 100, 2, 4, WallType.BRICK),
			new Map(350, 350, 2, 2, WallType.STEEL), new Map(350, 400, 2, 8, WallType.BRICK),
			new Map(400, 300, 2, 2, WallType.STEEL), new Map(400, 350, 2, 2, WallType.BRICK),
			new Map(450, 100, 2, 4, WallType.BRICK), new Map(450, 200, 2, 2, WallType.STEEL),
			new Map(450, 250, 2, 8, WallType.BRICK), new Map(450, 450, 2, 1, WallType.SEA),
			new Map(4500, 500, 2, 2, WallType.BRICK), new Map(450, 600, 2, 4, WallType.BRICK),
			new Map(500, 150, 2, 2, WallType.STEEL), new Map(500, 250, 1, 3, WallType.FOREST),
			new Map(500, 500, 2, 2, WallType.STEEL), new Map(500, 650, 2, 2, WallType.BRICK),
			new Map(550, 100, 2, 4, WallType.BRICK), new Map(550, 250, 2, 2, WallType.BRICK),
			new Map(550, 350, 2, 8, WallType.BRICK), new Map(550, 650, 2, 4, WallType.BRICK),
			new Map(600, 200, 1, 1, WallType.ICE), new Map(600, 250, 2, 2, WallType.STEEL),
			new Map(600, 450, 1, 4, WallType.SEA)
		},
		{
			new Map(0, 100, 1, 1, WallType.ICE), new Map(0, 150, 2, 2, WallType.STEEL),
			new Map(0, 200, 1, 5, WallType.SEA), new Map(0, 450, 2, 2, WallType.STEEL),
			new Map(0, 500, 2, 6, WallType.BRICK), new Map(0, 650, 2, 2, WallType.STEEL),
			new Map(50, 50, 3, 1, WallType.ICE), new Map(50, 100, 3, 5, WallType.FOREST),
			new Map(50, 350, 1, 1, WallType.FOREST), new Map(50, 425, 2, 3, WallType.BRICK),
			new Map(50, 600, 2, 4, WallType.BRICK), new Map(100, 350, 4, 1, WallType.ICE),
			new Map(100, 450, 5, 2, WallType.BRICK), new Map(100, 650, 4, 2, WallType.BRICK),
			new Map(150, 425, 2, 1, WallType.BRICK), new Map(150, 550, 1, 4, WallType.STEEL),
			new Map(200, 50, 2, 4, WallType.BRICK), new Map(200, 150, 9, 1, WallType.ICE),
			new Map(200, 250, 6, 2, WallType.BRICK), new Map(250, 50, 1, 1, WallType.ICE),
			new Map(275, 450, 1, 2, WallType.BRICK), new Map(250, 500, 2, 2, WallType.BRICK),
			new Map(275, 625, 4, 1, WallType.BRICK), new Map(275, 650, 1, 2, WallType.BRICK),
			new Map(300, 650, 1, 1, WallType.FORT), new Map(350, 650, 1, 2, WallType.BRICK),
			new Map(300, 300, 2, 2, WallType.BRICK), new Map(300, 350, 6, 2, WallType.STEEL),
			new Map(300, 450, 6, 1, WallType.BRICK), new Map(350, 50, 1, 1, WallType.ICE),
			new Map(350, 200, 2, 4, WallType.BRICK), new Map(350, 525, 4, 2, WallType.BRICK),
			new Map(400, 50, 2, 2, WallType.BRICK), new Map(450, 50, 3, 1, WallType.ICE),
			new Map(450, 200, 2, 4, WallType.BRICK), new Map(450, 350, 2, 1, WallType.ICE),
			new Map(450, 400, 4, 3, WallType.FOREST), new Map(450, 550, 1, 1, WallType.SEA),
			new Map(450, 600, 3, 1, WallType.FOREST), new Map(450, 650, 4, 2, WallType.BRICK),
			new Map(500, 125, 6, 1, WallType.STEEL), new Map(500, 200, 6, 2, WallType.BRICK),
			new Map(500, 550, 2, 1, WallType.FOREST), new Map(575, 250, 1, 4, WallType.BRICK),
			new Map(550, 350, 1, 1, WallType.FOREST), new Map(600, 350, 1, 1, WallType.ICE),
			new Map(600, 550, 1, 2, WallType.SEA)
		},
		{
			new Map(0, 100, 1, 2, WallType.FOREST), new Map(0, 200, 2, 1, WallType.STEEL),
			new Map(0, 300, 1, 1, WallType.SEA), new Map(0, 400, 2, 4, WallType.STEEL),
			new Map(0, 500, 1, 2, WallType.ICE), new Map(0, 600, 1, 1, WallType.FOREST),
			new Map(0, 650, 2, 2, WallType.STEEL), new Map(50, 50, 1, 3, WallType.ICE),
			new Map(75, 450,3, 2, WallType.BRICK), new Map(50, 500, 4, 1, WallType.BRICK),
			new Map(50, 550, 4, 2, WallType.BRICK), new Map(50, 650, 1, 1, WallType.FOREST),
			new Map(100, 50, 1, 1, WallType.FOREST), new Map(100, 200, 1, 1, WallType.ICE),
			new Map(125, 250, 1, 4, WallType.BRICK), new Map(100, 350, 2, 4, WallType.BRICK),
			new Map(100, 600, 4, 1, WallType.BRICK), new Map(175, 150, 1, 2, WallType.BRICK),
			new Map(150, 200, 2, 3, WallType.BRICK), new Map(150, 400, 2, 6, WallType.BRICK),
			new Map(150, 575, 2, 1, WallType.BRICK), new Map(200, 125, 2, 5, WallType.BRICK),
			new Map(200, 300, 1, 2, WallType.STEEL), new Map(200, 375, 4, 1, WallType.BRICK),
			new Map(200, 400, 1, 3, WallType.FOREST), new Map(200, 550, 2, 1, WallType.BRICK),
			new Map(250, 100, 2, 2, WallType.BRICK), new Map(250, 150, 2, 1, WallType.FOREST),
			new Map(250, 200, 2, 1, WallType.ICE), new Map(250, 400, 1, 1, WallType.FOREST),
			new Map(250, 450, 4, 6, WallType.BRICK), new Map(275, 625, 4, 1, WallType.BRICK),
			new Map(275, 650, 1, 2, WallType.BRICK), new Map(300, 650, 1, 1, WallType.FORT),
			new Map(350, 650, 1, 2, WallType.BRICK), new Map(300, 125, 2, 1, WallType.BRICK),
			new Map(300, 300, 1, 2, WallType.STEEL), new Map(300, 400, 1, 1, WallType.ICE),
			new Map(350, 100, 4, 1, WallType.FOREST), new Map(350, 150, 1, 1, WallType.ICE),
			new Map(350, 200, 2, 3, WallType.BRICK), new Map(350, 400, 2, 7, WallType.BRICK),
			new Map(400, 150, 2, 16, WallType.BRICK), new Map(400, 575, 2, 2, WallType.BRICK),
			new Map(450, 150, 2, 2, WallType.BRICK), new Map(450, 200, 2, 1, WallType.FOREST),
			new Map(450, 250, 2, 2, WallType.BRICK), new Map(450, 300, 1, 4, WallType.BRICK),
			new Map(450, 400, 2, 5, WallType.BRICK), new Map(450, 550, 2, 3, WallType.BRICK),
			new Map(500, 175, 2, 1, WallType.BRICK), new Map(500, 450, 1, 2, WallType.BRICK),
			new Map(500, 500, 2, 1, WallType.BRICK), new Map(500, 550, 2, 2, WallType.BRICK),
			new Map(500, 650, 1, 1, WallType.FOREST), new Map(550, 50, 1, 1, WallType.FOREST),
			new Map(550, 200, 1, 4, WallType.BRICK), new Map(550, 350, 2, 1, WallType.SEA),
			new Map(550, 400, 1, 3, WallType.FOREST), new Map(550, 550, 1, 1, WallType.ICE),
			new Map(550, 600, 1, 2, WallType.FOREST), new Map(600, 100, 1, 1, WallType.FOREST),
			new Map(600, 150, 2, 1, WallType.STEEL), new Map(600, 400, 2, 6, WallType.BRICK),
			new Map(600, 550, 1, 2, WallType.FOREST)
		}
	};
	
	public Map(int x, int y, int width, int height, WallType wallType) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.wallType = wallType;
	}
	
	public static Map[][] getMap() {
		return maps;
	}
}