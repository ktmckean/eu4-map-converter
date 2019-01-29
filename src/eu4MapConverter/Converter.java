package eu4MapConverter;

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



	// Assigns each input pixel to an output pixel
	static void convertPixels(Map inMap)
	{

	}

}
