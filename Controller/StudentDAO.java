package Controller;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDAO {
	public static ArrayList<Student> dbArrayList = new ArrayList<>();
	// �л� ����ϴ� �Լ�
	public static int insertStudentData(Student student) {
		int count =0;
		// 1.1 �����ͺ��̽��� �л����̺� �Է� �ϴ� ������
		StringBuffer insertStudent = new StringBuffer();
		insertStudent.append("insert into student ");
		insertStudent.append("(no,name,grade,ban,gender,kor,eng,mat,sci,soc,mus,total,avg,date,imagepath) ");
		insertStudent.append("values ");
		insertStudent.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		
		
//		String insertStudent = "insert into student "
//				+ "(no,name,grade,ban,gender,kor,eng,mat,sci,soc,mus,total,avg,date) " + "values "
//				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		
		// 1.2������ ���̽� Ŀ�ؼ��� �����;� �Ѵ�.
		Connection con = null;

		// 1.3�������� �����ؾ��� statement�� ������ �Ѵ�
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(insertStudent.toString());
			// 1.4������ ���� ������ ����
			psmt.setString(1,student.getNo());
			psmt.setString(2,student.getName());
			psmt.setString(3,student.getGread());
			psmt.setString(4,student.getBan());
			psmt.setString(5,student.getGender());
			psmt.setString(6,student.getKor());
			psmt.setString(7,student.getEng());
			psmt.setString(8,student.getMat());
			psmt.setString(9,student.getSci());
			psmt.setString(10,student.getSoc());
			psmt.setString(11,student.getMus());
			psmt.setString(12,student.getTotal());
			psmt.setString(13,student.getAvg());
			psmt.setString(14,student.getDate());
			psmt.setString(15,student.getImagepath());

			// 1.5������ ���� �����͸� ������ ������ �����Ѵ�.
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("���� ���� : ������ ���� ����");
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MainController.callAlert("���� ���� : ������ ���̽� ���� ����");
		} finally {
			// 1.6 �ڿ� ��ü�� �ݾƾ� �Ѵ�.
			try {
				if (psmt != null) {psmt.close();}
				if (con != null) {con.close();}
				

			} catch (Exception e) {MainController.callAlert("�ڿ� �ݱ� ���� : psmt con �ݱ����");}
		}
		return count;
	}

	//���̺��� ��ü ������ ��� �������� �Լ�
	public static ArrayList<Student> getStudentTotaldata(){
		
		// 1.1 �����ͺ��̽����� �л����̺� �ִ� �ڷḦ ��� ��������  ������
		String selectStudent = "select * from student";
		
		// 1.2������ ���̽� Ŀ�ؼ��� �����;� �Ѵ�.
		Connection con = null;

		// 1.3�������� �����ؾ��� statement�� ������ �Ѵ�
		PreparedStatement psmt = null;
		//1.4 �������� �����ϰ� ���� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rs = null;
		
		
		
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectStudent);
			
			// 1.5������ ���� �����͸� ������ ������ �����Ѵ�.(������ ģ��)
			//executeQuery���� �������� �����ؼ� ����� ������ �� ����ϴ� ������
			//executeUpdate �� �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
			rs = psmt.executeQuery();
			if (rs == null) {
				MainController.callAlert("���� ���� : ������ ���� ����");
				return null;
			}
			while(rs.next()) {
				Student student = new Student(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13),
						rs.getString(14),
						rs.getString(15)
						);
			dbArrayList.add(student);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			MainController.callAlert("���� ���� : ������ ���̽� ���� ����");
		} finally {
			// 1.6 �ڿ� ��ü�� �ݾƾ� �Ѵ�.
			try {
				if (psmt != null) {psmt.close();}
				if (con != null) {con.close();}
			} catch (Exception e) {MainController.callAlert("�ڿ� �ݱ� ���� : psmt con �ݱ����");}
		}
		return dbArrayList;
	}

	//���̺� �信�� ������ ���ڵ带 ������ ���̽����� �����ϴ� �Լ�.
	public static int deleteStudentData(String no) {
		// 1.1 �����ͺ��̽����� �л����̺� �ִ� �ڷḦ �����ϴ� ������
				String deleteStudent = "delete from student where no = ? ";
				
				// 1.2������ ���̽� Ŀ�ؼ��� �����;� �Ѵ�.
				Connection con = null;

				// 1.3�������� �����ؾ��� statement�� ������ �Ѵ�
				PreparedStatement psmt = null;
				//1.4 �������� �����ϰ� ���� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
				int count = 0;
				
				
				
				try {
					con = DBUtility.getConnection();
					psmt = con.prepareStatement(deleteStudent);
					psmt.setString(1, no);//   ?  �� ���� �����ϴ� ���̴�.
					
					
					// 1.5������ ���� �����͸� ������ ������ �����Ѵ�.(������ ģ��)
					//executeQuery���� �������� �����ؼ� ����� ������ �� ����ϴ� ������
					//executeUpdate �� �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
					count = psmt.executeUpdate();
					if (count == 0) {
						MainController.callAlert("���� ���� : ���� ������ ���� ����");
						return count;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					MainController.callAlert("���� ���� : ���� ���� ����");
				} finally {
					// 1.6 �ڿ� ��ü�� �ݾƾ� �Ѵ�.
					try {
						if (psmt != null) {psmt.close();}
						if (con != null) {con.close();}
					} catch (Exception e) {MainController.callAlert("�ڿ� �ݱ� ���� : psmt con �ݱ����");}
				}
		
		return count;
	}

	
	//4������ ���ڵ带 ������ ���ڵ带 ������ ���̽����̺� �����ϴ� �Լ�
	public static int updateStudentDate(Student student) {
		int count =0;
		// 1.1 �����ͺ��̽��� �л����̺� ���� �ϴ� ������
		StringBuffer updateStudent = new StringBuffer();
		updateStudent.append("update student set ");
		updateStudent.append("kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=?,imagepath=? where no=? ");
		
//		String updateStudent = "update student set "
//				+ "kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=? where no=? ";
				
		
		// 1.2������ ���̽� Ŀ�ؼ��� �����;� �Ѵ�.
		Connection con = null;

		// 1.3�������� �����ؾ��� statement�� ������ �Ѵ�
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updateStudent.toString());
			// 1.4������ ���� ������ ����
			
			psmt.setString(1,student.getKor());
			psmt.setString(2,student.getEng());
			psmt.setString(3,student.getMat());
			psmt.setString(4,student.getSci());
			psmt.setString(5,student.getSoc());
			psmt.setString(6,student.getMus());
			psmt.setString(7,student.getTotal());
			psmt.setString(8,student.getAvg());
			psmt.setString(9,student.getDate());
			psmt.setString(10,student.getImagepath());
			psmt.setString(11,student.getNo());

			// 1.5������ ���� �����͸� ������ ������ �����Ѵ�.
			count = psmt.executeUpdate();
			if (count == 0) {
				MainController.callAlert("�������� ���� : ������ ���� ����");
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MainController.callAlert("���� ���� : ������ ���̽� ���� ����");
		} finally {
			// 1.6 �ڿ� ��ü�� �ݾƾ� �Ѵ�.
			try {
				if (psmt != null) {psmt.close();}
				if (con != null) {con.close();}
				

			} catch (Exception e) {MainController.callAlert("�ڿ� �ݱ� ���� : psmt con �ݱ����");}
		}
		
		
		
		return count;
	}
	
	
	
	
	

}
