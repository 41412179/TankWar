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
public class TankNewMsg implements Msg {
	
	private int msgType = Msg.TANK_NEW_MSG;
	private cn.edu.snnu.client.Tank myTank;
	private TankWar tc;

	public TankNewMsg(Tank myTank) {
		this.myTank = myTank;
	}
	
	public TankNewMsg(TankWar tc) {
		this.tc = tc;
	}
	
	public void send(DatagramSocket ds, String IP, int serverUDPPort) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(myTank.id);
			dos.writeInt(myTank.x);
			dos.writeInt(myTank.y);
			dos.writeInt(myTank.dir.ordinal());
			dos.writeBoolean(myTank.isGood());
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, serverUDPPort));
			ds.send(dp);//至此，一个datagram packet 被发送了
			System.out.println("客户端发送了一个UDP包");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parse(DataInputStream dis) {
		//id, x, y, dir, good
		try {
				int id = dis.readInt();
				if(tc.myTank.id == id) {
					tc.nc.setMyTankNew(true);
					return;
				}
				tc.nc.setMyTankNew(false);
				int x = dis.readInt();
				int y = dis.readInt();
				Direction dir = Direction.values()[dis.readInt()];
				boolean good = dis.readBoolean();
				boolean exist = false;
				for(int i=0; i<tc.getTanks().size(); i++) {
					if(tc.getTanks().get(i).id == id) {
						exist = true;
						tc.nc.setNewJoin(false);
						break;
				}
			}
			if(exist) {
				exist = false;
				tc.nc.setNewJoin(false);
			} else {
				tc.nc.setNewJoin(true);
				Tank t = new Tank(x, y, dir, good, tc);
				t.id=id;
				tc.getTanks().add(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}