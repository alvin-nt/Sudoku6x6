import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

	private JFrame frmMiniSudokux;
	private JTextField A1;
	private JTextField A2;
	private JTextField A3;
	private JTextField A4;
	private JTextField A5;
	private JTextField B1;
	private JTextField C1;
	private JTextField D1;
	private JTextField E1;
	private JTextField A6;
	private JTextField F1;
	private JTextField B2;
	private JTextField B3;
	private JTextField B4;
	private JTextField B5;
	private JTextField B6;
	private JTextField C2;
	private JTextField C3;
	private JTextField C4;
	private JTextField C5;
	private JTextField C6;
	private JTextField D2;
	private JTextField D3;
	private JTextField D4;
	private JTextField D5;
	private JTextField D6;
	private JTextField E2;
	private JTextField E3;
	private JTextField E4;
	private JTextField E5;
	private JTextField E6;
	private JTextField F2;
	private JTextField F3;
	private JTextField F4;
	private JTextField F5;
	private JTextField F6;
	private JButton btnReset;

	private ClipsInterface clips = ClipsInterface.getInstance();
	
	private HashMap<String, Integer> sudokuData = new HashMap<>();
	private List<JTextField> cellList;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Initialization Error: ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmMiniSudokux.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMiniSudokux = new JFrame();
		frmMiniSudokux.setTitle("Mini SudokuX Solver");
		frmMiniSudokux.setBounds(100, 100, 450, 300);
		frmMiniSudokux.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMiniSudokux.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelSudoku = new JPanel();
		frmMiniSudokux.getContentPane().add(panelSudoku, BorderLayout.CENTER);
		panelSudoku.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow]", "[][][][][][][][][][][]"));
		
		A1 = new SudokuCell("A1");
		A1.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(A1, "cell 0 0,growx");
		A1.setColumns(10);
		
		A2 = new SudokuCell("A2");
		panelSudoku.add(A2, "cell 1 0,growx");
		A2.setColumns(10);
		
		A3 = new SudokuCell("A3");
		panelSudoku.add(A3, "cell 2 0,growx");
		A3.setColumns(10);
		
		A4 = new SudokuCell("A4");
		panelSudoku.add(A4, "cell 3 0,growx");
		A4.setColumns(10);
		
		A5 = new SudokuCell("A5");
		panelSudoku.add(A5, "cell 4 0,growx");
		A5.setColumns(10);
		
		A6 = new SudokuCell("A6");
		A6.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(A6, "cell 5 0,growx");
		A6.setColumns(10);
		
		B1 = new SudokuCell("B1");
		panelSudoku.add(B1, "cell 0 2,growx");
		B1.setColumns(10);
		
		B2 = new SudokuCell("B2");
		B2.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(B2, "cell 1 2,growx");
		B2.setColumns(10);
		
		B3 = new SudokuCell("B3");
		panelSudoku.add(B3, "cell 2 2,growx");
		B3.setColumns(10);
		
		B4 = new SudokuCell("B4");
		panelSudoku.add(B4, "cell 3 2,growx");
		B4.setColumns(10);
		
		B5 = new SudokuCell("B5");
		B5.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(B5, "cell 4 2,growx");
		B5.setColumns(10);
		
		B6 = new SudokuCell("B6");
		panelSudoku.add(B6, "cell 5 2,growx");
		B6.setColumns(10);
		
		C1 = new SudokuCell("C1");
		panelSudoku.add(C1, "cell 0 4,growx");
		C1.setColumns(10);
		
		C2 = new SudokuCell("C2");
		panelSudoku.add(C2, "cell 1 4,growx");
		C2.setColumns(10);
		
		C3 = new SudokuCell("C3");
		C3.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(C3, "cell 2 4,growx");
		C3.setColumns(10);
		
		C4 = new SudokuCell("C4");
		C4.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(C4, "cell 3 4,growx");
		C4.setColumns(10);
		
		C5 = new SudokuCell("C5");
		panelSudoku.add(C5, "cell 4 4,growx");
		C5.setColumns(10);
		
		C6 = new SudokuCell("C6");
		panelSudoku.add(C6, "cell 5 4,growx");
		C6.setColumns(10);
		
		D1 = new SudokuCell("D1");
		panelSudoku.add(D1, "cell 0 6,growx");
		D1.setColumns(10);
		
		D2 = new SudokuCell("D2");
		panelSudoku.add(D2, "cell 1 6,growx");
		D2.setColumns(10);
		
		D3 = new SudokuCell("D3");
		D3.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(D3, "cell 2 6,growx");
		D3.setColumns(10);
		
		D4 = new SudokuCell("D4");
		D4.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(D4, "cell 3 6,growx");
		D4.setColumns(10);
		
		D5 = new SudokuCell("D5");
		panelSudoku.add(D5, "cell 4 6,growx");
		D5.setColumns(10);
		
		D6 = new SudokuCell("D6");
		panelSudoku.add(D6, "cell 5 6,growx");
		D6.setColumns(10);
		
		E1 = new SudokuCell("E1");
		panelSudoku.add(E1, "cell 0 8,growx");
		E1.setColumns(10);
		
		E2 = new SudokuCell("E2");
		E2.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(E2, "cell 1 8,growx");
		E2.setColumns(10);
		
		E3 = new SudokuCell("E3");
		panelSudoku.add(E3, "cell 2 8,growx");
		E3.setColumns(10);
		
		E4 = new SudokuCell("E4");
		panelSudoku.add(E4, "cell 3 8,growx");
		E4.setColumns(10);
		
		E5 = new SudokuCell("E5");
		E5.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(E5, "cell 4 8,growx");
		E5.setColumns(10);
		
		E6 = new SudokuCell("E6");
		panelSudoku.add(E6, "cell 5 8,growx");
		E6.setColumns(10);
		
		F1 = new SudokuCell("F1");
		F1.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(F1, "cell 0 10,growx");
		F1.setColumns(10);
		
		F2 = new SudokuCell("F2");
		panelSudoku.add(F2, "cell 1 10,growx");
		F2.setColumns(10);
		
		F3 = new SudokuCell("F3");
		panelSudoku.add(F3, "cell 2 10,growx");
		F3.setColumns(10);
		
		F4 = new SudokuCell("F4");
		panelSudoku.add(F4, "cell 3 10,growx");
		F4.setColumns(10);
		
		F5 = new SudokuCell("F5");
		panelSudoku.add(F5, "cell 4 10,growx");
		F5.setColumns(10);
		
		F6 = new SudokuCell("F6");
		F6.setBackground(Color.LIGHT_GRAY);
		panelSudoku.add(F6, "cell 5 10,growx");
		F6.setColumns(10);
		
		JPanel panelButton = new JPanel();
		frmMiniSudokux.getContentPane().add(panelButton, BorderLayout.EAST);
		GridBagLayout gbl_panelButton = new GridBagLayout();
		gbl_panelButton.columnWidths = new int[]{111, 0};
		gbl_panelButton.rowHeights = new int[]{43, 43, 43, 43, 43, 43, 0};
		gbl_panelButton.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelButton.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelButton.setLayout(gbl_panelButton);
		
		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					solve();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(Main.this.frmMiniSudokux, "Caught Exception: " + e.getMessage(), "CLIPS Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSolve.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btnSolve = new GridBagConstraints();
		gbc_btnSolve.fill = GridBagConstraints.BOTH;
		gbc_btnSolve.insets = new Insets(0, 0, 5, 0);
		gbc_btnSolve.gridx = 0;
		gbc_btnSolve.gridy = 0;
		panelButton.add(btnSolve, gbc_btnSolve);
		
		JButton btnLoad = new JButton("Load");
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.fill = GridBagConstraints.BOTH;
		gbc_btnLoad.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoad.gridx = 0;
		gbc_btnLoad.gridy = 1;
		panelButton.add(btnLoad, gbc_btnLoad);
		
		JButton btnGeneratePuzzle = new JButton("Generate Puzzle");
		GridBagConstraints gbc_btnGeneratePuzzle = new GridBagConstraints();
		gbc_btnGeneratePuzzle.fill = GridBagConstraints.BOTH;
		gbc_btnGeneratePuzzle.insets = new Insets(0, 0, 5, 0);
		gbc_btnGeneratePuzzle.gridx = 0;
		gbc_btnGeneratePuzzle.gridy = 2;
		panelButton.add(btnGeneratePuzzle, gbc_btnGeneratePuzzle);
		
		btnReset = new JButton("Reset");
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.fill = GridBagConstraints.BOTH;
		gbc_btnReset.insets = new Insets(0, 0, 5, 0);
		gbc_btnReset.gridx = 0;
		gbc_btnReset.gridy = 3;
		panelButton.add(btnReset, gbc_btnReset);
		
		JButton btnAbout = new JButton("About");
		GridBagConstraints gbc_btnAbout = new GridBagConstraints();
		gbc_btnAbout.fill = GridBagConstraints.BOTH;
		gbc_btnAbout.insets = new Insets(0, 0, 5, 0);
		gbc_btnAbout.gridx = 0;
		gbc_btnAbout.gridy = 4;
		panelButton.add(btnAbout, gbc_btnAbout);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clips.destroy();
				frmMiniSudokux.dispose();
			}
		});
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.fill = GridBagConstraints.BOTH;
		gbc_btnExit.gridx = 0;
		gbc_btnExit.gridy = 5;
		panelButton.add(btnExit, gbc_btnExit);
		
		cellList = Arrays.asList(A1, A2, A3, A4, A5, A6, B1, B2, B3, B4, B5, B6, C1, C2, C3, C4, C5, C6,
								D1, D2, D3, D4, D5, D6, E1, E2, E3, E4, E5, E6, F1, F2, F3, F4, F5, F6);
	}

	private void solve() throws Exception {
		sudokuData.clear();
		for(int i = 0; i < cellList.size(); i++) {
			SudokuCell cell = (SudokuCell) cellList.get(i);
			if(Utils.isNumeric(cell.getText())) {
				sudokuData.put(cell.getId(), Integer.parseInt(cell.getText()));
			} else {
				sudokuData.put(cell.getId(), null);
			}
		}
		
		if(clips.isLoaded()) {
			sudokuData.clear();
			sudokuData = clips.solve();
		} else {
			sudokuData = clips.solve(sudokuData);
		}
		
		refreshCells();
	}
	
	private void refreshCells() {
		assert(sudokuData.size() == 36);
		
		for(int i = 0; i < sudokuData.size(); i++) {
			// TODO: more efficient method to get the reference needed
			
			Integer val = sudokuData.get(getId(i));
			JTextField cell = cellList.get(i);
			if(val != null) {
				cell.setText(val.toString());
			}
		}
	}
	
	private String getId(int num) {
		int calc = (num + 1) % 6;
		
		char row = (char)(num / 6 + 65);
		int col = (char)(calc == 0 ? 6 : calc);
		
		return "" + row + col;
	}
}
