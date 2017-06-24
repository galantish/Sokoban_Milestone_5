package view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class UserAccountController implements Initializable
{
	@FXML private TextField userID;
	@FXML private TextField username;
	@FXML private TextField phone;
	@FXML private Button submit;
	
	public void submit()
	{
		String id = userID.getText();
		String name = username.getText();
		String number = phone.getText();
		System.out.println("USerID: " + id + "\nUserName: " + name + "\nPhoneNumber: " + number);
		
		if(!id.equals(""))
		{
			Alert alert = new Alert(AlertType.NONE);
			alert.setTitle("Information");
			alert.setContentText("Your account has been created successfully :)");
			alert.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
			
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == ButtonType.FINISH)
			{
				Platform.exit();
			}
		}
		
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error while trying to create the user.\nPlease try again :)");
			alert.show();
		}
		
	}
	
	public void cencel()
	{
		System.out.println("Goodbye");
		Platform.exit();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
	
		
	}
}
