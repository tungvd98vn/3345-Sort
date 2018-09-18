/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3345.hw6;

/**
 *
 * @author Tung
 */

import java.util.*;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Sorts{

   static final int SIZE = 100; // size of array, change this for different cases
   static int[] array = new int[SIZE]; // values to be sorted

   static int move = 0;
   static int comparison = 0;

   // function to build the array with menu of choices
   static void Build(){
       
       int choice;
       int size;
       Random rand = new Random();
       Scanner input = new Scanner(System.in);
       
       System.out.println("Please select: 1-random; 2-sorted; 3-inverse sorted; 4-almost sorted :" );
       choice = input.nextInt();
       
       switch(choice){
            case 1: // random
                for (int index = 0; index < SIZE; index++)
                    array[index] = Math.abs(rand.nextInt()) % SIZE;
                break;
            case 2:// sorted
                for (int index = 0; index < SIZE; index++)
                    array[index] = index;
                break;
            case 3: // inverse
                int i = 0;
                for (int index = SIZE; index > 0; index--){
                    array[i] = index;
                    i = i+1;
                }
                break;
            case 4: // almost sorted
                array[0] = (int)(Math.random () * 10) + 1;

                for (int a = 1; a < SIZE; a++) {
                    array[a] = array[a-1] + (int)(Math.random() * 12) - 2;
                }
       }
       
       
       
       
       
   }

   // swap function
   static public void swap(int index1, int index2){
       move++;
       int temp = array[index1];
       array[index1] = array[index2];
       array[index2] = temp;
   }

   // Selection Sort

   static int minIndex(int startIndex, int endIndex){
       int indexOfMin = startIndex;
       for (int index = startIndex + 1; index <= endIndex; index++){
           comparison++;
           if (array[index] < array[indexOfMin])
               indexOfMin = index;
       }
       return indexOfMin;
   }

   static void selectionSort(){
       int endIndex = SIZE - 1;
       for (int current = 0; current < endIndex; current++)
           swap(current, minIndex(current, endIndex));
   }

   // Insertion Sort

   static void insertItem(int startIndex, int endIndex){
       boolean finished = false;
       int current = endIndex;
       boolean moreToSearch = true;
       while (moreToSearch && !finished){
           comparison++;
           if (array[current] < array[current - 1]){
               swap(current, current - 1);
               current--;
               moreToSearch = (current != startIndex);
           }else
               finished = true;
       }
   }
   
   static void insertionSort(){
       for (int count = 1; count < SIZE; count++)
           insertItem(0, count);
   }


   // Merge Sort

   static void merge (int leftFirst, int leftLast, int rightFirst, int rightLast){
       int[] tempArray = new int [SIZE];
       int index = leftFirst;
       int saveFirst = leftFirst; // to remember where to copy back
       while ((leftFirst <= leftLast) && (rightFirst <= rightLast))
       {
           comparison++;
           if (array[leftFirst] < array[rightFirst])
           {
               tempArray[index] = array[leftFirst];
               leftFirst++;
           }
           else
           {
               tempArray[index] = array[rightFirst];
               rightFirst++;
           }
           index++;
       }
       while (leftFirst <= leftLast)
           // Copy remaining items from left half.
       {
           tempArray[index] = array[leftFirst];
           leftFirst++;
           index++;
       }
       while (rightFirst <= rightLast)
           // Copy remaining items from right half.
       {
           tempArray[index] = array[rightFirst];
           rightFirst++;
           index++;
       }
       for (index = saveFirst; index <= rightLast; index++)
           array[index] = tempArray[index];
   }

   static void mergeSort(int first, int last)

   // Sorts the values array using the merge sort algorithm.

   {
       comparison++;
       if (first < last)
       {
           int middle = (first + last) / 2;
           mergeSort(first, middle);
           mergeSort(middle + 1, last);
           merge(first, middle, middle + 1, last);
       }
   }

   // Quick Sort

   static int split(int first, int last){
       int splitVal = array[first];
       int saveF = first;
       boolean onCorrectSide;
       first++;
       do{
           onCorrectSide = true;
           while (onCorrectSide){ // move first toward last
               comparison++;
               if (array[first] > splitVal)
                   onCorrectSide = false;
               else{
                   first++;
                   onCorrectSide = (first <= last);
               }
           }
           onCorrectSide = (first <= last);
           while (onCorrectSide) { // move last toward first
               comparison++;
               if (array[last] <= splitVal)
                   onCorrectSide = false;
               else{
                   last--;
                   onCorrectSide = (first <= last);
               }
           }
           comparison++;
           if (first < last){
               swap(first, last);
               first++;
               last--;
           }
       } while (first <= last);
       swap(saveF, last);
       return last;
   }

   static void quickSort(int first, int last){
       comparison++;
       if (first < last){
           int splitPoint;
           splitPoint = split(first, last);
           // values[first]..values[splitPoint - 1] <= splitVal

           // values[splitPoint] = splitVal

           // values[splitPoint+1]..values[last] > splitVal
           quickSort(first, splitPoint - 1);
           quickSort(splitPoint + 1, last);

       }

   }

   // Heap Sort

   static int newHole(int hole, int lastIndex, int item){
       int left = (hole * 2) + 1;
       int right = (hole * 2) + 2;
       comparison++;
       if (left > lastIndex)
           // hole has no children
           return hole;
       else{
           comparison++;
           if (left == lastIndex){
               comparison++;
               // hole has left child only
               if (item < array[left])
                   // item < left child
                   return left;
               else
                   // item >= left child
                   return hole;
           }
           else{
               comparison++;
               // hole has two children
               if (array[left] < array[right]){
                   comparison++;
                   // left child < right child
                   if (array[right] <= item)
                       // right child <= item
                       return hole;
                   else
                       // item < right child
                       return right;
               }
               else{
                   comparison++;
                   // left child >= right child
                   if (array[left] <= item)
                       // left child <= item
                       return hole;
                   else
                       // item < left child
                       return left;
               }
           }
       }
   }

   static void reheapDown(int item, int root, int lastIndex)
   {
       int hole = root; // current index of hole
       int newhole; // index where hole should move to
       newhole = newHole(hole, lastIndex, item); // find next hole
       while (newhole != hole)
       {
           array[hole] = array[newhole]; // move value up
           hole = newhole; // move hole down
           newhole = newHole(hole, lastIndex, item); // find next hole
       }
       array[hole] = item; // fill in the final hole
   }
   static void heapSort()
   // Sorts the values array using the heap sort algorithm.
   {
       int index;
       // Convert the array of values into a heap.
       for (index = SIZE/2 - 1; index >= 0; index--)
           reheapDown(array[index], index, SIZE - 1);
       // Sort the array.
       for (index = SIZE - 1; index >=1; index--)
       {
           swap(0, index);
           reheapDown(array[0], 0, index - 1);
       }
   }

   
   public static void main(String[] args)

   {
       long startTime = 0;
       long endTime = 0;
       
       System.out.println("The size of the array is: " + SIZE);
       Build();
       int[] copy = Arrays.copyOf(array, SIZE);

       // make call to sorting method here (just remove //)

       
       System.out.println("Sort\t\t\tMovements\tComparisons\t\tTime");
       
       
       
       Sorts.comparison = 0;
       Sorts.move = 0;
       array = Arrays.copyOf(copy, SIZE);
       startTime = System.nanoTime();
       insertionSort();
       endTime = System.nanoTime();
       System.out.print("InsertionSort\t\t"+Sorts.move+"\t\t"+Sorts.comparison);
       System.out.println("\t\t\t" + (float)(endTime - startTime)/1000000 + " milliseconds");
       
       Sorts.comparison = 0;
       Sorts.move = 0;
       startTime = System.nanoTime();
       selectionSort();
       endTime = System.nanoTime();
       System.out.print("Selection\t\t"+Sorts.move+"\t\t"+Sorts.comparison);
       System.out.println("\t\t\t" + (float)(endTime - startTime)/1000000 + " milliseconds");

       Sorts.comparison = 0;
       Sorts.move = 0;
       array = Arrays.copyOf(copy, SIZE);
       startTime = System.nanoTime();
       quickSort(0, SIZE - 1);
       endTime = System.nanoTime();
       System.out.print("QuickSort\t\t"+Sorts.move+"\t\t"+Sorts.comparison);
       System.out.println("\t\t\t" + (float)(endTime - startTime)/1000000 + " milliseconds");
       
       Sorts.comparison = 0;
       Sorts.move = 0;
       array = Arrays.copyOf(copy, SIZE);
       startTime = System.nanoTime();
       mergeSort(0, SIZE - 1);
       endTime = System.nanoTime();
       System.out.print("MergeSort\t\t"+Sorts.move+"\t\t"+Sorts.comparison);
       System.out.println("\t\t\t" + (float)(endTime - startTime)/1000000 + " milliseconds");
       
       Sorts.comparison = 0;
       Sorts.move = 0;
       array = Arrays.copyOf(copy, SIZE);
       startTime = System.nanoTime();
       heapSort();
       endTime = System.nanoTime();
       System.out.print("HeapSort\t\t"+Sorts.move+"\t\t"+Sorts.comparison);
       System.out.println("\t\t\t" + (float)(endTime - startTime)/1000000 + " milliseconds");

   }
}


