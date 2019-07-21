import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddStudentFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JList listDisplay;
	private JScrollPane listScroll;
	private String[] studentList;
	private JTextField nameField;
	private JTextField idField;
	private IdCompleter idc;
	private JLabel lblNewLabel_2;

	public AddStudentFrame(IdCompleter ic)
	{
		idc = ic;
		setTitle("Add Students");
		setResizable(false);
		getContentPane().setLayout(null);
		studentList = new String[ic.getMyList().size()];
		for (int i = 0; i < ic.getMyList().size(); i++)
		{
			studentList[i] = ic.getMyList().get(i).getMyName() + " - " + ic.getMyList().get(i).getMyId();
		}
		listDisplay = new JList(studentList);
		listDisplay.setLocation(12, 12);
		listDisplay.setSize(220, 402);
		listDisplay.setVisible(true);
		getContentPane().add(listDisplay);

		listScroll = new JScrollPane(listDisplay);
		listScroll.setLocation(10, 11);
		listScroll.setSize(200, 375);
		getContentPane().add(listScroll);

		nameField = new JTextField();
		nameField.setForeground(Color.BLACK);
		nameField.setBounds(339, 210, 87, 20);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Student Name: ");
		lblNewLabel.setBounds(220, 213, 114, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Student ID:");
		lblNewLabel_1.setBounds(219, 251, 101, 14);
		getContentPane().add(lblNewLabel_1);

		idField = new JTextField();

		idField.setForeground(Color.BLACK);
		idField.setBounds(339, 248, 87, 20);
		getContentPane().add(idField);
		idField.setColumns(10);

		JButton btnNewButton = new JButton("Delete Student");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (listDisplay.getSelectedIndex() != -1)
				{
					ArrayList<Student> temp = new ArrayList<Student>(idc.getMyList());
					temp.remove(listDisplay.getSelectedIndex());
					idc.setMyList(temp);
					idc.sortList(temp);
					studentList = new String[idc.getMyList().size()];
					for (int i = 0; i < ic.getMyList().size(); i++)
					{
						studentList[i] = ic.getMyList().get(i).getMyName() + " - " + ic.getMyList().get(i).getMyId();
					}
					listDisplay = new JList(studentList);
					listScroll.setViewportView(listDisplay);
					idc.saveData();
				} else
				{
					Error delErrorr = new Error("Select a student to delete.");
				}
			}
		});
		btnNewButton.setBounds(251, 33, 139, 52);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Add Student");
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String fName = "";
				String lName = "";
				String id = idField.getText();
				if (nameField.getText().contains(", "))
				{
					lName = nameField.getText().substring(0, nameField.getText().indexOf(", "));
					fName = nameField.getText().substring(nameField.getText().indexOf(", ") + 2);
				}
				if (!idc.validId(idField.getText()) || idField.getText().length() != 6)
				{
					id = idc.completeId(id);
				}
				if (!(fName.equals("") || lName.equals("")
						|| (!idc.validId(idField.getText()) || (idField.getText().length() > 6))))
				{
					if (idAlreadyExists(idc.completeId(idField.getText())))
					{
						Error idError = new Error("ID already exists. Please try again.");
						idField.setText("");
					} else
					{
						ArrayList<Student> temp = new ArrayList<Student>(idc.getMyList());
						temp.add(new Student((lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase()
								+ ", " + fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase()), id));
						idc.setMyList(temp);
						idc.sortList(temp);
						studentList = new String[idc.getMyList().size()];
						for (int i = 0; i < ic.getMyList().size(); i++)
						{
							studentList[i] = ic.getMyList().get(i).getMyName() + " - "
									+ ic.getMyList().get(i).getMyId();
						}
						listDisplay = new JList(studentList);
						listScroll.setViewportView(listDisplay);
						idc.saveData();
						nameField.setText("");
						idField.setText("");
					}
				} else if (!nameField.getText().contains(", ")
						&& (!idc.validId(idc.completeId(idField.getText())) || (idField.getText().length() > 6)))
				{
					Error error = new Error("Invalid Name and 6-Digit ID.");

				} else if (!nameField.getText().contains(", "))
				{
					Error error = new Error("Invalid Name(Last, First).");
				} else if (fName.equals("") && lName.equals(""))
				{
					Error emptyName = new Error("Students need first & last names.");
				} else if (fName.equals(""))

				{
					Error emptyName = new Error("Students need a first name.");
				} else if (lName.equals(""))
				{
					Error emptyLname = new Error("Students need a last name.");
				} else if (!idc.validId(idc.completeId(idField.getText())) || (idField.getText().length() > 6))
				{
					Error errorrr = new Error("Invalid 6-Digit Numeric ID.");
				}

			}
		});
		btnNewButton_1.setBounds(265, 289, 125, 23);

		getContentPane().add(btnNewButton_1);

		lblNewLabel_2 = new JLabel("New Student");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(220, 169, 211, 30);
		getContentPane().add(lblNewLabel_2);

		JLabel lbllastFirst = new JLabel("(Last, First)");
		lbllastFirst.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lbllastFirst.setBounds(251, 226, 55, 14);
		getContentPane().add(lbllastFirst);

		JLabel lblNumericdigits = new JLabel("Numeric. 6-Digits");
		lblNumericdigits.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNumericdigits.setBounds(220, 264, 100, 14);
		getContentPane().add(lblNumericdigits);

		setVisible(true);
		setSize(447, 425);
		setLocationRelativeTo(null);
	}

	public boolean idAlreadyExists(String id)
	{
		for (int i = 0; i < idc.getMyList().size(); i++)
		{
			if (idc.getMyList().get(i).getMyId().equals(id))
			{
				return true;
			}
		}
		return false;
	}
}
