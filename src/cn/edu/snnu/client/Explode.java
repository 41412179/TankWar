package cn.edu.snnu.client;
import java.awt.*;

/**
 * ±¬Õ¨·´Ó¦
 * @author Haodong Guo
 *
 */
public class Explode {

	int x, y;
	
	private TankWar tc;
	
	private boolean live = true;
	
	private static boolean init = false;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs = {
	//	tk.getImage(Explode.class.getClassLoader().getResource("Image/0.gif")),
	//	tk.getImage(Explode.class.getClassLoader().getResource("Image/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Image/2.gif")),
	//	tk.getImage(Explode.class.getClassLoader().getResource("Image/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Image/4.gif")),
	//	tk.getImage(Explode.class.getClassLoader().getResource("Image/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Image/6.gif")),
	//	tk.getImage(Explode.class.getClassLoader().getResource("Image/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Image/8.gif")),
	//	tk.getImage(Explode.class.getClassLoader().getResource("Image/9.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Image/10.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Image/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("Image/2.gif"))
	};
	
	int step = 0;
	
	public Explode(int x, int y, TankWar tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	/**
	 * »­³ö±¬Õ¨
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!init) {
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}
		
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step == imgs.length) {
			live = false;
			step = 0;
			return;
		}
		
		g.drawImage(imgs[step], x - imgs[step].getWidth(null)/2, y - imgs[step].getHeight(null)/2, null);
		
		step ++;
	}
}
