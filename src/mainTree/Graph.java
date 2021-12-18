package mainTree;

import java.util.ArrayList;
import java.util.List;

public class Graph<T> {

	protected ArrayList<GraphNode<T>> nodes;
	protected boolean[][] edges;
	protected double[][] weight;
	public static int INDEX_NOT_FOUND = -1;

	// Floyd
	public static double INFINITE = Double.POSITIVE_INFINITY;
	public final static int EMPTY = -1;
	protected double[][] A;
	protected int[][] P;

	// Dijkstra
	protected double[] D;
	protected int[] PD;

	/**
	 * Default constructor for Graph class
	 * 
	 * @param n
	 */
	public Graph(int n) {
		// Checking is the value is valid
		if (!checkValue(n)) {
			throw new RuntimeException("This value is invalid");
		}

		// Inicializating lists
		nodes = new ArrayList<GraphNode<T>>();
		edges = new boolean[n][n];
		weight = new double[n][n];

		A = new double[n][n];
		P = new int[n][n];
	}

	/**
	 * Returns true if n is valid and false if it is not
	 * 
	 * @param n
	 * @return
	 */
	private boolean checkValue(int n) {
		if (n >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * @return the nodes
	 */
	protected ArrayList<GraphNode<T>> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	protected void setNodes(ArrayList<GraphNode<T>> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the edges
	 */
	public boolean[][] getEdges() {
		return edges;
	}

	/**
	 * @param edges the edges to set
	 */
	protected void setEdges(boolean[][] edges) {
		this.edges = edges;
	}

	/**
	 * @return the weight
	 */
	public double[][] getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	protected void setWeight(double[][] weight) {
		this.weight = weight;
	}

	/**
	 * Returns the node of an specific element
	 * 
	 * @param element
	 * @return
	 */
	public int getNode(T element) {
		int pos = 0;
		for (GraphNode<T> node : nodes) {
			if (element.equals(node.getElement())) {
				return pos;
			}
			pos++;
		}

		return INDEX_NOT_FOUND; // Did not find the node
	}

	/**
	 * Returns the size of the nodes list
	 * 
	 * @return
	 */
	public int getSize() {
		return nodes.size();
	}

	/**
	 * Add an element node
	 * 
	 * @param element
	 * @throws Exception
	 */
	public void addNode(T element) throws Exception {
		int coord = getNode(element); // Getting the node

		if (coord == INDEX_NOT_FOUND) { // Is the element int the list?
			cleanEdges(coord); // Cleaning edges array
			cleanWeights(coord); // Cleaning weights array
			GraphNode<T> node = new GraphNode<T>(element);
			nodes.add(node); // Adding the node
		} else {
			throw new RuntimeException("No repeated elements are allowed!");
		}

	}

	/**
	 * Clean weights row and column for a node
	 * 
	 * @param coord
	 */
	private void cleanWeights(int coord) {
		for (int i = 0; i < weight.length; i++) {
			weight[i][getSize()] = 0.0;
			weight[getSize()][i] = 0.0;
		}
	}

	/**
	 * Clean edges row and column for a node
	 * 
	 * @param coord
	 */
	private void cleanEdges(int coord) {
		for (int i = 0; i < edges.length; i++) {
			edges[i][getSize()] = false;
			edges[getSize()][i] = false;
		}
	}

	/**
	 * Returns true if the edge exist and false if it not
	 * 
	 * @param origin
	 * @param dest
	 * @return
	 * @throws Exception
	 */
	public boolean existEdge(T origin, T dest) throws Exception {
		// Getting the coords
		int oriCoord = getNode(origin);
		int desCoord = getNode(dest);

		// Are this coords valid?
		if (oriCoord == INDEX_NOT_FOUND) {
			throw new RuntimeException("Origin does not exist");
		} else if (desCoord == INDEX_NOT_FOUND) {
			throw new RuntimeException("Destination does not exist");
		}

		// If they are valid return the edge
		return edges[oriCoord][desCoord];
	}

	/**
	 * Adds an edge
	 * 
	 * @param origin
	 * @param dest
	 * @param weight
	 * @throws Exception
	 */
	public void addEdge(T origin, T dest, double weight) throws Exception {
		if (existEdge(origin, dest)) {
			throw new RuntimeException("There exist already an edge");
		}
		if (origin == null) {
			throw new RuntimeException("The origin is null");
		}
		if (dest == null) {
			throw new RuntimeException("The destination is null");
		}

		int coordOrig = getNode(origin);
		if (coordOrig == INDEX_NOT_FOUND) {
			throw new RuntimeException("The origin does not exist");
		}

		int coordDest = getNode(dest);
		if (coordDest == INDEX_NOT_FOUND) {
			throw new RuntimeException("The destination does not exist");
		}

		edges[coordOrig][coordDest] = true;
		this.weight[coordOrig][coordDest] = weight;
	}

	/**
	 * It removes an edge between two elements.
	 * 
	 * @param origin
	 * @param dest
	 * @throws Exception
	 */
	public void removeEdge(T origin, T dest) throws Exception {
		if (origin == null) {
			throw new RuntimeException("The origin is null");
		}
		if (dest == null) {
			throw new RuntimeException("The destination is null");
		}

		int coordOrig = getNode(origin);
		if (coordOrig == INDEX_NOT_FOUND) {
			throw new RuntimeException("The origin does not exist");
		}

		int coordDest = getNode(dest);
		if (coordDest == INDEX_NOT_FOUND) {
			throw new RuntimeException("The destination does not exist");
		}

		edges[coordOrig][coordDest] = false;
		this.weight[coordOrig][coordDest] = 0.0;
	}

	// source node : No inputs, yes outputs
	// drain node : Yes inputs, no outputs

	/**
	 * This method removes a node
	 * 
	 * @param element
	 * @throws Exception
	 */
	public void removeNode(T element) throws Exception {
		int i = getNode(element);

		// Checking if the node is in the graph
		if (i == INDEX_NOT_FOUND) {
			throw new RuntimeException("Element does not exists");
		}

		// Replaces the deleted node by the last one
		nodes.set(i, nodes.get(getSize() - 1));
		nodes.remove(getSize() - 1);

		if (i != getSize()) { // It is not the last node
			// Replacing elements in the vectors edges and weights
			for (int j = 0; j < getSize(); j++) {
				weight[j][i] = weight[j][getSize()];
				weight[i][j] = weight[getSize()][j];
				edges[i][j] = edges[getSize()][j];
				edges[j][i] = edges[j][getSize()];
			}
			edges[i][i] = edges[getSize()][getSize()];
			weight[i][i] = weight[getSize()][getSize()];
		}
	}

	public boolean isDrainNode(T element) {
		if (element == null) {
			throw new RuntimeException("The element is null");
		}

		int i = getNode(element);

		if (i == INDEX_NOT_FOUND) {
			throw new RuntimeException("The element does not exist");
		}
		if (isIsolated(element)) {
			return false;
		}
		boolean isDrain = true;

		for (int j = 0; j < edges.length; j++) {
			if (edges[i][j]) {
				isDrain = false;
			}
		}

		return isDrain;
	}

	public boolean isSourceNode(T element) {
		if (element == null) {
			throw new RuntimeException("The element is null");
		}

		int i = getNode(element);

		if (i == INDEX_NOT_FOUND) {
			throw new RuntimeException("The element does not exist");
		}

		if (isIsolated(element)) {
			return false;
		}

		boolean isSource = true;

		for (int j = 0; j < edges.length; j++) {
			if (edges[j][i]) {
				isSource = false;
			}
		}

		return isSource;
	}

	public int countDrainNodes() {
		int count = 0;
		for (GraphNode<T> gn : nodes) {
			if (isDrainNode(gn.getElement())) {
				count++;
			}
		}

		return count;
	}

	public int countSourceNodes() {
		int count = 0;
		for (GraphNode<T> gn : nodes) {
			if (isSourceNode(gn.getElement())) {
				count++;
			}
		}

		return count;
	}

	/**
	 * It returns true if the node is isolated. Else false.
	 * 
	 * @param element
	 * @return
	 */
	private boolean isIsolated(T element) {
		int i = getNode(element);

		boolean hasEdge = true;

		for (int j = 0; j < edges.length; j++) {
			if (edges[i][j] || edges[j][i]) {
				hasEdge = false;
			}

		}

		return hasEdge;

	}

	public String traverseGraphDF(T element) {
		// sets the "visited" flag to false for each node
		if (element == null) {
			throw new RuntimeException("The element is null");
		}
		int coord = getNode(element);

		if (coord != INDEX_NOT_FOUND) {

			for (GraphNode<T> gn : nodes) {
				gn.setVisited(false);
			}

			return (DFPrint(coord));
		}
		return null;
	}

	public String DFPrint(int v) {
		// sets the "visited" flag of v to true
		nodes.get(v).setVisited(true);

		String path = nodes.get(v).getElement().toString() + "-";

		for (int i = 0; i < getSize(); i++) {
			if (edges[v][i]) {
				if (!nodes.get(i).isVisited()) {
					path += DFPrint(i);
				}
			}
		}

		return path;
		/**
		 * for catch reachable node from v that has not been previously visited aux =
		 * DFPrint(that node) return aux
		 */
	}

	// -------------------------------------
	// -------------------------------------
	// ----------------FLOYD----------------
	// -------------------------------------
	// -------------------------------------

	/**
	 * Returns the cost matrix
	 * 
	 * @return
	 */
	public double[][] getA() { // Cost matrix
		return A;
	}

	/**
	 * Returns the pathway matrix
	 * 
	 * @return
	 */
	public int[][] getP() { // Pathway matrix, P[i][j]
		return P;
	}

	/**
	 * Inits Floyd algorithm Sets the values of infinite and empty for A and P
	 */
	private void initsFloyd() {
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (edges[i][j]) {
					A[i][j] = weight[i][j];
				} else {
					A[i][j] = INFINITE;
				}
				P[i][j] = EMPTY;
				if (i == j) {
					A[i][i] = 0;
				}
			}
		}
	}

	/**
	 * This method computes floyd's cost matrix A up to the An iteration
	 * 
	 * @param An
	 */
	public void floyd(int An) {

		initsFloyd();

		for (int k = 0; k < An; k++) {
			for (int i = 0; i < getSize(); i++) {
				for (int j = 0; j < getSize(); j++) {
					if (A[i][k] + A[k][j] < A[i][j]) {
						A[i][j] = A[i][k] + A[k][j];
						P[i][j] = k;
					}
				}
			}

		}
	}

	// -------------------------------------
	// -------------------------------------
	// --------------DIJKSTRA---------------
	// -------------------------------------
	// -------------------------------------

	private void initsDijkstra(T elementIndex) {

		if (elementIndex == null) {
			throw new RuntimeException("The element is null");
		}

		int d = getNode(elementIndex);

		if (d == INDEX_NOT_FOUND) {
			throw new RuntimeException("The elementIndex does not exist");
		}

		// Reserve memory
		D = new double[getSize()];
		PD = new int[getSize()];

		// Filling D and PD
		for (int i = 0; i < getSize(); i++) {
			if (edges[d][i]) {
				D[i] = weight[d][i];
				PD[i] = d;
			} else {
				D[i] = INFINITE;
				PD[i] = EMPTY;
			}
		}
		D[d] = 0;
		PD[d] = d;
	}

	public double[] dijkstra(T i) {
		initsDijkstra(i);

		for (GraphNode<T> gn : nodes) {
			gn.setVisited(false);
		}

		int pivot = getPivot();
		while (pivot != EMPTY) {
			nodes.get(pivot).setVisited(true);
			for (int n = 0; n < getSize(); n++) {
				// from elem to n D[n]
				// from elem to pivot D[pivot] + W
				if (edges[pivot][n]) {
					if (D[n] > D[pivot] + weight[pivot][n]) {
						D[n] = D[pivot] + weight[pivot][n];
						PD[n] = pivot;
					}
				}
			}
			pivot = getPivot();
		}
		return D;
	}

	// MORE TEST FOR DIJSKTRA (HOMEWORK)

	private int getPivot() {
		double d = INFINITE;
		int cheapest = EMPTY;
		for (int i = 0; i < getSize(); i++) {
			if (!nodes.get(i).isVisited()) {
				if (D[i] < d) {
					d = D[i];
					cheapest = i;
				}
			}
		}
		return cheapest;
	}

	public String BFPrint(T node) {
		for (GraphNode<T> gn : nodes) {
			gn.setVisited(false);
		}
		
		String path = "";
		List <GraphNode<T>> list = new ArrayList<>();
		
		
		int pos = getNode(node);
		nodes.get(pos).setVisited(true);
		
		list.add(nodes.get(pos));
		
		do {
			GraphNode<T> n = list.get(0);
			int index = getNode(n.getElement());
			path += n.getElement() + "-";
			list.remove(0);
			for(int i = 0; i < getSize(); i++) {
				if (edges [index][i]) {
					if (!nodes.get(i).isVisited()) {
						list.add(nodes.get(i));
						nodes.get(i).setVisited(true);
					}
				}
						
			}
		} while (!list.isEmpty()); //If the list is not empty it will continue adding nodes
		
		return path;
	}

	public void print() {
		System.out.println("-----------------WEIGHT----------------");
		System.out.println();
		for (int i = 0; i < weight.length; i++) {
			for (int j = 0; j < weight.length; j++) {
				System.out.print(weight[i][j] + " | ");
			}
			System.out.println();
		}

		System.out.println();

		System.out.println("------------------EDGES-----------------");
		System.out.println();
		for (int i = 0; i < weight.length; i++) {
			for (int j = 0; j < weight.length; j++) {
				System.out.print(edges[i][j] + " |\t");
			}
			System.out.println();
		}

		System.out.println();

		System.out.println("-----------------NODES------------------");
		System.out.println();
		for (GraphNode<T> gn : nodes) {
			gn.print();
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * What's the shortest path length from n1 to n2
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public int shortestPathLength(T n1, T n2) {
		shortestsFloyd(getSize());
		return (int) A[getNode(n1)][getNode(n2)];
	}

	private void initsShortestFloyd() {
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (edges[i][j]) {
					A[i][j] = 1;
				} else {
					A[i][j] = INFINITE;
				}
				P[i][j] = EMPTY;
				if (i == j) {
					A[i][i] = 0;
				}
			}
		}
	}

	private void shortestsFloyd(int An) {

		initsShortestFloyd();

		for (int k = 0; k < An; k++) {
			for (int i = 0; i < getSize(); i++) {
				for (int j = 0; j < getSize(); j++) {
					if (A[i][k] + A[k][j] < A[i][j]) {
						A[i][j] = A[i][k] + A[k][j];
						P[i][j] = k;
					}
				}
			}

		}
	}

	/**
	 * Is the graph strongly connected?
	 * 
	 * @return
	 */
	public boolean isStronglyConnected() {
		floyd(getSize());
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (A[i][j] == INFINITE) {
					return false;
				}
			}
		}
		return true;
	}

	public double[] getD() {
		return D;
	}

	public int[] getPD() {
		return PD;
	}

	public Object printFloydPath(T d, T a) {
		if (d == null) {
			throw new RuntimeException("The element is null");
		}

		int i = getNode(d);

		if (i == INDEX_NOT_FOUND) {
			throw new RuntimeException("The departure does not exist");
		}

		if (a == null) {
			throw new RuntimeException("The element is null");
		}

		int j = getNode(a);

		if (j == INDEX_NOT_FOUND) {
			throw new RuntimeException("The arrival does not exist");
		}
		int k = P[i][j];

		String aux = "";
		if (k >= 0) {
			aux += printFloydPath(d, nodes.get(k).getElement());
			aux += nodes.get(k).getElement().toString() + "-";
			aux += printFloydPath(nodes.get(k).getElement(), a);
		}
		return aux;
	}
	
	/**
	 * HOMEWORK
	 * 
	 * double: reciprocity() Relation between the number of arches that are
	 * reciprocal divided by the total number of arches of the graph
	 * 
	 * T getCenter() The element with less excentricity (max cost to reach any node)
	 */
	public T getCenter() {
		
		floyd(getSize());
		
		int pos = 0;
		double min = INFINITE;

		List<Double> maxWeights = new ArrayList<>();

		for (int i = 0; i < getSize(); i++) {
			double max = 0.0;
			for (int j = 0; j < getSize(); j++) {
				if (A[i][j] > max && A[i][j] != INFINITE) {
					max = A[i][j];
				}
			}
			maxWeights.add(max);
		}
		int position = 0;
		for (Double doubleValue : maxWeights) {
			if (doubleValue.doubleValue() < min) {
				min = doubleValue.doubleValue();
				pos = position;
			}
			position++;
		}

		return nodes.get(pos).getElement();
	}

}