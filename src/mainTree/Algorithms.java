package mainTree;

public class Algorithms {
	
	/**
	 * Linear Algorithm
	 * @param n
	 */
	public static void linear(long n) {
		for (long i = 0; i <= n; i++) {
			// System.out.println("-----------------------------");
			TestBench.doNothing(i);
			// System.out.println("executing job: " + i);
		}
	}

	/**
	 * Quadratic Algorithm
	 * @param n
	 */
	public static void quadratic(long n) {
		for (long i = 0; i <= n; i++) {
			for (long j = 0; j <= n; j++) {
				// System.out.println("-----------------------------");
				TestBench.doNothing(i);
				// System.out.println("executing job: " + i);
			}
		}
	}

	/**
	 * Cubic Algorithm
	 * @param n
	 */
	public static void cubic(long n) {
		for (long i = 0; i <= n; i++) {
			for (long j = 0; j <= n; j++) {
				for (long z = 0; z <= n; z++) {
					// System.out.println("-----------------------------");
					TestBench.doNothing(i);
					// System.out.println("executing job: " + i);
				}
			}
		}
	}

	/**
	 * Logarithmic Algorithm
	 * @param n
	 */
	public static void logarithmic(long n) {
		while (n > 0) {
			TestBench.doNothing(n);
			n = n / 2;
		}
	}

	/**
	 * Factorial Algorithm
	 * @param n
	 * @return
	 */
	public static long factorial(long n) {
		TestBench.doNothing(n);
		if (n == 0) {
			return 1;
		}
		long factorial = 1;
		for (int i = 1; i <= n; i++) {
			factorial *= i;
		}
		return factorial;
	}

	/**
	 * Recursive Factorial Algorithm
	 * @param n
	 * @return
	 */
	public static long factorialRec(long n) {
		TestBench.doNothing(n);
		if (n == 0) {
			return 1;
		} else {
			return n * factorialRec(n - 1);
		}
	}

	/**
	 * Pow Algorithm
	 * @param n
	 * @return
	 */
	public static long pow(long n) { // O(n)
		TestBench.doNothing(n);
		long res = 1;
		for (long i = 1; i <= n; i++) {
			res *= 2;
		}
		return res;
	}

	/**
	 * Recursive Pow Algorithm V.1
	 * @param n
	 * @return
	 */
	public static long powRec1(long n) { // O(2^n)
		TestBench.doNothing(n);
		if (n == 0) {
			return 1;
		} else {
			return powRec1(n - 1) + powRec1(n - 1);
		}
	}

	/**
	 * Recursive Pow Algorithm V.2
	 * @param n
	 * @return
	 */
	public static long powRec2(long n) { // O(n)
		TestBench.doNothing(n);
		if (n == 0) {
			return 1;
		} else {
			return 2 * powRec2(n - 1);
		}
	}

	/**
	 * Recursive Pow Algorithm V.3
	 * @param n
	 * @return
	 */
	public static long powRec3(long n) { // O(n/2)
		TestBench.doNothing(n);
		if (n == 0) {
			return 1;
		} else {
			if (n % 2 != 0) {
				return 2 * (powRec3(n / 2) * powRec3(n / 2));
			} else {
				return powRec3(n / 2) * powRec3(n / 2);
			}
		}
	}

	/**
	 * Recursive Pow Algorithm V.4
	 * @param n
	 * @return
	 */
	public static long powRec4(long n) { //
		TestBench.doNothing(n);
		if (n == 0) {
			return 1;
		} else {
			long i = powRec3(n / 2);
			i *= i;
			if (n % 2 != 0) {
				return 2 * i;
			} else {
				return i;
			}
		}
	}

}
