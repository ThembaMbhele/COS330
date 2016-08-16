import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.security.MessageDigest;

public class AuthenticationSystem
{
	ArrayList<User> users;
	boolean userLoggedIn;
	int level;
	public AuthenticationSystem() throws FileNotFoundException, IOException
	{
		users = new ArrayList<User>();
		userLoggedIn = false;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the secure text file access system");
		while(true)
		{
			System.out.println("Pick a action:\n"
							+ "1 - Register User\n"
							+ "2 - Log in\n"
							+ "3 - Access File\n"
							+ "4 - Log out");
			System.out.print("Enter option: ");
			String userInput = scanner.nextLine();
			
			if(userInput.compareTo("1") == 0)
				registerUser();
			else if(userInput.compareTo("2") == 0)
				logIn();
			else if(userInput.compareTo("3") == 0)
				accessFile();
			else if(userInput.compareTo("4") == 0)
				logOut();
			else
			{
			}
		}
	}
	
	public void registerUser()
	{
		Scanner scanner = new Scanner(System.in);
		
		//get username
		System.out.print("Enter user name: ");
		String username = scanner.nextLine();
		
		//get password
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		
		//get access level
		System.out.print("Enter level of access(1, 2): ");
		int accessLevel = scanner.nextInt();
		
		users.add(new User(username, hashPassword(password), accessLevel));
		System.out.println(users.get(0).getPassword());
		
	}
	
	public void logIn()
	{
		Scanner scanner = new Scanner(System.in);
		
		//get username
		System.out.print("Enter user name: ");
		String username = scanner.nextLine();
		
		//get password
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		
		boolean logInUser = checkCredentials(username, password);
		if(logInUser)
			System.out.println("You have successfully logged In");
	}
	
	public boolean checkCredentials(String username, String password)
	{
		for(int i = 0; i < users.size(); ++i)
		{
			if(users.get(i).getUsername().compareTo(username) == 0 && users.get(i).getPassword().compareTo(hashPassword(password)) == 0)
			{
				userLoggedIn = true;
				level = users.get(i).getAccessLevel();
				break;
			}
		}
		
		return userLoggedIn;
	}
	
	public void accessFile() throws FileNotFoundException, IOException
	{
		if(!userLoggedIn)
		{
			System.out.println("Log in first");
			return;
		}
		System.out.println("Pick a file action:\n"
							+ "1 - Read file\n"
							+ "2 - Read/Write file");
		System.out.print("Enter option: ");
		Scanner scanner = new Scanner(System.in);
		int accessType = scanner.nextInt();
		
		if(accessType == 1)
			System.out.println(readFile());
		
		if(accessType == 2)
		{
			if(level != 2)
			{
				System.out.println("You are not authorized");
				return;
			}
			
			System.out.println("Pick a file action:\n"
							+ "1 - Read file\n"
								+ "2 - Read/Write file");
			System.out.print("Enter option: ");
			int readOrWrite = scanner.nextInt();
			
			if(readOrWrite == 1)
				System.out.println(readFile());
			if(readOrWrite == 2)
			{
				System.out.print("Enter new content: ");
				Scanner s = new Scanner(System.in);
				String newContent = s.nextLine();
				writeFile(newContent);
			}
			
		}
	}
	
	public String readFile() throws FileNotFoundException, IOException
        {
		BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
		String line, s = "";
		while ((line = reader.readLine()) != null)
		s += line; 
		return s;
	}
	
	public void writeFile(String newContent) throws IOException
        {
                BufferedWriter writer = new BufferedWriter( new FileWriter("file.txt",true));
                writer.append(newContent);
                writer.close();
        }  
	
	public String hashPassword(String password) 
	{
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			
			byte byteData[] = messageDigest.digest();
			
			StringBuffer stringBuffer = new StringBuffer();
			for(int i = 0; i < byteData.length; ++i)
				stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			
			return stringBuffer.toString();
		}
		catch(Exception error)
		{
		}
		
		return "";
	}
		
	
	public void logOut()
	{
		userLoggedIn = false;
	}
}