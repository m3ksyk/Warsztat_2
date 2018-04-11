import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserProgram {
	public static void userManagementView(Connection conn) throws SQLException {
	Scanner scan = new Scanner(System.in);
	System.out.println("enter your user id:");
	while (!scan.hasNextInt()) {
		System.out.println("Input not a number. Please type a number:");
        scan.next();
    }
	int id = scan.nextInt();
	
	System.out.println("Choose one of the options:");
	System.out.println("add - to add solutions to assigned tasks");
	System.out.println("view - to view your solutions");
	System.out.println("quit - to quit");
	String inputStr = scan.nextLine();
	while(!inputStr.equals("quit")) {
		if(inputStr.equals("add")) {
			addSolution(conn,scan, id);
			System.out.println("Choose one of the options:");
			inputStr = scan.nextLine();
		}else if (inputStr.equals("view")) {
			viewUserSolutions(conn, id);
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
	
	private static void addSolution(Connection conn, Scanner scan, int id) throws SQLException {

		System.out.println("Unsolved user tasks");
		String sql = "SELECT * FROM exercise LEFT JOIN solution ON "
				+ "exercise.id=solution.exercise_id WHERE solution.users_id = ? AND solution.description IS NULL;";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		ArrayList<Integer> list = new ArrayList<>();
		if (resultSet.next()) {			    	
			int id2 = resultSet.getInt("id");
			list.add(id2);
		    String title = resultSet.getString("title");
		    String description = resultSet.getString("description");
		    System.out.println(id2 + " " + title + " " + description);
		}

		System.out.println("Choose the task you wish to add solution to:");
		int exId = scan.nextInt();
		while (!scan.hasNextInt()) {
			System.out.println("Input not a number. Please type a number:");
            scan.next();
        }
		if(!list.contains(exId)) {
			System.out.println("A solution to this task already exists or there is no such task assigned to you");
		}
		System.out.println("Write your solution: ");
		String description = scan.nextLine();		
		LocalDateTime local = LocalDateTime.now();
		String updated = local.toString();
		String sql2 = "UPDATE solution SET updated=?, description=? where id = ?";
		PreparedStatement preparedStatement2;
		preparedStatement2 = conn.prepareStatement(sql2);
		preparedStatement2.setString(1, updated);
		preparedStatement2.setString(2, description);
		preparedStatement2.setInt(3, exId);
		preparedStatement2.executeUpdate();
		
	}
	
	private static void viewUserSolutions(Connection conn, int id) throws SQLException {
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





