package treesTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mainTree.BSTTree;

public class BSTAVLTest {

	@Test
	public void test_AH() {

		BSTTree<Character> charTree = new BSTTree<Character>();
		charTree.add('a');
		charTree.add('b');
		charTree.add('c');

		assertEquals("a(2)-b(1)-c(0)--", charTree.toString());

		charTree.add('d');
		assertEquals("a(3)-b(2)-c(1)-d(0)--", charTree.toString());

	}

	@Test
	public void test_BH() {

		// Example
		BSTTree<Character> a = new BSTTree<Character>();
		a.add('b');
		a.add('a');
		a.add('d');
		a.add('c');
		a.add('g');
		a.add('i');
		a.add('h');
		assertEquals("b(4)a(0)--d(3)c(0)--g(2)-i(1)h(0)---", a.toString());

		// Scenery III
		a.remove('d');
		assertEquals("b(4)a(0)--c(3)-g(2)-i(1)h(0)---", a.toString());

		// Scenery II
		a.remove('g');
		assertEquals("b(3)a(0)--c(2)-i(1)h(0)---", a.toString());

		// Scenery I
		a.remove('a');
		assertEquals("b(3)-c(2)-i(1)h(0)---", a.toString());
	}

	@Test
	public void test_B() {

		// Example
		BSTTree<Character> a = new BSTTree<Character>();
		a.add('b');
		a.add('a');
		a.add('d');
		a.add('c');
		a.add('g');
		a.add('i');
		a.add('h');
		assertEquals("ba--dc--g-ih---", a.toStringS());

		// Scenery III
		a.remove('d');
		assertEquals("ba--c-g-ih---", a.toStringS());

		// Scenery II
		a.remove('g');
		assertEquals("ba--c-ih---", a.toStringS());

		// Scenery I
		a.remove('a');
		assertEquals("b-c-ih---", a.toStringS());
	}

	@Test
	public void test_C_Joins() {

		// Example
		BSTTree<Integer> a = new BSTTree<Integer>();
		a.add(5);
		a.add(3);
		a.add(1);
		a.add(2);
		a.add(7);
		assertEquals("5(3)3(2)1(1)-2(0)---7(0)--", a.toString());

		BSTTree<Integer> b = new BSTTree<Integer>();
		b.add(7);
		b.add(6);
		b.add(8);
		assertEquals("7(1)6(0)--8(0)--", b.toString());
		
		BSTTree<Integer> c = new BSTTree<Integer>();
		c.add(5);
		c.add(3);
		c.add(1);
		c.add(2);
		c.add(7);
		assertEquals("5(3)3(2)1(1)-2(0)---7(0)--", a.toString());

		BSTTree<Integer> d = new BSTTree<Integer>();
		d.add(7);
		d.add(6);
		d.add(8);
		assertEquals("7(1)6(0)--8(0)--", b.toString());

		// At least one must work (ask the student to explain his/her algorithm)
		// Depends on the traverse strategy and which tree is used as the pivot
//		assertEquals("5(3)3(2)1(1)-2(0)---7(1)6(0)--8(0)--",
//				a.joins(b).toString());
		
		assertEquals("7(5)6(4)5(3)3(2)2(1)1(0)------8(0)--",
				d.joins(c).toString());
	}

	@Test
	public void test_D_Sample() {

		BSTTree<Character> a = new BSTTree<Character>();
		a.add('b');
		a.add('a');
		a.add('d');
		assertEquals("b(1)a(0)--d(0)--", a.toString());

		BSTTree<Character> b = new BSTTree<Character>();
		b.add('c');
		b.add('g');
		b.add('i');
		b.add('d');
		assertEquals("c(2)-g(1)d(0)--i(0)--", b.toString());
		assertEquals("b(3)a(0)--d(2)c(0)--g(1)-i(0)--", a.joins(b).toString());
	}

}
