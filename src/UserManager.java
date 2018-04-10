

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.models.User;

public class UserManager {
	
	public static void manageUsers(Connection conn) throws SQLException{
		
		User.viewAllUsers(conn);
        
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose one of the options:");
		System.out.println("add - to add user");
		System.out.println("edit - to edit user");
		System.out.println("delete - to delete user");
		System.out.println("quit - to quit");
		String inputStr = scan.nextLine();
		while(!inputStr.equals("quit")) {
			if(inputStr.equals("add")) {
				addUser(conn, scan);
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}else if (inputStr.equals("edit")) {
				editUser(conn, scan);
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}else if (inputStr.equals("delete")) {
				deleteUser(conn, scan);
				System.out.println("Choose one of the options:");			
			}else {
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}
			//catch null pointer!!
			User.viewAllUsers(conn);
		}
		scan.close();
	}
	
	public static void addUser(Connection conn, Scanner scan) throws SQLException {
		System.out.println("Adding a new user. Enter user data:");
		User user = new User();
		System.out.println("Enter user name: ");
		String username = scan.nextLine();
		user.setUsername(username);
		System.out.println("Enter unique user email address: ");
		String email = scan.nextLine();
		user.setEmail(email);
		System.out.println("Set user password: ");
		String password = scan.nextLine();
		user.setPassword(password);
		System.out.println("Enter user group id: ");
		int groupId = scan.nextInt();
		user.setUser_group_id(groupId);
		user.saveToDB(conn);		
		System.out.println("User added.");
		User.viewAllUsers(conn);
	}
	
	public static void editUser(Connection conn, Scanner scan) throws SQLException {
		System.out.println("Editing user. Enter user data:");
		User user = new User();
		System.out.println("Enter user name: ");
		String username = scan.nextLine();
		user.setUsername(username);
		System.out.println("Enter unique user email address: ");
		String email = scan.nextLine();
		user.setEmail(email);
		System.out.println("Set user password: ");
		String password = scan.nextLine();
		user.setPassword(password);
		System.out.println("Enter user group id: ");
		int groupId = scan.nextInt();
		user.setUser_group_id(groupId);
		System.out.println("Enter user id: ");
		int id = scan.nextInt();
		user.setId(id);
		user.saveToDB(conn);
		System.out.println("User edited.");
		User.viewAllUsers(conn);
	}
	public static void deleteUser(Connection conn, Scanner scan) throws SQLException {
		System.out.println("Deleting user. Enter user id:");
		int id = scan.nextInt();
		User.loadUserById(conn, id).delete(conn);
		System.out.println("User with id: " + id + " deleted");
		User.viewAllUsers(conn);
	}
}
