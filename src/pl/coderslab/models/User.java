package pl.coderslab.models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


//ma wspolpracowac z baza workshop_2 (znalexc jej sciezke)
public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	private int user_group_id;
	
	public User(String username, String email, String password, int user_group_id) {
	    this.username = username;
	    this.email = email;
	    this.setPassword(password);
	    this.user_group_id = user_group_id;
	}
	
	public User() {
		
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password2) {
		 this.password = BCrypt.hashpw(password2, BCrypt.gensalt());
		
	}
	
	public int getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(int user_group_id) {
		this.user_group_id = user_group_id;
	}

	public void saveToDB(Connection conn) throws SQLException {
		  if (this.id == 0) {
		      String sql = "INSERT INTO users(username, email, password, user_group_id) VALUES (?, ?, ?, ?)";
		      String generatedColumns[] = { "ID" };
		      PreparedStatement preparedStatement;
		      preparedStatement = conn.prepareStatement(sql, generatedColumns);
		      preparedStatement.setString(1, this.username);
		      preparedStatement.setString(2, this.email);
		      preparedStatement.setString(3, this.password);
		      preparedStatement.setInt(4, this.user_group_id);
		      preparedStatement.executeUpdate();
		      ResultSet rs = preparedStatement.getGeneratedKeys();
		      if (rs.next()) {
		    	  this.id = rs.getInt(1);
		      }
		  } else {
			  String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? where id = ?";
			  PreparedStatement preparedStatement;
			  preparedStatement = conn.prepareStatement(sql);
			  preparedStatement.setString(1, this.username);
			  preparedStatement.setString(2, this.email);
			  preparedStatement.setString(3, this.password);
			  preparedStatement.setInt(4, this.user_group_id);
			  preparedStatement.setInt(5, this.id);
			  preparedStatement.executeUpdate();
		  }
	}
	static public User loadUserById(Connection conn, int id) throws SQLException {
	    String sql = "SELECT * FROM users where id=?";
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(sql);
	    preparedStatement.setInt(1, id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    if (resultSet.next()) {
	        User loadedUser = new User();
	        loadedUser.id = resultSet.getInt("id");
	        loadedUser.username = resultSet.getString("username");
	        loadedUser.password = resultSet.getString("password");
	        loadedUser.email = resultSet.getString("email");
	        loadedUser.user_group_id = resultSet.getInt("user_group_id");
	        return loadedUser;
	   }
	   return null;
	}
	static public User[] loadAllUsers(Connection conn) throws SQLException {
	    ArrayList<User> users = new ArrayList<User>();
	    String sql = "SELECT * FROM users"; 
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(sql);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
	        User loadedUser = new User();
	        loadedUser.id = resultSet.getInt("id");
	        loadedUser.username = resultSet.getString("username");
	        loadedUser.password = resultSet.getString("password");
	        loadedUser.email = resultSet.getString("email");
	        loadedUser.user_group_id = resultSet.getInt("user_group_id");
	        users.add(loadedUser);
	    }
	    User[] uArray = new User[users.size()]; 
	    uArray = users.toArray(uArray);
	    return uArray;
	}
	public void delete(Connection conn) throws SQLException {
	    if (this.id != 0) {
	        String sql = "DELETE FROM users WHERE id= ?";
	        PreparedStatement preparedStatement;
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;
	    }
	}
	static public User[] loadAllByGroupId(Connection conn, int id) throws SQLException {
	    ArrayList<User> users = new ArrayList<User>();
	    String sql = "SELECT * FROM users WHERE user_group_id=?"; 
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(sql);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
	        User loadedUser = new User();
	        loadedUser.id = resultSet.getInt("id");
	        loadedUser.username = resultSet.getString("username");
	        loadedUser.password = resultSet.getString("password");
	        loadedUser.email = resultSet.getString("email");
	        users.add(loadedUser);
	    }
	    User[] uArray = new User[users.size()]; 
	    uArray = users.toArray(uArray);
	    return uArray;
	}
	
	public static void viewAllUsers(Connection conn) throws SQLException {
		User[] users = User.loadAllUsers(conn);
	    for (User user : users) {
	        System.out.println(user.toString());
	    }
    
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", user_group_id=" + user_group_id + "]";
	}
		
}

