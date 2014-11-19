import java.io.*;
import java.util.*;

/**
 * 
 * @author Team strikers
 * this is the implementation of an instance of both the galeShapely and the Merit List Allocation algorithm
 */
public class Main{
	/**
	 * this is the main function
	 */
	public static void main(String args[]){
		meritListAllocation task2=new meritListAllocation();
		task2.print();
		GaleShapelyAdmission geez=new GaleShapelyAdmission();
		geez.output();
	}
}
