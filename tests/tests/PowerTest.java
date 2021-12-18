package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mainTree.Algorithms;

public class PowerTest {

	@Test
	public void testIte() {
		assertEquals(1099511627776L, Algorithms.pow(40));
	}
	
//	@Test
//	public void testRec1() {
//		assertEquals(1099511627776L, Algorithms.powRec1(40));
//	}
	
	@Test
	public void testRec2() {
		assertEquals(1099511627776L, Algorithms.powRec2(40));
	}
	
	@Test
	public void testRec3() {
		assertEquals(1099511627776L, Algorithms.powRec3(40));
	}
	
	@Test
	public void testRec4() {
		assertEquals(1099511627776L, Algorithms.powRec4(40));
	}

}
