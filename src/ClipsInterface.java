import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import CLIPSJNI.*;

public class ClipsInterface {
	
	private boolean destroyed = true;
	private boolean loaded = false;
	private static ClipsInterface instance = new ClipsInterface();
	
	private static final List<String> diagonal1 = Arrays.asList("A1", "B2", "C3", "D4", "E5", "F6");
	private static final List<String> diagonal2 = Arrays.asList("A6", "B5", "C4", "D3", "E2", "F1");
	
	private Environment env;
	
	private ClipsInterface() {
		// load appropriate library
		int sysVersion = Integer.parseInt(System.getProperty("sun.arch.data.model"));
		if(sysVersion == 64) {
			System.out.println("CLIPSJNI: Detected 64-bit JVM");
			System.load(System.getProperty("user.dir") + "/lib/clipsjni64.dll");
		} else {
			System.out.println("CLIPSJNI: Detected 32-bit JVM");
			System.load(System.getProperty("user.dir") + "/lib/clipsjni32.dll");
		}
		
		initialize();
	}
	
	public void initialize() {
		if(destroyed) {
			env = new Environment();
			
			// load the rules
			env.clear();
			env.unwatch("all");
			env.load(System.getProperty("user.dir") + "/clips/sudoku.clp"); // rules of the SudokuX
			env.load(System.getProperty("user.dir") + "/clips/solve.clp"); // algorithm for solving
			env.load(System.getProperty("user.dir") + "/clips/output-frills.clp"); // the output in CLIPS; only for debugging purposes
			
			destroyed = false;
		}
	}
	
	public static ClipsInterface getInstance() {
		return instance;
	}
	
	public void destroy() {
		env.destroy();
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public boolean loadTestCase(String filename) {
		boolean ret = false;
		if(filename.toLowerCase().endsWith("clp")) {
			env.load(filename);
			loaded = true;
			// TODO: error checking
		} else {
			ret = parseTestCase(filename);
		}
		
		return ret;
	}
	
	public void reset() {
		loaded = false;
	}
	
	public HashMap<String, Integer> solve(HashMap<String, Integer> sudokuData) throws Exception {
		assert(sudokuData != null);
		assert(sudokuData.size() == 36);
		
		env.reset();
		env.assertString("(phase expand-any)");
		env.assertString("(size 3)");
		
		int idCount = 1;
		for(char row = 'A'; row <= 'F'; row++) {
			for(int col = 1; col <= 6; col++) {
				StringBuilder assertStr = new StringBuilder();
				
				// generate assertion string
				assertStr.append("(possible ");
				assertStr.append("(row ").append((int)(row - 64)).append(") ");
				assertStr.append("(column ").append(col).append(") ");
				
				String id = String.format("%c%d", row, col);
				
				// value
				Integer val = sudokuData.get(id);
				if(val == null) {
					assertStr.append("(value any) ");
				} else {
					assertStr.append("(value ").append(val).append(") ");
				}
				
				// group, diagonal, and idCount
				assertStr.append("(group ").append(getGroup(id)).append(") ");
				assertStr.append("(diagonal ").append(diagonal(id)).append(") ");
				assertStr.append("(id ").append(idCount++).append("))");
				
				System.out.println(assertStr.toString());
				env.assertString(assertStr.toString());
			}
		}
		
		env.run();
		
		return getResult();
	}
	
	public HashMap<String, Integer> solve() throws Exception {
		if(loaded) {
			env.reset();
			env.run();
		
			return getResult();
		} else {
			throw new Exception("Facts have not been loaded yet!");
		}
	}
	
	private HashMap<String, Integer> getResult() throws Exception {
		HashMap<String, Integer> result = new HashMap<>();
		
		// Evaluate value from CLIPS
		String formatStr = "(find-all-facts ((?f possible)) (and (eq ?f:row %d) (eq ?f:column %d)))";
		for(char row = 'A'; row <= 'F'; row++) {
			for(int col = 1; col <= 6; col++) {
				String evalStr = String.format(formatStr, (int)(row - 64), col);
				System.out.println(evalStr);
				
				PrimitiveValue pv = env.eval(evalStr);
				
				if(pv.size() != 1) continue; // shouldn't be possible: multiple/empty facts!
				
				// get the value
				PrimitiveValue fv = pv.get(0);
				String val = fv.getFactSlot("value").toString();
				assert(!Utils.isNullOrEmpty(val));
				System.out.println("" + row + col + ": " + val);
				
				Integer cellValue = null;
				if(Utils.isNumeric(val)) {
					cellValue = Integer.parseInt(val);
				} else if(val.equals("*")) {
					cellValue = null;
				} else {
					throw new Exception("Detected invalid value for cell: " + val);
				}
				
				result.put("" + row + col, cellValue);
				
			}
		}
		assert(result.size() == 36);
		
		return result;
	}
	
	private int getGroup(String id) throws Exception {
		int count = 1;
		
		switch(id.charAt(0)) {
		case 'A':
		case 'B':
			break;
		case 'C':
		case 'D':
			count += 2;
			break;
		case 'E':
		case 'F':
			count += 4;
			break;
		default:
			throw new Exception("Unknown row identifier: " + id.charAt(0));
		}
		
		switch(id.charAt(1)) {
		case '1':
		case '2':
		case '3':
			break;
		case '4':
		case '5':
		case '6':
			count += 1;
			break;
		default:
			throw new Exception("Unknown column identifier: " + id.charAt(1));
		}
		
		return count;
	}
	
	private int diagonal(String id) {
		int ret;
		if(diagonal1.contains(id)) {
			ret = 1;
		} else if(diagonal2.contains(id)) {
			ret = 2;
		} else {
			ret = 0;
		}
		
		return ret;
	}
	
	/**
	 * Used if the test case is in form of text file
	 * the format must be like this:
	 * 	* * * * * *
	 * 	* * 1 * * *
	 * 	* * * * * *
	 * 	2 * * * * *
	 * 	* * * * * *
	 * 	* * * * * 3
	 * star (*) denotes empty number
	 * @param filename
	 * @return
	 */
	private boolean parseTestCase(String filename) {
		assert(filename != null && !filename.isEmpty());
		return false;
	}
}
