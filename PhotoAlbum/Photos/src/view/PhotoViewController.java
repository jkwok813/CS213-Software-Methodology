/**
 * Class PhotoViewController is the controller for the interface allowing the user to interact with the interface designated for photo interaction
 * 
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 */

package view;

import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.application.Application;

import javafx.fxml.Initializable;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PhotoViewController {
	
	public Stage curStage;
	public Stage mainStage;
	
	@FXML Button addUser;
    @FXML Button deleteUser;
    @FXML ChoiceBox<String> andor;
    @FXML TextField tag1;
    @FXML TextField tag2;
    @FXML ChoiceBox<String> tagtype1;
    @FXML ChoiceBox<String> tagtype2;
    @FXML TextField newtagtype;
    @FXML TextField newtag;
    @FXML Button addTagbutton;
    @FXML TextField deltag;
    @FXML TextField deltagtype;
    @FXML Button delTagbutton;
    @FXML Button captionbutton;
    @FXML TextField caption;
    @FXML TextField createnewtagtype;
    @FXML ChoiceBox<String> oneormore;
    @FXML ListView<String> listView;
    @FXML Button deletetagtype;
    @FXML Button copyphoto;
    @FXML Button movephoto;
    @FXML TextArea disp;
    @FXML
    private Label label;

    @FXML TilePane tilePane;

    @FXML AnchorPane myAnchor;

    @FXML
    private ImageView bigDisplay;
    
    @FXML Button previous;
    @FXML Button next;

    private int nRows=10;
    private int nCols=10;

    private static final double ELEMENT_SIZE= 100;
    private static final double GAP= ELEMENT_SIZE/10;
    private ObservableList<String> albumList;
    private ObservableList<String> picList;
    private ObservableList<String> tagList;
    private ObservableList<String> capList;
    private ObservableList<String> dateList;
    public static final String storeDir = "data"+File.separator+"users";
    public String username = "";
    public String albumname = "";
    public User read;
    public ObservableList<String> tagtypes;
    public ObservableList<String> containList;
    
	
	File filesJpg[];
	
	public String[] tagholder;
	public String[] picholder;
	public String[] capholder;
	public String[] dateholder;
	public String[] containholder;
	
	private int count_index=0;
	
	/**
	 * 
	 * @param mS The Stage in which the Controller will be interacting with
	 * @throws ClassNotFoundException In case class is not found
	 * @throws IOException In case there is an IO Exception
	 */
	public void start(Stage mS) throws ClassNotFoundException, IOException {
		
		//Image image1=new Image("C:/Users/kisha/eclipse-workspace/Photos/StockImages/StockImage1.jpeg");
		picList =  FXCollections.observableArrayList();
		tagList = FXCollections.observableArrayList();
		capList =  FXCollections.observableArrayList();
		dateList = FXCollections.observableArrayList();
		tagtypes = FXCollections.observableArrayList();
		containList = FXCollections.observableArrayList();
	
		ImageView iv= new ImageView();
		iv.setFitWidth(100);
		iv.setPreserveRatio(true);
		String[] iminpain = {"and", "or", "singlesearch"};
		andor.getItems().addAll(iminpain);
		andor.setValue("singlesearch");
		String[] stillinpain = {"one", "two+"};
		oneormore.getItems().addAll(stillinpain);
		oneormore.setValue("one");
		curStage = mS;
		mainStage=mS;
		curStage.show(); 
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
		
		//iterate through tagList and find all tagtypes currently there already:
		
        for(int i = 0; i < tagholder.length; i++) {
            if(tagholder[i] == null) {
                break;
            }
            String tags = tagholder[i];
            //read until : or |
            String line;
            int a = 0;
            int b = 0;
            while(b!=tags.length()) {
                line = tags.substring(a, b);
                if(line.equalsIgnoreCase("|")){
                    //update beginning
                    a = b;
                }
                else if(line.equalsIgnoreCase(":")) {
                    //add everything from a to b here
                    //System.out.println("Added " + line);
                    tagtypes.add(line);
                    a = b;
                }
                b++;
            }
        }
		
        //if(username.contentEquals("Stock"))
		/*{
        	File file = new File(picList.get(0));
        	Image image = new Image(file.toURI().toString());
            bigDisplay.setImage(image);
		}*/
		
		{
			createThumbNail();
		}

		if(picList.size()>0)
		{
			File file = new File(picList.get(0)); //We can change this to the first picture in the list
			String temp=containList.get(0);
            int i=0;
            int j=1;
            while(j!=temp.length())
            {
                String temp2=temp.substring(j-1,j);
                if(temp2.equalsIgnoreCase("|"))
                {
                    if(albumname.equalsIgnoreCase((temp.substring(i,j-1))))
                    {
                        //System.out.println("Username in IF: "+username);
                        //System.out.println("Temp substring: "+(temp.substring(i,j-1)));
                            //System.out.println("Inside if statement");
                            Image image = new Image(file.toURI().toString());
                            bigDisplay.setImage(image);
                    }
                    else
                    {
                        i=j;
                    }
                }
                j++;
            }
	       // Image image = new Image(file.toURI().toString());
	        //bigDisplay.setImage(image);
		}
		
		if(picList.size()>0)
		{
			File file = new File(picList.get(0));
			Image image = new Image(file.toURI().toString());
            bigDisplay.setImage(image);
		}
		if(picList.size()>0) {
			display(mainStage);
		}
		
	}
	
	int count=0;
	
	/**
	 * Creates Thumbnails for the bottom of the PhotoView Interface
	 * @throws FileNotFoundException
	 */
	private void createThumbNail() throws FileNotFoundException
	{
		//System.out.println("Username is:"+username);
		if(username.equals("Stock"))
		{
			//This gets the stock image from the '/Photos/data/StockImages/' location
			String s=System.getProperty("user.dir");
			////System.out.println("Path:"+s);
			String path=s.replace("\\","/");
			//File selectedDirectory2= new File(path+"/data/StockImages/");
			////System.out.println("Path:"+path);
			File selectedDirectory= new File(path+"/data/StockImages/");
			
			if(selectedDirectory!=null)
			{
				FilenameFilter filterJpg=new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return (name.toLowerCase().endsWith(".jpg")||name.toLowerCase().endsWith(".jpeg")||name.toLowerCase().endsWith(".png")||name.toLowerCase().endsWith(".bmp")||name.toLowerCase().endsWith(".gif"));
					}
				};
				////System.out.println("before selectedDirectory Size: "+filesJpg.length);
				filesJpg= selectedDirectory.listFiles(filterJpg);
				////System.out.println("after selectedDirectory Size: "+filesJpg.length);
			}
			////System.out.println("Getting to createElement");
			//initialize();
			createElements();
		}
		else
		{
			for(int i=0; i<picList.size(); i++)
			{
				File selectedDirectory= new File(picList.get(i));
				if(selectedDirectory!=null)
				{
					FilenameFilter filterJpg=new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return (name.toLowerCase().endsWith(".jpg")||name.toLowerCase().endsWith(".jpeg")||name.toLowerCase().endsWith(".png")||name.toLowerCase().endsWith(".bmp")||name.toLowerCase().endsWith(".gif"));
						}
					};
					filesJpg= selectedDirectory.listFiles(filterJpg);
				}
				createElements2();
			}
		}
	}
	/**
	 * Called by non-Stocks for the creation of their thumbnail
	 * @throws FileNotFoundException
	 */
	private void createElements2() throws FileNotFoundException
	{
		tilePane.getChildren().clear();
		//tilePane.getChildren().add(createPage2(picList.get(count)));
		//count++;
		
		for(int i=0; i<nCols; i++)
		{
			for(int j=0; j<nRows;j++)
			{
				//System.out.println("Count"+count);
				if(count==picList.size())
				{
					break;
				}
				tilePane.getChildren().add(createPage2(picList.get(count)));
				count++;
				
			}
			if(count==picList.size())
			{
				break;
			}
		}
	}
	
	/** 
	 * Called by createElements2 for the displaying of thumnails
	 * @param string Location of a given Image
	 * @return The created Thumbnail
	 * @throws FileNotFoundException
	 */
	public VBox createPage2(String string) throws FileNotFoundException
	{
		ImageView imageView=new ImageView();
		
		////System.out.println("Size: "+filesJpg.length);
		////System.out.println("Index: "+index);
		
		//File file=filesJpg[string];
		InputStream stream=new FileInputStream(string);
		Image image=new Image(stream);
		imageView.setImage(image);
		imageView.setFitWidth(ELEMENT_SIZE);
		imageView.setFitHeight(ELEMENT_SIZE);
				
				//imageView.setPreserveRatio(true);
				
		imageView.setSmooth(true);
		imageView.setCache(true);
		VBox pageBox=new VBox();
		pageBox.getChildren().add(imageView);
		label=new Label ("Caption here "+capList.get(count));
		pageBox.getChildren().add(label);
		pageBox.setStyle("-fx-border-color: orange;");
		
		imageView=null;
		return pageBox;
	}
	
	/**
	 * Assists in creating Thumbnail for Stock
	 * @throws FileNotFoundException
	 */
	private void createElements() throws FileNotFoundException
	{
		tilePane.getChildren().clear();
		
		for(int i=0; i<nCols; i++)
		{
			for(int j=0; j<nRows;j++)
			{
				//System.out.println("Count"+count);
				if(count==filesJpg.length)
				{
					break;
				}
				tilePane.getChildren().add(createPage(count));
				count++;
				
			}
			if(count==filesJpg.length)
			{
				break;
			}
		}
	}
	
	/**
	 * Creates thumbnail for Stock
	 * @param index The index of the image from the image directory/folder.
	 * @return created Thumnail
	 * @throws FileNotFoundException
	 */
	public VBox createPage(int index) throws FileNotFoundException
	{
		ImageView imageView=new ImageView();
		
		////System.out.println("Size: "+filesJpg.length);
		////System.out.println("Index: "+index);
		
		File file=filesJpg[index];
		InputStream stream=new FileInputStream(file);
		Image image=new Image(stream);
		imageView.setImage(image);
		imageView.setFitWidth(ELEMENT_SIZE);
		imageView.setFitHeight(ELEMENT_SIZE);
				
				//imageView.setPreserveRatio(true);
				
		imageView.setSmooth(true);
		imageView.setCache(true);
		VBox pageBox=new VBox();
		pageBox.getChildren().add(imageView);
		label=new Label ("Caption here "+index);
		pageBox.getChildren().add(label);
		pageBox.setStyle("-fx-border-color: orange;");
		
		imageView=null;
		return pageBox;
	}
	
	/**
	 * Adds types of tag
	 * @throws IOException
	 */
	public void addtagtype() throws IOException {
		String ntt = createnewtagtype.getText();
		if(ntt == "") {//Check if the inputted tag is empty
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Input required";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		for(int i = 0; i < tagtypes.size(); i++){//Check for duplicates
			if(ntt.equals(tagtypes.get(i))) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText("Input Error");
				String Error = "Duplicate tag found";
				alert.setContentText(Error);
				alert.showAndWait();
				return;
			}
		}
		String tt = oneormore.getSelectionModel().getSelectedItem().toString();
		if(tt == "one") {//Determine tag type
			//ntt=ntt+"O";
		}
		else {
			//ntt=ntt+"M";
		}    
		tagtypes.add(ntt);
		taglist();
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
	
	/**
	 * Deletes tag type
	 * @throws IOException
	 */
	public void deletetagtype() throws IOException {
		//System.out.println("Deleting");
		String tt = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		tagtypes.remove(tt);
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
	
	/**
	 * List of tags of a given Image
	 */
	public void taglist() {
		listView.setItems(tagtypes); 
		// select the first item
		listView.getSelectionModel().select(0);
	}
	
	/**
	 * Creates the tag type and the tag value
	 * @throws IOException
	 */
	public void add() throws IOException {
		String ntag = newtag.getText();
		String ntagtype = newtagtype.getText();
		if(ntag == "" || ntagtype == "") {//new tag or tagtype is empty
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Input of tagtype and tag both required";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		
		String wowtag = ntagtype+":"+ntag+"|";
		for(int i = 0; i < tagList.size(); i++) {//Tag and tagtype are already there
			String comptag = tagList.get(i);
			if(comptag.equals(wowtag)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText("Input Error");
				String Error = "Duplicate tag";
				alert.setContentText(Error);
				alert.showAndWait();
				return;
			}
		}
		boolean tagcheck = false;
		for(int i = 0; i < tagtypes.size(); i++){//Check to see if tagtype is already in the database
			//System.out.println(tagtypes.get(i) + " is the tagtype, comparing to " + ntagtype);
			if(ntagtype.equals(tagtypes.get(i))) {
				tagcheck = true;
			}
		}
		if(!tagcheck) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Tag type does not exist";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		//Check to see if tag is a multi or a single tag. If single, search to see if it's already there.
		tagList.add(photoIndex, wowtag);
		print();
		//taglist(tagtypes);
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
		display(mainStage);
	}
	/**
	 * Deletes tag and its associates
	 * @throws IOException
	 */
	public void del() throws IOException {
		String dtag = deltag.getText();
		String dtagtype = deltagtype.getText();
		String wowtag = dtagtype+":"+dtag+"|";
		////System.out.println("wowtag in delete is " + wowtag);
		////System.out.println(tagList.get(0));
		boolean deleted = false;
		for(int i = 0; i < tagList.size(); i++) {
			String comptag = tagList.get(i);
			//System.out.println("In comparison part. Wowtag is " + wowtag + "comptag is " + comptag);
			if(comptag.equals(wowtag)) {
				tagList.remove(i);
				deleted = true;
			}
		}
		if(!deleted) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Tag does not exist";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		print();
		taglist();
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
		display(mainStage);
	}
	
	/**
	 * Prints a list of tags
	 */
	public void print() {
		String list = "";
		for(int i = 0; i < tagList.size(); i++) {
			list = list+tagList.get(i);
		}
		//System.out.println(list);
	}
	
	/**
	 * Searches by tag
	 * @throws IOException
	 */
	public void tagSearch() throws IOException {
		boolean ao = false;
		String te = andor.getSelectionModel().getSelectedItem().toString();
		String[] tagholder = new String[1000];
		String[] tagtypeholder = new String[1000];
		String tt1 = tagtype1.getSelectionModel().getSelectedItem().toString();
		String tt2 = tagtype2.getSelectionModel().getSelectedItem().toString();
		String t1 = tag1.getText();
		String t2 = tag2.getText();
		int tagcounter = 0;
		if(te.equals("and"))
		{
			ao = true;
		}
		else if(te.equals("or")){
			ao = false;
		}
		else if(te.equals("singlesearch")) {
			String tagtwoo = tag2.getText();
			String tagtwo = tagtype2.getSelectionModel().getSelectedItem().toString();	
			if(tagtwoo.isBlank() || tagtwo.isBlank()){
				//We are good, can continue
			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText("Input Error");
				String Error = "Single search can only search one tag!";
				alert.setContentText(Error);
				alert.showAndWait();
				return;
			}
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Input Error");
			String Error = "Tag search error";
			alert.setContentText(Error);
			alert.showAndWait();
			return;
		}
		for(int i = 0; i < tagList.size(); i++) {
			String tags = tagList.get(i);
			
			//read until : or |
			boolean bob = false;
			String line;
			int a = 0;
			int b = 1;
			while(b!=tags.length()) {
				line = tags.substring(b-1, b);
				if(line.equalsIgnoreCase("|") || line.equalsIgnoreCase(":")) {
					if(bob) {
						tagtypeholder[tagcounter] = tags;	
						bob = false;
					}
					else {
						tagholder[tagcounter] = tags;
						tagcounter++;
						bob = true;
					}
				}
				b++;
			}
			tagholder[tagcounter] = tags.substring(a, b-1);	
		}
		int z = 0;
		if(ao == false) { //or check
			while(tagtypeholder[z] != null) {
				if(tt1 == tagtypeholder[z]) {
					if(t1 == tagholder[z]) {
						//add to list of returned photos
						break;//return list of photos
					}
				}
				else if(tt2 == tagtypeholder[z]) {
					if(t2 == tagholder[z]) {
						//add to list of returned photos
						break;//return list of photos
					}
				}
				z++;
			}
		}
		if(ao == true) {//and check
			while(tagtypeholder[z] != null) {
				if(tt1 == tagtypeholder[z]) {
					if(t1 == tagholder[z]) {
						int z2 = 0;
						while(tagtypeholder[z2] != null) {
							if(tt2 == tagtypeholder[z]) {
								if(t2 == tagholder[z]) {
									//add to list
									break;//return list of photos
								}
							}
						}
						z2++;
					}
				}
				z++;
			}
		}
		else {//Singlesearch
			while(tagtypeholder[z] != null) {
				if(tt1 == tagtypeholder[z]) {
					if(t1 == tagholder[z]) {
						//add to list of returned photos
						break;//return list of photos
					}
				}
				z++;
			}
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
		return;
	}
	
	/**
	 * Returns the user who we are logged into as
	 * @param user
	 */
	public void userget(String user){
		username = user;
	}	
	
	/**
	 * Adds the user photo from local file
	 * @param b Clicking add photo button
	 * @throws IOException
	 */
	public void addPhoto(ActionEvent b) throws IOException
	{
		Stage stage=new Stage();
		FileChooser fileChooser= new FileChooser();
		fileChooser.setTitle("Choose An Image");
		////System.out.println("Choosing image");
		//fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png")); 
		
		File selectedFile=fileChooser.showOpenDialog(stage);
		
		//Get name of file
		String name=selectedFile.getName(); //Gets it as test1.jpg
		
		//Get file path with the correct slash
		String loc=selectedFile.getAbsolutePath();
		String location=loc.replace("\\","/");
		//picholder[count_index]=location;
		picList.add(location);
		containList.add(albumname+"|");
		
		//Part of the code that gives the date in dd/mm/yyyy
		String lastModified="";
		Path datePath=Paths.get(location);
		try {
			BasicFileAttributes attr=Files.readAttributes(datePath, BasicFileAttributes.class);
			//lastModified= (String) attr.lastModifiedTime();
			////System.out.println(attr.lastModifiedTime());
			FileTime date=attr.lastModifiedTime();
			DateFormat df=new SimpleDateFormat("MM/dd/yyyy");
			lastModified=df.format(date.toMillis());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tagList.add("");
		//tagtypes.add("");
		capList.add("");
		
		
		dateList.add(lastModified);
		//System.out.println("lastModifiedTime:"+lastModified);
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
	/**
	 * Removes photo
	 * @param i Clicking remove photo button
	 * @throws IOException
	 */
	public void removePhoto(ActionEvent i) throws IOException
	{
		
		picList.remove(photoIndex);
		containList.remove(photoIndex);
		
		tagList.remove(photoIndex);
		//tagtypes.remove(photoIndex);
		capList.remove(photoIndex);
		
		if(photoIndex == picList.size())
			photoIndex--;
		

		
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
	
	/**
	 * Pops up the big display when the Thumnbnail section is clicked
	 * @param c When mouse in bottom half of interface is clicked
	 */
	public void ThumbnailClicked(MouseEvent c)
    {
        ////System.out.println("Inside Stock");
        //showBigDisplay();
        if(picList.size()>0)
        {
            //System.out.println("ContainList: "+containList.get(photoIndex));
            //System.out.println("Album name: "+albumname);
            File file = new File(picList.get(photoIndex)); //We can change this to the first picture in the list

            String temp=containList.get(photoIndex);
            int i=0;
            int j=1;
            int g = temp.length()+1;
            while(j!=g)
            {
                String temp2=temp.substring(j-1,j);
                //System.out.println("temp2 = " + temp2);
                if(temp2.equalsIgnoreCase("|"))
                {
                    ////System.out.println(temp2 + "testing");
                    ////System.out.println(temp2 + " is the new temp2 AAAAAAAAAAAAAAAAA");
                    if(albumname.equals(temp.substring(i,j-1)))
                    {
                        //System.out.println("Username in IF: "+albumname);
                        //System.out.println("Temp substring: "+(temp.substring(i,j-1)));
                        //System.out.println("Inside if statement");
                        Image image = new Image(file.toURI().toString());
                        bigDisplay.setImage(image);
                    }
                    else
                    {
                        i=j;
                    }
                }
                j++;
            }
            //Image image = new Image(file.toURI().toString());
           // bigDisplay.setImage(image);
        }

        //System.out.println(picList);
        //System.out.println(dateList);
        //System.out.println(containList);
        display(mainStage);
    }
	
	public int photoIndex=0;
	
	/**
	 * Moves to the previous photo
	 */
	public void previousPhoto()
    {
        //System.out.println("Inside previousPhoto");
        if(photoIndex-1 < 0)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Input Error");
            String Error = "End of album";
            alert.setContentText(Error);
            alert.showAndWait();
            return;
        }
        else
        {
            photoIndex--;
            String prev=picList.get(photoIndex);
            if(picList.size()>0)
            {
                //System.out.println("ContainList: "+containList.get(photoIndex));
                //System.out.println("Album name: "+albumname);
                File file = new File(picList.get(photoIndex)); //We can change this to the first picture in the list
                
                String temp=containList.get(photoIndex);
                int i=0;
                int j=1;
                int g = temp.length()+1;
                while(j!=g)
                {
                    String temp2=temp.substring(j-1,j);
                    //System.out.println("temp2 = " + temp2);
                    if(temp2.equalsIgnoreCase("|"))
                    {
                        ////System.out.println(temp2 + "testing");
                        ////System.out.println(temp2 + " is the new temp2 AAAAAAAAAAAAAAAAA");
                        if(albumname.equals(temp.substring(i,j-1)))
                        {
                            //System.out.println("Username in IF: "+albumname);
                            //System.out.println("Temp substring: "+(temp.substring(i,j-1)));
                            //System.out.println("Inside if statement");
                            Image image = new Image(file.toURI().toString());
                            bigDisplay.setImage(image);
                        }
                        else
                        {
                            i=j;
                        }
                    }
                    j++;
                }
                //Image image = new Image(file.toURI().toString());
               // bigDisplay.setImage(image);
            }
        }
        //System.out.println(photoIndex);
        display(mainStage);
    }
	/**
	 * Moves the big display photo to the next entry
	 */
	public void nextPhoto()
    {
        //System.out.println("Inside nextPhoto");
        if(photoIndex+1 >= picList.size()) //photoCount
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Input Error");
            String Error = "End of album";
            alert.setContentText(Error);
            alert.showAndWait();
            return;
        }
        else
        {
            photoIndex++;
            String next=picList.get(photoIndex);
            if(picList.size()>0)
            {
                //System.out.println("ContainList: "+containList.get(photoIndex));
                //System.out.println("Album name: "+albumname);
                File file = new File(picList.get(photoIndex)); //We can change this to the first picture in the list
                
                String temp=containList.get(photoIndex);
                int i=0;
                int j=1;
                int g = temp.length()+1;
                while(j!=g)
                {
                    String temp2=temp.substring(j-1,j);
                    //System.out.println("temp2 = " + temp2);
                    if(temp2.equalsIgnoreCase("|"))
                    {
                        ////System.out.println(temp2 + "testing");
                        ////System.out.println(temp2 + " is the new temp2 AAAAAAAAAAAAAAAAA");
                        if(albumname.equals(temp.substring(i,j-1)))
                        {
                            //System.out.println("Username in IF: "+albumname);
                            //System.out.println("Temp substring: "+(temp.substring(i,j-1)));
                            //System.out.println("Inside if statement");
                            Image image = new Image(file.toURI().toString());
                            bigDisplay.setImage(image);
                        }
                        else
                        {
                            i=j;
                        }
                    }
                    j++;
                }
                //Image image = new Image(file.toURI().toString());
               // bigDisplay.setImage(image);
            }
            
        }
        //System.out.println(photoIndex);
        
        
        //System.out.println(picholder);
        //System.out.println(containholder);
        display(mainStage);
    }
	
	/**
	 * Updates display photo
	 */
	public void updateDisplayPhoto()
	{
		//System.out.println("Inside updateDisplayPhoto");
		String url=picList.get(photoIndex);
		File file = new File(url); //We can change this to the first picture in the list
        Image image = new Image(file.toURI().toString());
        bigDisplay.setImage(image);
	}
	
	private int index=0;
	//Sets image to the big display in the upper middle ImageView panel
	/*public void showBigDisplay(MouseEvent c)
	{
		 File file = new File("C:/Users/kisha/eclipse-workspace/Photos/data/StockImages/StockImage1.jpeg");
	        Image image = new Image(file.toURI().toString());
	        bigDisplay.setImage(image);
	}*/
	/**
	 * Code for copying photo to another album
	 * @param g Clicked copy photo button
	 * @throws IOException
	 */
	public void copyPhotoTo(ActionEvent g) throws IOException
	{
		TextInputDialog textInput=new TextInputDialog();
		textInput.setTitle("Copy Photo Dialog: Copying photo the photo displayed in the middle");
		textInput.getDialogPane().setContentText("Please type the Album you would like your photo to be copied to: ");
		Optional<String> result=textInput.showAndWait();
		TextField input=textInput.getEditor();
		
		//System.out.println("Copying photo: Input Text is: "+input.getText().toString());
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
	
	/**
	 * Code for moving photo to another album
	 * @param h Clicked move photo button
	 * @throws IOException
	 */
	public void movePhotoTo(ActionEvent h) throws IOException
	{
		TextInputDialog textInput=new TextInputDialog();
		textInput.setTitle("move Photo Dialog: Moving photo the photo displayed in the middle");
		textInput.getDialogPane().setContentText("Please type the Album you would like your photo to be moved to: ");
		Optional<String> result=textInput.showAndWait();
		TextField input=textInput.getEditor();
		
		//System.out.println("Moving photo: Input Text is: "+input.getText().toString());
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
	
	/**
	 * Quit the app
	 * @param e Clicked quit button
	 */
	public void quit(ActionEvent e) {
		//System.out.println("QUIT BUTTON PUSHED");
		System.exit(0);
	}
	
	/**
	 * Logout of the current user
	 * @param g Clicked logout button
	 * @throws IOException
	 * @throws ClassNotFoundException
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
	 * Saves the changes made to the user
	 * @param user The user that the change is being made to
	 * @throws IOException
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
	 * Deletes user
	 * @param user User name whose user account will be deleted
	 * @throws IOException
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
	 * Reads in the user information
	 * @param username String name of the user
	 * @return Returns a user object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static User readUser(String username) throws IOException, ClassNotFoundException{
		username = username+".txt";
		//System.out.println("Reading from file " + username);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir+File.separator+username));
		User user = (User)ois.readObject();
		return user;
	}
	
	/**
	 * Sets the album name
	 * @param album String name of album
	 */
	public void albumget(String album) {
        albumname = album;
    }
	
	/**
	 * Sets the list with the album names
	 * @param albumList2 sets albumList to the observable albumList
	 */
	public void albumlistget(ObservableList<String> albumList2) {
		albumList = albumList2;
	}
	
	/**
	 * Handles caption information
	 */
	public void caption(ActionEvent h) throws IOException
	{
		TextInputDialog textInput=new TextInputDialog();
		textInput.setTitle("Add/Rename Caption for the picture in the big display");
		textInput.getDialogPane().setContentText("Enter caption: ");
		Optional<String> result=textInput.showAndWait();
		////System.out.println("Result:"+result.get());
		TextField input=textInput.getEditor();
		////System.out.println("Input:"+input);
		capList.remove(photoIndex);
		capList.add(photoIndex, result.get());
		//System.out.println("Caption: "+capList.get(photoIndex));
		display(mainStage);
	}
	/**
	 * Displays the tags, date and caption
	 * @param mainStage
	 */
    public void display(Stage mainStage) {
        String tagdisp = tagList.get(photoIndex);
        String datedisp = dateList.get(photoIndex);
        String capdisp = capList.get(photoIndex);
        disp.setText("caption: " + capdisp + "\ndate: " + datedisp + "\ntags: " + tagdisp);
    }
    
    
    
}