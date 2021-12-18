package tests;

import org.junit.Test;

import mainTree.BSTNode;
import mainTree.BSTTree;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BSTTests {

	private BSTTree bt = new BSTTree();

	@Test
	public void test() {
		bt.add("A1");
		bt.getRoot().setLeft(new BSTNode("A2"));
		bt.getRoot().setRight(new BSTNode("A3"));
		bt.add("a2");
		System.out.println(bt.toString());
	}

}
