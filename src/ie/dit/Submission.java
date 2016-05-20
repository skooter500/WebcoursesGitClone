package ie.dit;

public class Submission {
	public String name;
	public String number;
	public String gitLink;
	
	String element(String[] array, int i)
	{
		return (i < array.length) ? array[i] : "";	
	}
	
	public Submission(String line)
	{
		String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		name = element(parts,1) 
				+ " " + element(parts,4) + " " + element(parts,5) 
				+ " " + element(parts,8) + " " + element(parts,9)
				+ " " + element(parts,12) + " " + element(parts,13)
				+ " " + element(parts,16) + " " + element(parts,17)
				+ " " + element(parts,20) + " " + element(parts,21);
		name = name.trim();
		number = "";
		gitLink = element(parts, 2);
	}
	
	public Submission()
	{
	}
}
