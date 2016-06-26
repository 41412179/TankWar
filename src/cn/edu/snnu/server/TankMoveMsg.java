package cn.edu.snnu.server;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import cn.edu.snnu.client.*;


/**
 * 
 * @author Haodong Guo
 *
 */
public class TankMoveMsg implements Msg {
	private int msgType = Msg.TANK_MOVE_MSG;
	private int id;
	private Direction dir;
	private int x,y;
	private TankWar tc;
	private Direction ptDir;
	
	public TankMoveMsg(int id, Direction dir, Direction ptDir, int x, int y) {
		this.id = id;
		this.dir = dir;
		this.ptDir = ptDir;
		this.x = x;
		this.y = y;
	}
	
	public TankMoveMsg(TankWar tc) {
		this.tc = tc;
	}

	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			if(tc.myTank.id == id) {
				tc.nc.setMyTankMove(true);
				return;
			}
			tc.nc.setMyTankMove(false);
			//x, y, dir, ptDir, 
			x = dis.readInt();
			y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
			Direction ptDir = Direction.values()[dis.readInt()];
			for(int i=0; i<tc.getTanks().size(); i++) {
				Tank t = tc.getTanks().get(i);
				if(t.id == id) {
					t.x=(x);
					t.y=(y);
					t.dir = (dir);
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
			dos.writeInt(id);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeInt(ptDir.ordinal());
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
