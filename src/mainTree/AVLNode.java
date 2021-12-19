package mainTree;

public class AVLNode<T extends Comparable<T>> {

	// ATTRIBUTES
	private T element;
	private AVLNode<T> right;
	private AVLNode<T> left;
	private int height;

	/**
	 * Constructor for the BSTNode class
	 * 
	 * @param element
	 * @param right
	 * @param left
	 */
	public AVLNode(T element) {
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
	 * @param element the element to set
	 */
	public void setElement(T element) {
		this.element = element;
	}

	/**
	 * @return the right
	 */
	public AVLNode<T> getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(AVLNode<T> right) {
		this.right = right;
	}

	/**
	 * @return the left
	 */
	public AVLNode<T> getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(AVLNode<T> left) {
		this.left = left;
	}

	/**
	 * To String method for BSTNode class
	 */
	public String toString() {
		String aux = element.toString();
		aux += "(" + getBF() + ")";
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
	 * 
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

			}
			if (getLeft() != null) {
				getLeft().updateHeight();
				leftHeight = getLeft().getHeight();
			}
			if (rightHeight > leftHeight) {
				this.height = rightHeight + 1;
			} else {
				this.height = leftHeight + 1;
			}

		}
	}

	/**
	 * Returns the node balance factor
	 * 
	 * @return
	 */
	public int getBF() {
		if (getRight() == null && getLeft() == null) {
			return 0;
		} else if (getRight() != null && getLeft() == null) {
			return getHeight();
		} else if (getRight() == null && getLeft() != null) {
			return -getHeight();
		} else if (getRight() != null && getLeft() != null) {
			return getRight().getHeight() - getLeft().getHeight();
		}

		return -1;
	}

	/**
	 * Copy the node
	 * 
	 * @return
	 */
	public AVLNode<T> copyNode() {
		AVLNode<T> left = null;
		AVLNode<T> right = null;
		if (this.left != null) {
			left = this.left.copyNode();
		}
		if (this.right != null) {
			right = this.right.copyNode();
		}

		AVLNode<T> a = new AVLNode<T>(this.element);
		a.setRight(right);
		a.setLeft(left);
		return a;
	}

	/**
	 * Returns the number of childs of a node
	 * 
	 * @return
	 */
	public int getChilds() {
		if (this.left == null && this.right == null) {
			return 0;
		}
		if (this.left != null && this.right == null) {
			return 1 + left.getChilds();
		}
		if (this.left == null && this.right != null) {
			return 1 + right.getChilds();
		} else {
			return 2 + left.getChilds() + right.getChilds();
		}
	}

	public double getChildsBFTotal() {
		if (this.left == null && this.right == null) {
			return 0.0;
		}
		if (this.left != null && this.right == null) {
			return left.getBF() + left.getChilds();
		}
		if (this.left == null && this.right != null) {
			return right.getBF() + right.getChilds();
		} else {
			return left.getBF() + right.getBF() + left.getChilds()
					+ right.getChilds();
		}
	}
}
