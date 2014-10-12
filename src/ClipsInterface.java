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
	
	private static int id1 = 1;
	private static int id2 = 7;
	private static int id3 = 13;
	private static int id4 = 19;
	private static int id5 = 25;
	private static int id6 = 31;
	
	private static int getId(int group) {
		int ret = -1;
		switch(group) {
		case 1:
			ret = id1++;
			break;
		case 2:
			ret = id2++;
			break;
		case 3:
			ret = id3++;
			break;
		case 4:
			ret = id4++;
			break;
		case 5:
			ret = id5++;
			break;
		case 6:
			ret = id6++;
			break;
		}
		
		return ret;
	}
	
	public static void resetId() {
		id1 = 1;
		id2 = 7;
		id3 = 13;
		id4 = 19;
		id5 = 25;
		id6 = 31;
	}
	
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
		
		resetId();
		env.getInputBuffer();
		
		// build the rule
		StringBuilder evalStr = new StringBuilder();
		
		evalStr.append("(defrule grid-values").append(System.lineSeparator());
		evalStr.append("?f <- (phase grid-values) =>").append(System.lineSeparator());
		evalStr.append("(retract ?f)").append(System.lineSeparator());
		evalStr.append("(assert (phase expand-any))").append(System.lineSeparator());
		evalStr.append("(assert (size 3))").append(System.lineSeparator());
		
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
				int group = getGroup(id);
				assertStr.append("(group ").append(group).append(") ");
				assertStr.append("(diagonal ").append(diagonal(id)).append(") ");
				assertStr.append("(id ").append(getId(group)).append("))");
				
				System.out.println("[DEBUG] Assertion string: " + assertStr.toString());
				evalStr.append("(assert ").append(assertStr.toString()).append(')').append(System.lineSeparator());
			}
		}
		evalStr.append(")");
		
		//System.out.println("[DEBUG] Evaluation String output: ");
		//System.out.println(evalStr.toString());
		
		// build the rule
		env.build(evalStr.toString());
		
		env.reset();
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
				
				//System.out.println("[DEBUG] Evaluation string:");
				//System.out.println(evalStr);
				
				PrimitiveValue pv = env.eval(evalStr);
				
				if(pv.size() != 1) continue; // shouldn't be possible: multiple/empty facts!
				
				// get the value
				PrimitiveValue fv = pv.get(0);
				String val = fv.getFactSlot("value").toString();
				
				//assert(!Utils.isNullOrEmpty(val));
				
				Integer cellValue = null;
				if(Utils.isNumeric(val)) {
					cellValue = Integer.parseInt(val);
				} else if(val.equals("any")) {
					cellValue = null;
				} else {
					throw new Exception("Detected invalid value for cell: " + val);
				}
				
				result.put("" + row + col, cellValue);
				System.out.println("[DEBUG] " + row + col + ": " + result.get("" + row + col));
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
	
	public static int diagonal(String id) {
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

