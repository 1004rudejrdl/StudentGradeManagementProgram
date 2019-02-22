package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable{
	public Stage mainStage;
//******************************ù��°ȭ��*************************
	@FXML private TextField t1TextName;
	@FXML private ComboBox<String> t1CmbGrade;
	@FXML private ComboBox<String> t1CmbBan;
	@FXML private RadioButton t1RdoMale;
	@FXML private RadioButton t1RdoFemale;
	@FXML private TextField t1TextMath;
	@FXML private TextField t1TextSoc;
	@FXML private TextField t1TextEnglish;
	@FXML private TextField t1TextSci;
	@FXML private TextField t1Textmus;
	@FXML private TextField t1TextKorean;
	@FXML private TextField t1Textsum;
	@FXML private TextField t1Textavg;
	@FXML private Button t1Btnsum;
	@FXML private Button t1Btnavg;
	@FXML private Button t1Btnclear;
	@FXML private Button t1BtnReg;
	@FXML private Button t1BtnEdit;
	@FXML private Button t1BtnDel;
	@FXML private Button t1BtnExit;
	@FXML private TextField t1TextSearch;
	@FXML private Button t1BtnSearch;
	
	@FXML private Button t1BtnBarChart;
	@FXML private DatePicker t1DatePicker;
	@FXML private ToggleGroup group;
	@FXML private TableView<Student> t1TableView;
	@FXML private TextField t1TextNo;
	
	//����
	@FXML private Button t1BtnImage;
	@FXML private ImageView t1ImageView;
	
	
	
	ObservableList<Student> t1ListData = FXCollections.observableArrayList();
	ObservableList<String> t1ListGrade = FXCollections.observableArrayList();
	ObservableList<String> t1ListBan = FXCollections.observableArrayList();
	ArrayList<Student> dbArrayList;
	private Student selectStudent;
	private int selectStudentIndex;
	private boolean editFlag;
	//����
	private File selectFile = null;
	//������ �����ϱ� ���ؼ� ���� ����
	private File imageDirectory = new File("C:/images");
	private String fileName = "";
	
	//***************************�ι�° ȭ��*************************
	@FXML private Button tab2BtnSecond;
	
	
	
	//***************************����° ȭ��*************************
	@FXML private Button tab3BtnThird;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//0. DB  ���� ��û ��û
		//Connection con = DBUtility.getConnection();
		//1.���̺� �� �⺻ ���� ���� �Ѵ�. ������ ���̽����� ��ü �ڷḦ ���� �´�.
		setTab1TableView();
		//1.2�޺��ڽ�(�г�, ��) ������ �Ѵ�
		setComboBoxGradeBan();
		//1.3�ؽ�Ʈ �ʵ� �Է°� ���伳��
		setTextFieldInputFormat();
		//2.��ư�� �Է� �ʵ� �ʱ� ����
		setBtnTextFieldInitiate("ó��");
		//3.���� ��ư�� �������� ó�� �ؾ��ϴ� �Լ�
		t1Btnsum.setOnAction(e->{handleT1BtnTotalAction();});
		//4.��� ��ư�� �������� ó���ϴ� �Լ�
		t1Btnavg.setOnAction(e->{handleT1BtnAvgAction();});
		//5.�ʱ�ȭ ��ư�� �������� ó���ϴ� �Լ� 
		t1Btnclear.setOnAction(e->{handleT1BtnClearAction();});
		//6.��� ��ư�� �������� ó���ϴ� �Լ� 
		t1BtnReg.setOnAction(e->{handleT1BtnRegisterAction();});
		//7. ���� ��ư�� �������� ó���ϴ� �Լ� 
		t1BtnExit.setOnAction(e->{Platform.exit();});
		//8. ���̺� �並 Ŭ�� ������ ó���ϴ� �Լ�(�ѹ� Ŭ���ι�Ŭ�� ������Ʈ)
		t1TableView.setOnMouseClicked((e)-> {handleT1TableViewAction(e);});
		//9. ���� ��ưȰ��ȭ �Ǿ����� ó���ϴ� �Լ�
		t1BtnEdit.setOnMousePressed(e->{handleT1BtnEditAction();});
		//10. ���� ��ưȰ��ȭ �Ǿ����� ó���ϴ� �Լ�
		t1BtnDel.setOnMousePressed(e->{handleT1BtnDelAction();});
		//11. �˻� ��ưȰ��ȭ �Ǿ����� ó���ϴ� �Լ�
		t1BtnSearch.setOnMousePressed(e->{handleT1BtnSearchAction();});
		//12.����Ʈ ��ư�� Ŭ�������� ó���ϴ� �Լ�(����Ʈ�� ������� �Ѵ�)
		t1BtnBarChart.setOnAction(e->{handleT1BtnBarChartAction();});
		//13.�̹��� ��� ��ư�� Ŭ�� ������ ó���ϴ� �Լ�
		t1BtnImage.setOnAction(e->{handleT1BtnImageAction();});
	}

		//1.���̺� �� �⺻ ���� ���� �Ѵ�.������ ���̽����� ��ü �ڷḦ ���� �´�.
		private void setTab1TableView() {
			TableColumn tcNo = t1TableView.getColumns().get(0);
			tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
			tcNo.setStyle("-fx-alignment: CENTER;");
			TableColumn tcName = t1TableView.getColumns().get(1);
			tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
			tcName.setStyle("-fx-alignment: CENTER;");
			TableColumn tcGread = t1TableView.getColumns().get(2);
			tcGread.setCellValueFactory(new PropertyValueFactory<>("gread"));
			tcGread.setStyle("-fx-alignment: CENTER;");
			TableColumn tcBan = t1TableView.getColumns().get(3);
			tcBan.setCellValueFactory(new PropertyValueFactory<>("ban"));
			tcBan.setStyle("-fx-alignment: CENTER;");
			TableColumn tcGender = t1TableView.getColumns().get(4);
			tcGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
			tcGender.setStyle("-fx-alignment: CENTER;");
			TableColumn tcKor = t1TableView.getColumns().get(5);
			tcKor.setCellValueFactory(new PropertyValueFactory<>("kor"));
			tcKor.setStyle("-fx-alignment: CENTER;");
			TableColumn tcEng = t1TableView.getColumns().get(6);
			tcEng.setCellValueFactory(new PropertyValueFactory<>("eng"));
			tcEng.setStyle("-fx-alignment: CENTER;");
			TableColumn tcMat = t1TableView.getColumns().get(7);
			tcMat.setCellValueFactory(new PropertyValueFactory<>("mat"));
			tcMat.setStyle("-fx-alignment: CENTER;");
			TableColumn tcSci = t1TableView.getColumns().get(8);
			tcSci.setCellValueFactory(new PropertyValueFactory<>("sci"));
			tcSci.setStyle("-fx-alignment: CENTER;");
			TableColumn tcSoc = t1TableView.getColumns().get(9);
			tcSoc.setCellValueFactory(new PropertyValueFactory<>("soc"));
			tcSoc.setStyle("-fx-alignment: CENTER;");
			TableColumn tcMus = t1TableView.getColumns().get(10);
			tcMus.setCellValueFactory(new PropertyValueFactory<>("mus"));
			tcMus.setStyle("-fx-alignment: CENTER;");
			TableColumn tcTotal = t1TableView.getColumns().get(11);
			tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
			tcTotal.setStyle("-fx-alignment: CENTER;");
			TableColumn tcAvg = t1TableView.getColumns().get(12);
			tcAvg.setCellValueFactory(new PropertyValueFactory<>("avg"));
			tcAvg.setStyle("-fx-alignment: CENTER;");
			TableColumn tcDate = t1TableView.getColumns().get(13);
			tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
			tcDate.setStyle("-fx-alignment: CENTER;");
			TableColumn tcimagepath = t1TableView.getColumns().get(14);
			tcimagepath.setCellValueFactory(new PropertyValueFactory<>("imagepath"));
			tcimagepath.setStyle("-fx-alignment: CENTER;");
			
			
			//ObservableList<Student> t1ListData = FXCollections.observableArrayList();
			//t1ListData.add(new Student("��", "2�г�", "8��", "100", "100", "100", "100", "100", "100", "600", "100"));
			t1TableView.setItems(t1ListData);
			
			
			dbArrayList = StudentDAO.getStudentTotaldata();
			for(Student student : dbArrayList) {
				t1ListData.add(student);
				System.out.println(student);
			}
			
		
	}
		//1.2�޺��ڽ�(�г�, ��) ������ �Ѵ� 
		private void setComboBoxGradeBan() {
			t1ListGrade.addAll("1��","2��","3��","4��","5��","6��");
			t1ListBan.addAll("1��","2��","3��","4��","5��","6��");
			t1CmbGrade.setItems(t1ListGrade);
			t1CmbBan.setItems(t1ListBan);
			
		}
		//1.3.�ؽ�Ʈ �ʵ� �Է°� ���伳��
		private void setTextFieldInputFormat() {
			inputDecimalFormat(t1TextMath);
			inputDecimalFormat(t1TextSoc);
			inputDecimalFormat(t1TextEnglish);
			inputDecimalFormat(t1TextSci);
			inputDecimalFormat(t1Textmus);
			inputDecimalFormat(t1TextKorean);
			inputDecimalFormatTwoDigut(t1TextNo);
		}

		//2.��ư�� �Է� �ʵ� �ʱ� ����	
		private void setBtnTextFieldInitiate(String message) {
			t1Textsum.setEditable(false);
			t1Textavg.setEditable(false);
			switch(message) {
			case "ó��": 
				t1Btnsum.setDisable(false);
				t1Btnavg.setDisable(true);
				t1BtnEdit.setDisable(true);
				t1BtnDel.setDisable(true);
				t1BtnReg.setDisable(true);break;
			case "���� �� ����": 
				t1Btnsum.setDisable(false);
				t1Btnavg.setDisable(true);
				t1BtnEdit.setDisable(true);
				t1BtnDel.setDisable(false);
				t1BtnReg.setDisable(true);break;
			case "����" :
				t1Btnsum.setDisable(true);
				t1Btnavg.setDisable(false);
				t1BtnEdit.setDisable(true);
				t1BtnDel.setDisable(true);
				t1BtnReg.setDisable(true);break;
			case "���" :
				t1Btnsum.setDisable(true);
				t1Btnavg.setDisable(true);
				if(editFlag) {					
					t1BtnReg.setDisable(true);
					t1BtnEdit.setDisable(false);
					editFlag = false;
				}else {
					t1BtnReg.setDisable(false);
				}
				break;
			
			}
		}
		//3.���� ��ư�� �������� ó�� �ؾ��ϴ� �Լ�
		private void handleT1BtnTotalAction() {
			int kor = Integer.parseInt(t1TextKorean.getText());
			int math = Integer.parseInt(t1TextMath.getText());
			int eng = Integer.parseInt(t1TextEnglish.getText());
			int sci = Integer.parseInt(t1TextSci.getText());
			int soc = Integer.parseInt(t1TextSoc.getText());
			int mus = Integer.parseInt(t1Textmus.getText());
			String message = "���� �Է� :";
			if(kor>100) {
				message += ("kor = " + kor);
			}
			if(math>100) {
				message += ("math = " + math);
			}
			if(eng>100) {
				message += ("eng = " + eng);
			}
			if(sci>100) {
				message += ("sci = " + sci);
			}
			if(soc>100) {
				message += ("soc = " + soc);
			}
			if(mus>100) {
				message += ("mus = " + mus);
			}
			if(!message.equals("���� �Է� :")) {
				message +=" ������ �� �Է��� �ּ���";
				callAlert(message);
				return;
			}
			
			t1Textsum.setText(String.valueOf(kor+math+eng+sci+soc+mus));
			setBtnTextFieldInitiate("����");
			
		}
		//4.��� ��ư�� �������� ó���ϴ� �Լ�
		private void handleT1BtnAvgAction() {
			double total = Double.parseDouble(t1Textsum.getText())/6;
			t1Textavg.setText(String.format("%.2f", total));
			setBtnTextFieldInitiate("���");
			
		}
		//5.�ʱ�ȭ ��ư�� �������� ó���ϴ� �Լ� 
		private void handleT1BtnClearAction() {
			t1DatePicker.setValue(null);
			t1TextName.clear();
			t1CmbGrade.getSelectionModel().clearSelection();
			t1CmbBan.getSelectionModel().clearSelection();
			group.selectToggle(null);
			t1TextNo.clear();
//			t1TextGread.clear();
//			t1TextBan.clear();
			t1TextKorean.clear();
			t1TextEnglish.clear();
			t1TextMath.clear();
			t1TextSci.clear();
			t1TextSoc.clear();
			t1Textmus.clear();
			t1Textsum.clear();
			t1Textavg.clear();
			t1TableView.getSelectionModel().clearSelection();
			t1ImageView.setImage(new Image(getClass().getResource("../images/bagicimage.jpg").toString()));
			selectFile = null;
			fileName=null;
			setBtnTextFieldInitiate("ó��");
		}
		
		//6.��� ��ư�� �������� ó���ϴ� �Լ� 
		private void handleT1BtnRegisterAction() {
			
			imageSave();
			if(selectFile == null) {
				callAlert("�̹������� ���� : �̹����� ������ �ּ���.");
				return;
			}
			
			
			
			
			String studentNo = "";
			String number = "0";
			//10<1 ->01   
			if(t1TextNo.getText().trim().length() ==1) {
				number+=t1TextNo.getText().trim();
			}else {
				number=t1TextNo.getText().trim();
			}
			studentNo = t1CmbGrade.getSelectionModel().getSelectedItem().substring(0, 1)+t1CmbBan.getSelectionModel().getSelectedItem().substring(0, 1)+number;
				Student student = new Student(studentNo,
						t1TextName.getText(),
						t1CmbGrade.getSelectionModel().getSelectedItem(),
						t1CmbBan.getSelectionModel().getSelectedItem(),
						group.getSelectedToggle().getUserData().toString(),
						t1TextKorean.getText(), t1TextMath.getText(), 
						t1TextEnglish.getText(), t1TextSci.getText(), 
						t1TextSoc.getText(), t1Textmus.getText(), 
						t1Textsum.getText(), t1Textavg.getText(),
						t1DatePicker.getValue().toString(),
						fileName);//���� ��� file:/D://java_project
			
				t1ListData.add(student);
			int count = StudentDAO.insertStudentData(student);
			if(count !=0) {
				callAlert("���� : ����");
			}
				
			handleT1BtnClearAction();
		}
		//8. ���̺� �並 Ŭ�� ������ ó���ϴ� �Լ�(�ѹ� Ŭ�� �ι�Ŭ�� ������Ʈ)
		private void handleT1TableViewAction(MouseEvent e) {
			selectStudent = t1TableView.getSelectionModel().getSelectedItem();
			selectStudentIndex = t1TableView.getSelectionModel().getSelectedIndex();
			if(e.getClickCount()==1) {
				
				t1DatePicker.setValue(LocalDate.parse(selectStudent.getDate()));
				t1TextName.setText(selectStudent.getName());
				t1CmbGrade.getSelectionModel().select(selectStudent.getGread());
				t1CmbBan.getSelectionModel().select(selectStudent.getBan());
				t1TextNo.setText(selectStudent.getNo().substring(2));
				if(selectStudent.getGender().equals("����")) {
					t1RdoMale.setSelected(true);
				}else {
					t1RdoFemale.setSelected(true);
				}
//				t1TextGread.clear();
//				t1TextBan.clear();
				t1TextKorean.setText(selectStudent.getKor());
				t1TextEnglish.setText(selectStudent.getEng());
				t1TextMath.setText(selectStudent.getMat());
				t1TextSci.setText(selectStudent.getSci());
				t1TextSoc.setText(selectStudent.getSoc());
				t1Textmus.setText(selectStudent.getMus());
				t1Textsum.clear();
				t1Textavg.clear();
				Image image = new Image("file:///"+imageDirectory.getAbsolutePath()+"/"+selectStudent.getImagepath());
				t1ImageView.setImage(image);
				//�߿���  ���� ����� Ȱ��ȭ ��Ų��  -> ����� ��������
				editFlag = true;
			setBtnTextFieldInitiate("���� �� ����");
			}else if(e.getClickCount()==2) {
				try {
					Stage pieStage = new Stage(StageStyle.UTILITY);
					pieStage.initModality(Modality.WINDOW_MODAL);
					pieStage.initOwner(mainStage);
					pieStage.setTitle(selectStudent.getName()+"������Ʈ");
					FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/piechart.fxml"));
					Parent root = loder.load();
					//*********���̵� ã�Ƽ� �;� �Ѵ�. = @FXML private Button c1BtnExit**************
					PieChart c2PieChart = (PieChart)root.lookup("#c2PieChart");
					Button c2BtnExit = (Button)root.lookup("#c2BtnExit");
					
					//*********�̺�Ʈ ��� �� �ʱ�ȭ �ڵ鷯���� ******************//
					ObservableList pieList = FXCollections.observableArrayList();
					pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getKor())));
					pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getEng())));
					pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getMat())));
					pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getSci())));
					pieList.add(new PieChart.Data("��ȸ", Integer.parseInt(selectStudent.getSoc())));
					pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getMus())));
					
					c2PieChart.setData(pieList);
					
					
					
					c2BtnExit.setOnAction(e1->{pieStage.close();});
					
					
					
					Scene scene = new Scene(root);
					pieStage.setScene(scene);
					pieStage.show();
					} catch (Exception e1) {
						callAlert("������Ʈâ ���� : ���� ��Ʈ â ������ �߻��Ǿ����ϴ�.");
					}
			}else {return;}
			
		}
		//���� ���*****************************************
		//9. ���� ��ưȰ��ȭ �Ǿ����� ó���ϴ� �Լ�
		private void handleT1BtnEditAction() {
//			callAlert("�̹��� ��� :"  + t1ImageView.getImage().getClass().getName());
			if(!fileName.equals(null)) {
				imageDelete();
				imageSave();
				
				
			}
			
			
			String studentNo = "";
			String number = "0";
			//10<1 ->01   
			if(t1TextNo.getText().trim().length() ==1) {
				number+=t1TextNo.getText().trim();
			}else {
				number=t1TextNo.getText().trim();
			}
			studentNo = t1CmbGrade.getSelectionModel().getSelectedItem().substring(0, 1)+t1CmbBan.getSelectionModel().getSelectedItem().substring(0, 1)+number;
			
			
			Student student = new 	Student(studentNo,t1TextName.getText(),
					t1CmbGrade.getSelectionModel().getSelectedItem().toString(),
					t1CmbBan.getSelectionModel().getSelectedItem().toString(),
					group.getSelectedToggle().getUserData().toString(),
					t1TextKorean.getText(), t1TextMath.getText(), 
					t1TextEnglish.getText(), t1TextSci.getText(), 
					t1TextSoc.getText(), t1Textmus.getText(), 
					t1Textsum.getText(), t1Textavg.getText(),
					t1DatePicker.getValue().toString(),
					fileName);  //������
			
			int count = StudentDAO.updateStudentDate(student);
			if(count !=0) {
				
				t1ListData.remove(selectStudentIndex);
				t1ListData.add(selectStudentIndex,student);
				int arrayIndex = dbArrayList.indexOf(selectStudent);
				dbArrayList.set(arrayIndex, student);
				handleT1BtnClearAction();
				callAlert("�����Ϸ� : "+selectStudent.getName()+"���� �����Ǿ����ϴ�.");
			}else {return;}
			
		}
		//10. ���� ��ưȰ��ȭ �Ǿ����� ó���ϴ� �Լ�
		private void handleT1BtnDelAction() {
			int count = StudentDAO.deleteStudentData(selectStudent.getNo());
			if(count!=0) {
				t1ListData.remove(selectStudentIndex); 
				System.out.println("������"+dbArrayList.size());
				dbArrayList.remove(selectStudent);
				System.out.println("������"+dbArrayList.size());
				editFlag = false;
				
				imageDelete();
				
				//�ʱ�ȭ �������� �Լ� ��
				setBtnTextFieldInitiate("ó��");
				callAlert("���� �Ϸ� : ������ �Ϸ� �Ǿ����ϴ�. ");
				
			}else {return;}
		}
		

		//11. �˻� ��ưȰ��ȭ �Ǿ����� ó���ϴ� �Լ�
		private void handleT1BtnSearchAction() {
			for(Student  stu   :t1ListData) {
				if(t1TextSearch.getText().trim().equals(stu.getName())) {
					t1TableView.getSelectionModel().select(stu);
				}
			}//end for each
		}
		//12.����Ʈ ��ư�� Ŭ�������� ó���ϴ� �Լ�(����Ʈ�� ������� �Ѵ�)
		private void handleT1BtnBarChartAction() {
			try {
			Stage bcStage = new Stage(StageStyle.UTILITY);
			bcStage.initModality(Modality.WINDOW_MODAL);
			bcStage.initOwner(mainStage);
			bcStage.setTitle("����Ʈ");
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/barchart.fxml"));
			Parent root = loder.load();
			//*********���̵� ã�Ƽ� �;� �Ѵ�. = @FXML private Button c1BtnExit**************
			BarChart c1BarChart = (BarChart)root.lookup("#c1BarChart");
			Button c1BtnExit = (Button)root.lookup("#c1BtnExit");
			
			//*********�̺�Ʈ ��� �� �ʱ�ȭ �ڵ鷯���� ******************//
			XYChart.Series seriesKor = new XYChart.Series<>();
			seriesKor.setName("����");
			ObservableList korList = FXCollections.observableArrayList();
			for(int i=0;i<t1ListData.size();i++) {
				korList.add(new XYChart.Data<>(t1ListData.get(i).getName(),Integer.parseInt(t1ListData.get(i).getKor())));
			}
			seriesKor.setData(korList);
			c1BarChart.getData().add(seriesKor);
			
			
			XYChart.Series seriesEng = new XYChart.Series<>();
			seriesEng.setName("����");
			ObservableList engList = FXCollections.observableArrayList();
			for(int i=0;i<t1ListData.size();i++) {
				engList.add(new XYChart.Data<>(t1ListData.get(i).getName(),Integer.parseInt(t1ListData.get(i).getEng())));
			}
			seriesEng.setData(engList);
			c1BarChart.getData().add(seriesEng);
			
			c1BtnExit.setOnAction(e->{bcStage.close();});
			
			
			
			Scene scene = new Scene(root);
			bcStage.setScene(scene);
			bcStage.show();
			} catch (Exception e) {
				callAlert("����Ʈâ ���� : �� ��Ʈ â ������ �߻��Ǿ����ϴ�.");
			}
		}
		//13.�̹��� ��� ��ư�� Ŭ�� ������ ó���ϴ� �Լ�(����â�� �������� �Ѵ�.)
		private void handleT1BtnImageAction() {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File","*.png","*.jpg","*.gif"));
			selectFile = fileChooser.showOpenDialog(mainStage);
			String localURL = null;
			if(selectFile !=null) {
				try {
					localURL = selectFile.toURI().toURL().toString();
				} catch (MalformedURLException e) {		}
			}
			t1ImageView.setImage(new Image(localURL,false));//����ȭ�� �������� �������°� �ƴϴ�.?��¥ ��ü�� �����´�
			fileName = selectFile.getName();//���õ� ���ϸ��� �ش�  fileName�� �ݵ�� �̹��� ������ ���������� ���� �����Ѵ�.
			//callAlert("���õ� �̹��� : " + fileName);
		}
		
	//��Ÿ �˸�â "�������� : ���� ����� �Է��� �ּ���"  �߰��� �� :�� ������
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("���");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
	
	//��Ÿ �Է°� �ʵ� ���� ���� ��� : ���� ���ڸ��� �޴� ����� ������
	private void inputDecimalFormat(TextField textField) {
		// ���ڸ� �Է�(������ �Է¹���), �Ǽ��Է¹ް������ new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("###");
		// ���� �Է½� ���� ���� �̺�Ʈ ó��
		textField.setTextFormatter(new TextFormatter<>(event -> {  
			//�Է¹��� ������ ������ �̺�Ʈ�� ������.  
		if (event.getControlNewText().isEmpty()) { return event; }
		//������ �м��� ���� ��ġ�� ������. 
		    	ParsePosition parsePosition = new ParsePosition(0);
			//�Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
		    	Object object = format.parse(event.getControlNewText(), parsePosition); 
		//���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.) �̸� null ������. 
		if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
		      || event.getControlNewText().length() == 4) {
		     return null;    
		}else {
		     return event;    
		}   
		}));

	}
	

	private void inputDecimalFormatTwoDigut(TextField t1TextNo2) {
		DecimalFormat format = new DecimalFormat("##");
		// ���� �Է½� ���� ���� �̺�Ʈ ó��
		t1TextNo2.setTextFormatter(new TextFormatter<>(event -> {  
			//�Է¹��� ������ ������ �̺�Ʈ�� ������.  
		if (event.getControlNewText().isEmpty()) { return event; }
		//������ �м��� ���� ��ġ�� ������. 
		    	ParsePosition parsePosition = new ParsePosition(0);
			//�Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
		    	Object object = format.parse(event.getControlNewText(), parsePosition); 
		//���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.) �̸� null ������. 
		if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
		      || event.getControlNewText().length() == 3) {
		     return null;    
		}else {
		     return event;    
		}   
		}));
		
	}
	
	//��Ÿ �̹��� C:/images/student123412341234_���õ� ���ϸ����� �����Ѵ�.
	private void imageSave() {
		if(!imageDirectory.exists()) {
			imageDirectory.mkdir();   //���丮�� ������ �ȵǾ� ������ ������ �����.
		}
		FileInputStream fis=null;
		BufferedInputStream bis =null;
		FileOutputStream fos=null;
		BufferedOutputStream bos = null;
		//���õ� �̹����� c:/images/  ���õ� �̹��� �̸������� �����Ѵ�
		try {
			fis = new FileInputStream(selectFile);
			bis = new BufferedInputStream(fis);
			
			//FileChooser���� ���õ� ���ϸ��� C:/kkk/ppp/jjj/name.jpg
			//selecFile.getName()  -> name.jpg �� �����´�
			//���ο� ���� ���� ���� �ϴµ� -> student+12345678912_name.jpg
			//imageDirectory.getAbsolutePath()+"\\"+fileName 
			//-> C:/images/student+12345678912_name.jpg �̸����� �����Ѵ�
			//C:/kkk/ppp/jjj/name.jpg �о   C:/images/student+12345678912_name.jpg����� �����Ѵ�
			fileName ="student"+ System.currentTimeMillis()+"_"+selectFile.getName();
			fos = new FileOutputStream(imageDirectory.getAbsolutePath()+"\\"+fileName);
			bos = new BufferedOutputStream(fos);
			int data=-1;
			while((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();
			}
			
		} catch (Exception e) {
			callAlert("�̹��� ���� ���� : c/images/�������� ���� ���� �ٶ�");
		}finally {
			try {
			if(fis != null) {fis.close();}
			if(bis != null) {bis.close();}
			if(fos != null) {fos.close();}
			if(bos != null) {bos.close();}
			} catch (IOException e) {}
		}
			
	}
	//�̹�������
	private void imageDelete() {
		boolean delFlag = false;
		File imageFile = new File(imageDirectory.getAbsolutePath()+"\\"+selectStudent.getImagepath());
		if(imageFile.exists() && imageFile.isFile()) {
			delFlag = imageFile.delete();
			if(delFlag == false) {callAlert("�̹��� ���� ����:���Ž��� ���˹ٶ�");}
		}
	}
	
}
