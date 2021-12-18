package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mainTree.Algorithms;

public class FactorialTest {

	@Test
	public void testIte() {
		assertEquals(720, Algorithms.factorial(6));
	}

	@Test
	public void testRec() {
		assertEquals(720, Algorithms.factorialRec(6));
	}

}
