import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends JFrame
{
	private JTextField searchField;
	private ArrayList<String> listArray;
	private JTextField rosterFld;
	private String tempString;
	private JComboBox comboBox;
	private final String rosterList = "ROSTER LIST.txt";

	public Menu()
	{
		setResizable(false);
		setTitle("Student Information");
		getContentPane().setLayout(null);
		Scanner scan;
		File rosterFile = new File(rosterList);
		if(!rosterFile.exists())
		{
			try
			{
				rosterFile.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try
		{
			scan = new Scanner(new File(rosterList));
			listArray = new ArrayList<String>();
			while (scan.hasNextLine())
			{
				String line = scan.nextLine();
				listArray.add(line);

			}

			comboBox = new JComboBox();
			comboBox.setBounds(155, 11, 130, 20);
			for (int i = 0; i < listArray.size(); i++)
			{
				comboBox.addItem(listArray.get(i).substring(0, listArray.get(i).indexOf(".txt")));
			}
			getContentPane().add(comboBox);
		} catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(listArray.size() == 0)
		{
			File defaultFile = new File("Default Roster.txt");
			try
			{
				if(defaultFile.exists())
				{
					defaultFile.delete();
				}
				defaultFile.createNewFile();
				listArray.add("Default Roster.txt");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			comboBox.removeAllItems();
			for (int i = 0; i < listArray.size(); i++)
			{
				comboBox.addItem(listArray.get(i).substring(0, listArray.get(i).indexOf(".txt")));
			}
		}

		JButton btnAddStudent = new JButton("Add/Remove Student(s)");
		btnAddStudent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					if(comboBox.getSelectedIndex() != -1)
					{
					IdCompleter ic = new IdCompleter(comboBox.getSelectedItem().toString() + ".txt");
					AddStudentFrame studentFrame = new AddStudentFrame(ic);
					}
					else
					{
						Error er = new Error("Select/create a roster to add to.");
					}
				} catch (Exception E)
				{

				}

			}
		});
		btnAddStudent.setBounds(50, 70, 263, 30);
		getContentPane().add(btnAddStudent);

		searchField = new JTextField();
		searchField.setForeground(Color.GRAY);
		searchField.setText("Enter Name or ID");
		searchField.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				if (searchField.getText().equals("Enter Name or ID"))
				{
					searchField.setForeground(Color.BLACK);
					searchField.setText("");
				}
			}
		});
		searchField.setBounds(155, 39, 116, 20);
		getContentPane().add(searchField);
		searchField.setColumns(10);
		getContentPane().add(searchField);

		JLabel lblSearchStudent = new JLabel("Search for Student:");
		lblSearchStudent.setBounds(10, 42, 146, 14);
		getContentPane().add(lblSearchStudent);

		JLabel lblSelectAList = new JLabel("Select a List:");
		lblSelectAList.setBounds(40, 14, 105, 14);
		getContentPane().add(lblSelectAList);

		JButton btnNewRoster = new JButton("Create/Delete a Roster");
		btnNewRoster.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				newRosterFrame();
			}
		});
		btnNewRoster.setBounds(50, 112, 263, 30);
		getContentPane().add(btnNewRoster);

		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (comboBox.getSelectedIndex() != -1 && !searchField.getText().equals("Enter Name or ID") && !searchField.getText().equals(""))
				{
					SearchWindow s = new SearchWindow(
							new IdCompleter(comboBox.getSelectedItem().toString() + ".txt", searchField.getText().toLowerCase()));
					searchField.setText("");
				}
				else
				{
					Error e = new Error("Select a roster and type to search.");
				}
			}
		});
		btnGo.setBounds(281, 36, 53, 23);
		getContentPane().add(btnGo);

		setVisible(true);
		setSize(371, 181);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void newRosterFrame()
	{
		JFrame r = new JFrame();
		r.setTitle("New Roster");
		r.getContentPane().setLayout(null);
		r.setResizable(false);
		r.setTitle("Add/Delete a Roster");

		JLabel lblRosterName = new JLabel("New Roster Name:");
		lblRosterName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRosterName.setBounds(10, 11, 138, 14);
		r.getContentPane().add(lblRosterName);

		rosterFld = new JTextField();
		rosterFld.setBounds(158, 8, 86, 20);
		r.getContentPane().add(rosterFld);
		rosterFld.setColumns(10);

		JButton btnCreateRoster = new JButton("Create Roster");
		btnCreateRoster.setBounds(64, 36, 140, 23);
		btnCreateRoster.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!rosterFld.getText().equals(""))
				{
					tempString = rosterFld.getText() + ".txt";
					File newF = new File(tempString);
					try
					{
						if (newF.exists())
						{
							JFrame prompt = new JFrame();
							prompt.getContentPane().setLayout(null);
							prompt.setSize(353, 159);

							JTextField owPrompt = new JTextField("Overwrite existing file?");
							owPrompt.setFont(new Font("Tahoma", Font.PLAIN, 15));
							owPrompt.setBounds(87, 11, 150, 37);
							owPrompt.setEditable(false);
							prompt.getContentPane().add(owPrompt);

							JButton yes = new JButton("Yes");
							yes.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									newF.delete();
									listArray.remove(tempString);
									try
									{
										newF.createNewFile();
										listArray.add(tempString);
										saveRosterList();
										comboBox.removeAllItems();
										for (int i = 0; i < listArray.size(); i++)
										{
											comboBox.addItem(listArray.get(i).substring(0, listArray.get(i).indexOf(".txt")));
										}
										getContentPane().add(comboBox);
									} catch (IOException e1)
									{
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									prompt.dispose();
									r.dispose();
								}
							});
							yes.setBounds(51, 47, 89, 42);
							prompt.getContentPane().add(yes);

							JButton no = new JButton("No");
							no.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									prompt.dispose();
									r.dispose();
								}
							});
							no.setBounds(193, 47, 89, 42);
							prompt.getContentPane().add(no);
							prompt.setVisible(true);
						} else
						{
							newF.createNewFile();
							listArray.add(tempString);
							saveRosterList();
							r.dispose();
							comboBox.removeAllItems();
							for (int i = 0; i < listArray.size(); i++)
							{
								comboBox.addItem(listArray.get(i).substring(0, listArray.get(i).indexOf(".txt")));
							}
							getContentPane().add(comboBox);
						}
					} catch (HeadlessException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					Error error = new Error("Please enter a roster name.");
				}
			}
		});
		r.getContentPane().add(btnCreateRoster);

		JPanel panel = new JPanel();
		panel.setBounds(0, 70, 279, 76);
		r.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblDeleteARoster = new JLabel("Delete a Roster:");
		lblDeleteARoster.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDeleteARoster.setBounds(5, 11, 106, 14);
		panel.add(lblDeleteARoster);

		JComboBox delComboBox = new JComboBox();
		for (int i = 0; i < listArray.size(); i++)
		{
			delComboBox.addItem(listArray.get(i).substring(0, listArray.get(i).indexOf(".txt")));
		}
		delComboBox.setBounds(115, 8, 133, 20);
		panel.add(delComboBox);

		JButton btnDeleteRoster = new JButton("Delete Roster");
		btnDeleteRoster.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (delComboBox.getSelectedIndex() != -1)
				{
					listArray.remove(delComboBox.getSelectedIndex());
					comboBox.removeAllItems();
					for (int i = 0; i < listArray.size(); i++)
					{
						comboBox.addItem(listArray.get(i).substring(0, listArray.get(i).indexOf(".txt")));
					}
					delComboBox.removeAllItems();
					for (int i = 0; i < listArray.size(); i++)
					{
						delComboBox.addItem(listArray.get(i).substring(0, listArray.get(i).indexOf(".txt")));
					}
					saveRosterList();
					r.dispose();
				}
				else
				{
					Error err = new Error("Select a roster to delete.");
				}
				
			}
		});
		btnDeleteRoster.setBounds(64, 42, 140, 23);
		panel.add(btnDeleteRoster);
		
		

		r.setVisible(true);
		r.setSize(270, 175);
		r.setLocationRelativeTo(null);

	}

	public void saveRosterList()
	{
		try
		{

			FileWriter fw;
			fw = new FileWriter(rosterList);
			PrintWriter writer = new PrintWriter(fw);
			for (int i = 0; i < listArray.size(); i++)
			{
				writer.println(listArray.get(i));
			}
			writer.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[])
	{
		Menu menu = new Menu();
	}

	public ArrayList<String> getListArray()
	{
		return listArray;
	}

	public void setListArray(ArrayList<String> listArray)
	{
		this.listArray = listArray;
	}
}
