public class User
{
	private String username;
	private String password;
	private int accessLevel;
	
	public User(String username, String password, int accessLevel)
	{
		this.username = username;
		this.password = password;
		this.accessLevel = accessLevel;
	}
	
	public int getAccessLevel()
	{
		return accessLevel;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
}