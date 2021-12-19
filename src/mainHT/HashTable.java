package mainHT;

import java.util.ArrayList;
import java.util.List;

public class HashTable<T extends Comparable<T>> {

	// ATTRIBUTES
	public static final Integer LINEAR_PROBING = 0;
	public static final Integer QUADRATIC_PROBING = 1;
	public static final Integer DOUBLE_HASHING = 2;

	private List<HashNode<T>> nodes = new ArrayList<HashNode<T>>();

	private Integer redispersionType;
	private Integer B; // Size
	private Integer R; // Prev prime number of the maximum size

	private double maxLoadFactor;
	private double minLoadFactor;

	private Integer validElems = 0;

	/**
	 * Constructor without minLF
	 * 
	 * @param size
	 * @param redispersionType
	 * @param maxLoadFactor
	 */
	public HashTable(int size, Integer redispersionType, double maxLoadFactor) {
		this.redispersionType = redispersionType;
		this.B = size;
		this.maxLoadFactor = maxLoadFactor;
		this.minLoadFactor = 0;
		for (int i = 0; i < B; i++) {
			nodes.add(new HashNode<>());
		}

		this.R = getPrevPrimeNumber(B);
	}

	/**
	 * Constructor with minLF
	 * 
	 * @param size
	 * @param redispersionType
	 * @param maxLoadFactor
	 * @param minLF
	 */
	public HashTable(int size, Integer redispersionType, double maxLoadFactor,
			double minLF) {
		this(size, redispersionType, maxLoadFactor);
		this.minLoadFactor = minLF;
	}

	/**
	 * This function allows us a new position for the element having into
	 * account if we are working with linear, double or quadratic proving
	 * 
	 * @param element
	 * @param attempt
	 * @return
	 */
	public int f(T element, int attempt) {
		if (redispersionType == LINEAR_PROBING) {
			return (Math.abs(element.hashCode()) + attempt) % B;
		} else if (redispersionType == DOUBLE_HASHING) {
			Integer aux = R - Math.abs(element.hashCode()) % R;
			return (Math.abs(element.hashCode()) + attempt * aux) % B;
		} else if (redispersionType == QUADRATIC_PROBING) {
			return (Math.abs(element.hashCode()) + (attempt * attempt)) % B;
		}
		return 0;
	}

	/**
	 * This method will be executed to resize the size of the hashtable when we
	 * have a wrong lf
	 * 
	 * @param newSize
	 */
	public void dynamicResize(int newSize) {
		HashTable<T> newNodes = new HashTable<>(newSize, redispersionType,
				maxLoadFactor);
		for (HashNode<T> hashNode : nodes) {
			if (hashNode.getStatus() == 1)
				newNodes.add(hashNode.getElement());
		}
		this.nodes = newNodes.getNodes();
		this.R = newNodes.getR();
	}

	/**
	 * This method allows us to add elements to the hashtable
	 * 
	 * @param element
	 */
	public void add(T element) {
		int attempt = 0;
		boolean inserted = false;
		while (!inserted) {
			if (f(element, attempt) < B) {
				if (nodes.get(f(element, attempt)).getStatus() != HashNode.EMPTY
						&& nodes.get(f(element, attempt))
								.getStatus() != HashNode.DELETED) {
					attempt++;
				} else {
					if (nodes.get(f(element, attempt))
							.getStatus() == HashNode.EMPTY
							|| nodes.get(f(element, attempt))
									.getStatus() == HashNode.DELETED) {
						nodes.get(f(element, attempt)).setElement(element);
						nodes.get(f(element, attempt))
								.setStatus(HashNode.VALID);
						inserted = true;
						validElems++;
					}
				}
			} else {
				break;
			}
		}

		if (getLF() > maxLoadFactor) {
			dynamicResize(getNextPrimeNumber(B * 2));
		}
	}

	/**
	 * This method will return true if the element that we are searching is in
	 * the HashTable and false if it is not
	 * 
	 * @param element
	 * @return
	 */
	public boolean search(T element) {
		boolean found = false;
		int attempt = 0;
		int pos = f(element, attempt);
		while (!found) {
			if (pos < B) {
				if (nodes.get(pos).getStatus() == HashNode.EMPTY
						|| nodes.get(pos).getStatus() == HashNode.DELETED) {
					return false;
				}
				if (nodes.get(pos).getStatus() == HashNode.VALID
						&& nodes.get(pos).getElement().equals(element)) {
					found = true;
				} else {
					attempt++;
				}
			} else {
				return false;
			}
			pos = f(element, attempt);
		}
		return found;
	}

	/**
	 * This method will set as deleted the element we want to remove from the
	 * HashTable
	 * 
	 * @param element
	 */
	public void remove(T element) {
		boolean removed = false;
		int attempt = 0;
		int pos = f(element, attempt);
		if (!search(element)) {
			throw new RuntimeException("The element is not in the Hast Table");
		}
		if (search(element)) {
			while (!removed) {
				if (pos < B) {
					if (nodes.get(pos).getStatus() == HashNode.VALID
							&& nodes.get(pos).getElement().equals(element)) {
						nodes.get(pos).setStatus(HashNode.DELETED);
						removed = true;
						validElems--;
					} else {
						attempt++;
					}
				} else {
					break;
				}
				pos = f(element, attempt);
			}
		}
		if (getLF() < minLoadFactor) {
			dynamicResize(getNextPrimeNumber(B / 2));
		}

	}

	/**
	 * To String method for the HashTable class
	 */
	public String toString() {
		String aux = "";
		for (HashNode<T> hashNode : nodes) {
			aux += "[" + nodes.indexOf(hashNode) + "]";
			aux += " (" + hashNode.getStatus() + ") = ";
			aux += hashNode.getElement() + " - ";
		}
		return aux;
	}

	/**
	 * Returns true if the number is prime and false if it is not
	 * 
	 * @param n
	 * @return
	 */
	public static boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the next number from a given number that is prime
	 * 
	 * @param n
	 * @return
	 */
	public static int getNextPrimeNumber(int n) {
		int number = n + 1;
		while (number >= 0) {
			if (isPrime(number)) {
				return number;
			} else {
				number++;
			}
		}
		return -1;
	}

	/**
	 * Returns the previous number from a given number that is prime
	 * 
	 * @param n
	 * @return
	 */
	public static int getPrevPrimeNumber(int n) {
		int number = n - 1;
		while (number >= 0) {
			if (isPrime(number)) {
				return number;
			} else {
				number--;
			}
		}
		return -1;
	}

	/**
	 * Number of valid elements / size
	 * 
	 * @return
	 */
	public double getLF() {
		return (double) validElems / (double) nodes.size();
	}

	/**
	 * Returns the list of nodes
	 * 
	 * @return
	 */
	public List<HashNode<T>> getNodes() {
		return nodes;
	}

	/**
	 * Returns the size
	 * 
	 * @return
	 */
	public Integer getB() {
		return B;
	}

	/**
	 * Returns the max load factor
	 * 
	 * @return
	 */
	public double getMaxLoadFactor() {
		return maxLoadFactor;
	}

	public Integer getR() {
		return R;
	}

	// T getHermano(T elem) Si no se encuentra devuelve null
	// double getBFMean() La media de factores de balance del arbol
}
