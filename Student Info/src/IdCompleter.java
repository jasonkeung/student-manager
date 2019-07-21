import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IdCompleter
{
	private ArrayList<Student> myList;
	private String myPath;
	private String searchString;

	public IdCompleter(String path)
	{
		myPath = path;
		myList = new ArrayList<Student>();
		try
		{
			File file = new File(myPath);
			Scanner in = new Scanner(file);
			while (in.hasNextLine())
			{
				String line = in.nextLine();
				String name = line.substring(0, line.indexOf("-"));
				String id = line.substring(line.indexOf("-") + 1);
				myList.add(new Student(name, id));
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	public IdCompleter(String path, String search)
	{
		myPath = path;
		searchString = search;
		myList = new ArrayList<Student>();
		File file = new File(myPath);
		Scanner in;
		try
		{
			in = new Scanner(file);
			while (in.hasNextLine())
			{
				String line = in.nextLine();
				String name = line.substring(0, line.indexOf("-"));
				String id = line.substring(line.indexOf("-") + 1);
				myList.add(new Student(name, id));
			}
			for(int i = 0; i < myList.size(); i++)
			{
				if(!myList.get(i).getMyName().toLowerCase().contains(search) && !myList.get(i).getMyId().contains(search))
				{
					myList.remove(i);
					i--;
				}
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		
	}

	
	public void saveData()
	{
		try
		{
			FileWriter fw = new FileWriter(myPath);
			PrintWriter writer = new PrintWriter(fw);
			sortList(myList);
			for (int i = 0; i < myList.size(); i++)
			{
				writer.println(myList.get(i).getMyName() + "-" + myList.get(i).getMyId());
			}
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sortList(ArrayList<Student> list)
	{
		int min;

		for (int outer = 0; outer < list.size() - 1; outer++)
		{
			min = outer;
			for (int inner = outer + 1; inner < list.size(); inner++)
			{
				if (list.get(inner).getMyName().compareToIgnoreCase(list.get(min).getMyName()) < 0)
					min = inner;

			}
			String temp = list.get(outer).getMyName();
			list.get(outer).setMyName(list.get(min).getMyName());
			list.get(min).setMyName(temp);
		}

	}

	public ArrayList<Student> getMyList()
	{
		return myList;
	}


	public boolean validId(String potential)
	{
		
		for(int i = 0; i < potential.length(); i++)
		{
			boolean isNum = false;
			for(int k = 0; k < 10; k++)
			{
				if(potential.substring(i,i+1).equalsIgnoreCase(Integer.toString(k)))
				{
					isNum = true;
				}
			}
			if(!isNum)
			{
				return false;
			}
		}
		return true;
	}

	public void setMyList(ArrayList<Student> myList)
	{
		this.myList = myList;
	}

	public String completeId(String id)
	{
		String fixed = id;
		while (fixed.length() < 6)
		{
			fixed = "0" + fixed;
		}
		while (fixed.length() > 6)
		{
			fixed = fixed.substring(1);
		}
		return fixed;
	}
	public String getMyPath()
	{
		return myPath;
	}
	public String getSearchString()
	{
		return searchString;
	}
}
