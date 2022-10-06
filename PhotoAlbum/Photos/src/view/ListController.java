// By Kishan Sojan (ks1326) and Justin Kwok (jdk179)
	/**
	 * Class ListController is the loginscreen, where a user will input their username or admin to login to see all their albums.
	 * 
	 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
	 */

package view;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.*;

public class ListController{
	

	@FXML         
	ListView<String> listView;   
	@FXML TextField Login;
	@FXML Button LogConf;
	@FXML Button Quit;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	
	public Stage curStage;

	private ObservableList<String> userList;   
	
	public static final String storeDir = "data"+File.separator+"users";
	//public static final String storeFile = "Users.txt";

	public void start(Stage mainStage) throws ClassNotFoundException, IOException {
		curStage = mainStage;
		userList = FXCollections.observableArrayList(                               
				);
		File folder = new File(storeDir);
		File[] list = folder.listFiles();
		for(int a = 0; a < list.length; a++) {
			//System.out.println(list[a].getName());
			User read = readUser(list[a].getName());
			userList.add(read.getName());
		}
		//System.out.println("Succ");
	}
	
	
	public void quit(ActionEvent e) {
		//System.out.println("QUIT BUTTON PUSHED");
		System.exit(0);
	}
	
	/**
	 * the login method launches the interface of either the user or the admin.
	 */
	public void login(ActionEvent f) throws Exception {
		String user = Login.getText();
		////System.out.println("Login = " + user);	
		boolean succ = false;
		if(user.equals("admin")) {
			//System.out.println("User is admin.");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin.fxml"));
			VBox root = (VBox)loader.load();
			adminController adminController = loader.getController();
			adminController.start(curStage);
			//adminController.list(obsList);
			Scene scene = new Scene(root);
			curStage.setScene(scene);
			succ = true;
		}
		else if(user.equals("photo2"))
        {
            //System.out.println("Going to photoView");
            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
            //System.out.println("Right before Vbox");
            VBox root4 = (VBox)loader4.load();
            //System.out.println("Right after Vbox");
            PhotoViewController photoVController = loader4.getController();
            photoVController.userget(user);
            photoVController.start(curStage);
            //adminController.list(obsList);
            Scene scene4 = new Scene(root4);
            curStage.setScene(scene4);
            curStage.show();
            succ = true;
        }
		else {//check to see if it's in the list of users
			for(int i = 0; i < userList.size(); i++) {
				String name = userList.get(i);
				////System.out.println("Name = " + name);
				if(name.equals(user)) {
			    	FXMLLoader loader2 = new FXMLLoader();
			    	loader2.setLocation(getClass().getResource("/view/albums2.fxml"));
					VBox root2 = (VBox)loader2.load();
					AlbumController albumController = loader2.getController();
					albumController.userget(user);
					albumController.start(curStage);
					Scene scene2 = new Scene(root2, 640, 400);
					curStage.setScene(scene2);
					curStage.show();
			    	succ = true;
				}
			}
		}
		if(succ == false) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "User Not Registered";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
	}
	
	/**
	 * the writeUser method saves all progress on the edited user.
	 */
	
	public static void writeUser(User user) throws IOException {
		String username = user.getName();
		username = username +".txt";
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir+File.separator+username));
		oos.writeObject(user);
		oos.close();
	}
	
	/**
	 * the readUser method reads the user and returns it for use.
	 */
	
	public static User readUser(String username) throws IOException, ClassNotFoundException{
		//username = username+".txt";
		//System.out.println("Reading from file " + username);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator+username));
		User user = (User)ois.readObject();
		return user;
	}
}

