package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class RootController implements Initializable{
	public Stage primaryStage;
	@FXML private TextField textId;
	@FXML private PasswordField textPassword;
	@FXML private Button btnLogin;
	@FXML private Button btnClose;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textPassword.setOnKeyPressed(event -> {
	          if (event.getCode().equals(KeyCode.ENTER)) {
	             handlerBtnLoginAction();
	          }
	       });
		btnLogin.setOnAction(e->{handlerBtnLoginAction();});
		
		btnClose.setOnAction(e->{handlerBtnCloseAction();});
		
	}

		private void handlerBtnLoginAction() {
		if(textId.getText().equals("root") && textPassword.getText().equals("123456")) {
			callAlert("�α��� ���� : "+textId.getText() +"�� ȯ���մϴ�. ");
		}else {
			callAlert("�α��� ���� : ���̵� ��� ��ȣ�� Ȯ���� �ּ���");		
			textId.clear(); textPassword.clear();
			return;
		}
		
		try {
			Stage mainStage = new Stage();
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/main.fxml"));
			Parent root = loder.load();
			MainController mainController = loder.getController();
			mainController.mainStage = mainStage;
			
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			primaryStage.close();
			mainStage.show();
			
			callAlert("ȭ�� ��ȯ ���� : ���� ȭ������ ��ȯ �Ǿ����ϴ�.");
			
			
		} catch (Exception e) {
			callAlert("ȭ�� ��ȯ ���� : ȭ�� ��ȯ�� ������ �ֽ��ϴ�.���� �ٶ��ϴ�.");
		}

	}
		
	private void handlerBtnCloseAction() {
			Platform.exit();
	}
		
		//��Ÿ �˸�â "�������� : ���� ����� �Է��� �ּ���"  �߰��� �� :�� ������
	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("���");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}

}
