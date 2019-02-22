package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	
	public static Connection getConnection() {
		Connection con = null;
		//1 MySql database class �ε�
		try {
			Class.forName("com.mysql.jdbc.Driver");
		
		//2�ּҿ� ���̵� ��й�ȣ�� ���ؼ� ���� ��û�Ѵ�.
		con = DriverManager.getConnection("jdbc:mysql://localhost/studentdb", "root", "123456");
		
		MainController.callAlert("������ ���̽� ���� ����: DB���� ���� �ϼ̽��ϴ�.");
		} catch (Exception e) {
			MainController.callAlert("������ ���̽� ���� ����: DB���� ����.");
			return null;
		}
		return con;
	}
	
	
}
