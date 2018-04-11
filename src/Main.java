
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop_2?useSSL=false",
	            "root", "coderslab")) {
		
			//IN THE CURRENT VERSION BOTH USER AND MANAGER PROGRAM ARE CALLED FROM A SHARED MENU IN MAIN METHOD
			Scanner scan =new Scanner(System.in);
			System.out.println("Press 1 to enter user view");
			System.out.println("Press 2 to enter Manager view");
			System.out.println("Press q to quit");
			String inputStr = scan.nextLine();
			while(!inputStr.equals("q")) {
				if(inputStr.equals("1")) {
					System.out.println("User view");
					UserProgram.userManagementView(conn);
					inputStr = scan.nextLine();
				}else if (inputStr.equals("2")) {
					System.out.println("Manager view");
					Manager.manage(conn);
					inputStr = scan.nextLine();				
				}else {
					System.out.println("Choose one of the options:");
					inputStr = scan.nextLine();
				}
			}
			scan.close();	
					
		} catch (SQLException e) {
			e.printStackTrace();		
		}catch (NullPointerException e) {
			e.printStackTrace();		
		}catch (Exception e) {
			e.printStackTrace();		
		}
	}	
}
//creating table users
//create table users (id int auto_increment primary key, username varchar(255), email  
//varchar(255),password varchar(60), unique(email)) character set utf8;
//alter table users add user_group_id int;
//alter table users add foreign key (user_group_id) references user_group(id);

//creating table user_group
//create table user_group (id int auto_increment primary key, name varchar(255)) character set utf8;

//creating table exercise
//create table exercise(id int auto_increment primary key, title varchar(255), description text) character set utf8;

//creating table solution
//create table solution (id int auto_increment primary key, created datetime, updated datetime, description text, 
//exercise_id int, users_id int, foreign key(exercise_id) references exercise(id),foreign key (users_id) references users(id))
//character set utf8;

