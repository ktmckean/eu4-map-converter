package eu4MapConverter;

public class Converter
{
	byte[][] mappedValues;	// An array of output pixels (main array); and of the input pixels that were mapped to each (sub-arrays)
	int[] numMappedTo;	//  Tracks how many values outPix[i] has been assigned
	Projection outputProjection;	//
	Map outMap;

	static Map Convert(Map inMap, Projection outputProjection)
	{
		outPix = new bool[input.getSize()];
		numMappedTo = new int[input.getSize()];
		outputProjection = outputProjection;

		//	Assign pixels
		mapPixels(inMap);


		return input;
	}

	// Assigns each input pixel to an output pixel
	static void MapPixels(Map inMap)
	{

	}

}
