package eu4MapConverter;

//import java.util.Scanner;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
//import java.io.BufferedOutputStream;

//This class provides a variety of useful methods for reading bitmap files.

public class BmpReader {

	private BufferedInputStream in;
	private boolean  initialized = false;

	//Error codes
	String noAssociatedFile = "Error:  There is no file associated with this reader. Use .initialize(filename) first.";
	String fileTooShort = "Error:  More bytes were expected, but the file ended.";


	//Creates a new reader for reading the file '[filename]'
	public BmpReader(String filename){
		this.initialize(filename);
	}

	//Associates the bmp reader with a file.
	public void initialize(String filename){
		if (initialized){
			System.out.println("Error:  This reader has already been initialized.");
		}		

		try{
			in = new BufferedInputStream(new FileInputStream(filename));
		}  catch (FileNotFoundException e) {
			System.out.println("Error:  Specified file could not be found.");
		}
		initialized = true;
	}

	//A public getter for checking whether file has been initialized
	public boolean initialized(){
		if(this.initialized){
			return true;
		}
		System.out.println(noAssociatedFile);
		return false;
	}

	//Prints out the 54-byte header of a bitmap file
	//Returns true iff there is a file with a valid header.
	public boolean printHeader(){
		if(!this.initialized()){
			return false;
		}

		ByteBuffer bbuf;					//Used for reading multi-byte numbers

		String header = "";

		//A Bitmap header is 54 bytes long;
		byte[] b = new byte[54];		//Byte array b will hold the bytes
		try {			//Might have an IOException

			//Read characters and store them in the buffer
			in.read(b);
			bbuf = ByteBuffer.wrap(b);
			bbuf.order(ByteOrder.nativeOrder());	//Sets endianness to system default

			//First two bytes (0 and 1) are the characters "BM":
			header += "" + (char)bbuf.get() + (char)bbuf.get();
			if(header.charAt(0) != 'B' || header.charAt(1) != 'M'){
				return false;
			}

			//Bytes 2-5 are the file size in bytes (though this is unreliable):
			header += "\nSize: ";
			header += bbuf.getInt();	//bbuf.getInt returns the integer value of the next 4 bytes

			//Bytes 6-9 must be all zeroes:
			header += "\nFour zeroes: ";
			header += bbuf.getInt();
			if(header.charAt(header.length()-1) != 0){
				return false;
			}

			//Bytes 10-13 give the offset in bytes to the start of the image data
			header += "\nImage offset: ";
			header += bbuf.getInt();

			//Bytes 14-17 give the size of the BitMap Info Header (bytes)
			header += "\nInfo header size: ";
			header += bbuf.getInt();

			//Bytes 18-21 give the image width
			header += "\nWidth: ";
			header += bbuf.getInt();

			//Bytes 22-25 give the image height
			header += "\nHeight: ";
			header += bbuf.getInt();

			//Bytes 26-27 give the number of planes in the image (must be 1)
			header += "\nNumber of planes: ";
			int planesAndBits = bbuf.getInt();	//planesAndBits is 2 2-byte integers combined
			if(bbuf.order() == ByteOrder.LITTLE_ENDIAN){
				header += planesAndBits%256;	//The modulo keeps only the 2nd 2 bytes
			}
			else{
				header += planesAndBits/256;	//Integer division by 256 keeps only the first 2 bytes
			}

			//Bytes 28-29 give the number of bits per pixel
			header += "\nBits per pixel: ";
			if(bbuf.order() == ByteOrder.LITTLE_ENDIAN){
				header += planesAndBits/256;		
			}
			else{
				header += planesAndBits%256;
			}

			//Bytes 30-33 give the compression type (0 means none)
			header += "\nCompression type: ";
			header += bbuf.getInt();

			//Bytes 34-37 give the size (bytes) of the image data
			header += "\nImage data size: ";
			header += bbuf.getInt();

			//Bytes 38-43 give the horizontal resolution in pixels per meter (unreliable)
			header += "\nHorizontal resolution: ";
			header += bbuf.getInt();

			//Bytes 42-45 give the vertical resolution in pixels per meter (unreliable)
			header += "\nVertical resolution: ";
			header += bbuf.getInt();

			//Bytes 46-49 give the number of colors, or 0
			header += "\nNumber of colors: ";
			header += bbuf.getInt();

			//Bytes 50-53 give the number of important colors, or 0
			header += "\nNumber of important colors: ";
			header += bbuf.getInt();

		} catch (IOException e) {
			System.out.println("Error reading file.");
			e.printStackTrace();
			return false;
		}

		System.out.println(header);
		return true;



	}

	public static void main(String[] args){
		BmpReader read = new BmpReader("map/terrain.bmp");
		read.printHeader();
	}

}
