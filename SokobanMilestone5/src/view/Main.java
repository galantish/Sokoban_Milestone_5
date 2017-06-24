package view;
	
import java.io.FileInputStream;
import java.util.List;
import controller.SokobanController;
import controller.server.SokobanClientHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MyModel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application 
{	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));				 
			BorderPane root = (BorderPane) mainLoader.load();
			MainWindowController view = mainLoader.getController();
			MyModel model = new MyModel();
			
			SokobanController sokobanController;
			
			//Get the ARGS from the main
			List<String> args = getParameters().getRaw();
			
			//Running with the server
			if((args.size() > 0) && (args.get(0).toLowerCase().equals("-server")))
			{
				int port = Integer.parseInt(args.get(1));
				SokobanClientHandler clientHandler = new SokobanClientHandler();
				sokobanController = new SokobanController(model, view, clientHandler, port);
				clientHandler.addObserver(sokobanController);
			}
			
			//Running without the server
			else
				sokobanController = new SokobanController(model, view);

			model.addObserver(sokobanController);
			view.addObserver(sokobanController);	
			view.setPrimaryStage(primaryStage);
			
			Scene sceneRoot = new Scene(root,1200,900);
			sceneRoot.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Pac-Ban");
			primaryStage.getIcons().add(new Image(new FileInputStream("./resources/Images/1.gif")));
			primaryStage.setScene(sceneRoot);
			
			Stage secondStage = new Stage();
			
			FXMLLoader recordLoader = new FXMLLoader(getClass().getResource("RecordView.fxml"));
			AnchorPane recordPane = (AnchorPane) recordLoader.load();
			RecordViewController recordController = recordLoader.getController();
			recordController.addObserver(view);
			view.setRecordViewController(recordController);
			
			Scene sceneRecord = new Scene(recordPane,976,803);
			sceneRecord.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    view.setSecondStage(secondStage);
		    secondStage.setTitle("Records");
		    secondStage.setScene(sceneRecord);
			
			primaryStage.show();	
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
