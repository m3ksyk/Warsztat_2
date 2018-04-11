

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

import pl.coderslab.models.Exercise;
import pl.coderslab.models.Solution;
import pl.coderslab.models.User;

public class TaskManager {

	public static void manageTasks(Connection conn) throws SQLException{
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose one of the options:");
		System.out.println("add - to assign a task to a user");
		System.out.println("view - to view user's solutions");
		System.out.println("quit - to quit");
		String inputStr = scan.nextLine();
		while(!inputStr.equals("quit")) {
			if(inputStr.equals("add")) {
				addTask(conn, scan);
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}else if (inputStr.equals("view")) {
				viewUserTasks(conn, scan);
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();						
			}else {
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}
			//catch null point
		}
		scan.close();
		}
		
		public static void addTask(Connection conn, Scanner scan) throws SQLException {
			User.viewAllUsers(conn);
			System.out.println("Assigning a task to a user. Step 1: Enter user id:");
			while (!scan.hasNextInt()) {
				System.out.println("Input not a number. Please type a number:");
	            scan.next();
	        }
			int id = scan.nextInt();
			
			Exercise.viewAllExercises(conn);
			System.out.println("Assigning a task to a user. Step 2: Enter excercice id:");
			while (!scan.hasNextInt()) {
				System.out.println("Input not a number. Please type a number:");
	            scan.next();
	        }
			int id2 = scan.nextInt();			
			Solution solution = new Solution();
			LocalDateTime local = LocalDateTime.now();
			String created = local.toString();
			solution.setCreated(created);
			solution.setExercise_id(id2);
			solution.setUsers_id(id);
			solution.saveToDB(conn);		
			System.out.println("Task " + id2 + " assigned to user " + id);
		}
		
		public static void viewUserTasks(Connection conn, Scanner scan) throws SQLException {
			User.viewAllUsers(conn);
			System.out.println("Choose user whose tasks you wish to view:");
			while (!scan.hasNextInt()) {
				System.out.println("Input not a number. Please type a number:");
	            scan.next();
	        }
			int id = scan.nextInt();

			String sql = "SELECT * FROM solution where users_id=?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {			    	
				int id2 = resultSet.getInt("id");
			    String title = resultSet.getString("title");
			    String description = resultSet.getString("description");
			    System.out.println(id2 + " " + title + " " + description);
			}

		}
		
}
