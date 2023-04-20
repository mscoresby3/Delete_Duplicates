import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main {

	private JFrame frame;
	private JTextField entry;
	private DefaultListModel<Integer> listnums;
	private JList<Integer> numList;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		System.out.println("System Starting");
		initialize();
		System.out.println("System Started");
	}

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblHeading = new JLabel("Delete Duplicates");
		lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeading.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblHeading.setBounds(110, 11, 215, 40);
		frame.getContentPane().add(lblHeading);

		listnums = new DefaultListModel<Integer>();
		numList = new JList<Integer>(listnums);
		numList.setBounds(280, 50, 130, 190);
		frame.getContentPane().add(numList);

		entry = new JTextField();
		entry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (entry.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Nothing to Add");
				} else {
					try {
						listnums.addElement(Integer.parseInt(entry.getText()));
					} catch (Exception checkEntry) {
						JOptionPane.showMessageDialog(frame, "Not a Vail Entry");
					} finally {
						entry.setText("");
					}
				}
			}
		});
		entry.setBounds(40, 65, 145, 30);
		frame.getContentPane().add(entry);
		entry.setColumns(10);

		JButton btnAddNumber = new JButton("Add Number");
		btnAddNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (entry.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Nothing to Add");
				} else {
					try {
						listnums.addElement(Integer.parseInt(entry.getText()));
					} catch (Exception checkEntry) {
						JOptionPane.showMessageDialog(frame, "Not a Vail Entry");
					} finally {
						entry.setText("");
					}
				}

			}
		});
		btnAddNumber.setBounds(42, 110, 140, 25);
		frame.getContentPane().add(btnAddNumber);

		JButton btnRemoveSelected = new JButton("Remove Selected");
		btnRemoveSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numList.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(frame, "Nothing Selected");
				} else {
					listnums.remove(numList.getSelectedIndex());
				}
			}
		});
		btnRemoveSelected.setBounds(35, 145, 160, 25);
		frame.getContentPane().add(btnRemoveSelected);

		JButton btnRemoveLast = new JButton("Remove Last Number");
		btnRemoveLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listnums.getSize() == 0) {
					JOptionPane.showMessageDialog(frame, "Nothing to Remove");
				} else {
					listnums.remove(listnums.getSize() - 1);
				}
			}
		});
		btnRemoveLast.setBounds(35, 180, 160, 25);
		frame.getContentPane().add(btnRemoveLast);

		JButton btnFinished = new JButton("Finished");
		btnFinished.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listnums.getSize() == 0) {
					JOptionPane.showMessageDialog(frame, "There are No Numbers");
				} else if (listnums.getSize() == 1) {
					JOptionPane.showMessageDialog(frame, "A Single Number cannot have a Duplicate\n" + listnums.get(0));
				} else {
					Map<Integer, AtomicInteger> duplicates = new TreeMap<Integer, AtomicInteger>();
					/*
					 * A dictionary but in java form. Integer is the key, the AtomicInteger is the
					 * value associated with it An AtomicInteger can be changed and everywhere it's
					 * assigned also changes, if that doesn't make sense ask google.
					 */
					// Runs for every number in the list
					for (int i = 0; i < listnums.getSize(); i++) {
						// Gets the AtomicInteger in the Map, If there isn't an AtomicInteger there it
						// creates one with the value zero
						AtomicInteger ai = duplicates.computeIfAbsent(listnums.get(i), (ii) -> new AtomicInteger(0));
						// Increases the value of the value in the Map
						ai.incrementAndGet();
					}
					String message = "";
					for (int i = 0; i < listnums.getSize(); i++) {
						message = message + listnums.get(i) + ", ";
					}
					message = message.substring(0, message.length() - 2) + "\n";
					if (duplicates.size() == 0) {
						message = message + "No Duplicate Values\n";
					} else {
						for (Entry<Integer, AtomicInteger> entry2 : duplicates.entrySet()) {
							if (entry2.getValue().get() > 1) {
								message += "Value " + entry2.getKey() + ": " + entry2.getValue() + "\n";
							}
						}
					}
					for (Entry<Integer, AtomicInteger> entry2 : duplicates.entrySet()) {
						message += entry2.getKey() + ", ";
					}
					message = message.substring(0, message.length() - 2);
					listnums.clear();
					JOptionPane.showMessageDialog(frame, message);
				}
			}
		});
		btnFinished.setBounds(42, 215, 140, 25);
		frame.getContentPane().add(btnFinished);
	}
}