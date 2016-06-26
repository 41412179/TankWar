package cn.edu.snnu.server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import cn.edu.snnu.client.TankWar;
/**
 * 
 * @author Haodong Guo
 *
 */
public class TankReviveMsg implements Msg{
	private int msgType = TANK_REVIVE_MSG;
	private int id = -1;
	private TankWar tc;
	public TankReviveMsg(int id) {
		this.id = id;
	}
	public TankReviveMsg(TankWar tc) {
		this.tc = tc;
	}
	@Override
	public void send(DatagramSocket ds, String IP, int serverUDPPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
			DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, serverUDPPort));
			try {
				ds.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}//至此，一个datagram packet 被发送了
	}
	@Override
	public void parse(DataInputStream dis) {
		try {
			id = dis.readInt();
			if(id==tc.myTank.id) {
				return ;
			}
			for(int i=0; i<tc.getTanks().size(); i++) {
				if(tc.getTanks().get(i).id == id) {
					tc.getTanks().get(i).setLife(100);
					tc.getTanks().get(i).setLive(true);
					break;
			}
		}
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
}