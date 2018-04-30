/**
 * This class implements six different comparison sorts (and an optional
 * seventh sort for extra credit):
 * <ul>
 * <li>selection sort</li>
 * <li>insertion sort</li>
 * <li>merge sort</li>
 * <li>quick sort</li>
 * <li>heap sort</li>
 * <li>selection2 sort</li>
 * <li>(extra credit) insertion2 sort</li>
 * </ul>
 * It also has a method that runs all the sorts on the same input array and
 * prints out statistics.
 */

public class ComparisonSort {
	public static int prevDataMoves;
	
	private static <E extends Comparable<E>> E assign(E newValue){
		prevDataMoves++;
		return newValue;
	}
    /**
     * Sorts the given array using the selection sort algorithm. You may use
     * either the algorithm discussed in the on-line reading or the algorithm
     * discussed in lecture (which does fewer data moves than the one from the
     * on-line reading). Note: after this method finishes the array is in sorted
     * order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void selectionSort(E[] A) {
    	prevDataMoves=0;
    	
    	int j, k, minIndex;
        E min = null;
        int N = A.length;

        for (k = 0; k < N; k++) {
            //assign(min,A[k]);
            min = A[k];
            prevDataMoves++;
            minIndex = k;
            for (j = k+1; j < N; j++) {
                if (A[j].compareTo(min) < 0) {
                    min = assign(A[j]);
                    minIndex = j;
                }
            }
           A[minIndex] = assign(A[k]);
           A[k] = assign(min);
        }
    	
    }

    /**
     * Sorts the given array using the insertion sort algorithm. Note: after
     * this method finishes the array is in sorted order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void insertionSort(E[] A) {
    	prevDataMoves=0;
    	
    	int k, j;
        E tmp;
        int N = A.length;
          
        for (k = 1; k < N; k++) {
            tmp = assign(A[k]);
            j = k - 1;
            while ((j >= 0) && (A[j].compareTo(tmp) > 0)) {
                A[j+1] = assign(A[j]); // move one value over one place to the right
                j--;
            }
            A[j+1] = assign(tmp);    // insert kth value in correct place relative 
                               // to previous values
        }
    }

    /**
     * Sorts the given array using the merge sort algorithm. Note: after this
     * method finishes the array is in sorted order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void mergeSort(E[] A) {
    	prevDataMoves=0;
    	mergeAux(A, 0, A.length - 1); // call the aux. function to do all the work
        // TODO: implement this sorting algorithm
    }
    
    private static <E extends Comparable<E>> void mergeAux(E[] A, int low, int high) {
        // base case
        if (low == high) return;
     
        // recursive case
        
     // Step 1: Find the middle of the array (conceptually, divide it in half)
        int mid = (low + high) / 2;
         
     // Steps 2 and 3: Sort the 2 halves of A
        mergeAux(A, low, mid);
        mergeAux(A, mid+1, high);
     
     // Step 4: Merge sorted halves into an auxiliary array
        E[] tmp = (E[])(new Comparable[high-low+1]);
        int left = low;    // index into left half
        int right = mid+1; // index into right half
        int pos = 0;       // index into tmp
         
        while ((left <= mid) && (right <= high)) {
        // choose the smaller of the two values "pointed to" by left, right
        // copy that value into tmp[pos]
        // increment either left or right as appropriate
        // increment pos
        	if (A[left].compareTo(A[right]) <= 0){
                tmp[pos] = assign(A[left]);
                left++;
            }
            else {
                tmp[pos] = assign(A[right]);
                right++;
            }
            pos++;
        }
        
        // when one of the two sorted halves has "run out" of values, but
        // there are still some in the other half, copy all the remaining 
        // values to tmp
        // Note: only 1 of the next 2 loops will actually execute
        while (left <= mid) { 
        	tmp[pos] = assign(A[left]);  
        	left++;
        	pos++; 
        }
        while (right <= high) {
        	tmp[pos] = assign(A[right]); 
            right++;
            pos++;
        }
     
        // all values are in tmp; copy them back into A
        java.lang.System.arraycopy(tmp, 0, A, low, tmp.length);
    }

    /**
     * Sorts the given array using the quick sort algorithm, using the median of
     * the first, last, and middle values in each segment of the array as the
     * pivot value. Note: after this method finishes the array is in sorted
     * order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A   the array to sort
     */
    public static <E extends Comparable<E>> void quickSort(E[] A) {
    	quickAux(A, 0, A.length-1);
    }
   
    private static <E extends Comparable<E>> void quickAux(E[] A, int low, int high) {
        if (high-low < 4) insertionSortAssist(A, low, high);
        else {
            int right = partition(A, low, high);
            quickAux(A, low, right);
            quickAux(A, right+2, high);
        }
    }
    
    private static <E extends Comparable<E>> void insertionSortAssist(E[] A, int low, int high){
    	int k, j;
        E tmp;
        int N = high;
          
        for (k = low+1; k <= N; k++) {
            tmp = assign(A[k]);
            j = k - 1;
            while ((j >= low) && (A[j].compareTo(tmp) > 0)) {
                A[j+1] = assign(A[j]); 
                j--;
            }
            A[j+1] = assign(tmp);    
                               
        }
    }
    
    private static <E extends Comparable<E>> E medianOfThree(E[] A, int low, int high){
    	int mid = (low+high)/2;
    	
    	SortObject[] sortAssist = new SortObject[3];
    	sortAssist[0] = assign((SortObject)A[low]);
    	sortAssist[1] = assign((SortObject)A[mid]);
    	sortAssist[2] = assign((SortObject)A[high]);
    	
    	int tempMoveCount = prevDataMoves;
    	selectionSort(sortAssist);
    	prevDataMoves += tempMoveCount;
    	
    	A[low] = (E) assign(sortAssist[0]);
    	A[mid] = assign(A[A.length-2]);
    	A[A.length-2] = (E) assign(sortAssist[1]);
    	A[high] = (E) assign(sortAssist[2]);
    	
    	
    	return A[1];
    }
    
    private static <E extends Comparable<E>> int partition(E[] A, int low, int high) {
    	// precondition: A.length > 3

    	    E pivot = medianOfThree(A, low, high); // this does step 1
    	    int left = low+1; 
    	    int right = high-2;
    	    while ( left <= right ) {
    	        while (A[left].compareTo(pivot) < 0) left++;
    	        while (A[right].compareTo(pivot) > 0) right--;
    	        if (left <= right) {
    	            swap(A, left, right);
    	            left++;
    	            right--;
    	        }
    	    }

    	    swap(A, right+1, high-1); // step 4
    	    return right;
    	}
    
    private static <E extends Comparable<E>> void swap(E[] A, int left, int right){
    	E temp = assign(A[left]);
    	A[left] = assign(A[right]);
    	A[right] = assign(temp);
    }


    /**
     * Sorts the given array using the heap sort algorithm outlined below. Note:
     * after this method finishes the array is in sorted order.
     * <p>
     * The heap sort algorithm is:
     * </p>
     * 
     * <pre>
     * for each i from 1 to the end of the array
     *     insert A[i] into the heap (contained in A[0]...A[i-1])
     *     
     * for each i from the end of the array up to 1
     *     remove the max element from the heap and put it in A[i]
     * </pre>
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
//    public static <E extends Comparable<E>> void heapSort(E[] A) {
//    	//Set the keyArray index being pointed at with the item
//    	SortObject[] heap = new SortObject[A.length+1];
//    	for(int i = 0; i<A.length; i++){
//    		heap[i+1] = (SortObject)A[i];
//    		
//        	/*Bubble up the item to make sure that its value is less than that of
//        	*its parents
//        	*/
//        	ComparisonSort.bubbleUp(heap, i+1);
//    	}
//    	
//    	for(int i =1; i< heap.length;i++){
//    		A[i-1] = assign(removeMax(heap, heap.length-i));
//    	}
//    
//    }
    
   
	private static <E extends Comparable<E>> void bubbleUp(E[] heap, int value){
    	//if the node is in arrayHeap[1], it has no parents to be compared to
    	if(value == 1){
    		return;
    	}
    	
    	//run if the value of the parent is smaller than that of the child
    	if(heap[value/2].compareTo(heap[value]) < 0){
    		//swtich the parent and the child
    		E temp = assign(heap[value/2]);
    		heap[value/2] = heap[value];
    		heap[value] = temp;
    		//bubble up the child with its new parent
    		bubbleUp(heap,value/2);
    	}
    	return;
    }

    /**
     * Sorts the given array using the selection2 sort algorithm outlined
     * below. Note: after this method finishes the array is in sorted order.
     * <p>
     * The selection2 sort is a bi-directional selection sort that sorts
     * the array from the two ends towards the center. The selection2 sort
     * algorithm is:
     * </p>
     * 
     * <pre>
     * begin = 0, end = A.length-1
     * 
     * // At the beginning of every iteration of this loop, we know that the 
     * // elements in A are in their final sorted positions from A[0] to A[begin-1]
     * // and from A[end+1] to the end of A.  That means that A[begin] to A[end] are
     * // still to be sorted.
     * do
     *     use the MinMax algorithm (described below) to find the minimum and maximum 
     *     values between A[begin] and A[end]
     *     
     *     swap the maximum value and A[end]
     *     swap the minimum value and A[begin]
     *     
     *     ++begin, --end
     * until the middle of the array is reached
     * </pre>
     * <p>
     * The MinMax algorithm allows you to find the minimum and maximum of N
     * elements in 3N/2 comparisons (instead of 2N comparisons). The way to do
     * this is to keep the current min and max; then
     * </p>
     * <ul>
     * <li>take two more elements and compare them against each other</li>
     * <li>compare the current max and the larger of the two elements</li>
     * <li>compare the current min and the smaller of the two elements</li>
     * </ul>
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void selection2Sort(E[] A) {
        // TODO: implement this sorting algorithm
    	prevDataMoves=0;
    	int begin = 0;
    	int end = A.length-1; 
    	int i = 0;
    	int j = 0;
    	E temp; 
    	E temp1; 
    	int tempMin = 0;
    	int tempMax= A.length-1;
    	
    	do{
    		i = begin + 1;
    		j = end - 1; 
    		
    		if(A[begin].compareTo(A[end])>0){
    		temp = A[begin];
    		A[begin] = A[end]; 
    		A[end] = temp; 
    	}
    		tempMax = end;
    		tempMin = begin;
    	do{
    	 
    		
    		if(A[i].compareTo(A[j])>0){
    			if(A[i].compareTo(A[tempMax])>0){
    				tempMax = i; 
    			}
    			if(A[j].compareTo(A[tempMin])<0){
    				tempMin = j; 
    			
    		}
    		}
    		else {
    			if(A[j].compareTo(A[tempMax])>0){
    				tempMax = j; 
    			
    		}
    			if(A[i].compareTo(A[tempMin])<0){
    				tempMin = i;  
    			}
    		}
    		
    		i++;
    		j--;
    		

    	
    	} while(i<=j);
    	
    	temp =A[end];
    	temp1 = A[begin];
    	A[end] = assign(A[tempMax]);
    	A[begin] = assign(A[tempMin]);
    	A[tempMax ] = assign(temp);
    	A[tempMin] = assign(temp1) ; 
    	
    	
    	begin++;
    	end--; 
    	} while(begin<=end);
     
    	
    }

    
    /**
     * <b>Extra Credit:</b> Sorts the given array using the insertion2 sort 
     * algorithm outlined below.  Note: after this method finishes the array 
     * is in sorted order.
     * <p>
     * The insertion2 sort is a bi-directional insertion sort that sorts the 
     * array from the center out towards the ends.  The insertion2 sort 
     * algorithm is:
     * </p>
     * <pre>
     * precondition: A has an even length
     * left = element immediately to the left of the center of A
     * right = element immediately to the right of the center of A
     * if A[left] > A[right]
     *     swap A[left] and A[right]
     * left--, right++ 
     *  
     * // At the beginning of every iteration of this loop, we know that the elements
     * // in A from A[left+1] to A[right-1] are in relative sorted order.
     * do
     *     if (A[left] > A[right])
     *         swap A[left] and A[right]
     *  
     *     starting with with A[right] and moving to the left, use insertion sort 
     *     algorithm to insert the element at A[right] into the correct location 
     *     between A[left+1] and A[right-1]
     *     
     *     starting with A[left] and moving to the right, use the insertion sort 
     *     algorithm to insert the element at A[left] into the correct location 
     *     between A[left+1] and A[right-1]
     *  
     *     left--, right++
     * until left has gone off the left edge of A and right has gone off the right 
     *       edge of A
     * </pre>
     * <p>
     * This sorting algorithm described above only works on arrays of even 
     * length.  If the array passed in as a parameter is not even, the method 
     * throws an IllegalArgumentException
     * </p>
     *
     * @param  A the array to sort
     * @throws IllegalArgumentException if the length or A is not even
     */    
    public static <E extends Comparable<E>> void insertion2Sort(E[] A) { 
        // TODO: implement this sorting algorithm 
    }

    /**
     * Internal helper for printing rows of the output table.
     * 
     * @param sort          name of the sorting algorithm
     * @param compares      number of comparisons performed during sort
     * @param moves         number of data moves performed during sort
     * @param milliseconds  time taken to sort, in milliseconds
     */
    private static void printStatistics(String sort, int compares, int moves,
                                        long milliseconds) {
        System.out.format("%-23s%,15d%,15d%,15d\n", sort, compares, moves, 
                          milliseconds);
    }

    /**
     * Sorts the given array using the six (seven with the extra credit)
     * different sorting algorithms and prints out statistics. The sorts 
     * performed are:
     * <ul>
     * <li>selection sort</li>
     * <li>insertion sort</li>
     * <li>merge sort</li>
     * <li>quick sort</li>
     * <li>heap sort</li>
     * <li>selection2 sort</li>
     * <li>(extra credit) insertion2 sort</li>
     * </ul>
     * <p>
     * The statistics displayed for each sort are: number of comparisons, 
     * number of data moves, and time (in milliseconds).
     * </p>
     * <p>
     * Note: each sort is given the same array (i.e., in the original order) 
     * and the input array A is not changed by this method.
     * </p>
     * 
     * @param A  the array to sort
     */
    static public void runAllSorts(SortObject[] A) {
        System.out.format("%-23s%15s%15s%15s\n", "algorithm", "data compares", 
                          "data moves", "milliseconds");
        System.out.format("%-23s%15s%15s%15s\n", "---------", "-------------", 
                          "----------", "------------");

        // TODO: run each sort and print statistics about what it did
    }
}
