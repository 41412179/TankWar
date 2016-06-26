package cn.edu.snnu.server;
import java.io.DataInputStream;
import java.net.DatagramSocket;

/**
 * 
 * @author Haodong Guo
 *
 */
public interface Msg {
    static final int TANK_NEW_MSG = 1;
	static final int TANK_MOVE_MSG = 2;
	static final int MISSILE_NEW_MSG = 3;
	static final int TANK_DEAD_MSG=4;
	static final int MISSILE_DEAD_MSG = 5;
	static final int TANK_REVIVE_MSG = 6;
	static final int BLOOD_NEW_MSG = 7;
	
	public void send(DatagramSocket ds, String IP, int udpPort);
	public void parse(DataInputStream dis);
}
