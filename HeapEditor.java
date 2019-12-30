package pa01;

/***
 * 
 * This class has several methods which allows a user to create and edit a heap
 * queue, or sort a list. If a user were to make a Heap object from this class,
 * and creates a heap priority queue in the list using the Heap object, they
 * must use the same heap object to edit the queue within the list.
 *
 */
public class HeapEditor {
	public int heapsize; // size of heap created when one builds a max heap

	/***
	 * Method which takes an array and index of a node, and rearranges the array
	 * such that the specific node is in a position that it's parent node is greater
	 * than it, and it's child nodes are less
	 * 
	 * @param searchList
	 *            - an array
	 * @param index
	 *            - index of node
	 */
	public void maxHeapify(Url[] searchList, int index) {
		int leftIndex = (index * 2) + 1; // left child of node
		int rightIndex = (index * 2) + 2; // right child of node
		int largest;
		if (leftIndex < heapsize && searchList[leftIndex].getTotal() > searchList[index].getTotal()) {
			largest = leftIndex; // if the left child is larger, then the index of the left child is saved
		} else {
			largest = index; // otherwise the the index of the parent node is saved
		}
		if (rightIndex < heapsize && searchList[rightIndex].getTotal() > searchList[largest].getTotal()) {
			largest = rightIndex; // if the right child is larger than the saved node at the saved index, then the
									// index of the right child is saved
		}
		if (largest != index) { // if the saved index is not that of the parent node, then the parent node is
								// switched with the node of the saved index
			Url largerUrl = searchList[largest];
			searchList[largest] = searchList[index];
			searchList[index] = largerUrl;
			maxHeapify(searchList, largest);
		}

	}

	/**
	 * This method returns the true length of an array, meaning that it counts the
	 * number of spaces in the array that aren't null.
	 * 
	 * @param searchList
	 *            - the array
	 * @return - the number of spaces in the array that aren't null within the array
	 */
	public int getTrueLength(Url[] searchList) {
		int length = 0;
		for (int i = 0; i < searchList.length; i++) {
			if (searchList[i] == null) { // increments index until the index hits null
				break;
			}
			length++;
		}
		return length;
	}

	/***
	 * Method which will create a MaxHeap from an array in which the parent node
	 * will be greater than the child node
	 * 
	 * @param searchList
	 *            - the array that is turned into a heap
	 */
	public void buildMaxHeap(Url[] searchList) {
		heapsize = getTrueLength(searchList);
		for (int i = (searchList.length / 2) - 1; i >= 0; i--) { // starts from each Parent Node, and calls maxHeapfiy
																	// to make sure the Parent Node is larger than it's
																	// child
			maxHeapify(searchList, i);
		}
	}

	/***
	 * Method which will take an array, create a heap from the array, sort the heap,
	 * and turn the heap back into an array
	 * 
	 * @param searchList
	 *            - the array that will be sorted
	 */
	public void heapSorter(Url[] searchList) {
		buildMaxHeap(searchList); // creates a heap from the array, and sorts the heap
		for (int i = getTrueLength(searchList) - 1; i > 0; i--) { // takes the first number in the heap and adds it to
																	// the end
			// of the array until, the array is from least to greatest
			Url correctUrl = searchList[0];
			searchList[0] = searchList[i];
			searchList[i] = correctUrl;
			heapsize--;
			maxHeapify(searchList, 0);
		}
	}

	/***
	 * method which returns back the largest node or the first node in queue
	 * 
	 * @param heap
	 *            - the heap that is built
	 * @return - returns the largest node
	 */
	public Url heapMaximum(Url[] heap) {
		return heap[0];
	}

	/***
	 * method which extracts the first node in the queue, and returns it to the user
	 * 
	 * @param heap
	 *            - the heap queue
	 * @return - the first node within the queue
	 * @throws Exception
	 *             - exception thrown if there is no object in the queue
	 */
	public Url heapExtractMax(Url[] heap) throws Exception {
		if (heapsize < 1) {
			throw (new Exception("Heap Underflow "));
		}
		Url max = heap[0];
		heap[0] = heap[heapsize - 1];
		heapsize--;
		maxHeapify(heap, 0);
		return max;
	}

	/***
	 * method which increases the key of a specific node within the heap, and then
	 * rearranges the heap so it matches the heap property
	 * 
	 * @param heap
	 *            - the heap that is edited
	 * @param i
	 *            - index of node that its key is changed
	 * @param key
	 *            - the new key that the node will have
	 * @throws Exception
	 *             - exception thrown if the new key is less than the previous key
	 */
	public void heapIncreaseKey(Url[] heap, int i, int key) throws Exception {
		if (key < heap[i].getadverNum()) {
			throw (new Exception("new key is smaller than current key"));
		}
		heap[i].setPageRank(key);
		int parentIndex;
		if (i % 2 != 0) { // getting the parent node of the node
			parentIndex = (i / 2);
		} else {
			parentIndex = (i / 2) - 1;
		}
		while (i > 0 && heap[parentIndex].getTotal() < heap[i].getTotal()) { // while node which moves the node to the
																				// correct place within the queue
			Url temp = heap[parentIndex];
			heap[parentIndex] = heap[i];
			heap[i] = temp;
			i = parentIndex;
			if (i % 2 != 0) { // getting the parent node of the node
				parentIndex = (i / 2);
			} else {
				parentIndex = (i / 2) - 1;
			}
		}
	}

	/**
	 * method which inserts a new node into the queue
	 * 
	 * @param heap
	 *            - the queue in which the new node will be inserted
	 * @param key
	 *            - the key of the new node
	 * @param url
	 *            - the url of the new node
	 * @throws Exception
	 *             - exception thrown if the key is less than the new node, but
	 *             shouldn't occur since the node is at it's lowest
	 */
	public void heapInsert(Url[] heap, int key, String url) throws Exception {
		heapsize++;// increases the heapsize
		heap[heapsize - 1] = new Url(url, 0, 0, 0, 0); // sets the new node with the lowest key possible
		heapIncreaseKey(heap, heapsize - 1, key); // inserts the new node with the key that is wanted
	}

}
