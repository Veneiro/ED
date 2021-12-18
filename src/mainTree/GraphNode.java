package mainTree;

public class GraphNode<T> {

	// ATTRIBUTES
	private T element;
	private boolean visited;

	/**
	 * Default constructor for the GraphNode Class
	 * 
	 * @param element
	 */
	public GraphNode(T element) {
		this.element = element;
	}

	/**
	 * Getter for element
	 * 
	 * @return
	 */
	public T getElement() {
		return element;
	}

	/**
	 * Setter for element
	 * 
	 * @param element
	 */
	public void setElement(T element) {
		this.element = element;
	}

	/**
	 * Getter for visited
	 * 
	 * @return
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Setter for visited
	 * 
	 * @param visited
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * ToString method for the GraphNode class
	 */
	public String toString() {
		String aux = "";
		aux += "GN(N:";
		aux += element.toString();
		aux += "/V:";
		aux += this.visited + ")";

		return aux;
	}

	/**
	 * Print method for the GraphicNode class
	 */
	public void print() {
		System.out.println(toString());
	}
}
