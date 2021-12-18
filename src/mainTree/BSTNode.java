package mainTree;

public class BSTNode<T extends Comparable<T>> {

	//ATTRIBUTES
	private T element;
	private BSTNode<T> right;
	private BSTNode<T> left;
	private int height;
	
	/**
	 * Constructor for the BSTNode class
	 * @param element
	 * @param right
	 * @param left
	 */
	public BSTNode(T element) {
		super();
		this.element = element;
		this.right = null;
		this.left = null;
	}
	
	/**
	 * @return the element
	 */
	public T getElement() {
		return element;
	}
	/**
	 * @param bstNode the element to set
	 */
	public void setElement(T bstNode) {
		this.element = bstNode;
	}
	/**
	 * @return the right
	 */
	public BSTNode<T> getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(BSTNode<T> right) {
		this.right = right;
	}
	/**
	 * @return the left
	 */
	public BSTNode<T> getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(BSTNode<T> left) {
		this.left = left;
	}
	
	/**
	 * To String method for BSTNode class
	 */
	public String toString() {
		String aux = element.toString();
		aux += "(" + getHeight() + ")";
		return aux;
	}
	
	public String toStringSimple() {
		String aux = element.toString();
		return aux;
	}
	
	/**
	 * Return the height of the node
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Setter for the height fieldç
	 * @param int h
	 */
	public void setHeight(int h) {
		this.height = h;
	}
	
	/**
	 * This method updates the height field of the node checking his childs
	 */
	public void updateHeight() {
		if (getRight() == null && getLeft() == null) {
			setHeight(0);
		} else {
			int leftHeight = 0;
			int rightHeight = 0;
			if (getRight() != null) {
				getRight().updateHeight();
				rightHeight = getRight().getHeight();

			} if (getLeft() != null) {
				getLeft().updateHeight();
				leftHeight = getLeft().getHeight();
			} if (rightHeight > leftHeight) {
				this.height = rightHeight + 1;
			} else {
				this.height = leftHeight + 1;
			}

		}
	}
	
	public BSTNode<T> copyNode() {
		BSTNode<T> left = null;
		BSTNode<T> right = null;
        if (this.left != null) {
            left = this.left.copyNode();
        }
        if (this.right != null) {
            right = this.right.copyNode();
        }
        
        BSTNode<T> a = new BSTNode<T>(this.element);
        a.setRight(right);
        a.setLeft(left);
        return a;
    }
}
