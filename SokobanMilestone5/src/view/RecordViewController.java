package view;

import java.util.List;
import java.util.Observable;
import commons.Record;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RecordViewController extends Observable
{
	@FXML private TextField userField;
	@FXML private TextField levelField;
	@FXML private Button search;
	@FXML private ToggleGroup group;
	
	@FXML private TableView<TableRow> table;
	@FXML private AnchorPane anc;

	private String userIDInput;
	private String levelIDInput;
	private String orderByInput;
	
	private String theLevelID;
	
	private ObservableList<TableRow> rows;
	private TableColumn<TableRow, Integer> record;
	private TableColumn<TableRow, String> levelID;
	private TableColumn<TableRow, String> userName;
	private TableColumn<TableRow, Integer> steps;
	private TableColumn<TableRow, String> time;
	
	private Stage stage;
	
	public RecordViewController() 
	{
		init();
	}
	
	public void init()
	{
		this.orderByInput = "steps";
		this.userIDInput = "";
		this.levelIDInput = "";
		
	}
	
	@SuppressWarnings("unchecked")
	public void showDBRecord(List<Record> recordsList, Stage stage)
	{
		this.stage = stage;
		changeRecordToTableRow(recordsList);
		
		this.record=new TableColumn<TableRow, Integer>("Record ID");
		this.record.setCellValueFactory(new PropertyValueFactory<TableRow, Integer>("record"));
		this.record.setPrefWidth(191);
		
		this.levelID=new TableColumn<TableRow, String>("Level ID");
		this.levelID.setCellValueFactory(new PropertyValueFactory<TableRow, String>("levelID"));
		this.levelID.setPrefWidth(203);

		this.userName=new TableColumn<TableRow, String>("User ID");
		this.userName.setCellValueFactory(new PropertyValueFactory<TableRow, String>("userName"));
		this.userName.setPrefWidth(181);

		this.steps=new TableColumn<TableRow, Integer>("Steps");
		this.steps.setCellValueFactory(new PropertyValueFactory<TableRow, Integer>("steps"));
		this.steps.setPrefWidth(169);

		this.time=new TableColumn<TableRow, String>("Time");
		this.time.setCellValueFactory(new PropertyValueFactory<TableRow, String>("time"));
		this.time.setPrefWidth(210);

		this.table.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent arg0) 
			{
				TableRow tr = table.getSelectionModel().getSelectedItem();
				userIDInput = tr.getUserName();
				search();
			}
		});
		
		Platform.runLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				table.setEditable(true);
				table.getColumns().setAll(record, levelID, userName, steps, time);
				table.setItems(rows);				
			}
		});
		
		exitSecondStage();
	}
	
	public void exitSecondStage()
	{
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		{
			@Override
			public void handle(WindowEvent event) 
			{
				clear();
				System.out.println("red");
			}
		});
	}
	
	public String getTheLevelID() 
	{
		return theLevelID;
	}

	public void setTheLevelID(String theLevelID) 
	{
		this.theLevelID = theLevelID;
	}

	public void changeRecordToTableRow(List<Record> recordsList)
	{
		this.rows = FXCollections.observableArrayList();
		for(Record r: recordsList)
		{
			this.rows.add(new TableRow(r.getRecord(), r.getLevelID(), r.getUserName(), r.getSteps(), r.getTime()));
		}
	}
	
	public void searchBy()
	{
		this.userIDInput = userField.getText();
		this.levelIDInput = levelField.getText();
	}
	
	public void clear()
	{
		init();
		search();
	}
	
	public void orderBy()
	{
		RadioButton radioButton = (RadioButton) group.getSelectedToggle();
		this.orderByInput = radioButton.getText();
	}
	
	public void search()
	{
		setChanged();
		notifyObservers("query " + this.levelIDInput + " " + this.userIDInput + " " + this.orderByInput);
	}
	
	public void setUserIDInput(String userIdInput)
	{
		this.userIDInput = userIdInput;
	}
	
	public void setLevelIDInput(String levelIDInput)
	{
		this.levelIDInput = levelIDInput;
	}
}