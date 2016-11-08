
/*******************************************************/
/*** Purpose:implementations of three search  **********/
/***methods(refined sequential search,    *************/
/***binary search and hash search with linear probing)**/
/*** Author:Matthew Walls                            ***/
/*** Date:23/11/2016                                ***/
/***                                          ***/
/*** Note: Based on skeleton code provided by ***/
/*** Jason Steggles 23/09/2016                ***/
/************************************************/

import java.io.*;
import java.text.*;
import java.util.*;

public class Search {

	/** Global variables for counting comparisons **/
	public int compSeq = 0;
	public int compBin = 0;
	public int compHash = 0;

	/** Array of values to be searched and size **/
	private int[] A;
	private int size;

	/** Hash array and size **/
	private int[] H;
	private int hSize;

	/** Constructor **/
	Search(int n, int hn) {
		/** set size of array **/
		size = n;
		hSize = hn;

		/** Create arrays **/
		A = new int[size];
		H = new int[hSize];

		/** Initialize hash array **/
		/** Assume -1 indicates a location is empty **/
		for (int i = 0; i < hSize; i++) {
			H[i] = -1;
		}
	}

	/********************************************/
	/*** Read a file of numbers into an array ***/
	/********************************************/
	public void readFileIn(String file) {
		try {
			/** Set up file for reading **/
			FileReader reader = new FileReader(file);
			Scanner in = new Scanner(reader);

			/** Loop round reading in data **/
			for (int i = 0; i < size; i++) {
				/** Get net value **/
				A[i] = in.nextInt();
			}
		} catch (IOException e) {
			System.out.println("Error processing file " + file);
		}
	}

	/*********************/
	/*** Hash Function ***/
	/*********************/
	public int hash(int key) {
		return key % hSize;
	}

	/********************************************/
	/**** add a number into the Hash array *****/
	/********************************************/
	private void addToHash(int value) {
		if (H[hash(value)] == -1) {
			// if space in the hash array is empty, place the value in the
			// position
			H[hash(value)] = value;
		} else {
			int i = hash(value);
			// if hash array position is taken then search through array to next
			// available position
			while (H[i] != -1) {
				i++;
				if (i == hSize) {
					i = 0;
					// if at the end of the array, circle back to start
				} else if (i == H[hash(value)]) {
					System.out.println("Error, hash array full");
					return;
					// if array is full return
				}

			}

			H[i] = value;
			// add value at position H[i]
		}
	}

	/********************************************/
	/*** Read a file of numbers into the Hash ***/
	/********************************************/
	public void readIntoHash(String file) {
		try {

			FileReader reader = new FileReader(file);
			Scanner in = new Scanner(reader);
			// read in each value and call method to add to the hash table

			for (int i = 0; i < size; i++) {

				addToHash(in.nextInt());
				// call addToHash method with values from file
			}
		} catch (IOException e) {
			System.out.println("Error processing file " + file);
		}
	}

	/*****************************/
	/******** Hash Search ********/
	/*****************************/
	public int hashSearch(int key) {
		int i = hash(key);
		// begin search at hashed index

		while (H[i] != -1) {
			// if H[i] is equal to -1 then the value isn't in the hash array
			compHash = compHash + 2;
			// increment compSeq to account for (A[i] <= key && i < size)
			// returning true and increment comparisons for (A[i] == key)
			if (H[i] == key)
				return i;
			// if value found return index
			i++;
			if (i == hSize)
				i = 0;
			// if index is at the end of the array circle back to the start

		}
		compHash++;
		// add one more to compHash to account for the final while condition
		// check when the value isn't found
		return -1;
		// return -1 if value not found
	}

	/*****************************/
	/*** Display array of data ***/
	/*****************************/
	public void displayData(int line, String header) {
		/* ** Integer Formatter ** */
		NumberFormat FI = NumberFormat.getInstance();
		FI.setMinimumIntegerDigits(3);

		/** Print header string **/
		System.out.print("\n\n" + header);

		/** Display array data **/
		for (int i = 0; i < size; i++) {
			/** New line? **/
			if (i % line == 0) {
				System.out.println();
			}

			/** Display value **/
			System.out.print(FI.format(A[i]) + " ");
		}
	}

	/**************************/
	/*** Display hash array ***/
	/**************************/
	public void displayHash(int line) {
		/** Integer Formatter **/
		NumberFormat FI = NumberFormat.getInstance();
		FI.setMinimumIntegerDigits(3);
		int cs = 0;
		/** Print header string **/
		System.out.print("\n\nHash Array of size " + hSize);

		/** Display array data **/
		for (int i = 0; i < hSize; i++) {
			/** New line? **/
			if (i % line == 0) {
				System.out.println();
			}

			/** Display value **/
			System.out.print(FI.format(H[i]) + " ");
		}

	}

	/**************************/
	/*** Sequential search ***/
	/**************************/
	public int seqSearch(int key) {
		int i = 0;
		// i used to iterate through the array

		while (A[i] <= key && i < size) {
			compSeq = compSeq + 2;
			// increment compSeq to account for (A[i] <= key && i < size)
			// returning true and increment comparisons for (A[i] == key)

			if (A[i] == key)
				return i;
			// i is position in the array that the key is located, therefore
			// return i

			i++;

		}
		compSeq++;
		// increment for comparison (A[i] <= key && i < size)returning false
		return -1;
		// key not found so return -1
	}

	/**************************/
	/***** Binary search ******/
	/**************************/
	public int binSearch(int key, int L, int R) {
		if (R < L)
			return -1;
		// if right pointer is less than left pointer then return -1
		int m = ((R + L) / 2);
		// calculate the mid point of the array
		compBin = compBin + 1;
		// count increased for comparison below
		if (key == A[m])
			return m;
		// if midpoint equals the key being searched for then return the
		// index m

		compBin = compBin + 1;
		// count increased for comparison below
		if (key > A[m]) {

			// if midpoint is greater than the key being searched for then
			// return the search on the right hand side of the array (all values
			// greater than midpoint)
			return binSearch(key, m + 1, R);

		} else {

			// if midpoint is less than the key being searched for then return
			// the search on the left hand side of the array (all values less
			// than midpoint)
			return binSearch(key, L, m - 1);
		}
	}

} /*** End of class Search ***/