package cn.edu.snnu.client;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 炮弹类
 * @author Haodong Guo
 *
 */
public class Missile {
	/**
	 * 炮弹横向移动速度
	 * 炮弹竖向移动速度
	 * 炮弹宽度
	 * 炮弹高度
	 */
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	public int x;
	public int y;
	public Direction ptDir;
	public TankWar tc;
	private boolean live = true;
	private boolean good;
	private int missileID;
	private int tankID;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		imgs.put("L", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileL.gif")));
		imgs.put("LU", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileLU.gif")));
		imgs.put("U", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileU.gif")));
		imgs.put("UR", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileUR.gif")));
		imgs.put("R", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileR.gif")));
		imgs.put("RD", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileRD.gif")));
		imgs.put("D", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileD.gif")));
		imgs.put("DL", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileDL.gif")));
		imgs.put("Lf", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileL_f.gif")));
		imgs.put("LUf", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileLU_f.gif")));
		imgs.put("Uf", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileU_f.gif")));
		imgs.put("URf", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileUR_f.gif")));
		imgs.put("Rf", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileR_f.gif")));
		imgs.put("RDf", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileRD_f.gif")));
		imgs.put("Df", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileD_f.gif")));
		imgs.put("DLf", tk.getImage(Missile.class.getClassLoader().getResource("Image/missileDL_f.gif")));
	}
	
	public Missile(int x, int y, Direction ptDir) {
		this.x = x;
		this.y = y;
		this.ptDir = ptDir;
	}
	
	public Missile(int x, int y, Direction ptDir, boolean good, TankWar tc) {
		this(x, y, ptDir);
		this.good = good;
		this.tc = tc;
	}

	/**
	 * 描画炮弹
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		switch(ptDir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case UR:
			g.drawImage(imgs.get("UR"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case DL:
			g.drawImage(imgs.get("DL"), x, y, null);
			break;
		}
		move();
	}

	private void move() {
		switch(ptDir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case UR:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case DL:
			x -= XSPEED;
			y += YSPEED;
			break;
		}
		
		if(x < 0 || y < 0 || x > TankWar.GAME_WIDTH || y > TankWar.GAME_HEIGHT) {
			live = false;
		}
	}
	
	/**
	 * 获取炮弹位置大小信息
	 * @return 炮弹外切矩形
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * 子弹击中坦克
	 * @param 碰到的坦克
	 * @return 是否击中了该坦克
	 */
	public boolean hitTanknet(Tank t) {
		if(this.live && t.isLive() && (this.getMissileID()%2) != (t.id%2) &&this.getRect().intersects(t.getRect())) {
			this.live = false;
			t.setLife(t.getLife()-20);
			if(t.getLife()>0) {
			} else {
				t.setLive(false);
			}
			tc.getExplodes().add(new Explode(x, y, tc));
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 判断炮弹是否相撞
	 * @param m 目标炮弹
	 * @return 相撞返回true，否则返回false
	 */
	public boolean hitMissile(Missile m) {
		if(live && m.isLive() && good != m.isGood() && getRect().intersects(m.getRect())) {
			live = false;
			m.setLive(false);
			return true;
		}
		return false;
	}
	
	/**
	 * 判断炮弹相撞
	 * @param missiles 炮弹集合
	 * @return 相撞返回true，否则返回false
	 */
	public boolean hitMissiles(List<Missile> missiles) {
		for(int i=0; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			if(m != this && hitMissile(m)) return true;
		}
		return false;
	}
	
	/**
	 * 判断炮弹撞墙
	 * @param w 墙
	 * @return 撞到墙返回true，否则返回false
	 */
	public boolean collidesWithWall(Wall w) {
		if(w.getWallType() != WallType.FOREST && w.getWallType() != WallType.ICE && w.getWallType() != WallType.SEA && w.isLive() && live && getRect().intersects(w.getRect())) {
			if(w.getWallType()==WallType.BRICK){
				w.setLive(false);
			}
			tc.explodes.add(new Explode(x, y, tc));
			live = false;
			return true;
		}
		return false;
	}
	public boolean collidesWithWalls(List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			if(collidesWithWall(walls.get(i))) return true;
		}
		return false;
	}

	/**
	 * 设置炮弹生死
	 * @param live 生死值
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * 获取炮弹生死
	 * @return 炮弹生返回true，否则返回false
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * 判断是敌友发出的炮弹
	 * @return 敌军发出的炮弹返回false，否则返回true
	 */
	public boolean isGood() {
		return good;
	}
	
	public int getMissileID() {
		return missileID;
	}
	public int getTankID() {
		return tankID;
	}
	public void setMissileID(int missileID) {
		this.missileID = missileID;
	}
}
