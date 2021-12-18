package mainTree;

public class BSTTree<T extends Comparable<T>> {

	// ATTRIBUTES
	private BSTNode<T> root;

	/**
	 * Constructor for the BSTTree
	 * 
	 * @param root
	 */
	public BSTTree() {
		super();
		this.root = null;
	}

	/**
	 * @return the root
	 */
	public BSTNode<T> getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(BSTNode<T> root) {
		this.root = root;
	}

	public void add(T elem) {
		this.root = add(elem, root);
	}

	private BSTNode<T> add(T elem, BSTNode<T> node) {
		if (node == null) {
			return new BSTNode<T>(elem);
		} else {
			if (elem.compareTo(node.getElement()) == 0) {
				throw new RuntimeException("We don't allow repeated elements");
			}
			if (elem.compareTo(node.getElement()) < 0) {
				node.setLeft(add(elem, node.getLeft()));
			}
			if (elem.compareTo(node.getElement()) > 0) {
				node.setRight(add(elem, node.getRight()));
			}
		}
		node.updateHeight();
		return (node);
	}

	

	public String toString() {
		return toString(root);
	}

	public BSTTree<T> joins(BSTTree<T> tree) {
		BSTTree<T> result = copyTree(this);

		while (tree.getRoot() != null) {
			if (!result.search(tree.getRoot().getElement())) {
				result.add(tree.getRoot().getElement());
			}
			tree.remove(tree.getRoot().getElement());
		}

		return result;
	}
	
	private BSTTree<T> copyTree(BSTTree<T> tree) {
		BSTTree<T> result = new BSTTree<T>();
		BSTNode<T> theRoot = getRoot().copyNode();
		result.setRoot(theRoot);
		return result;
	}

	public boolean search(T element) {
		if (element == null) {
			throw new RuntimeException("The element is null!");
		}
		return search(root, element);
	}

	private boolean search(BSTNode<T> root2, T element) {
		if (root2 == null) {
			return false;
		} else if (element.compareTo(root2.getElement()) == 0) {
			return true;
		} else {
			if (element.compareTo(root2.getElement()) < 0) {
				return search(root2.getLeft(), element);
			} else if (element.compareTo(root2.getElement()) > 0) {
				return search(root2.getRight(), element);
			}
		}
		return false;
	}

	public String toStringS() {
		return toStringSimple(root);
	}

	private String toStringSimple(BSTNode<T> node) {
		String aux = "";
		if (node == null) {
			aux += "-";
			return aux;
		}
		aux += node.toStringSimple();
		aux += toStringSimple(node.getLeft());
		aux += toStringSimple(node.getRight());
		return aux;
	}

	private String toString(BSTNode<T> node) {
		
		String aux = "";
		if (node == null) {
			aux += "-";
			return aux;
		}
		aux += node.toString();

		aux += toString(node.getLeft());
		aux += toString(node.getRight());

		return aux;
	}

	public boolean search(BSTNode<T> node) {
		if (node.equals(null)) {
			return false;
		}
		if (node.equals(root)) {
			return true;
		}
		searchLeft(node, root.getLeft());
		searchRight(node, root.getRight());
		return false;
	}

	private boolean searchRight(BSTNode<T> node, BSTNode<T> right) {
		if (node.equals(right)) {
			return true;
		}
		searchRight(node, right.getRight());
		return false;
	}

	private boolean searchLeft(BSTNode<T> node, BSTNode<T> left) {
		if (node.equals(left)) {
			return true;
		}
		searchLeft(node, left.getLeft());
		return false;
	}

	private T getMax(BSTNode<T> theRoot) {
		if (theRoot == null) {
			return null;
		}
		else {
			return getMaxRec(theRoot);
		}
	}
	
	private T getMaxRec (BSTNode<T> theRoot) {
		if (theRoot.getRight() == null) {
			return theRoot.getElement();
		} else {
			return getMaxRec(theRoot.getRight());
		}
	}

	/**
	 * If we found the element we continue with the execution
	 * -------------------------------------------------------------------- If
	 * the elem is a leave we just remove it
	 * 
	 * If it has only one children we return the children
	 * 
	 * If the elem has more the one substitute the element by the maximum at
	 * it's left branch and call the remove for this element
	 */
	public void remove(T element) {
		root = remove(root, element);
	}

	private BSTNode<T> remove(BSTNode<T> theRoot, T element) {

		if (theRoot == null) {
			throw new RuntimeException("The element does not exist!");
		} else {
			if (element.compareTo(theRoot.getElement()) < 0) {
				theRoot.setLeft(remove(theRoot.getLeft(), element));
			} else {
				if (element.compareTo(theRoot.getElement()) > 0) {
					theRoot.setRight(remove(theRoot.getRight(), element));
				} else {
					if (theRoot.getLeft() == null
							&& theRoot.getRight() == null) {
						return null;
					} else if (theRoot.getLeft() == null
							&& theRoot.getRight() != null) {
						return theRoot.getRight();
					} else if (theRoot.getRight() == null
							&& theRoot.getLeft() != null) {
						return theRoot.getLeft();
					} else
						theRoot.setElement(getMax(theRoot.getLeft()));
					theRoot.setLeft(
							remove(theRoot.getLeft(), theRoot.getElement()));
				}
			}
		}
		theRoot.updateHeight();
		return (theRoot);
	}

	/*
	 * Height of the tree
	 * 
	 * If the node has no childs we set his height to 0
	 * 
	 * If the node has one node we check for the height of the nodes with get
	 * height and we add 1
	 */

}
