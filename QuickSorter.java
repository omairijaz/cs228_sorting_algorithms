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
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);//set up the other instance variables in abstract sorter in this constructor
		this.algorithm = "quick sort";
		this.outputFileName = "quick.txt";
	}
		

	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public QuickSorter(String inputFileName) throws FileNotFoundException
	{
		super(inputFileName);
		this.algorithm = "quick sort";
		this.outputFileName = "quick.txt";
	}


	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
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
			setComparator(); //this sets our comparator to use PolarAngleComparator's compare method
		} 	
		//actual sorting
		long startTime = System.nanoTime();
		quickSortRec(this.points , 0 , points.length - 1);
		this.sortingTime = System.nanoTime() - startTime;
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(Point[] pts, int first, int last)
	{
		if (first >= last) //base case
			return;
		int p = partition(pts, first, last);
		quickSortRec(pts, first, p);
		quickSortRec(pts, p + 1, last);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(Point[] pts, int first, int last)
	{
		Point pivot = this.points[first];
		int i = first - 1;
		int j = last + 1;
		while (i < j)
		{
			i++;
			while(pointComparator.compare(pts[i], pivot) < 0)
			{
				i++;
			}
			j--;
			while(pointComparator.compare(pts[j], pivot) > 0)
			{
				j--;
			}
			if(i < j)
				swap(i,j);
		}
		return j;
	}		
}
