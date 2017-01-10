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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.DataOutputStream;



public class Map {

	//Private variables
	private String filename;	//The file path from which the map data is read in
	private String projection = "PDXMiller";	//The type of projection that the map is.  Game files are a modified miller projection
	private int height;			//Image height in pixels
	private int width;			//Image width in pixels
	private int size;			//File size in bytes
	private int offset;			//The byte at which the image data begins.
	private int psize;			//The size of each pixel, in bits
	private byte[] fileData;	//All of the data from the original file.
	private byte[] pix;			//The pixels of the image
	private byte[] header;		//The header data of the image

	
	//Constructor for loading from a known file path
 	public Map(String path){
		loadFile(path);
	}

	//Loads the map data from a file
	public void loadFile(String path){
		try{
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
			if(!in.markSupported()){
				System.out.println("The input stream is unable to mark the file.");
			}
			in.mark(54);		//Marks the file's start for later

			//If we were able to access the file successfully, then we can set our path
			this.filename = path;
			
			//And try reading from the file
			try {
				readHeader(in);		//This reads the header and initializes the size and other fields.
				in.reset();		//Go back to start of file
				in.mark(this.size);		//Mark the beginning of the file, so we can return later
				
				//Now that we know the file's size, we can read the whole thing:
				fileData = new byte[this.size];
				in.read(fileData);
				
				//Close the input stream, since we now have everything we need.
				in.close();
				
				/*
				//Now that we have the header, we can read the offset data
				int offSize = this.size - this.offset - 54;	//The size of the offset data.
				if(offSize > 0){		//Size might be zero; there is only info if it is positive.
					this.offsetData = new byte[offSize];	//Initialize the data array
					in.skip(54);
					in.read(offsetData);		//Read the data
				}
				
				
				//Now we know the image's parameters, we can read the pixel data
				in.reset();		//Go back to start of file
				readPixels(in);		//Reads the pixels into the pix array
				
				in.close();			//Closes the input stream
				//*/
				
				
			} catch (IOException e) {
				System.out.println("Error reading file");
				e.printStackTrace();
				return;
			}
		}  catch (FileNotFoundException e) {
			System.out.println("Error:  Specified file could not be found.");
			return;
		}
	}



	//Reads header from the file; initializes values from info contained therein
	//Returns false if the header is not formatted as expected.
	private boolean readHeader(BufferedInputStream in) throws IOException{
		this.header = new byte[54];		//.bmp files have 54-byte headers

		in.read(header);  //read bytes into the array 'header' until there is no more room.

		ByteBuffer head = ByteBuffer.wrap(header);	//A buffer class to make reading operations easier
		head.order(ByteOrder.nativeOrder());		//Sets head to use the same endianness as the local system.
		
		
		//==============================================
		//Now we get the important data from the header:
		
		//Check magic letters:
		//Letters are one byte each, in ascii.  getChar reads two bytes and returns unicode.
		//So, to read each character and no extra, we must read a single byte and cast it to a char.
		//This works because 16-bit unicode is a superset of ascii.
		if((char)head.get(0) != 'B' || (char)head.get(1) != 'M'){
			System.out.println("Error:  File header does not match bitmap format.  Magic letters are " + (char)head.get(0) + " and " + (char)head.get(1));
			return false;
		}
		
		//Get the file size
		this.size = head.getInt(2); 	//Size starts at byte 2

		//Get the image offset
		this.offset = head.getInt(10);	//Offset starts at byte 10

		//Get image width
		this.width = head.getInt(18);	//Width is bytes 18-21

		//Get image height
		this.height = head.getInt(22);	//Bytes 22-25

		//The number of bits ber pixel is given as a 2-byte (i.e., short) integer
		this.psize = head.getShort(28);	//Bytes 28-29

		
		return true;
	}

	
	/*These classes are no longer needed.  It would be a good idea to replace them with
	 * classes that extract the pixel data from this.fileData, but those might not be needed either.

	//Reads the pixels from a file, storing them in this.pix
	//Returns true iff the read is successful, false otherwise.
	private boolean readPixels(BufferedInputStream in) throws IOException{
		
		
		//Pixels are ordered differently depending on the number of bytes per pixel.
		if(psize == 24){	//if 24 bits per pixel
			return readPix24Bit(in);
		}
		else{
			System.out.println("Non-24-byte pixel sizes are not yet implemented.");
			return false;
		}
	}
	
	//Reads 24-bit pixels into an array.  Helper method for readPixels.
	//Returns true iff the read was successful, false otherwise
	private boolean readPix24Bit(BufferedInputStream in) throws IOException{
		//Bitmap files with 24 bits for pixel have three bytes per pixel:  Blue, Green, and Red (in that order.)
		//That means that there are 3 * # of pixels bytes, total.  And height*width = # of pixels.
		
		//Sanity check:
		if(3*height*width != size-offset){
			System.out.println("Error: Size mismatch: 3*h*w - (size-offset) = " + (3*height*width - (size-offset)) );
			return false;
		}
		
		this.pix = new byte[3 * this.height * this.width];  //Array for holding pixels
		
		in.skip(this.offset);	//Move to the start of pixel data
		in.read(this.pix);		//Read the pixels into the array
		
		return true;
	}

	*/
	
	
	
	//Writes the map object to a file with path outname
	public boolean writeToFile(String outname){
		try {
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(outname));
			
			out.write(fileData);
			
			out.close();
	
		} catch (IOException e) {
			System.out.println("Error:  Unable to write to file.");
			return false;
		}
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
		Map provinces = new Map("map/provinces.bmp");
		
		provinces.writeToFile("output.bmp");
	}

}
