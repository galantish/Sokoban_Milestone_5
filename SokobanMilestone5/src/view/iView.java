package view;

import java.util.List;

import commons.Level;
import commons.Record;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;

public interface iView 
{
	public void displayLevel(Level theLevel); 
	public void displayError(String msg);
	public void createBindSteps(StringProperty Counter);
	public void setPrimaryStage(Stage primaryStage);
	public void exitPrimaryStage(Stage primaryStage);
	public void setSecondStage(Stage secondStage);
	public void showDBRecords(List<Record> records);
}
