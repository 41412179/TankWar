package cn.edu.snnu.client;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Map;

import cn.edu.snnu.server.MissileNewMsg;
import cn.edu.snnu.server.TankMoveMsg;
import cn.edu.snnu.server.TankNewMsg;
import cn.edu.snnu.server.TankReviveMsg;
import cn.edu.snnu.server.TankServer;

/**
 * 坦克类
 * @author Haodong Guo
 *
 */
/**
 * int x;
 * int y;
 * @author asus
 *
 */
public class Tank {

	private static final int XSPEED = 5;
	private static final int YSPEED = 5;
	private static final int WIDTH = 36;
	private static final int HEIGHT = 36;
	private static final int MAX_LIFE = 100;
	private static final int SUPER_FIRE_COUNT = 20;
	private static int sfCount = SUPER_FIRE_COUNT;
	static int MISSILE_POWER = 100;
	
	static Random random = new Random();
	private BloodBar bb = new BloodBar();
	public int x;
	public int y;
	private int oldX;
	private int oldY;
	public int id;
	private int step = random.nextInt(20) + 3;
	private int drawStep = 0;
	private int life = MAX_LIFE;
	private boolean bL = false, bU = false, bR = false, bD = false;
	public boolean good = true;
	private boolean god = true;
	private boolean live = true;

	TankWar tc;
	
	public Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		imgs.put("L", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankL.gif")));
		imgs.put("LU", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankLU.gif")));
		imgs.put("U", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankU.gif")));
		imgs.put("UR", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankUR.gif")));
		imgs.put("R", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankR.gif")));
		imgs.put("RD", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankRD.gif")));
		imgs.put("D", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankD.gif")));
		imgs.put("DL", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankDL.gif")));
		imgs.put("L0", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankL0.gif")));
		imgs.put("LU0", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankLU0.gif")));
		imgs.put("U0", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankU0.gif")));
		imgs.put("UR0", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankUR0.gif")));
		imgs.put("R0", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankR0.gif")));
		imgs.put("RD0", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankRD0.gif")));
		imgs.put("D0", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankD0.gif")));
		imgs.put("DL0", tk.getImage(Tank.class.getClassLoader().getResource("Image/tankDL0.gif")));
		imgs.put("God0", tk.getImage(Tank.class.getClassLoader().getResource("Image/god0.gif")));
		imgs.put("God1", tk.getImage(Tank.class.getClassLoader().getResource("Image/god1.gif")));
		
	}
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
		if(good) ptDir = Direction.U;
	}
	
	public Tank(int x, int y, boolean good, TankWar tc) {
		this(x, y, good);
		this.tc = tc;
	}

	
	
	public Tank(int x, int y, boolean good, Direction dir, TankWar tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	

	public Tank(int x2, int y2, Direction dir2, boolean good2, TankWar tc2) {
		// TODO Auto-generated constructor stub
		this.x=x2;
		y=y2;
		dir=dir2;
		good=good2;
		tc=tc2;
	}

	public boolean hitTank(Tank t) {
		if(this.live && t.isLive() && this.good != t.isGood() &&this.getRect().intersects(t.getRect())) {
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
	 * 画出坦克
	 * @param g
	 */
	public void draw(Graphics g) {
		drawStep ++;
		if(drawStep >= 100) {
			drawStep = 0;
		}
		
		if(!live) 
			return;
		else
			bb.draw(g, this.id);
		
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
	
	/**
	 * 坦克移动
	 */
	void move() {
		
		oldX = x;
		oldY = y;
		
		switch(dir) {
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
		case STOP:
			break;
		}
		
		if(dir != Direction.STOP) {
			ptDir = dir;
		}
		
		if(x < -5) x = -4;
		if(y < 17) y = 18;
		if(x + Tank.WIDTH + 3 > TankWar.GAME_WIDTH) x = TankWar.GAME_WIDTH - Tank.WIDTH - 4;
		if(y + Tank.HEIGHT + 3 > TankWar.GAME_HEIGHT) y = TankWar.GAME_HEIGHT - Tank.HEIGHT - 4;
	}
	
	/**
	 * 游戏操作按键按下时的响应
	 * @param e 按键信息
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_F1:
			Help.getHelpDialog(tc);
			break;
		case KeyEvent.VK_F2:
			if(!live) {
				
				god = true;
				live = true;
				drawStep = 0;
				life = MAX_LIFE;
				sfCount = SUPER_FIRE_COUNT;
				TankWar.dieCount ++;
				//TankNewMsg tnm = new TankNewMsg(tc.myTank);
				//tnm.send(tc.nc.getDs(), tc.nc.getServerIP(), TankServer.getSERVER_UDP_PORT());
				TankReviveMsg trm = new TankReviveMsg(tc.myTank.id);
				trm.send(tc.nc.getDs(), tc.nc.getServerIP(), TankServer.getSERVER_UDP_PORT());
				
			}
			break;
		case KeyEvent.VK_CONTROL :
		case KeyEvent.VK_J :
			fire(ptDir);
			break;
		case KeyEvent.VK_K :
		case KeyEvent.VK_ENTER :
			superFire();
			break;
		case KeyEvent.VK_UP :
		case KeyEvent.VK_W :
			bU = true;
			break;
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S :
			bD = true;
			break;
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_A :
			bL = true;
			break;
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_D :
			bR = true;
			break;
		}
		locateDirection();
	}

	/**
	 * 游戏操作按键抬起时的响应
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP :
		case KeyEvent.VK_W :
			bU = false;
			break;
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S :
			bD = false;
			break;
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_A :
			bL = false;
			break;
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_D :
			bR = false;
			break;
		}
		locateDirection();
	}
	
	/**
	 * 改变坦克的位置方向
	 */
	void locateDirection() {
		Direction olddir = dir;
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.UR;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.DL;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
		if(dir!=olddir) {
			TankMoveMsg msg = new TankMoveMsg(id, dir, ptDir, x, y);
			msg.send(tc.nc.getDs(), tc.nc.getServerIP(), TankServer.getSERVER_UDP_PORT());
		}
	}
	
	/**
	 * 使坦克回到上一步
	 */
	private void stay() {
		x = oldX;
		y = oldY;
	}
	
	/**
	 * 坦克攻击，发射炮弹
	 * @param ptDir 炮筒方向
	 * @return 返回发射出去的炮弹
	 */
	public Missile fire(Direction ptDir) {
		if(!live) return null;
		try {
			   URL cb;
			   File f = new File("e://5796.wav");
			   cb = f.toURL();
			   AudioClip aau;
			   aau = Applet.newAudioClip(cb);
			   //aau.loop();//循环播放  aau.play() 单曲 aau.stop()停止播放
			   aau.play();

			  } catch (MalformedURLException e) {
			   e.printStackTrace();
			  }
		int x = this.x + imgs.get("" + ptDir).getWidth(null)/2 - 6;
		int y = this.y + imgs.get("" + ptDir).getHeight(null)/2 - 6;
		Missile m = new Missile(x, y, ptDir, good, tc);
		tc.missiles.add(m);
		MissileNewMsg msg = new MissileNewMsg(m);
		msg.send(tc.nc.getDs(), tc.nc.getServerIP(), TankServer.getSERVER_UDP_PORT());
		return m;
	}
	
	/**
	 * 坦克攻击，发射超级炮弹
	 */
	public void superFire() {
		Direction[] dirs = Direction.values();
		if(sfCount <= 0) return;
		for(int i=0; i<8; i++) {
				fire(dirs[i]);
		}
		sfCount --;
	}

	/**
	 * 获取坦克位置大小信息
	 * @return 返回坦克外切矩形
	 */
	public Rectangle getRect() {
		return new Rectangle(x + 5, y + 5, WIDTH, HEIGHT);
	}
	
	/**
	 * 坦克撞墙操作
	 * @param w 被撞的墙
	 * @return 撞到墙返回true，否则返回false
	 */
	public boolean collidesWithWall(Wall w) {
		if(w.getWallType() != WallType.FOREST && w.getWallType() != WallType.ICE && w.isLive() && live && getRect().intersects(w.getRect())) {
			stay();
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
	
	public boolean collidesWithTank(Tank t) {
		if(live && t.isLive() && getRect().intersects(t.getRect())) {
			this.stay();
			t.stay();
			return true;
		}
		return false;
	}
	
	/**
	 * 坦克群撞墙
	 * @param tanks 所以坦克群体
	 * @return 有坦克撞倒墙返回true，否则返回false
	 */
	public boolean collidesWithTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t && collidesWithTank(t)) return true;
		}
		return false;
	}
	
	public void setLocation(int x, int y, Direction ptDir) {
		this.x = x;
		this.y = y;
		this.ptDir = ptDir;
	}
	
	/**
	 * 获取坦克生命值
	 * @return 坦克生命值
	 */
	public int getLife() {
		return life;
	}

	/**
	 * 设置坦克生命值
	 * @param life 生命值
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * 获取超级炮弹数量
	 * @return 超级炮弹数量
	 */
	public int getSfCount() {
		return sfCount;
	}

	/**
	 * 判断坦克是否生存
	 * @return 坦克生存返回true，已死亡返回false
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * 设置坦克存亡
	 * @param live 坦克生死
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * 判断坦克敌友
	 * @return 敌军返回false，否则返回true
	 */
	public boolean isGood() {
		return good;
	}



	private class BloodBar {
		public void draw(Graphics g, int id) {
			Color c = g.getColor();
			if(id%2==1)
			g.setColor(Color.RED);
			else {
				g.setColor(Color.BLUE);
			}
			g.drawRect(x + 6, y - 5, WIDTH, 5);
			g.fillRect(x + 6, y - 5, WIDTH * life/MAX_LIFE, 5);
			g.setColor(c);
		}
	}
	public void drawspecial(Graphics g) {
		// TODO Auto-generated method stub
		if(!live)
			return;
		else
			bb.draw(g, this.id);
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
	}
}
