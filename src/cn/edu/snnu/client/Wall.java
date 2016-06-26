package cn.edu.snnu.client;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Haodong Guo
 *
 */
public class Wall {

	int x, y, width, height;
	int iw = 25, ih = 25;
	private TankWar tc;
	private WallType wallType;
	private boolean live = true;
	private boolean init = false;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Map<WallType, Image> imgs = new HashMap<WallType, Image>();
	private Image img = null;
	private int id;
	static {
		imgs.put(WallType.STEEL, tk.getImage(Wall.class.getClassLoader().getResource("Image/wall_steel.gif")));
		imgs.put(WallType.BRICK, tk.getImage(Wall.class.getClassLoader().getResource("Image/wall_brick.gif")));
		imgs.put(WallType.FOREST, tk.getImage(Wall.class.getClassLoader().getResource("Image/wall_forest.gif")));
		imgs.put(WallType.SEA, tk.getImage(Wall.class.getClassLoader().getResource("Image/wall_sea.gif")));
		imgs.put(WallType.FORT, tk.getImage(Wall.class.getClassLoader().getResource("Image/wall_fort.gif")));
		imgs.put(WallType.ICE, tk.getImage(Wall.class.getClassLoader().getResource("Image/wall_ice.gif")));
	}
	
	public Wall(int x, int y, int width, int height, WallType wallType, TankWar tc, int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.wallType = wallType;
		this.tc = tc;
		this.id = id;
		img = imgs.get(wallType);
	//	System.out.println("width:" + img.getWidth(null) + "\nheight:" + img.getHeight(null) + "   " + wallType);
	}
	
	/**
	 * 画墙
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!init) {
			WallType[] wt = WallType.values();
			for(int i = 0; i < wt.length; i++) {
				g.drawImage(imgs.get(wt[i]), -100, -100, null);
			}
			init = true;
		}
		
		if((wallType == WallType.BRICK || wallType == WallType.STEEL) && (width != 1 || height != 1)) {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					tc.walls.add(new Wall(x + i * iw, y + j * ih, 1, 1, wallType, tc, j+1000));
					System.out.println("wall + 63 + " + tc.walls.size());
				}
			}
			live = false;
		}
		if(!live) {
			tc.walls.remove(this);
			return;
		}
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				g.drawImage(img, x + i * img.getWidth(null), y + j * img.getHeight(null), null);
			}
		}
	}
	
	/**
	 * 获取墙的位置大小信息
	 * @return 矩形墙
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, width * img.getWidth(null), height * img.getHeight(null));
	}
	
	public boolean intersectWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			Wall w = walls.get(i);
			if(this != w && getRect().intersects(w.getRect())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean intersectTank(Tank t) {
		if(t.isLive() && wallType != WallType.FOREST && getRect().intersects(t.getRect())) {
			return true;
		}
		return false;
	}
	
	public boolean intersectTanks(List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			if(intersectTank(tanks.get(i))) return true;
		}
		return false;
	}

	public WallType getWallType() {
		return wallType;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
}
