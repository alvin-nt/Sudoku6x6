import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.*;

import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * The sudoku cell
 * @author Alvin Natawiguna
 *
 */
public class SudokuCell extends JTextField {
	/**
	 * The id of the cell, e.g. A1, B2, C3, that represents the cell's location
	 * 
	 * the character represents the column, whereas the number represents the row.
	 */
	private String id;
	
	/**
	 * Default constructor
	 * @param id the cell's id
	 */
	public SudokuCell(String id) {
		super();
		initialize(id);
	}
	
	/**
	 * Constructor, with number of columns defined
	 * @param id
	 * @param col
	 */
	public SudokuCell(String id, int col) {
		super(col);
		initialize(id);
	}
	
	
	
	/**
	 * Initialize the properties of this cell
	 * @param id
	 */
	private void initialize(String id) {
		assert(!Utils.isNullOrEmpty(id));
		
		this.id = id.toUpperCase();
		((AbstractDocument)this.getDocument()).setDocumentFilter(new SudokuCellDocumentFilter(this.id));
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(!Utils.isNullOrEmpty(getText())) {
					if(getText().equals(SudokuCell.this.id)) {
						setText("");
					}
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(Utils.isNullOrEmpty(getText())) {
					setText(SudokuCell.this.id);
				}
			}
		});
		this.setText(this.id);
	}
	
	/**
	 * Get the id of this cell
	 * @return id
	 */
	public String getId() {
		return id;
	}
}

class SudokuCellDocumentFilter extends DocumentFilter {
	private static final int limit = 1;
	private final String id;
	
	SudokuCellDocumentFilter(String id) {
		this.id = id;
	}
	
	@Override
	public void insertString(DocumentFilter.FilterBypass fp,
			int offset, String string, AttributeSet aset)
	throws BadLocationException
	{
		boolean isValid;
		
		if (Utils.isNullOrEmpty(string)) {
			isValid = true;
		} else if(fp.getDocument().getLength() + string.length() > limit) {
			isValid = (string.equals(id));
		} else {
			isValid = Utils.isNumeric(string);
		}
		
		if(isValid) {
			super.insertString(fp, offset, string, aset);
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}
	
	@Override
	public void replace(DocumentFilter.FilterBypass fp,
			int offset, int length, String string, AttributeSet aset)
	throws BadLocationException
	{
		boolean isValid;
		
		if (Utils.isNullOrEmpty(string)) {
			isValid = true;
		} else if(fp.getDocument().getLength() + string.length() > limit) {
			isValid = (string.equals(id));
		} else {
			isValid = Utils.isNumeric(string);
		}
		
		if(isValid) {
			super.replace(fp, offset, length, string, aset);
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}
}