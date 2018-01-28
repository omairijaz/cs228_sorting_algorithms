package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException; 


/**
 *  
 * @author Omair Ijaz
 *
 */

/**
 * 
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);//set up the other instance variables in abstract sorter in this constructor
		this.algorithm = "selection sort";
		this.outputFileName = "select.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public SelectionSorter(String inputFileName) throws FileNotFoundException
	{
		super(inputFileName);
		this.algorithm = "selection sort";
		this.outputFileName = "select.txt";
	}
	
	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 *
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{
		if (order > 2 || order < 1)
			throw new IllegalArgumentException("Illegal parameter for " + this.getClass());
		if(order == 1)
		{
			this.sortByAngle = false;//we will only be sorting by x axis location
			setComparator(); 		 //we are now able to compare using point's compare
		}
		if(order == 2)
		{
			this.sortByAngle = true;
			setComparator(); 		//this sets our comparator to use PolarAngleComparator's compare method
		}
		
		//time to begin actual sorting
		if (this.points == null || this.points.length == 0)
			throw new NullPointerException();
				
		long startTime = System.nanoTime();					//start our timer
		for(int i = 0; i < points.length - 1; i++) 			//we need to loop thru the entire array
		{
			
			int minPos = i;									//find the position of the element with the smallest X/Polar angle
			for(int j = i + 1; j < points.length; j++)		//we start the loop at 1 and go up
			{
				if (pointComparator.compare(points[j], points[minPos]) < 0) //nondecreasing order means that we must find -1
					minPos = j;												//the point that returned -1 is smaller than the point we passed in
			}
			//use swap here once we found the SMALLEST point
			if ( i!= minPos )
				swap(minPos, i);
		}
		this.sortingTime = System.nanoTime() - startTime;					//stop our timer and set our field equal to the difference
	}
}
