package edu.iastate.cs228.hw2;


/**
 *  
 * @author Omair Ijaz
 *
 */

import java.util.Arrays;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort, and QuickSort.
 * It stores the input (later on the sorted) sequence and records the employed sorting algorithm, 
 * the comparison method, and the time spent on sorting. 
 *
 */


public abstract class AbstractSorter
{
	
	protected Point[] points;    // Array of points operated on by a sorting algorithm. 
	                             // The number of points is given by points.length.
	
	protected String algorithm = null; // "selection sort", "insertion sort",  
	                                   // "merge sort", or "quick sort". Initialized by a subclass 
									   // constructor.
	protected boolean sortByAngle;     // true if last sort was done by polar angle and false 
									   // if by x-coordinate 
	
	protected String outputFileName;   // "select.txt", "insert.txt", "merge.txt", or "quick.txt"
	
	protected long sortingTime; 	   // execution time in nanoseconds. 
	 
	protected Comparator<Point> pointComparator;  // comparator which compares polar angle if 
												  // sortByAngle == true and x-coordinate if 
												  // sortByAngle == false 
	
	private Point lowestPoint; 	    // lowest point in the array, or in case of a tie, the
									// leftmost of the lowest points. This point is used 
									// as the reference point for polar angle based comparison.

	
	// Add other protected or private instance variables you may need. 
	
	/**
	 * This constructor accepts an array of points as input. Copy the points into the array points[]. 
	 * Sets the instance variable lowestPoint.
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0)
			throw new IllegalArgumentException("Invalid Point array parameter for " + this.getClass());
		
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) //copying our parameter argument into our field
		{
			points[i] = pts[i];
		}
		
		lowestPoint = points[0]; //we set the lowestPoint as the first point
		//this for loop goes thru each point and compares its y-coordinate with the lowestPoints' y-coord
		for (int i = 1; i < points.length; i++)
		{
			if (points[i].getY() < lowestPoint.getY()) //if our points y coord is smaller we change lowestPoint
				lowestPoint = points[i];
			
			if (points[i].getY() == lowestPoint.getY())//if our points y coord is the same then we must compare our x coord
				if(points[i].getX() < lowestPoint.getX()) 
					lowestPoint = points[i];
		}
	}

	/**
	 * This constructor reads points from a file. Sets the instance variables lowestPoint and 
	 * outputFileName.
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	protected AbstractSorter(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		File inputFile = new File(inputFileName);//construct our scanner that reads our File object
		
		Scanner in = new Scanner(inputFile);
		
		//we must set up our points, the ints are given and a point consists of 2 ints: first x then y
		//to avoid exceptions use a while loop that sees if we have a next int
		//in the while loop construct a point object and assign it into the array
		int i = 0;//keep track of our array index and size
		points = new Point[3];

		while(in.hasNextInt())
		{
			int xValue = in.nextInt();//we do know that we still have atleast one more bc we are in the while loop
			
			if(in.hasNextInt()) //if we still have another int value we can create a point and put it into our array
			{
				int yValue = in.nextInt();
				Point newPoint = new Point(xValue, yValue);
				if(i < points.length) //if our array is still less than our original size we can continue
				{
					points[i] = newPoint;
					i++; 
				}	
				else
				{	//otherwise we create a larger array
					Point[] newpoints = Arrays.copyOf(points, 2 * points.length);
					points = newpoints;
					points[i] = newPoint;
					i++;
				}
			}
			else
			{	//now if we didnt get a y value we had an odd amount of inputs, throw an exception
				throw new InputMismatchException("Odd number of inputs in " + this.getClass());
			}
		}
		in.close();//close our scanner after we are done
		points = Arrays.copyOf(points, i);//change the size of the array to fit aka get rid of null points
		
		//finally this is how we set up the lowest point now that we have an array of points, same as in
		//the other constructor
		lowestPoint = points[0]; 
		for (int j = 1; j < points.length; j++)
		{
			if (points[j].getY() < lowestPoint.getY()) 
				lowestPoint = points[j];
			
			if (points[j].getY() == lowestPoint.getY())
				if(points[j].getX() < lowestPoint.getX())
					lowestPoint = points[j];
		}
	}
	

	/**
	 * Sorts the elements in points[]. 
	 * 
	 *     a) in the non-decreasing order of x-coordinate if order == 1
	 *     b) in the non-decreasing order of polar angle w.r.t. lowestPoint if order == 2 
	 *        (lowestPoint will be at index 0 after sorting)
	 * 
	 * Sets the instance variable sortByAngle based on the value of order. Calls the method 
	 * setComparator() to set the variable pointComparator and use it in sorting.    
	 * Records the sorting time (in nanoseconds) using the System.nanoTime() method. 
	 * (Assign the time to the variable sortingTime.)  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle w.r.t lowestPoint 
	 *
	 * @throws IllegalArgumentException if order is less than 1 or greater than 2
	 */
	public abstract void sort(int order) throws IllegalArgumentException; 
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the assignment description. 
	 */
	public String stats()
	{
		String output = String.format("%-20s %-12d %-12d %n", this.algorithm, points.length, this.sortingTime);
		return output;
	}
	
	
	/**
	 * Write points[] to a string.  When printed, the points will appear in order of increasing
	 * index with every point occupying a separate line.  The x and y coordinates of the point are 
	 * displayed on the same line with exactly one blank space in between. 
	 */
	@Override
	public String toString()
	{
		String pointsString = new String();
		for (int i = 0; i < points.length; i++)
		{
			pointsString += points[i].getX() + " " + points[i].getY() + "\n";
		}
		return pointsString;
	}

	
	/**
	 *  
	 * This method, called after sorting, writes point data into a file by outputFileName. It will 
	 * be used for Mathematica plotting to verify the sorting result.  The data format depends on 
	 * sortByAngle.  It is detailed in Section 4.1 of the assignment description assn2.pdf. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(this.outputFileName);
		if(sortByAngle) //our format has to meet the spec found in 4.1 if sortbyangle is true
		{
			for (int i = 0; i < points.length; i++)
			{
				if (i == 0)
					pw.println(lowestPoint.getX()   + " " + lowestPoint.getY());
				else
				{
					pw.println( points[i].getX()   + " " + points[i].getY() + " "
								+ lowestPoint.getX()   + " " + lowestPoint.getY()   + " "
								+ points[i].getX() + " " + points[i].getY() );
				}
			}
		}
		else //this is how we print our sorted by x's
		{
			for(Point element : points)
			{
				pw.println(element.getX() + " " + element.getY());
			}
		}
		pw.close();//close our printwriter object
	}	

	
	/**
	 * Generates a comparator on the fly that compares by polar angle if sortByAngle == true
	 * and by x-coordinate if sortByAngle == false. Set the protected variable pointComparator
	 * to it. Need to create an object of the PolarAngleComparator class and call the compareTo() 
	 * method in the Point class, respectively for the two possible values of sortByAngle.  
	 * 
	 * @param order
	 */
	protected void setComparator() 
	{
		if(sortByAngle)	//if we have to sort by angle, we create a polarAngleComparator object which has a compare method
			pointComparator = new PolarAngleComparator(this.lowestPoint);
		else
		{
			pointComparator = new Comparator<Point>()
			{  //anonymous class, we create an object of this class
				@Override								//which lets us use the compareTo method found in point
				public int compare(Point a, Point b)	
				{
					return a.compareTo(b);
				}
			};
		}
	}

	
	/**
	 * Swap the two elements indexed at i and j respectively in the array points[]. 
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}	
}
