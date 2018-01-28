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
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
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
	public InsertionSorter(Point[] pts) 
	{
		super(pts);//set up the other instance variables in abstract sorter in this constructor
		this.algorithm = "insertion sort";
		this.outputFileName = "insert.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public InsertionSorter(String inputFileName) throws FileNotFoundException
	{
		super(inputFileName);
		this.algorithm = "insertion sort";
		this.outputFileName = "insert.txt"; 
	}
	
	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
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
		
		//start our counter
		long startTime = System.nanoTime();
		//implement insertion sort
		for (int i = 1; i < points.length; i++) //we assume the first element is sorted
		{
			Point nextPoint = points[i];
			//move all larger points up
			int j = i;
			while (j > 0 && pointComparator.compare(points[j - 1], nextPoint) > 0) 
			{//this while loop continues until we are no longer larger
				points[j] = points[j - 1];
				j--;
			}
			//insert our point in the correct location
			points[j] = nextPoint;
		}
		this.sortingTime = System.nanoTime() - startTime;
	}		
}
