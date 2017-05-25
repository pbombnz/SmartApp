package controller;

import event.Event;
import gui.*;
import gui.base.DataEntryGUI;
import javafx.stage.Stage;
import model.BusinessModel;
import model.User;

import java.util.List;

public class Controller {
	public static final String MAIL = "MAIL";
	public static final String LOGIN = "LOGIN";
	public static final String EVENTGUI = "EVENTGUI";
	public static final String TRANSPORTDISC = "TRANSPORTDISC";

	Stage primaryStage;
	DataEntryGUI currentView;


	// Global System Components
	private BusinessModel model;
	private UserDatabase userDatabase;
	private User loggedIn;

	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.userDatabase = new UserDatabase();
	}

	public void handleEvent(Event entry, DataEntryGUI sourceView) {
		this.currentView = sourceView;

		// first check for accuracy
		if (validateEvent(entry)) {

			// Then send it to model
			model.processEvent(entry);
		}
		model.processEvent(entry);
	}

	public void handleEvent(String nextScreen) {

		if (nextScreen.equals(MAIL)) {
			MailDelivery mailDeliveryGUI = new MailDelivery(this);
			primaryStage.setScene(mailDeliveryGUI.scene());
		}
		if (nextScreen.equals(EVENTGUI)) {
			EventGUI eventGUI = new EventGUI(this);
			primaryStage.setScene(eventGUI.scene());
		}
		if (nextScreen.equals(LOGIN)) {
			primaryStage.setScene(Login.scene());
		}
		if (nextScreen.equals(TRANSPORTDISC)) {
			TransportDiscontinued transdisc = new TransportDiscontinued(this);
			primaryStage.setScene(transdisc.scene());
		}

	}

	private boolean validateEvent(Event entry) {
		// if entry not valid call the source GUI e.g.
		// currentView.setError(errormsg)
		return true;
	}

	public UserDatabase getUserDatabase() {
		return userDatabase;
	}

	/**
	 * @return the {@link User} logged in.
	 */
	public User getLoggedInUser() {
		return loggedIn;
	}

	/**
	 * Set the current logged in user. To retrieve a user to set for this method, use the
	 * {@link UserDatabase#getUser(String)} or implicitly through {@link UserDatabase#getUsers()}
	 *
	 * @param loggedIn set the user that is logged in to the application
	 */
	public void setLoggedInUser(User loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void login(String inputUsername, String inputPassword) {
		List<User> users = userDatabase.getUsers();
		for(User user : users) {
			if(user.getUsername().equals(inputUsername)
					&& user.getPassword().equals(inputPassword)) {
				setLoggedInUser(user);
				handleEvent(Controller.EVENTGUI);
				return;
			}
		}

		// Display Error message here if user
		AlertBox.display("Incorrect Login Information",
				"Please input the correct credentials to login to KPSmart");
	}
}
