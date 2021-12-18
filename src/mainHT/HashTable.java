package mainHT;

import java.util.ArrayList;
import java.util.List;

public class HashTable<T extends Comparable<T>> {

    public static final Integer LINEAR_PROBING = 0;
    public static final Integer QUADRATIC_PROBING = 1;
    public static final Integer DOUBLE_HASHING = 2;

    private List<HashNode<T>> nodes = new ArrayList<HashNode<T>>();

    private Integer redispersionType;
    private Integer B;
    private double maxLoadFactor;
    private double minLoadFactor;
    private Integer R;

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

	R = getPrevPrimeNumber(B);
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

    public int f(T element, int attempt) {
	if (redispersionType == LINEAR_PROBING) {
	    return (Math.abs(element.hashCode()) + attempt) % B;
	} else if (redispersionType == DOUBLE_HASHING) {
	    Integer aux = R - Math.abs(element.hashCode()) % R;
	    return (Math.abs(element.hashCode()) + (attempt * aux)) % B;
	} else {
	    return (Math.abs(element.hashCode()) + (attempt * attempt)) % B;
	}
    }

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

    public void add(T element) {
	add(element, f(element, 0));
    }

    private void add(T element, int attempt) {
	if (nodes.get(f(element, attempt)).getStatus() == 0
		|| nodes.get(f(element, attempt)).getStatus() == 2) {
	    nodes.get(f(element, attempt)).setElement(element);
	    nodes.get(f(element, attempt)).setStatus(HashNode.VALID);
	    validElems++;
	} else {
	    add(element, f(element, attempt + 1));
	}

	if (getLF() > maxLoadFactor) {
	    dynamicResize(getNextPrimeNumber(B * 2));
	}
    }

    public boolean search(T element) {
	return search(element, f(element, 0));
    }

    private boolean search(T element, int attempt) {

	// Stop condition
	if (nodes.get(f(element, attempt)).getStatus() == HashNode.EMPTY) {
	    return false;
	}

	if (nodes.get(f(element, attempt)).getElement().equals(element) && nodes
		.get(f(element, attempt)).getStatus() == HashNode.VALID) {
	    return true;
	} else {
	    return search(element, f(element, attempt + 1));
	}
    }

    public void remove(T element) {
	remove(element, f(element, 0));
    }

    private void remove(T element, int attempt) {
	// Stop condition
	if (nodes.get(f(element, attempt)).getStatus() == HashNode.EMPTY) {
	    throw new RuntimeException("The element is not in the HashNode");
	}

	if (nodes.get(f(element, attempt)).getElement().equals(element) && nodes
		.get(f(element, attempt)).getStatus() == HashNode.VALID) {
	    nodes.get(f(element, attempt)).setStatus(HashNode.DELETED);
	    validElems--;
	} else {
	    remove(element, f(element, attempt + 1));
	}
	if (getLF() < minLoadFactor) {
	    dynamicResize(R);
	}
    }

    public String toString() {
	String aux = "";
	for (HashNode<T> hashNode : nodes) {
	    aux += "[" + nodes.indexOf(hashNode) + "]";
	    aux += "() = ";
	    aux += hashNode.toString();
	}
	return aux;
    }

    public static Object isPrime(int i) {
	// TODO Auto-generated method stub
	return null;
    }

    public static int getNextPrimeNumber(int i) {
	// TODO Auto-generated method stub
	return -1;
    }

    public static int getPrevPrimeNumber(int n) {
	if (n % 2 != 0) {
	    n -= 2;
	} else {
	    n--;
	}

	int i, j;
	for (i = n; i >= 2; i -= 2) {
	    if (i % 2 == 0) {
		continue;
	    }
	    for (j = 3; j <= Math.sqrt(i); j += 2) {
		if (i % j == 0)
		    break;
	    }
	    if (j > Math.sqrt(i)) {
		return i;
	    }
	}
	return 2;
    }

    /**
     * Number of valid elements / size
     * 
     * @return
     */
    public double getLF() {
	return validElems / nodes.size();
    }

    public List<HashNode<T>> getNodes() {
	return nodes;
    }

    public Integer getB() {
	return B;
    }

    public double getMaxLoadFactor() {
	return maxLoadFactor;
    }

    public Integer getR() {
	return R;
    }

    // T getHermano(T elem) Si no se encuentra devuelve null
    // double getBFMean() La media de factores de balance del arbol
}
