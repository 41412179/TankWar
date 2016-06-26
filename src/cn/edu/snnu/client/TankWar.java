package cn.edu.snnu.client;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cn.edu.snnu.server.MissileDeadMsg;
import cn.edu.snnu.server.TankDeadMsg;
import cn.edu.snnu.server.TankServer;

/**
 * 坦克大战游戏主界面
 * @author Haodong Guo
 *
 */
public class TankWar extends Frame {

	/**
	 * 主界面宽度
	 * 主界面高度
	 */
	public static final int GAME_WIDTH = 650;
	public static final int GAME_HEIGHT = 700;
	public static final int MAX_TANK_COUNT = 50;
	private ConnDialog cd = new ConnDialog();
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image img = tk.getImage(Tank.class.getClassLoader().getResource("Image/wukong.png"));
	private static int timer = 0;
	private static boolean over = true;
	Image offScreenImage = null;
	
	static int dieCount = 0;
	public Tank myTank = null;
	ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<Tank> tanks = new ArrayList<Tank>();
	ArrayList<Missile> missiles = new ArrayList<Missile>();
	ArrayList<Explode> explodes = new ArrayList<Explode>();
	Map[][] maps = Map.getMap();
	public NetClient nc = new NetClient(this);
	
	/**
	 * 用于获取显示屏大小信息
	 */
	public static Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
	
	int bo = 0;
	int tankCount = 0;
	
	/**
	 * 游戏运行主方法
	 * @param args
	 */
	public static void main(String[] args) {
		new TankWar().lauchFrame();
	}

	/**
	 * 显示游戏窗口
	 */
	public void lauchFrame() {
		
		this.setTitle("TankWar");
		this.setLocation(dimScreen.width/2 - GAME_WIDTH/2, dimScreen.height/2 - GAME_HEIGHT/2);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(new Color(199, 237, 204));
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.addKeyListener(new KeyMonitor());
		this.setResizable(false);
		this.setLookAndFeel();
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	/**
	 * 界面刷新
	 */
	public void paint(Graphics g) {
		if(over) {
			timer ++;
			
			if(timer >=60 || bo == 0) {
				g.drawImage(img, GAME_WIDTH - img.getWidth(null), GAME_HEIGHT - img.getHeight(null), null);
				Color c = g.getColor();
				Font f = g.getFont();
				g.setColor(new Color(199, 237, 204));
				g.setFont(new Font("中华行楷", Font.BOLD, 25));
				g.drawString("第 " + (bo + 1) + " 局", GAME_WIDTH/2 - 40, GAME_HEIGHT/2 - 100);
				g.drawString("stage: " + (bo + 1), GAME_WIDTH/2 - 45, GAME_HEIGHT/2 - 60);
				g.setFont(f);
				g.setColor(c);
				
				if((bo == 0 && timer >= 40) || (bo != 0 && timer >= 100)) {
					tankCount = 0;
					missiles.clear();
					walls.clear();
				
					URL cb = null;
					   File f1 = new File("G://TankWar//startBMG.wav");
						//File f1 = new File("startBGM.wav");//
					   try {
						cb = f1.toURL();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					   AudioClip aau;
					   aau = Applet.newAudioClip(cb);
					   //aau.loop();//循环播放  aau.play() 单曲 aau.stop()停止播放
					   aau.play();
					addWall();

					int num = (int)(Math.random()*4)+1;
					int tankx = 0,tanky = 0;
					switch(num){
					case 1:
						tankx=10;
						tanky=50;
						break;
					case 2:
						tankx=100;
						tanky=550;
						break;
					case 3:
						tankx=750;
						tanky=50;
						break;
					case 4:
						tankx=600;
						tanky=550;
						break;
					}
					this.myTank = new Tank(tankx, tanky, true, Direction.STOP, this);
					over = false;
					timer = 0;
					bo ++;
				}
				return;
			}
		}
		
		if(myTank.id%2==0) {
			myTank.good = true;
		} else {
			myTank.good=(false);
		}
		/**
		 * 将坦克画出来
		 */
		if(myTank.isLive())
		myTank.draw(g);
		/**
		 * 将ice画出来
		 */
		for(int i = 0; i < walls.size(); i++) {
			Wall w = walls.get(i);
			if(w.getWallType() == WallType.ICE) w.draw(g);
		}
		
		/**
		 * 将坦克画出来
		 */
		myTank.drawspecial(g);
		myTank.collidesWithTanks(tanks);
		myTank.collidesWithWalls(walls);
		/**
		 * 把地图画出来(不包括ice)
		 */
		//System.out.println(walls.size());
		for(int i = 0; i < walls.size(); i++) {
			Wall w = walls.get(i);
			if(w.getWallType() != WallType.ICE) 
				w.draw(g);
		}
		
		/**
		 * 将坦克画出来
		 */
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
		}
		/**
		 * 将子弹碰到坦克
		 */
		for(int i=0; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			if(m.hitTanknet(myTank)) {
				TankDeadMsg tdm = new TankDeadMsg(myTank.id, myTank.getLife());
				tdm.send(this.nc.getDs(), this.nc.getServerIP(), TankServer.getSERVER_UDP_PORT());
				MissileDeadMsg mdm = new MissileDeadMsg(m.getTankID(), m.getMissileID());
				mdm.send(this.nc.getDs(), this.nc.getServerIP(), TankServer.getSERVER_UDP_PORT());
			}
			/**
			 * 子弹碰到墙壁
			 */
			for(int j=0;j<walls.size();j++) {
				if(m.collidesWithWall(walls.get(j))) {
					MissileDeadMsg mdm = new MissileDeadMsg(m.getTankID(), m.getMissileID());
					mdm.send(this.nc.getDs(), this.nc.getServerIP(), TankServer.getSERVER_UDP_PORT());
				}
			}
			/**
			 * 如果子弹没碰到子弹,也没碰到墙壁,将子弹画出来
			 */
			m.draw(g);
		}
		/**
		 * 将爆炸画出来
		 */
		for(int i=0; i<explodes.size(); i++) {
			Explode e = explodes.get(i);
			if(e!=null)
			e.draw(g);
		}
		
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString("Missiles   Count: " + missiles.size(), 10, 40);
		g.drawString("Explodes Count: " + explodes.size(), 10, 60);
		g.drawString("Tanks       Count: " + tanks.size(), 10, 80);
		g.drawString("Die            Count: " + dieCount, 10, 100);
		g.drawString("SuperFire Count: " + myTank.getSfCount(), 10, 120);
		g.drawString("MyTank   Life: " + myTank.getLife(), 10, 140);
		g.drawString("第 " + bo + " 局", GAME_WIDTH/2, 40);
		g.drawString("帮助 F1", 600, 40);
		g.setColor(c);
	}

	private void addWall() {
		int temp = bo % maps.length;
		for(int i = 0; i < maps[temp].length; i++) {
			walls.add(new Wall(maps[temp][i].x, maps[temp][i].y, maps[temp][i].width, maps[temp][i].height, maps[temp][i].wallType, this, i));
		}
	}
	/**
	 * 刷新
	 */
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	private class PaintThread implements Runnable {

		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
}

	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_C) {
				cd.setVisible(true);
			} else {
				myTank.keyPressed(e);
			}
		}
	}
	
	private void setLookAndFeel() {
		//当前系统的UI UIManager.getSystemLookAndFeelClassName()
		//"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
		//跨平台外观  UIManager.getCrossPlatformLookAndFeelClassName() 
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" );
			SwingUtilities.updateComponentTreeUI(this) ;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}   
	}
	
	class ConnDialog extends Dialog {
		TextField serverIP = new TextField("127.0.0.1", 12);
		TextField serverTcpPort = new TextField("8889", 4);
		TextField clientUDPPort = new TextField("5669", 4);
		
		public ConnDialog() {
			super(TankWar.this, true);
			Button b = new Button("确定");
			this.setLayout(new FlowLayout());
			this.add(new Label("Ip"));
			this.add(serverIP);
			this.add(new Label("Tcp Port"));
			this.add(serverTcpPort);
			this.add(new Label("Udp Port"));
			this.add(clientUDPPort);
			this.add(b);
			this.pack();
			this.setLocation(300, 300);
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
				//	super.windowClosing(e);
			        setVisible(false);
				}
			});
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TankWar.this.nc.setServerIP(serverIP.getText().trim());
					TankWar.this.nc.setServerTcpPort(Integer.parseInt(serverTcpPort.getText().trim()));
					TankWar.this.nc.setClientUDPPort(Integer.parseInt(clientUDPPort.getText().trim()));
					setVisible(false);
					TankWar.this.nc.connect();
				}
			});
		}
	}
	public List<Wall> getWalls() {
		return walls;
	}
	public List<Tank> getTanks() {
		return tanks;
	}
	public List<Missile> getMissiles() {
		return missiles;
	}
	public List<Explode> getExplodes() {
		return explodes;
	}
}