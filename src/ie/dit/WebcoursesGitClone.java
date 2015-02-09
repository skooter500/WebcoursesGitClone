package ie.dit;


// What does import mean?
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.InputStream;

public class WebcoursesGitClone 
{
	private String src;
	private String dest;
	
	// What is this an example of?
	public WebcoursesGitClone(String src, String dest)
	{
		this.src = src;
		this.dest = dest;
	}
	
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.out.println("Usage: java ie.dit.WebcoursesGitClone SOURCE_PATH DESTINATION_PATH");
			return;
		}
		
		// Where do the values come from for the args array?
		WebcoursesGitClone wgc = new WebcoursesGitClone(args[0], args[1]);		
		wgc.doWork();
		
	}

	private void doWork()
	{
		
		// What does the File class in Java do?
		// What package is it in?
		// What is an anonymous inner class?
		File[] files = new File(src).listFiles(new FileFilter() // What does the FileFilter class do?
		{
			// What object is this method on?
			public boolean accept(File f)
			{
				// toLowerCase and endsWith are methods on the String class
				// What parameters do they take and what do they return?				
				return (f.getName().toLowerCase().endsWith(".txt"));
				
				// How do you do the following to a String:
				// - Extract a substring
				// - Find a String in a String
				// Replace a String in a String
				// What is a StringBuilder and why is it useful?
			}
		});
		
		// Why would this ever happen? WHy do we need to check for null?
		if (files == null) // What does null mean?
		{
			System.out.println("No files found");
			return;
		}
		// What does the : mean in this for loop?
		for (File file : files) 
		{
			// Why would the following code ever return false?
		    if (file.isFile()) 
		    {
		        processFile(file);
		    }
		}				
	}

	private void processFile(File file)
	{
		String studentNumber = "";
		String studentName = "";
		String url = "";
		
		// What does try mean?
		try
		{
			// What does indexOf do?
			int start_ = file.getName().indexOf("_");
			// Why is the second parameter start_ + 1?
			int end_ = file.getName().indexOf("_", start_ + 1);
			
			// Why is the first parameter start_ + 1?
			studentNumber = file.getName().substring(start_ + 1, end_);
			String current;
			
			// What is a BufferedReader used for?
			// Why do we pass a FileReader as a parameter?
			// Why do we pass a File object as a parameter to the FileReader?
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// What does this line do??
			// WHat does the readLIne method return?
			while ((current = reader.readLine()) != null) 
			{
				// What does contains return and what does it do?
				if (current.contains("Name: "))
				{
					int i = current.indexOf("(");
					// What do the parameters to substring do? 
					// What happens if you passed a negative number or 
					// A number greater than the number of characters in the STring as parameters
					studentName = current.substring(6, i - 1);
				}
				if (current.contains("http"))
				{
					// What the hell does the parameter to replaceAll mean??
					// I want a detailed answer!
					current = current.replaceAll("\\<[^>]*>","");
					int i = current.indexOf("http");
					url = current.substring(i, current.length());
					System.out.println(studentNumber + "\t" + current);
					cloneRepo(studentNumber, studentName, url);
					break;
				}
			}
			reader.close(); // Why do we do this?
			studentNumber = "";
			studentName = "";
			url = "";
		} 
		// What does try/catch mean?
		// Whats the significance of catching the Exception type here?
		catch (Exception e) 
		{			
			System.out.println("Error cloning:  " + url + " Student: " + studentNumber + " Name: " + studentName);
			// What will this line do?
			e.printStackTrace();
		}		
	}

	
	private void cloneRepo(String studentNumber, String studentName, String url)
	{
		// WHat do these next two lines do?
		int i = url.lastIndexOf("/");
		String projectName = url.substring(i + 1, url.length());
		
		// What will destPath look like after this line executes		
		String destPath = dest + File.separator + studentNumber + " " + studentName + File.separator + projectName;
		// What is the type File.separator?
		// What will its value on Windows/Linux/Android/MacOS be?
		
		// What is happening here?
		String[] cmd = {
				"git"
				, "clone"
				, url
				, destPath
		};			

		// What do the next few lines mean?
		File test = new File(destPath);
		if (test.exists())
		{
			System.out.println("Skipping: " + destPath);
			return;
		}
		
		try
		{
			// What is a Process object?
			// What package is Process in?
			Process process;
			
			// What does this line do?
			// What is the second parameter? Why is it null?
			process = Runtime.getRuntime().exec(cmd,null);
			
			// What is an InputStream?
			InputStream in = process.getErrorStream();
	        int c;
	        // What does in.read do?
	        while ((c = in.read()) != -1) {
	            System.out.print((char)c);
	        }
	        in.close();
			
			/*InputStream stream = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = reader.readLine()) != null)
			{
				System.out.println(line);
			}
			reader.close();
			*/
			while(process.isAlive())
			{
				// What type of exception can this throw?
				// What does the code do?
				Thread.sleep(1000);
			}
			
		} 
		catch (Exception e)
		{
			System.out.println("Could not spawn git process");
			e.printStackTrace();
			return;
		}		
	}
}
