package mainHM;

import java.util.ArrayList;
import java.util.List;

public class HashMap<T extends Comparable<T>> {

    public static final Integer LINEAR_PROBING = 0;
    public static final Integer QUADRATIC_PROBING = 1;
    public static final Integer DOUBLE_HASHING = 2;

    private List<HashMapNode<T>> nodes = new ArrayList<HashMapNode<T>>();

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
    public HashMap(int size, Integer redispersionType, double maxLoadFactor) {
	this.redispersionType = redispersionType;
	this.B = size;
	this.maxLoadFactor = maxLoadFactor;
	this.minLoadFactor = 0;
	for (int i = 0; i < B; i++) {
	    nodes.add(new HashMapNode<>());
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
    public HashMap(int size, Integer redispersionType, double maxLoadFactor,
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
	HashMap<T> newNodes = new HashMap<>(newSize, redispersionType,
		maxLoadFactor);
	for (HashMapNode<T> hashNode : nodes) {
	    if(hashNode.getStatus() == 1)
		newNodes.add(hashNode.getElement());
	}
	this.nodes = newNodes.getNodes();
	this.R = newNodes.getR();
    }

    public void add(T element) {
	add(element, f(element, 0));
    }

    //void put (T Key, V Value)
    private void add(T element, int attempt) {
	if (nodes.get(f(element, attempt)).getStatus() == 0
		|| nodes.get(f(element, attempt)).getStatus() == 2) {
	    nodes.get(f(element, attempt)).setElement(element);
	    nodes.get(f(element, attempt)).setStatus(HashMapNode.VALID);
	    validElems++;
	} else {
	    add(element, f(element, attempt + 1));
	}
	
	if(getLF() > maxLoadFactor) {
	    dynamicResize(getNextPrimeNumber(B*2));
	}
    }

    //V get (T Key)
    public Object search(int i) {
	// TODO Auto-generated method stub
	return null;
    }

    public void remove(int i) {
	// TODO Auto-generated method stub

    }

    public String toString() {
	String aux = "";
	for (HashMapNode<T> hashNode : nodes) {
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
	// TODO Auto-generated method stub
	return 0;
    }

    public List<HashMapNode<T>> getNodes() {
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
}
