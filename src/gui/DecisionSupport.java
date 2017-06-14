package gui;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.tk.Toolkit;

import controller.Controller;
import event.Event;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
import gui.dialogs.LogoutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Database;
import model.EventManager;
import model.Route;

public class DecisionSupport implements DataEntryGUI, EventHandler<ActionEvent>{
	BusinessMonitor bm;
	EventManager eventManager;
	Database fullEventDatabase;
	Controller controller;
	int counter = 0;
	Button forwardButton;
	Button backwardButton;
	private Scene scene = null;

	public DecisionSupport(Controller controller){
		BorderPane root = new BorderPane();

		this.fullEventDatabase = controller.getDatabase();
		System.out.println(fullEventDatabase.getEvent().isEmpty());
		this.controller = controller;

		forwardButton = new Button();
		forwardButton.setText("Forward");
		forwardButton.setOnAction(this);

		backwardButton = new Button();
		backwardButton.setText("Backward");
		backwardButton.setOnAction(this);

		bm = new BusinessMonitor(this);
		VBox vbox = bm.vbox();
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(forwardButton, backwardButton);
		VBox mainContainer = new VBox();
		mainContainer.setAlignment(Pos.TOP_CENTER);
		mainContainer.getChildren().addAll(vbox);


		scene = new Scene(root, 600, 400);
		root.setCenter(mainContainer);
		root.setBottom(hbox);

		initialize();

	}

	@Override
	public void showError(String errmsg) {
		// TODO Auto-generated method stub

	}

	public void initialize(){
		Database db = new Database();
		ArrayList <Event> e = new ArrayList <Event> ();
		e.add(fullEventDatabase.getEvent().get(0));
		db.setEvent(e);

		eventManager = new EventManager(null,db);
		eventManager.getNewRoutes();
	}

	public void handle(ActionEvent event) {

		if(event.getSource() == forwardButton){
			clickForward();
		}
		else{
			clickBackward();
		}

	}

	public void clickForward(){
		counter++;
		if(counter > fullEventDatabase.getEvent().size()-1){ counter =  fullEventDatabase.getEvent().size()-1;};
		Event eve = fullEventDatabase.getEvent().get(counter);
		eventManager.processEvent(eve);
		Route route = eventManager.getRoute(eve);
		if(route != null){
		displayRoute(route);}
		else{
			System.out.print("Route is null");
		}
	}

	public void clickBackward(){
		counter--;
		if(counter < 0 ){counter = 0;};
		Event eve = fullEventDatabase.getEvent().get(counter);
		Route route = eventManager.getRoute(eve);
		displayRoute(route);
	}

	public Scene scene() {
		return scene;
	}
	@Override
	public Controller getController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayRoute(Route route) {
		bm.display(route);


	}
}