package ie.dit;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.WebRowSet;

public class SheetGitClone 
{
	private String src;
	private String dest;
	
	public SheetGitClone(String src, String dest)
	{
		this.src = src;
		this.dest = dest;
	}
	
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.out.println("Usage: java ie.dit.WebcoursesGitClone CSV_FILE DESTINATION_PATH");
			return;
		}
		
		SheetGitClone wgc = new SheetGitClone(args[0], args[1]);
		
		wgc.processFile(new File(args[0]));
		
	}

	private void processFile(File file)
	{
		Submission sub = new Submission();
		try
		{
			String current;
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			boolean first = true;
			
			while ((current = reader.readLine()) != null) 
			{
				if (first)
				{
					first = false;
					continue;					
				}
				sub = new Submission(current);				
				cloneRepo(sub.number, sub.name, sub.gitLink);				
			}
			reader.close();
			System.out.println("Done!");
		} 
		catch (Exception e)
		{
			System.out.println("Error cloning:  " + sub.gitLink + " Student: " + sub.number + " Name: " + sub.name);			
			e.printStackTrace();
		}
		
	}

	private void cloneRepo(String studentNumber, String studentName, String url)
	{
		int i = url.lastIndexOf("/");
		String projectName = url.substring(i + 1, url.length());
		//String destPath = dest + "\\" + studentNumber + " " + studentName + "\\" + projectName;
		String destPath = dest + "\\" + studentName;
		destPath = destPath.trim();
		String[] cmd = {
				"git"
				, "clone"
				, url
				, destPath
		};			
		
		File test = new File(destPath);
		if (test.exists())
		{
			System.out.println("Skipping: " + destPath);
			return;
		}
		
		try
		{
			Process process;
			process = Runtime.getRuntime().exec(cmd,null);
			
			InputStream in = process.getErrorStream();
	        int c;
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
