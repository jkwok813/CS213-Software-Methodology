// By Kishan Sojan (ks1326) and Justin Kwok (jdk179)
/**
 * The class user stores all the data of a user, allowing it to be saved and read freely.
 * 
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 */

package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User implements Serializable{
		private String user = "";
		private String[] albumList = new String[1000];
		private String[] picList = new String[1000];
		private String[] tagList = new String[1000];
		private String[] capList = new String[1000];
		private String[] dateList = new String[1000];
		private String[] containList = new String[1000];
		
		
	public User(String a, String[] a1, String[] a2, String[] a3, String[] a4, String[] a5, String[] a6){
			user = a;
			albumList = a1;
			picList = a2;
			tagList = a3;
			capList = a4;
			dateList = a5;
			containList = a6;
	}
		
	public String getName() {
		return user;
	}
	public String[] getalbumList(){
		return albumList;
	}
	public String[] getpicList(){
		return picList;
	}
	public String[] gettagList(){
		return tagList;
	}
	public String[] getcapList(){
		return capList;
	}
	public String[] getdateList(){
		return dateList;
	}
	public String[] getcontainList() {
		return containList;
	}
	public String toString(){
		return user;
	}
}
