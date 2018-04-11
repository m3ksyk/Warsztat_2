

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Manager {
	
	public static void manage(Connection conn) throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Press 1 to manage users");
		System.out.println("Press 2 to manage groups");
		System.out.println("Press 3 to manage exercises");
		System.out.println("Press 4 to manage user tasks");
		System.out.println("Press q to quit");
		String inputStr = scan.nextLine();
		while(!inputStr.equals("q")) {
			if(inputStr.equals("1")) {
				System.out.println("User management");
				UserManager.manageUsers(conn);
				inputStr = scan.nextLine();
			}else if (inputStr.equals("2")) {
				System.out.println("Group management");
				GroupManager.manageGroups(conn);
				inputStr = scan.nextLine();
			}else if (inputStr.equals("3")) {
				System.out.println("Exercise management");
				ExerciseManager.manageExercises(conn);
				inputStr = scan.nextLine();
			}else if (inputStr.equals("4")) {
				System.out.println("Task management");
				TaskManager.manageTasks(conn);
				inputStr = scan.nextLine();
			}else {
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}
			
			
		}
		scan.close();
		}
}