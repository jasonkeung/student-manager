import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;

public class SearchWindow extends JFrame
{
	private JList listDisplay;
	private JScrollPane listScroll;
	private String[] studentList;
	private IdCompleter idc;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	public SearchWindow(IdCompleter ic)
	{
		idc = ic;
		setTitle("Student Search");
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
		btnNewButton.setBounds(258, 152, 125, 52);
		getContentPane().add(btnNewButton);

		lblNewLabel = new JLabel("Students in " + idc.getMyPath().substring(0, idc.getMyPath().indexOf(".txt")));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(220, 11, 198, 39);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("matching " + "\"" + idc.getSearchString() + "\"");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(220, 50, 198, 52);
		getContentPane().add(lblNewLabel_1);

		setVisible(true);
		setSize(447, 425);
		setLocationRelativeTo(null);
	}
}
