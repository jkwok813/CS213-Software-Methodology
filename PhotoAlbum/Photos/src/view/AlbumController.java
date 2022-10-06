// By Kishan Sojan (ks1326) and Justin Kwok (jdk179)
/**
 * AlbumController.java is the controller for the album interface, it displays all the albums a user has and allows users to create, delete, or rename albums.
 *
 *
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 *
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

public class AlbumController {
	

	@FXML         
	ListView<String> listView;   
	@FXML TextField albums;
	@FXML TextField albums1;
	@FXML Button LogConf;
	@FXML Button Quit;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	@FXML Button addPhoto;
	@FXML Button delPhoto;
	@FXML Button movePhoto;
	@FXML Button copyPhoto;
	@FXML Button capPhoto;
	@FXML Button Prev;
	@FXML Button Next;
	@FXML Button addTag;
	@FXML Button delTag;
	@FXML TextField Tag;
	@FXML Button Search;
	@FXML TextArea disp;
	
	public Stage curStage;

	//private ObservableList<String> userList;
	private ObservableList<String> albumList;
	private ObservableList<String> picList;
	private ObservableList<String> tagList;
	private ObservableList<String> capList;
	private ObservableList<String> dateList;
	private ObservableList<String> containList;
	public static final String storeDir = "data"+File.separator+"users";
	public String username = "";
	public User read;
	public String[] tagholder;
	public String[] picholder;
	public String[] capholder;
	public String[] dateholder;
	public String[] containholder;

	
	/**
	 * The start method launches the interface itself. It also initializes all the Observable Lists and calls the read method to read the info from the inputted user. It then sets the listView.
	 */
	
	public void start(Stage mainStage) throws ClassNotFoundException, IOException {
		curStage = mainStage;
		albumList = FXCollections.observableArrayList();
		picList = FXCollections.observableArrayList();
		tagList = FXCollections.observableArrayList();
		capList = FXCollections.observableArrayList();
		dateList = FXCollections.observableArrayList();
		containList = FXCollections.observableArrayList();
		//System.out.println("username is " + username);
		//String t = "Waaa";
		//albumList.add(t);
		read = readUser(username);
		tagholder = read.gettagList();
		picholder = read.getpicList();
		capholder = read.getcapList();
		dateholder = read.getdateList();
		containholder = read.getcontainList();
		
		for(int i = 0; i < tagholder.length; i++) { //Populate the tagList obsList
            if(tagholder[i] == null) {
                break;
            }
            tagList.add(tagholder[i]);
        }
        for(int i = 0; i < picholder.length; i++) { //Populate the picList obsList
            if(picholder[i] == null) {
                break;
            }
            picList.add(picholder[i]);
        }
        for(int i = 0; i < capholder.length; i++) {
            if(capholder[i] == null) {
                break;
            }
            capList.add(capholder[i]);
        }
        for(int i = 0; i < dateholder.length; i++) {
            if(dateholder[i] == null) {
                break;
            }
            dateList.add(dateholder[i]);
        }
        for(int i = 0; i < containholder.length; i++) {
            if(containholder[i] == null) {
                break;
            }
            containList.add(containholder[i]);
        }
		String[] holder = read.getalbumList();
		int x = 0;
		while(holder[x] != null) {
			albumList.add(holder[x]);
			x++;
		}
		listView.setItems(albumList); 

		// select the first item
		listView.getSelectionModel().select(0);
		
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				display(mainStage));

	}
	/**
	 * the userGet method receives the user from the Login interface.. 
	 */
	public void userget(String user){
		username = user;
	}
		
	/**
	 * The method add in AlbumController adds an album into the observableList, then saves all data.
	 */
	public void add(ActionEvent a) throws IOException{
		//System.out.println("Add");
		String album = albums.getText();
		//System.out.println(album + " will be inserted into albumList");
		if(album.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Input is required";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}		
		boolean dup=false;
		if(albumList.size() < 1) {
			dup = false;
		}
		else {
			int loc = albumList.size();
			for(int i=0; i<albumList.size(); i++)
			{
				if(albumList.get(i).equals(album))
				{
					dup=true;
					break;	
				}			
			}
		}
		if(dup==true)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Album already exists";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		else {
			albumList.add(album);
			////System.out.println("loc = " + loc);
			//listView.getSelectionModel().select(loc);
		}
		
		//Add saving to file here
		String[] a1 = new String[1000];
		String[] a2 = new String[1000];
		String[] a3 = new String[1000];
		String[] a4 = new String[1000];
		String[] a5 = new String[1000];
		String[] a6 = new String[1000];
		for(int o = 0; o < albumList.size(); o++) {
			a1[o] = albumList.get(o);
		}
		User newbie = new User(username, a1, a2, a3, a4, a5, a6);
		writeUser(newbie);
		listView.getSelectionModel().select(0);
	}
	/**
	 *  The method delete in AlbumController removes the selected album from albumList, then saves.
	 */
	public void delete(ActionEvent b) throws IOException, ClassNotFoundException{
		//System.out.println("Deleting user");
		String album = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		////System.out.println(index);
		albumList.remove(album);
		listView.getSelectionModel().select(index);
		//System.out.println("Current albumList size is " + albumList.size());
		String[] a1 = new String[1000];
		String[] a2 = new String[1000];
		String[] a3 = new String[1000];
		String[] a4 = new String[1000];
		String[] a5 = new String[1000];
		String[] a6 = new String[1000];
		for(int a = 0; a < albumList.size(); a++) {
			a1[a] = albumList.get(a);
		}
		for(int a = 0; a < picList.size(); a++) {
			a2[a] = picList.get(a);
		}
		for(int a = 0; a < tagList.size(); a++) {
			a3[a] = tagList.get(a);
		}
		for(int a = 0; a < capList.size(); a++) {
			a4[a] = capList.get(a);
		}
		for(int a = 0; a < dateList.size(); a++) {
			a5[a] = dateList.get(a);
		}
		for(int a = 0; a < containList.size(); a++) {
			a6[a] = containList.get(a);
		}
		User newuser = new User(username, a1, a2, a3, a4, a5, a6);
		writeUser(newuser);
		//read = readUser(username);
		//System.out.println("Current albumList size is " + albumList.size());
	}
	
	/**
	 *  The rename method allows you to rename an album, then saves.
	 */
	public void rename(ActionEvent c) throws IOException{
		//System.out.println("Renaming");
		String album = albums1.getText();
		String oldalbum = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		if(album.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Input is required";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}		
		boolean dup=false;
		if(albumList.size() < 1) {
			dup = false;
		}
		else {
			int loc = albumList.size();
			for(int i=0; i<albumList.size(); i++)
			{
				if(albumList.get(i).equals(album))
				{
					dup=true;
					break;	
				}			
			}
		}
		if(dup==true)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Album already exists";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		else {
			//System.out.println(oldalbum + " deleted");
			//System.out.println(album + " added");
			albumList.remove(oldalbum);
			albumList.add(album);
		}
		String[] a1 = new String[1000];
		String[] a2 = new String[1000];
		String[] a3 = new String[1000];
		String[] a4 = new String[1000];
		String[] a5 = new String[1000];
		String[] a6 = new String[1000];
		for(int a = 0; a < albumList.size(); a++) {
			a1[a] = albumList.get(a);
		}
		for(int a = 0; a < picList.size(); a++) {
			a2[a] = picList.get(a);
		}
		for(int a = 0; a < tagList.size(); a++) {
			a3[a] = tagList.get(a);
		}
		for(int a = 0; a < capList.size(); a++) {
			a4[a] = capList.get(a);
		}
		for(int a = 0; a < dateList.size(); a++) {
			a5[a] = dateList.get(a);
		}
		for(int a = 0; a < containList.size(); a++) {
			a6[a] = containList.get(a);
		}
		User newuser = new User(username, a1, a2, a3, a4, a5, a6);
		writeUser(newuser);
	}
	
	public void open(ActionEvent d) throws ClassNotFoundException, IOException {
		//System.out.println("Open");
        //System.out.println("Going to photoView");
        String albumname = listView.getSelectionModel().getSelectedItem();
        //System.out.println("Albumname in albumncontroller is " + albumname);
        FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
        //System.out.println("Right before Vbox");
        VBox root4 = (VBox)loader4.load();
        //System.out.println("Right after Vbox");
        PhotoViewController photoVController = loader4.getController();
        photoVController.userget(username);
        photoVController.albumget(albumname);
        photoVController.albumlistget(albumList);
        photoVController.start(curStage);
        //adminController.list(obsList);
        Scene scene4 = new Scene(root4);
        curStage.setScene(scene4);
        curStage.show();
	}
	
	/**
	 * The quit method allows you to exit the program safely.
	 */
	
	public void quit(ActionEvent e) {
		//System.out.println("QUIT BUTTON PUSHED");
		System.exit(0);
	}
	
	/**
	 * The login button allows you to exit the program and go to the login screen.
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
	 * the writeUser method saves all progress on the edited user.
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
	 * the delUser method deletes the selected user by deleting the file it's attached to.
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
		username = username+".txt";
		//System.out.println("Reading from file " + username);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator+username));
		User user = (User)ois.readObject();
		return user;
	}
	
	/**
	 * the display method displays information in the textarea in the interface.
	 */
	
	public void display(Stage mainStage) {
		String fromlist=listView.getSelectionModel().getSelectedItem();		
		disp.setText("Album: "+ fromlist);
	}
}

