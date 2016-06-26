package cn.edu.snnu.server;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import cn.edu.snnu.client.Direction;
import cn.edu.snnu.client.Missile;
import cn.edu.snnu.client.TankWar;
/**
 * 
 * @author Haodong Guo
 *
 */
public class MissileNewMsg implements Msg {
	private int msgType = Msg.MISSILE_NEW_MSG;
	private Missile m;
	private TankWar tc;
	
	public MissileNewMsg(Missile m) {
		this.m = m;
	}

	public MissileNewMsg(TankWar tc) {
		this.tc = tc;
	}

	public void parse(DataInputStream dis) {
		int id, x, y, missileID;
		Direction dir;
		boolean good;
		try {
			id = dis.readInt();
			if(id == tc.myTank.id) {
				//missileID=dis.readInt();
				return ;
			}
			missileID = dis.readInt();
			x = dis.readInt();
			y = dis.readInt();
			dir = Direction.values()[dis.readInt()];
			good = dis.readBoolean();
			Missile m = new Missile(x, y, dir, good, tc);
			m.setMissileID(missileID);
			tc.getMissiles().add(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(m.tc.myTank.id);
			m.setMissileID(m.tc.myTank.id);
			dos.writeInt(m.tc.myTank.id);//用坦克的id代表子弹的id
			dos.writeInt(m.x);
			dos.writeInt(m.y);
			dos.writeInt(m.ptDir.ordinal());
			dos.writeBoolean(m.isGood());
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
