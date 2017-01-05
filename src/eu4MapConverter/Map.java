package eu4MapConverter;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
//import java.io.BufferedOutputStream;

public class Map {
	String fileName;
	BufferedInputStream in = null;
	
	public Map(String name){
	
	}
	
	
	private boolean getFile(String path){
		this.fileName = path;
		if (!in.equals(null)){
			System.out.println("Error:  This Map instance is already associated with a file.");
			return false;
		}		

		try{
			in = new BufferedInputStream(new FileInputStream(path));
		}  catch (FileNotFoundException e) {
			System.out.println("Error:  Specified file could not be found.");
			return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
	}

}
