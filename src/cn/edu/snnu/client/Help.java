package cn.edu.snnu.client;
import java.awt.*;

import javax.swing.JOptionPane;
/**
 * 
 * @author Haodong Guo
 *
 */
public class Help {
	private static String help = "�ϣ�    W  ��  �������\n" +
			"�£�    S   ��  �������\n" +
			"��    A   ��  �������\n" +
			"�ң�    D   ��  �������\n" +
			"��ͨ������ J  ��  Ctrl\n" +
			"���⹥���� K  ��  Enter\n" +
			"������          F1\n" +
			"ԭ�ظ�� F2\n\n" ;
	public static void getHelpDialog(TankWar tc) {
		Dialog h = new JOptionPane(help).createDialog(tc, "����˵��");
		h.setVisible(true);		
	}
}