package mainTree;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<T>> {

	// ATTRIBUTES
	private AVLNode<T> root;

	/**
	 * Constructor for the BSTTree
	 * 
	 * @param root
	 */
	public AVLTree() {
		super();
		this.root = null;
	}

	/**
	 * @return the root
	 */
	public AVLNode<T> getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(AVLNode<T> root) {
		this.root = root;
	}

	public void add(T elem) {
		this.root = add(elem, root);
	}

	private AVLNode<T> add(T elem, AVLNode<T> node) {
		if (node == null) {
			return new AVLNode<T>(elem);
		} else {
			if (elem.compareTo(node.getElement()) == 0) {
				throw new IllegalArgumentException(
						"We don't allow repeated elements");
			}
			if (elem.compareTo(node.getElement()) < 0) {
				node.setLeft(add(elem, node.getLeft()));
			}
			if (elem.compareTo(node.getElement()) > 0) {
				node.setRight(add(elem, node.getRight()));
			}
		}

		return (updateBF(node));
	}

	public String toString() {
		return toString(root);
	}

	private String toString(AVLNode<T> node) {
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

	public boolean search(T element) {
		if (element == null) {
			throw new RuntimeException("The element is null!");
		}
		return search(root, element);
	}

	private boolean search(AVLNode<T> theRoot, T element) {
		if (theRoot == null) {
			return false;
		} else if (element.compareTo(theRoot.getElement()) == 0) {
			return true;
		} else {
			if (element.compareTo(theRoot.getElement()) < 0) {
				return search(theRoot.getLeft(), element);
			} else if (element.compareTo(theRoot.getElement()) > 0) {
				return search(theRoot.getRight(), element);
			}
		}
		return false;
	}

	public AVLTree<T> joins(AVLTree<T> tree) {
		AVLTree<T> result = copyTree(this);

		while (tree.getRoot() != null) {
			if (!result.search(tree.getRoot().getElement())) {
				result.add(tree.getRoot().getElement());
			}
			tree.remove(tree.getRoot().getElement());
		}

		return result;
	}

	private AVLTree<T> copyTree(AVLTree<T> tree) {
		AVLTree<T> result = new AVLTree<T>();
		AVLNode<T> theRoot = getRoot().copyNode();
		result.setRoot(theRoot);
		return result;
	}

	public T getMax(AVLNode<T> theRoot) {
		if (theRoot == null) {
			return null;
		} else {
			return getMaxRec(theRoot);
		}
	}

	private T getMaxRec(AVLNode<T> theRoot) {
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

	/**
	 * Removes a node from the AVLTree
	 * 
	 * @param root
	 * @param element
	 * @return
	 */
	private AVLNode<T> remove(AVLNode<T> theRoot, T element) {
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
		return (updateBF(theRoot));
	}

	/*
	 * Height of the tree
	 * 
	 * If the node has no childs we set his height to 0
	 * 
	 * If the node has one node we check for the height of the nodes with get
	 * height and we add 1
	 */

	public int getHeight() {
		return getHeight(root);
	}

	public int getHeight(AVLNode<T> theRoot) {
		if (theRoot == null) {
			return 0;
		}

		int leftHeight = 0;
		int rightHeight = 0;

		leftHeight = getHeight(theRoot.getLeft());
		rightHeight = getHeight(theRoot.getRight());

		if (rightHeight > leftHeight) {
			return rightHeight + 1;
		} else {
			return leftHeight + 1;
		}
	}

	/**
	 * This method can update the tree, by doing rotations if it is necessary
	 * and also update the height of the root
	 * 
	 * @param theRoot
	 * @return
	 */
	private AVLNode<T> updateBF(AVLNode<T> theRoot) {
		theRoot.updateHeight();
		if (theRoot.getBF() == -2) {
			if (theRoot.getLeft().getBF() == 1) {
				theRoot = doubleLeftRotation(theRoot);
			} else {
				theRoot = singleLeftRotation(theRoot);
			}
		} else if (theRoot.getBF() == 2) {
			if (theRoot.getRight().getBF() == -1) {
				theRoot = doubleRightRotation(theRoot);
			} else {
				theRoot = singleRightRotation(theRoot);
			}
		}
		return theRoot;
	}

	/**
	 * Do the single left rotation
	 * 
	 * @param b
	 * @return
	 */
	private AVLNode<T> singleLeftRotation(AVLNode<T> b) {
		// Build the second tree by modifying the b subtree received
		// Update heights if necessary and return the new root
		AVLNode<T> a = b.getLeft();

		b.setLeft(a.getRight());
		a.setRight(b);
		b.updateHeight();

		return a;
	}

	/**
	 * Do the single right rotation
	 * 
	 * @param b
	 * @return
	 */
	private AVLNode<T> singleRightRotation(AVLNode<T> b) {
		AVLNode<T> a = b.getRight();

		b.setRight(a.getLeft());
		a.setLeft(b);
		b.updateHeight();

		return a;
	}

	/**
	 * Do the double left rotation
	 * 
	 * @param c
	 * @return
	 */
	private AVLNode<T> doubleLeftRotation(AVLNode<T> c) {
		// Build the second tree by modifying the c subtree received
		// Update heights if necessary and return the new root
		AVLNode<T> a = c.getLeft();
		AVLNode<T> b = c.getLeft().getRight();

		c.setLeft(b.getRight());
		a.setRight(b.getLeft());

		b.setLeft(a);
		b.setRight(c);

		a.updateHeight();
		b.updateHeight();
		c.updateHeight();

		return b;
	}

	/**
	 * Do the double right rotation
	 * 
	 * @param c
	 * @return
	 */
	private AVLNode<T> doubleRightRotation(AVLNode<T> c) {
		AVLNode<T> a = c.getRight();
		AVLNode<T> b = c.getRight().getLeft();

		c.setRight(b.getLeft());
		a.setLeft(b.getRight());

		b.setLeft(c);
		b.setRight(a);

		a.updateHeight();
		b.updateHeight();
		c.updateHeight();

		return b;
	}

	/**
	 * Returns the arithmetric mean of all the balance factors of the nodes in
	 * the ALVTree
	 * 
	 * @return
	 */
	public double factorBalanceMedio() {
		return getRoot().getChildsBFTotal()
				+ getRoot().getBF() / getRoot().getChilds() + 1;
	}

	/**
	 * Returns the brother of the given element
	 * 
	 * @param elem
	 * @return
	 */
	private T getHermano(AVLNode<T> root, T elem) {
		int flag = 0;
		T brother = null;
		if (root == null)
			throw new RuntimeException("The given root is null");

		// Stores nodes level wise
		Queue<AVLNode<T>> q = new LinkedList<>();

		// Push the root
		q.add(root);

		// Continue until all
		// levels are traversed
		while (!q.isEmpty()) {
			// Stores current node
			AVLNode<T> temp = q.peek();
			q.remove();

			// Enqueue all children
			// of the current node
			for (int i = 0; i < temp.getChilds(); i++) {
				// If node value is found
				if (temp.getListOfChilds().get(i).getElement() == elem) {
					flag = 1;

					// Print all children of current
					// node
					// except value as the answer
					for (int j = 0; j < temp.getChilds(); j++) {
						if (elem != temp.getListOfChilds().get(j).getElement())
							System.out.print(
									temp.getListOfChilds().get(j).getElement()
											+ " ");
						brother = temp.getListOfChilds().get(j).getElement();
					}
					break;
				}

				// Push the child nodes
				// of temp into the queue
				q.add(temp.getListOfChilds().get(i));
			}
		}

		if (flag == 0) {
			System.out.print("No siblings!!");
			return null;
		}
		return brother;
	}

	/**
	 * Returns the tree which is the difference between this tree and the given
	 * 
	 * @param tree
	 * @return
	 */
	public AVLTree<T> difference(AVLTree<T> tree) {
		AVLTree<T> t = copyTree(this);

		while (tree.getRoot() != null) {
			if (t.search(tree.getRoot().getElement())) {
				t.remove(tree.getRoot().getElement());
			}
			tree.remove(tree.getRoot().getElement());
		}

		return t;
	}
}
