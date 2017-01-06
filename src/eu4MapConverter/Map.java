/*
 * The Map class is a representation of the data in a .bmp image.  It is associated with a particular file.
 * The class contains methods for manipulating the pixels of the image, and for writing out a new file.
 * 
 * Author:  Kerry McKean
 * 
 * TO DO:  Store the pixels in the instance variable "pix".
 * 			-Fix the read Header method.  Set Position might not be working as expected, because it doesn't affect the numbers.
 * 
 */

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

	//Private variables
	private String filename;		//The file path from which the map data is read in
	private String projection;		//The type of projection that the map is.
	private int height;		//Image height in pixels
	private int width;		//Image width in pixels
	private int size;		//File size in bytes
	private int offset;		//The byte at which the image data begins.
	private int psize;		//The size of each pixel, in bytes
	private int[] pix;		//The pixels of the image
	//byte[] bytes;		//input image data

	//Constructor for loading from a known file path
	public Map(String path){
		loadFile(path);
	}

	//Loads the map data from a file
	public boolean loadFile(String path){
		try{
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
			if(!in.markSupported()){
				System.out.println("wtf,man?!");
			}
			in.mark(54);		//Marks the file's start for later

			//If we were able to access the file successfully, then we can set our path
			this.filename = path;
			
			//And try reading from the file
			try {
				if(!readHeader(in)){		//readHeader() returns false if there are formatting issues
					return false;
				}
			} catch (IOException e) {
				System.out.println("Error reading header");
				e.printStackTrace();
				return false;
			}

			//Now we know the image's parameters, we can read the pixel data
			try {
				in.reset();		//reset's the stream's position to the start of the file
				
				readPixels(in);		//Reads the pixels into the pix array
				
			} catch (IOException e) {
				System.out.println("Error reading pixels");
				e.printStackTrace();
				return false;
			}



		}  catch (FileNotFoundException e) {
			System.out.println("Error:  Specified file could not be found.");
			return false;
		}


		return true;
	}



	//Reads the height and width from a file
	//Returns false if the header is not formatted as expected.
	private boolean readHeader(BufferedInputStream in) throws IOException{
		byte[] header = new byte[54];		//.bmp files have 54-byte headers

		in.read(header);  //read bytes into the array 'header' until there is no more room.

		ByteBuffer head = ByteBuffer.wrap(header);
		head.order(ByteOrder.nativeOrder());		//Sets head to use the same endianness as the local system.

		//Get the file size	
		head.position(2);		//Starts at byte 2
		this.size = head.getInt();

		//Get the image offset
		head.position(10);	//Starts at byte 10
		this.offset = head.getInt();

		//Width comes first
		head.position(18);	//Starts at byte 18
		this.size = head.getInt();

		//Now height; it's right after, so no need to reset position.
		this.height = head.getInt();

		//The number of bits ber pixel is given as a 2-byte (i.e., short) integer
		head.position(28);
		int pixBitSize = head.getShort();
		if(pixBitSize%8 != 0){
			System.out.println("Error: Pixels are not an integer number of bytes");
			return false;
		}
		this.psize = pixBitSize/8;  //psize is the pixel size in bytes


		return true;
	}

	//Reads the pixels from a file, storing them in this.pix
	//This method may currently be bugged.
	private boolean readPixels(BufferedInputStream in) throws IOException{
		
		byte[] pixels = new byte[size - offset];	//(size - offset) is the size of the pixel data, in bytes
		in.skip(1074);
		in.read(pixels);	//Reads in all the pixels to the byte array "pixels".
		
		return true;
	}

	
	
	//String render methods
	//At present, I haven't needed toString to include anything other than metadata.
	public String toString(){
		return this.metaInfo();
	}
	
	//Returns a string made from the file's metadata (I.e., info from the header)
	public String metaInfo(){
		String meta = "";
		
		meta += "File name: " + filename;
		meta += "\nFile size: " + size + " bytes";
		meta += "\nProjection: " + projection;
		meta += "\nWidth x Height: " + width + " x " + height;
		meta += "\nPixel size: " + psize;
		meta += "\nPixel offset: " + offset;
		
		return meta;		
	}

	//Getters
	public int getHeight(){
		return this.height;
	}

	public int getWidth(){
		return this.width;
	}

	public String getFileName(){
		return this.filename;		//The path of the file from which the map data was read in.
	}

	public static void main(String[] args) {
		String[] maps = {"heightmap", "provinces", "rivers", "terrain", "trees", "world_normal"};
		for(int i = 0; i<maps.length; i++){
			Map map = new Map("map/"+maps[i]+".bmp");
			System.out.println(map+"\n");
		}
		//Map terrain = new Map("map/heightmap.bmp");
		//System.out.println(terrain);
	}

}
