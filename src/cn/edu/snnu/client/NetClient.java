package cn.edu.snnu.client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import cn.edu.snnu.server.MissileDeadMsg;
import cn.edu.snnu.server.MissileNewMsg;
import cn.edu.snnu.server.Msg;
import cn.edu.snnu.server.TankDeadMsg;
import cn.edu.snnu.server.TankMoveMsg;
import cn.edu.snnu.server.TankNewMsg;
import cn.edu.snnu.server.TankReviveMsg;
import cn.edu.snnu.server.TankServer;
/**
 * 
 * @author Haodong Guo
 *
 */
public class NetClient {
	private TankWar tc;
	private String serverIP;
	private int clientUDPPort;
	private int serverTcpPort;
	private boolean isMyTankNew = true;
	private boolean isMyTankMove = true;
	private DatagramSocket ds = null;
	private boolean newJoin;
	
	public NetClient(TankWar tc) {
		this.tc = tc;
	}
	public void connect() {
		new Thread(new UDPRecvThread()).start();
		TCPConnect();//�õ�id���Լ�����Ϣ���͵�������
		TankNewMsg msg = new TankNewMsg(tc.myTank);
		if(ds==null) {
			try {
				System.out.println(clientUDPPort);
				ds = new DatagramSocket(tc.nc.clientUDPPort);
				System.out.println("�ͻ���UDP�̱߳�����");
			} catch (SocketException e1) {
				System.out.println("����,udp socket����ʧ��");
			}
		}
		msg.send(this.ds, serverIP, TankServer.getSERVER_UDP_PORT());
	}
	
	private void TCPConnect() {
			int myTankID = 0;
			Socket s = null;
		try {
			s = new Socket(serverIP, serverTcpPort);
			s.setSoTimeout(100000);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(clientUDPPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			myTankID = dis.readInt();
			tc.myTank.id=myTankID;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(s != null) {
				try {
					s.close();
					s = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private class UDPRecvThread implements Runnable {
		
		byte[] buf = new byte[1024];
		public void run() {
			if(ds==null){
				try {
					
					System.out.println(clientUDPPort);
					ds = new DatagramSocket(tc.nc.clientUDPPort);
					System.out.println("�ͻ���UDP�̱߳�����");
				} catch (SocketException e1) {
					System.out.println("����,udp socket����ʧ��");
				}
			}
			
			while(true){
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
					parse(dp);				
					System.out.println("�ͻ���" + tc.myTank.id  + "�ӷ������˽��յ���һ��DatagramPacket");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void parse(DatagramPacket dp) {//��������packet����
			ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
			DataInputStream dis = new DataInputStream(bais);
			int msgType = 0;
			try {
				msgType = dis.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
				Msg msg = null;
				switch (msgType) {
				case Msg.TANK_NEW_MSG:
					msg = new TankNewMsg(NetClient.this.tc);
					//��ʾ���µ�̹�˼��룬 Ȼ��server��udp��ԭ����old tank�� old tank �����Լ�����Ϣ��֪�µ�tank��Ȼ���ok
					msg.parse(dis);//������������
					if(isMyTankNew) break;
					
					if(newJoin) {
						msg = new TankNewMsg(tc.myTank);
						System.out.println("���ڸղŽ��յ���һ���¼����̹�˵İ�,���Է�����myTank packet");
						if(tc.myTank.isLive()) {
							msg.send(ds, serverIP, TankServer.getSERVER_UDP_PORT());
						}
					}
					break;
				case Msg.TANK_MOVE_MSG:
					msg = new TankMoveMsg(NetClient.this.tc);
					msg.parse(dis);
					break;
				case Msg.MISSILE_NEW_MSG:
					msg = new MissileNewMsg(NetClient.this.tc);
					msg.parse(dis);
					break;
				case Msg.TANK_DEAD_MSG:
					msg = new TankDeadMsg(NetClient.this.tc);
					msg.parse(dis);
					break;
				case Msg.MISSILE_DEAD_MSG:
					msg = new MissileDeadMsg(NetClient.this.tc);
					msg.parse(dis);
					break;
				case Msg.TANK_REVIVE_MSG:
					msg = new TankReviveMsg(NetClient.this.tc);
					msg.parse(dis);
					break;
			}
		}	
	}

	public boolean isNewJoin() {
		return newJoin;
	}

	public void setNewJoin(boolean newJoin) {
		this.newJoin = newJoin;
	}
	public TankWar getTc() {
		return tc;
	}
	public void setTc(TankWar tc) {
		this.tc = tc;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public int getClientUDPPort() {
		return clientUDPPort;
	}
	public void setClientUDPPort(int clientUDPPort) {
		this.clientUDPPort = clientUDPPort;
	}
	public int getServerTcpPort() {
		return serverTcpPort;
	}
	public void setServerTcpPort(int serverTcpPort) {
		this.serverTcpPort = serverTcpPort;
	}
	public boolean isMyTankNew() {
		return isMyTankNew;
	}
	public void setMyTankNew(boolean isMyTankNew) {
		this.isMyTankNew = isMyTankNew;
	}
	public boolean isMyTankMove() {
		return isMyTankMove;
	}
	public void setMyTankMove(boolean isMyTankMove) {
		this.isMyTankMove = isMyTankMove;
	}
	public DatagramSocket getDs() {
		return ds;
	}
	public void setDs(DatagramSocket ds) {
		this.ds = ds;
	}
}
