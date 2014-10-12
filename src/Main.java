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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {

	private JFrame frmMiniSudokux;
	private JButton btnReset;

	private ClipsInterface clips = ClipsInterface.getInstance();
	
	private HashMap<String, Integer> sudokuData = new HashMap<>();
	private ArrayList<SudokuCell> cells;
	
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
		
		// generate the cells
		cells = new ArrayList<>();
		for(int i = 0; i < 36; i++) {
			String id = getId(i);
			cells.add(new SudokuCell(id));
			
			SudokuCell cell = cells.get(i);
			if(ClipsInterface.diagonal(cell.getId()) > 0) {
				cell.setBackground(Color.LIGHT_GRAY);
			}
			
			int col = (int)(id.charAt(1) - 48);
			int row = (int)(id.charAt(0) - 64);
			
			panelSudoku.add(cell, "cell " + (col - 1) + " " + ((row-1)*2) + ",growx");
			cell.setColumns(10);
		}
		
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
					refreshCells();
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
		
	}

	private void solve() throws Exception {
		sudokuData.clear();
		
		if(clips.isLoaded()) {
			sudokuData = clips.solve();
		} else {
			for(SudokuCell cell : cells) {
				String text = cell.getText();
				
				System.out.println("[DEBUG] Loading cell " + cell.getId() + ": " + text);
				
				if(Utils.isNullOrEmpty(text)) {
					throw new Exception("Empty text on " + cell.getId());
				} else {
					if(Utils.isNumeric(text)) {
						sudokuData.put(cell.getId(), (Integer) Integer.parseInt(text));
					} else {
						sudokuData.put(cell.getId(), null);
					}
				}
			}
			
			sudokuData = clips.solve(sudokuData);
		}
	}
	
	private void refreshCells() {
		System.out.println("[DEBUG] sudokuData.size(): " + sudokuData.size());
		//assert(sudokuData.size() == 36);
		
		for(int i = 0; i < sudokuData.size(); i++) {
			String id = getId(i);
			Integer val = sudokuData.get(id);
			SudokuCell cell = cells.get(i);
			
			if(val != null) {
				cell.setText(val.toString());
			} else {
				cell.setText(cell.getId());
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
