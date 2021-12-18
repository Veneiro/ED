package mainTree;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestBench {

	/**
	 * ATTRIBUTTES
	 */
	public static final int SLEEP_TIME = 20;
	private List<String> l = new ArrayList<String>();
	public static final String PATH = "main.";

	/**
	 * This method is used to make the method wait SLEEP_TIME for each iteration
	 * 
	 * @param i
	 */
	public static void doNothing(long i) {
//		System.out.println("Doing nothing at iteration...(" + i +")");

		long endTime = System.currentTimeMillis() + SLEEP_TIME;
		while (System.currentTimeMillis() < endTime) {

		}
	}

	/**
	 * Basic test method
	 * 
	 * @param n
	 * @param samples
	 */
	public void test(long n, int samples) {
		double i = System.currentTimeMillis();

		Algorithms.linear(n);
		Algorithms.quadratic(n);
		Algorithms.cubic(n);
		Algorithms.logarithmic(n);

		i = System.currentTimeMillis() - i;
		l.add(Double.toString(i));
		System.out.println(i);
	}

	/**
	 * This method allows us to test all the algorithms created
	 * 
	 * @param samples
	 * @param outputFileName
	 * @param startN
	 * @param endN
	 * @param className
	 * @param methodName
	 */
	public void test(int samples, String outputFileName, int startN, int endN,
			String className, String methodName) {
		FileWriter file = null;
		PrintWriter pw = null;

		System.out.println();
		System.out.println(outputFileName + "algorithm");

		try {
			file = new FileWriter(outputFileName);
			pw = new PrintWriter(file);

			for (int j = startN; j <= endN; j++) {
				long avg = 0;
				for (int i = 0; i < samples; i++) {
					long timeStart = System.currentTimeMillis();
					testAlgorithm(PATH + className, methodName, j);
					avg = System.currentTimeMillis() - timeStart;
				}
				avg /= samples;
				pw.println(avg);
				System.out.println(avg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null)
					file.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		System.out.println();
	}

	/**
	 * Test Algorithm method to find the class we are searching to execute an
	 * algorithm
	 *
	 * @param className
	 * @param methodName
	 * @param n
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void testAlgorithm(String className, String methodName,
			long n) throws Exception {
		Class<?> myClass = null;
		Object myObject = null;

		// Loads the class dynamically using reflection
		myClass = Class.forName(className);
		myObject = myClass.newInstance();

		// Gets a method instance
		Class<?>[] params = new Class[1];
		params[0] = Long.TYPE;

		Method m = myClass.getMethod(methodName, params);

		// Calls the method in java using reflection
		m.invoke(myObject, n);
	}
}
