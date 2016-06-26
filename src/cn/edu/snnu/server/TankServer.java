package cn.edu.snnu.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Haodong Guo
 *
 */
public class TankServer {
	private static int ID = 100;
	private String ServerIP = "127.0.0.1";
	private static final int SERVER_TCP_PORT = 8889;
	private static final int SERVER_UDP_PORT = 6669;
	//������ά���ͻ��˵�һ������
	private List<Client> clients = new ArrayList<Client>();
	public void start() {
		//�������˿����������߳�tcp and udp thread
		new Thread(new UDPThread()).start();
		connectServerSocket();
	}
	private void connectServerSocket() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(SERVER_TCP_PORT);
			System.out.println("in server,TCP thread is started and port is:" + SERVER_TCP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			Socket s = null;
			try {
				s = ss.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				//�õ��ͻ��˵�IP��udp�˿ں�
				String IP = s.getInetAddress().getHostAddress();
				int clientUDPPort = dis.readInt();
				
				//�õ��ͻ��˵�UDP�˿ں�
				Client c = new Client(IP, clientUDPPort);
				clients.add(c);
				
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());//��������д����
				dos.writeInt(TankServer.getID());
				System.out.println("�����Ƿ������ˣ�һ��id������");
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
	}
	public static void main(String[] args) {
		new TankServer().start();
	}
	
	private class Client {
		String clientIP;
		int clientUDPPort;
		
		public Client(String IP, int udpPort) {
			this.clientIP = IP;
			this.clientUDPPort = udpPort;
		}
	}
	
	private class UDPThread implements Runnable {
		
		byte[] buf = new byte[1024];
		
		public void run() {
			DatagramSocket ds = null;
			try {
				ds = new DatagramSocket(SERVER_UDP_PORT);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			System.out.println("��������UDP�����ˣ�ռ�õĶ˿ں��ǣ�" + SERVER_UDP_PORT);
			while(true) {
				
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				
				try {
					ds.receive(dp);//�յ�dp��ת���������Ŀͻ���
					Client c = null;
					for(int i=0; i<clients.size(); i++) {
						c = clients.get(i);
						dp.setSocketAddress(new InetSocketAddress(c.clientIP, c.clientUDPPort));
						ds.send(dp);
						System.out.println("���յ��İ���ת�����˵�" + (i+1) + "���ͻ���");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public static int getID() {
		return ID++;
	}
	public static int getSERVER_UDP_PORT() {
		return SERVER_UDP_PORT;
	}
}