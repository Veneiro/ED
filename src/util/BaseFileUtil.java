package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseFileUtil {

	/**
	 * This method read lines from a file
	 * 
	 * @param inFileName
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public List<String> readLines(String inFileName) throws FileNotFoundException {
		BufferedReader in = createBufferedReaderChain(inFileName);
		List<String> res = new LinkedList<>();
		String line;
		try {
			try {
				while ((line = in.readLine()) != null) {
					res.add(line);
				}
			} finally {
				in.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found");
			throw new FileNotFoundException();
		} catch (IOException ex) {
			System.out.println("Cannot read the file");
			throw new FileNotFoundException();
		}
		return res;
	}

	protected abstract BufferedReader createBufferedReaderChain(String inFileName) throws FileNotFoundException;

	/**
	 * This method write lines on a file
	 * 
	 * @param outZippedFileName
	 * @param lines
	 * @throws Exception
	 */
	public void writeLines(String fileName, List<String> lines) throws IOException {
		try {
			BufferedWriter output = createBufferedWriterChain(fileName);
			try {
				for (String line : lines) {
					output.write(line);
					output.newLine();
				}
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			System.out.println("Cannot write the file");
			throw new IOException(ex);
		}
	}

	protected abstract BufferedWriter createBufferedWriterChain(String fileName) throws IOException;

}
