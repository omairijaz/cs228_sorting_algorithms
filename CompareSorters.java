package edu.iastate.cs228.hw2;

/**
 *  
 * @author Omair Ijaz
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Perform the four sorting algorithms over each sequence of integers, comparing 
	 * points by x-coordinate or by polar angle with respect to the lowest point.  
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 **/
	public static void main(String[] args) throws FileNotFoundException 
	{		
		// TODO 
		// 
		// Conducts multiple sorting rounds. In each round, performs the following: 
		// 
		//    a) If asked to sort random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		//    b) Reassigns to elements in the array sorters[] (declared below) the references to the 
		//       four newly created objects of SelectionSort, InsertionSort, MergeSort and QuickSort. 
		//    c) Based on the input point order, carries out the four sorting algorithms in a for 
		//       loop that iterates over the array sorters[], to sort the randomly generated points
		//       or points from an input file.  
		//    d) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 2 of the assignment description. 
		// 	
		
		
		// Within a sorting round, every sorter object write its output to the file 
		// "select.txt", "insert.txt", "merge.txt", or "quick.txt" if it is an object of 
		// SelectionSort, InsertionSort, MergeSort, or QuickSort, respectively. 
		
		AbstractSorter[] sorters = new AbstractSorter[4]; //all variables we need
		Scanner in = new Scanner(System.in);
		int keyInput;
		int orderInput;
		int trialNumber = 1;
		
		String header1 = "algorithm";
		String header2 = "size";
		String header3 = "time (ns)";
		
		System.out.println("Comparison of Four Sorting Algorithms \n");
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
		System.out.println("order: 1 (by x-coordinate) 2 (by polar angle) \n");
		
		while(true)
		{
			System.out.printf ("Trial %d => Enter key: ", trialNumber);
			keyInput = in.nextInt();
			
			if(keyInput == 1)//we must create a random Point array if keyInput is 1
			{
				System.out.print("Enter number of random points:  ");
				int numOfRandPoints = in.nextInt();
				Random generator = new Random();
				Point[] rp = generateRandomPoints(numOfRandPoints, generator);//create 4 sort objects from this random Point array
				sorters[0] = new SelectionSorter(rp);
				sorters[1] = new InsertionSorter(rp);
				sorters[2] = new MergeSorter(rp);
				sorters[3] = new QuickSorter(rp);
			}
			if (keyInput == 2)
			{
				System.out.println("Points from a file");
				System.out.print("File name: ");
				String inputFileName = in.next(); //create 4 sort objects with numbers from this fileName;
				sorters[0] = new SelectionSorter(inputFileName);
				sorters[1] = new InsertionSorter(inputFileName);
				sorters[2] = new MergeSorter(inputFileName);
				sorters[3] = new QuickSorter(inputFileName);
			}
			if(keyInput == 3)
				break;

			System.out.print("Order used in sorting: ");
			orderInput = in.nextInt();
			
			System.out.println("\n");
			String header = String.format("%-20s %-12s %-12s", header1, header2, header3);
			System.out.println(header);
			System.out.println("------------------------------------------- \n");
			
			//sort our objects and then print their stats
			for (int i = 0; i < sorters.length; i++)
			{
				sorters[i].sort(orderInput);
				System.out.print(sorters[i].stats());
				sorters[i].writePointsToFile();
			}
			System.out.println("");
			trialNumber++; //increment trial number
		}
		in.close();
	}
	
	
	/**
	 * This method generates a given number of random points to initialize randomPoints[].
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 of assignment description document on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1)
			throw new IllegalArgumentException("Number of points must be larger than 1");
		//create Random object to generate pseudo random numbers
		Point[] setOfRandPoints = new Point[numPts]; //create a point array to be returned with size of input
		
		for(int i = 0; i < numPts; i++)
		{//this loop creates a random point array
			int randNum1 = rand.nextInt(101) - 50;
			int randNum2 = rand.nextInt(101) - 50;
			Point randPoint = new Point(randNum1, randNum2);
			setOfRandPoints[i] = randPoint;
		}
		return setOfRandPoints;
	}
}
