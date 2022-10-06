// By Kishan Sojan (ks1326) and Justin Kwok (jdk179)
/**
 * Class adminController is the controller for the interface allowing an admin to create/delete users.
 * 
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 */

package view;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class adminController {
	

	@FXML         
	ListView<String> listView;   
	@FXML Button addUser;
	@FXML Button deleteUser;
	@FXML TextField userinput;
	
	public Stage curStage;
	
	private ObservableList<String> userList;
	private ObservableList<String> albumList;
	private ObservableList<String> picList;
	private ObservableList<String> tagList;
	private ObservableList<String> capList;
	private ObservableList<String> dateList;
	public static final String storeDir = "data"+File.separator+"users";

	public void start(Stage mainStage) throws ClassNotFoundException, IOException{
		curStage = mainStage;
		userList = FXCollections.observableArrayList();
		albumList = FXCollections.observableArrayList();
		picList = FXCollections.observableArrayList();
		tagList = FXCollections.observableArrayList();
		capList = FXCollections.observableArrayList();
		dateList = FXCollections.observableArrayList();
		
		//read all the files in the directory here
		File folder = new File(storeDir);
		File[] list = folder.listFiles();
		for(int a = 0; a < list.length; a++) {
			//System.out.println(list[a].getName());
			User read = readUser(list[a].getName());
			userList.add(read.getName());
		}
		////System.out.println(userList.size());
		if(userList.size()>0) {
			list(userList);
		}
	}
	
	/**
	 * the method list returns the list of all users.
	 */
	
	public void list(ObservableList<String> obsList) {
		//System.out.println(userList.get(0));
		listView.setItems(userList); 

		// select the first item
		listView.getSelectionModel().select(0);
	}
	
	/**
	 * the addUser method allows you to add a user to the obsList of users.
	 */
	
	public void addUser(ActionEvent a) throws IOException {
		//System.out.println("Adding user");
		String newuser = userinput.getText();
		if(newuser.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Input is required";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		boolean dup=false;
		////System.out.println("ObsList size: "+obsList.size());
		int loc=userList.size();
		for(int i=0; i<userList.size(); i++)
		{
			if(userList.get(i).equals(newuser))
			{
				dup=true;
				break;	
			}			
		}
		if(dup==true)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "User already exists";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		else {
			////System.out.println("newuser added");
			userList.add(newuser);
			//System.out.println("loc = " + loc);
			listView.getSelectionModel().select(loc);
		}
		
		//Add saving to file here aptcd
		String[] a1 = new String[1000];
		String[] a2 = new String[1000];
		String[] a3 = new String[1000];
		String[] a4 = new String[1000];
		String[] a5 = new String[1000];
		String[] a6 = new String[1000];
		User newbie = new User(newuser, a1, a2, a3, a4, a5, a6);
		writeUser(newbie);
		list(userList);
		
		/*for(int i = 0; i < obsList.size(); i++) {
			//System.out.println("Printing item " + i + " of obslist");
			User.writeUser(obsList.get(i));		
		}*/	
	}
	
	/**
	 * the delUser method deletes the selected user.
	 */
	
	public void delUser(ActionEvent b) throws IOException {
		//System.out.println("Deleting user");
		String user = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		////System.out.println(index);
		userList.remove(user);
		listView.getSelectionModel().select(index);
		////System.out.println("All songs: "+ obsList);
		delUser(user);		
	}
	
	/**
	 * the quit method exits the program safely.
	 */
	
	public void quit(ActionEvent e) {
		//System.out.println("QUIT BUTTON PUSHED");
		System.exit(0);
	}
	
	/**
	 * the logout method logs you back into the login page safely.
	 */
	
	public void logout(ActionEvent g) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		VBox root = (VBox)loader.load();
		ListController listController = loader.getController();
		listController.start(curStage);
		Scene scene = new Scene(root, 500, 350);
		curStage.setScene(scene);
		curStage.show(); 
	}
	
	/**
	 * writeUser saves the current progress.
	 */
	
	public static void writeUser(User user) throws IOException {
		String username = user.getName();
		username = username +".txt";
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir+File.separator+username));
		oos.writeObject(user);
		//System.out.println(user + " written to file");
		oos.close();
	}
	
	/**
	 * the method delUser deletes the user by deleting the file. 
	 */
	
	public static void delUser(String user) throws IOException{
		String fi = user + ".txt";
		//System.out.println("Deleting " + fi);
		try {
			File f = new File(storeDir+File.separator+fi);
			if(f.delete()) {
				//System.out.println("Deleted");
			}
			else {
				//System.out.println("failed to delete");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
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

