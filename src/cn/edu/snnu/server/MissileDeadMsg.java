package cn.edu.snnu.server;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import cn.edu.snnu.client.Explode;
import cn.edu.snnu.client.Missile;
import cn.edu.snnu.client.TankWar;

/**
 * 
 * @author Haodong Guo
 *
 */
public class MissileDeadMsg implements Msg {
	
	private final int msgType = Msg.MISSILE_DEAD_MSG;
	private TankWar tc;
	private int tankID;
	private int missileID;
	
	public MissileDeadMsg(TankWar tc) {
		this.tc = tc;
	}
	
	public MissileDeadMsg(int tankID, int missileID) {
		this.tankID = tankID;
		this.missileID = missileID;
	}

	public void parse(DataInputStream dis) {
		try {
			tankID = dis.readInt();
			missileID = dis.readInt();
			for(int i=0; i<tc.getMissiles().size(); i++) {
				Missile m = tc.getMissiles().get(i);
				if(m.getMissileID() == missileID && m.getTankID()==tankID) {
					m.setLive(false);
					tc.getExplodes().add(new Explode(m.x, m.y, tc));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(tankID);
			dos.writeInt(missileID);
		} catch (IOException e) {
			e.printStackTrace();
		}
			byte[] buf = baos.toByteArray();
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, udpPort));
			ds.send(dp);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
