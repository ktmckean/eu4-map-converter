package eu4MapConverter;

import java.lang.Math.*;

public static class Converter
{
	static Projection outputProjection;	//
	Map outMap;
	static String[] mapFiles = {
			"map/heightmap.bmp",
			"map/provinces.bmp",
			"map/rivers.bmp",
			"map/terrain.bmp",
			"map/trees.bmp",
			"map/world_normal",		// This and all below are half-size
			"map/random/colormap_autumn",
			"map/random/colormap_spring",
			"map/random/colormap_summer",
			"map/random/colormap_water_base",
			"map/random/colormap_winter",
			"map/random/heightmap_base"		// Not listed on wiki
	};

	static String[] canalMapsFiles = {
			"map/kiel_canal_river.bmp",
			"map/panama_canal_river.bmp",
			"map/suez_canal_river.bmp"
	};

	static String[] TextFiles = {
			"map/ambient_object.txt",
			"map/positions.txt",
			"map/lakes/00_lakes.txt"
			// maps/tradewinds.txt has the orentation of tradewind arrows.  Not necessary for release version
			// maps/adjacencies.txt controls where strait lines are drawn.  Probably still works without changing this, but needed to look nice.
	};


	// Ultimately, this should read through all the relevant directories, converting all relevant maps and text files
	//	That means a lot of supplementary methods will need to be written.
	static void ConvertAllFiles(Projection inputProj, Projection outputProj)
	{
		outputProjection = outputProj;

		// Convert baseline map -- Terrain?  Provinces?



		// ConvertAllMaps

		// ConvertAllTextFiles
	}

	// This should maybe be
	static Map ConvertMap(Map inMap, Projection outputProjection)
	{
		byte[][] mappedValues;	// An array of output pixels (main array); and of the input pixels that were mapped to each (sub-arrays)
		int[] numMappedTo;	//  Tracks how many values outPix[i] has been assignedz
		outPix = new bool[input.getSize()];
		numMappedTo = new int[input.getSize()];
		outputProjection = outputProjection;

		//	Assign pixels
		convertPixels(inMap);

		// Convert all pixels
		// Resolve pixel collisions
		//


		return input;
	}

	// A basic Miller-To-Spherical conversion.  Does not account for the American Latitude adjustment or any of the
	//	fine-tuning necessary for, e.g., rivers
	static Map FromPdxMiller(Map in)
	{
		// int[][] latLong = new byte[in.getPix().length][];
		int[][] latLong = new int[in.getPix().length][2];

		for (int i = 0; i < in.getPix().length; ++i){
			int x = i % in.getWidth(); 	// Pixel x coord
			int y = i / in.getWidth(); 	// Pixel y coord

			latLong[i][0] = x;										  // Longitude
			latLong[i][1] = 1.25 * Math.arctan(Math.sinh(0.8 * y)); // Latitude
		}

		// Convert latLong to map.  Keys are ints (in.pix entries), values are <int,int> pairs (long, lat)
		//

		// Desired output:  A map but instead of the pixels being (x,y) image coords, they should be (lat,long) coordinates

		// For each entry in latLong:
		//

		// For every entry in pix, define an entry in retvalPix
		//

		// Smooth lat and long to not conflict
		Map retval;

		//
	}



	// Assigns each input pixel to an output pixel
	static void convertPixels(Map inMap)
	{

	}

}
