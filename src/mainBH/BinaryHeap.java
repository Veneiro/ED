package mainBH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {

	List<T> heap;

	/**
	 * Constructor without index
	 */
	public BinaryHeap() {
		heap = new ArrayList<T>();
	}

	/**
	 * Constructor with index
	 * 
	 * @param elements
	 */
	public BinaryHeap(T[] elements) {
		this();
		for (int i = 0; i < elements.length; i++) {
			heap.add(elements[i]);
		}
		int startN = heap.size() / 2;

		for (int i = startN; i >= 0; i--) {
			filterDown(i);
		}
	}

	/**
	 * Adds an element to the Binary Heap
	 * 
	 * @param element
	 */
	public void add(T element) {
		// PROCESS
		// Add element as the last of the list
		// We know our parent, if it is greater we make a swap
		// It's iterative so we repeat the process
		// STOP CONDITION
		// We stop if we reach a parent that is null or
		// lower than the element we want to insert in the binary heap

		heap.add(element);
		filterUp(heap.indexOf(element));
	}

	/**
	 * Reorganize the Binary heap after adding an element
	 * 
	 * @param pos
	 */
	private void filterUp(int pos) {
		// While we don't reach the stop condition
		while (pos > 0
				&& (heap.get(pos).compareTo(heap.get((pos - 1) / 2)) < 0)) {
			// We get the leave and it's parent
			T e = heap.get(pos);
			T e1 = heap.get((pos - 1) / 2);

			// We swap the elements
			heap.set(pos, e1);
			heap.set((pos - 1) / 2, e);

			// The we do another iteration
			filterUp((pos - 1) / 2);
		}
	}

	/**
	 * Reorganize the Binary heap after taking out the root
	 * 
	 * @param pos
	 */
	private void filterDown(int pos) {
		while (pos < heap.size() / 2) {
			int leftChild = pos * 2 + 1;
			int rightChild = pos * 2 + 2;
			int lowestChild = 0;
			if (rightChild >= heap.size()) {
				lowestChild = leftChild;
			} else {
				if (heap.get(leftChild).compareTo(heap.get(rightChild)) > 0) {
					lowestChild = rightChild;
				} else {
					lowestChild = leftChild;
				}
			} if (heap.get(pos).compareTo(heap.get(lowestChild)) > 0) {
				Collections.swap(heap, pos, lowestChild);
				pos = lowestChild;

			} else {
				break;
			}
		}
	}

	/**
	 * Has the node on pos childs?
	 * 
	 * @param pos
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean hasChilds(int pos) {
		if ((pos * 2) + 1 <= heap.size() - 1) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the lowest child of an element
	 * 
	 * @param pos
	 * @return
	 */
	@SuppressWarnings("unused")
	private int getLowest(int pos) {
		int check = heap.get((pos * 2) + 1).compareTo(heap.get((pos * 2) + 2));
		if (check < 0) {
			return (pos * 2) + 1;
		} else if (check > 0) {
			return (pos * 2) + 2;
		}
		return pos;
	}

	/**
	 * Return the minimum value of the Binary Heap
	 * 
	 * @return
	 */
	public T getMin() {
		T r = heap.get(0);

		Collections.swap(heap, 0, heap.size() - 1);
		heap.remove(heap.size() - 1);

		filterDown(0);

		return r;
	}

	/**
	 * Checks if the Binary Heap is empty or not
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (heap.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * To String method of the Binary Heap class
	 */
	public String toString() {
		return heap.toString();
	}
}
