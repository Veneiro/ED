package mainTree;

import java.util.ArrayList;

public class GraphMockExam<T> {

	protected ArrayList<GraphNode<T>> nodes;
	protected boolean[][] edges;
	protected double[][] weight;
	public static int INDEX_NOT_FOUND = -1;

	// Floyd
	public static double INFINITE = Double.MAX_VALUE;
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
	public GraphMockExam(int n) {
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
			throw new RuntimeException();
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
		edges[coordDest][coordOrig] = true;
		this.weight[coordOrig][coordDest] = weight;
		this.weight[coordDest][coordOrig] = weight;
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
		edges[coordDest][coordOrig] = false;
		this.weight[coordOrig][coordDest] = 0.0;
		this.weight[coordDest][coordOrig] = 0.0;
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
		 * for catch reachable node from v that has not been previously visited
		 * aux = DFPrint(that node) return aux
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
			throw new RuntimeException("The departure does not exist");
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
	}

	public double[] dijkstra(T i) {
		initsDijkstra(i);

		for (GraphNode<T> gn : nodes) {
			gn.setVisited(false);
		}

		nodes.get(getNode(i)).setVisited(true);

		int pivot = getPivot();
		while (pivot != EMPTY) {
			nodes.get(pivot).setVisited(true);
			for (int n = 0; n < getSize(); n++) {
				// from elem to n D[n]
				// from elem to pivot D[pivot] + W
				if (D[n] > D[pivot] + weight[pivot][n]) {
					D[n] = D[pivot] + weight[pivot][n];
					PD[n] = pivot;
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
		String p = "";
		do {
			nodes.get(getNode(node)).setVisited(true);
			p += node + "-";
			for (int i = 0; i < getSize(); i++) {

			}
		} while (!isEmpty());
		return p;
	}

	private boolean isEmpty() {
		int i = 0;
		for (GraphNode<T> node : nodes) {
			if (node.isVisited()) {
				i++;
			}
		}
		if (i > 0) {
			return true;
		}
		return false;
	}

	public void print() {

	}

	public Object shortestPathLength(String string, String string2) {

		return null;
	}

	public Object getCenter() {

		return null;
	}
}