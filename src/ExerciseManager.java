

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.models.Exercise;

public class ExerciseManager {
	public static void manageExercises(Connection conn) throws SQLException{
		
		Exercise.viewAllExercises(conn);
        
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose one of the options:");
		System.out.println("add - to add exercise");
		System.out.println("edit - to edit exercise");
		System.out.println("delete - to delete exercise");
		System.out.println("quit - to quit");
		String inputStr = scan.nextLine();
		while(!inputStr.equals("quit")) {
			if(inputStr.equals("add")) {
				addExercise(conn, scan);
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}else if (inputStr.equals("edit")) {
				editExercise(conn, scan);
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}else if (inputStr.equals("delete")) {
				deleteExercise(conn, scan);
				System.out.println("Choose one of the options:");			
			}else {
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}
			//catch null pointer!!
			Exercise.viewAllExercises(conn);
		}
		scan.close();
		}
		
		public static void addExercise(Connection conn, Scanner scan) throws SQLException {
			System.out.println("Adding a new exercise. Enter exercise data:");
			Exercise exercise = new Exercise();
			System.out.println("Enter exercise title: ");
			String title = scan.nextLine();
			exercise.setTitle(title);
			System.out.println("Enter exercise description: ");
			String description = scan.nextLine();
			exercise.setDescription(description);
			exercise.saveToDB(conn);		
			System.out.println("Exercise added.");
			Exercise.viewAllExercises(conn);
		}
		
		public static void editExercise(Connection conn, Scanner scan) throws SQLException {
			System.out.println("Editing exercise. Enter exercise data:");
			Exercise exercise = new Exercise();
			System.out.println("Enter exercise title: ");
			String title = scan.nextLine();
			exercise.setTitle(title);
			System.out.println("Enter exercise description: ");
			String description = scan.nextLine();
			exercise.setDescription(description);
			System.out.println("Enter exercise id: ");
			while (!scan.hasNextInt()) {
				System.out.println("Input not a number. Please type a number:");
	            scan.next();
	        }
			int id = scan.nextInt();
			exercise.setId(id);
			exercise.saveToDB(conn);
			System.out.println("Exercise edited.");
			Exercise.viewAllExercises(conn);
		}
		public static void deleteExercise(Connection conn, Scanner scan) throws SQLException {
			System.out.println("Deleting exercise. Enter exercise id:");
			while (!scan.hasNextInt()) {
				System.out.println("Input not a number. Please type a number:");
	            scan.next();
	        }
			int id = scan.nextInt();
			Exercise.loadExerciseById(conn, id).delete(conn);
			System.out.println("Exercise with id: " + id + " deleted");
			Exercise.viewAllExercises(conn);
		}
}
