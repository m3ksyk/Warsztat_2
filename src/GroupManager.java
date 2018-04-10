

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.models.Group;

public class GroupManager {
	
	public static void manageGroups(Connection conn) throws SQLException{
		
		Group.viewAllGroups(conn);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose one of the options:");
		System.out.println("add - to add group");
		System.out.println("edit - to edit group");
		System.out.println("delete - to delete group");
		System.out.println("quit - to quit");
		String inputStr = scan.nextLine();
		while(!inputStr.equals("quit")) {
			if(inputStr.equals("add")) {
				addGroup(conn, scan);
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}else if (inputStr.equals("edit")) {
				editGroup(conn, scan);
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}else if (inputStr.equals("delete")) {
				deleteGroup(conn, scan);
				System.out.println("Choose one of the options:");			
			}else {
				System.out.println("Choose one of the options:");
				inputStr = scan.nextLine();
			}
			//catch null pointer!!
			Group.viewAllGroups(conn);
		}
		scan.close();
		}
		
		public static void addGroup(Connection conn, Scanner scan) throws SQLException {
			System.out.println("Adding a new group. Enter group data:");
			Group group = new Group();
			System.out.println("Enter group name: ");
			String name = scan.nextLine();
			group.setName(name);
			group.saveToDB(conn);		
			System.out.println("Group added.");
			Group.viewAllGroups(conn);
		}
		
		public static void editGroup(Connection conn, Scanner scan) throws SQLException {
			System.out.println("Editing  group. Enter  group data:");
			Group group = new Group();
			System.out.println("Enter group name: ");
			String name = scan.nextLine();
			group.setName(name);
			System.out.println("Enter group id: ");
			int id = scan.nextInt();
			group.setId(id);
			group.saveToDB(conn);
			System.out.println("Group edited.");
			Group.viewAllGroups(conn);
		}
		
		public static void deleteGroup(Connection conn, Scanner scan) throws SQLException {
			System.out.println("Deleting group. Enter group id:");
			int id = scan.nextInt();
			Group.loadGroupById(conn, id).delete(conn);
			System.out.println("Group with id: " + id + " deleted");
			Group.viewAllGroups(conn);
		}
}
