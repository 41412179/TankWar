package cn.edu.snnu.server;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import cn.edu.snnu.client.TankWar;

/**
 * 
 * @author Haodong Guo
 *
 */
public class TankDeadMsg implements Msg {
	private final int msgType = Msg.TANK_DEAD_MSG; 
	private int id;
	private int life = 0;
	private TankWar tc;
	
	public TankDeadMsg(int id) {
		this.id = id;
	}
	public TankDeadMsg(int id,int life) {
		this.id = id;
		this.life = life;
	}
	
	public TankDeadMsg(TankWar tc) {
		this.tc = tc;
		this.life = tc.myTank.getLife();
	}
	
	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			int recelife = dis.readInt();
			if(tc.myTank.id == id) {
				if(recelife<=0) {
					tc.myTank.setLive(false);
					return;
				} else {
					tc.myTank.setLife(recelife);
				}
			}
			
			for(int i=0; i<tc.getTanks().size(); i++) {
				if(tc.getTanks().get(i).id == id) {
					cn.edu.snnu.client.Tank t = tc.getTanks().get(i);
					if(recelife<=0) {
						t.setLive(false);
						break;
					} else {
						t.setLife(recelife);
						break;
					}
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	public void send(DatagramSocket ds, String IP, int serverUDPPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(id);
			dos.writeInt(life);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, serverUDPPort));
			if(dp!=null) {
				ds.send(dp);
				System.out.println("客户端" + id + "发送了一个UDP包");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}