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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 * in the array. 
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);//set up the other instance variables in abstract sorter in this constructor
		this.algorithm = "merge sort";
		this.outputFileName = "merge.txt";
	}
	
	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public MergeSorter(String inputFileName) throws FileNotFoundException
	{
		super(inputFileName);
		this.algorithm = "merge sort";
		this.outputFileName = "merge.txt";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
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
		mergeSortRec(this.points);
		this.sortingTime = System.nanoTime() - startTime;
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)//we can change this method as much as we want, its private
	{
		if(pts.length <= 1) //base case
			return;
		//creating our split arrays of points
		Point[] first = new Point[pts.length / 2];
		Point[]	second = new Point[pts.length - first.length];
		
		for(int i = 0; i < first.length; i++)
		{//filling in our first half
			first[i] = pts[i];
		}
		
		for(int i = 0; i < second.length; i++)
		{//filling in our second half
			second[i] = pts[first.length + i];
		}
		
		mergeSortRec(first); //recursively call this method
		mergeSortRec(second);//until we can merge them
		merge(first, second, pts);
		
	}
		

	
	private void merge(Point[] first, Point[] second, Point[] pts)
	{
		int firstIndex = 0; //next element position in first points array
		int secondIndex = 0;//next element position in second points array
		int openPos = 0; 	//next open pos in final array
		
		while(firstIndex < first.length && secondIndex < second.length)
		{
			if(pointComparator.compare(first[firstIndex], second[secondIndex]) < 0)	//while our first and second array are not emptied
			{																			//we fill the final array in nondecreasing order
																						//from the first or second Point array
				pts[openPos] = first[firstIndex];
				firstIndex++;
			}
			else
			{
				pts[openPos] = second[secondIndex];
				secondIndex++;
			}
			openPos++;	
		}
		
		while (firstIndex < first.length)
		{//this checks for the case where our second is emptied while we still have point in our first
			pts[openPos] = first[firstIndex];
			firstIndex++;
			openPos++;
		}
		
		while (secondIndex < second.length)
		{//this checks for the case where our first is emptied while we still have point in our second
			pts[openPos] = second[secondIndex];
			secondIndex++;
			openPos++;
		}
	}
}
