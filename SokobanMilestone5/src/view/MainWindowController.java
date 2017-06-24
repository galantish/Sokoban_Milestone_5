package view;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import commons.Level;
import commons.Record;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

public class MainWindowController extends Observable implements iView, Initializable, Observer
{
	//SokobanDisplayer
	@FXML private SokobanDisplayer sokobanDisplayer;	

	//Music
	@FXML private MediaView mediaView;
	private MediaPlayer mediaPlayer;
	private Media media;
	private boolean isStop;
	@FXML private Button musicButton;
	
	//Errors
	@FXML private Label status;
	
	//Steps
	@FXML private Text countText;

	//Timer
	@FXML private Text timerText;
	private Timer timer;
	private StringProperty CounterTime;
	private int seconds;
	private int minutes;
	private boolean isLoadFromGUI;
	
	//Stage
	private Stage primaryStage;
	private Stage secondStage;

	//Keyboard setting
	private KeySettings keySettings;
		
	//Records
	private RecordViewController recordController;
	
	//Finish
	private boolean isFinish;
	
	//The level
	private String theLevelID;
	
	public MainWindowController() 
	{
		this.isFinish = false;
		this.status = new Label();
		this.CounterTime = new SimpleStringProperty();
		this.isStop = false;
		this.seconds = 0;
		this.minutes = 0;
		this.isLoadFromGUI = false;
		this.keySettings = initKeySetting("./resources/Settings/keySettings.xml");
		this.theLevelID = "";
	}
	
	public void setRecordViewController(RecordViewController recordController)
	{
		this.recordController = recordController;
	}
	
	public void showRecords()
	{
		if(this.theLevelID != "")
			this.recordController.setLevelIDInput(this.theLevelID);
		this.recordController.search();
		this.secondStage.show();				
	}
	
	private KeySettings initKeySetting(String path)
 	{
		XMLDecoder xmlDecoder;
 		KeySettings keySetting = null;
 		try 
 		{
			xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(new File(path))));
	 		keySetting = (KeySettings) xmlDecoder.readObject();
	 		xmlDecoder.close();
		} 
 		catch (FileNotFoundException e) 
 		{
			e.printStackTrace();
		}
 		return keySetting;
 	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		sokobanDisplayer.initCanvas();
		playAutoMusic();
		setFocus();
		sokobanDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->sokobanDisplayer.requestFocus());		
		sokobanDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent event) 
			{
				String command = null;
				status.setText("");
				
				if(event.getCode() == keySettings.getMoveLeft())
					command = "move left";	
				else if(event.getCode() == keySettings.getMoveRight())
					command = "move right";
				else if(event.getCode() == keySettings.getMoveUp())
					command = "move up";
				else if(event.getCode() == keySettings.getMoveDown())
					command = "move down";
				else
				{
					command = null;
					displayError("Invalid key.");
				}
				
				if(command != null)
				{
					sokobanDisplayer.setDirection(command);
					setChanged();
					notifyObservers(command);
				}
			}
		});	
	}
	
	@Override
	public void displayLevel(Level theLevel) 
	{
		this.theLevelID = theLevel.getLevelID();
		this.sokobanDisplayer.setLevelData(theLevel.getLevelBoard());
		
		recordController.setTheLevelID(theLevel.getLevelID());
		status.setText("");
		
		if(theLevel.isFinished() == true)
		{
			finishLevel();
			stopTimer();
		}

		if(isLoadFromGUI == false)
		{
			startTimer(0,0);
			this.isLoadFromGUI = true;
		}
	}
	
	@Override
	public void setPrimaryStage(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		exitPrimaryStage(this.primaryStage);
	}
 	
	@Override
	public void setSecondStage(Stage secondStage)
	{
		this.secondStage = secondStage;
	}
	
	@Override
	public void exitPrimaryStage(Stage primaryStage)
	{
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		{
			@Override
			public void handle(WindowEvent event) 
			{
				setChanged();
				notifyObservers("exit");
			}
		});
	}
	
	@Override
	public void createBindSteps(StringProperty Counter)
	{
		this.countText.textProperty().bind(Counter);
	}
		
	private void startTimer(int sec, int min) 
	{	
		this.seconds = sec;
		this.minutes = min;
		this.timer = new Timer();	
		this.timerText.textProperty().bind(this.CounterTime);
		this.timer.scheduleAtFixedRate(new TimerTask() 
		{
			@Override
			public void run() 
			{
				seconds++;
				if(seconds > 59)
				{
					minutes++;
					seconds = 0;
				}
				if(minutes < 10)
				{
					if(seconds < 10)
						CounterTime.set("0" + (minutes) + ":0" + (seconds));
					else
						CounterTime.set("0" + (minutes) + ":" + (seconds));
				}
				else
					CounterTime.set("" + (minutes) + ":" + (seconds));
			}
		}, 0, 1000);
	}
	
	private void stopTimer()
	{
		if(timer != null)
			timer.cancel();
	}
	
	public void openFile()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Load level");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text File", "*.txt"), new ExtensionFilter("XML File", "*.xml"), new ExtensionFilter("Object File", "*.obj"));

		File choosenFile = fc.showOpenDialog(this.primaryStage);
		if(choosenFile == null)
			return;
		
		setChanged();
		notifyObservers("load " + choosenFile.getPath());
		this.isLoadFromGUI = true;
		this.isFinish = false;
		stopTimer();
		startTimer(0,0);
	}
	
	public void saveFile()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Save level");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text File", "*.txt"), new ExtensionFilter("XML File", "*.xml"), new ExtensionFilter("Object File", "*.obj"));
		File choosenFile = fc.showSaveDialog(null);
		if(choosenFile != null)
		{
			setChanged();
			notifyObservers("save " + choosenFile.getPath());
		}
	}
	
	public void exit()
	{
		stopTimer();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to exit?");
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK)
		{
			setChanged();
			notifyObservers("exit");
			Platform.exit();
		}
		
		if(timer != null)
			startTimer(this.seconds, this.minutes);
	}
	
	private void finishLevel()
	{
		if(this.isFinish == true)
			return;
		
		Platform.runLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Level complated");
				alert.setHeaderText("Congratulations! You win!!");
				alert.setContentText("Steps: " + countText.getText() + "\nTime: " + timerText.getText() + " seconds.\nDo you want to save your score?");
				
				Optional<ButtonType> firstResult = alert.showAndWait();
				
				if (firstResult.get() == ButtonType.OK)
				{
					isFinish = true;
					
					// Create the custom dialog
					Dialog<Pair<String, String>> dialog = new Dialog<>();
					dialog.setTitle("Account Dialog");
					dialog.setHeaderText("Create Your Account");

					// Set the button types
					ButtonType submitButtonType = new ButtonType("Submit", ButtonData.OK_DONE);
					dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

					// Create the UserID and UserName labels and fields
					GridPane grid = new GridPane();
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(20, 150, 10, 10));

					TextField userName = new TextField();
					userName.setPromptText("204587802");

					grid.add(new Label("User Name:"), 0, 0);
					grid.add(userName, 1, 0);

					//Enable/Disable submit button depending on whether a UserID was entered
					Node submitButton = dialog.getDialogPane().lookupButton(submitButtonType);
					submitButton.setDisable(true);

					// Do some validation (using the Java 8 lambda syntax)
					userName.textProperty().addListener((observable, oldValue, newValue) -> 
					{
						
						submitButton.setDisable(newValue.trim().isEmpty());
						submitButton.setDisable(oldValue.trim().isEmpty());
					});

					dialog.getDialogPane().setContent(grid);

					// Request focus on the UserID field by default
					Platform.runLater(() -> userName.requestFocus());

					// Convert the result to a UserID-Username-pair when the login button is clicked.
					dialog.setResultConverter(dialogButton -> 
					{
					    if (dialogButton == submitButtonType) 
					    {
					        return new Pair<>(null, userName.getText());
					    }
					    return null;
					});

					Optional<Pair<String, String>> result = dialog.showAndWait();

					result.ifPresent(userID -> 
					{
					    setChanged();
					    notifyObservers("adduser " + userID.getValue());
					    setChanged();
					    notifyObservers("addRecord " + userID.getValue() + " " + CounterTime.getValue());
					});
				}
				
				else
					System.out.println("The user doesn't want to save his score!");
			}
		});
		stopTimer();
	}
	
	private void playAutoMusic()
	{
		try 
		{
			ImageView mute = new ImageView(new Image(new FileInputStream("./resources/Images/on.jpg")));
			mute.setFitWidth(25);
			mute.setFitHeight(25);
			this.musicButton.setGraphic(mute);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		media = new Media(new File("./resources/Songs/pacman.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setOnEndOfMedia(null);
	}
	
	public void playMusic()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Song file");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("MP3", "*.mp3"), new ExtensionFilter("MP4", "*.mp4"));

		File choosenFile = fc.showOpenDialog(null);
		
		if(choosenFile != null)
		{
			this.isStop = false;
			stopMusic();
			String path = choosenFile.getAbsolutePath();
			media = new Media(new File(path).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			mediaView.setMediaPlayer(mediaPlayer);
			mediaPlayer.setAutoPlay(true);
			mediaPlayer.setOnEndOfMedia(null);
			musicImageOn();
		}
	}
	
	public void stopMusic()
	{
		if(isStop == false)
		{			
			musicImageOff();
			mediaPlayer.pause();
			isStop = true;
		}
		else
		{
			musicImageOn();
			mediaPlayer.play();
			isStop = false;
		}
	}
	
	private void musicImageOn()
	{
		try 
		{
			ImageView mute = new ImageView(new Image(new FileInputStream("./resources/Images/on.jpg")));
			mute.setFitWidth(25);
			mute.setFitHeight(25);
			this.musicButton.setGraphic(mute);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void musicImageOff()
	{
		try 
		{
			ImageView mute = new ImageView(new Image(new FileInputStream("./resources/Images/off.jpg")));
			mute.setFitWidth(25);
			mute.setFitHeight(25);
			this.musicButton.setGraphic(mute);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void displayError(String msg) 
	{
		Platform.runLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				status.setText("ERROR: " + msg);
			}
		});
	}
	
	private void setFocus()
	{
		sokobanDisplayer.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) 
            {
                Platform.runLater(new Runnable()
                {
                    public void run() 
                    {
                    	sokobanDisplayer.requestFocus();
                    }
                });                    
            }
        });
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		setChanged();
		notifyObservers(arg);
	}

	@Override
	public void showDBRecords(List<Record> recordsList) 
	{
		this.recordController.showDBRecord(recordsList, this.secondStage);
	}
}
