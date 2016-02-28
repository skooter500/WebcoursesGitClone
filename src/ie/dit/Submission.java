package ie.dit;

public class Submission {
	public String name;
	public String number;
	public String gitLink;
	
	public Submission(String line)
	{
		String[] parts = line.split(",");
		name = parts[1];
		number = parts[2];
		gitLink = parts[3];
	}
	
	public Submission()
	{
	}
}
