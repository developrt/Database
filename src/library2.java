import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class library2 {
	static Connection conn = null;
		/**
		 * @param args
		 */
		public static void main(String[] args) {

			/* book table */
			String book_id, title;
			/* borrower table */
			String card_no, fname, lname, address, phone;
			/* library_branch table */
			String branch_id, branch_name;
			/* book_authors */
			String author_name;
			String tot_num;
			String avl_num;
			
			int num_of_branches = 5;
			int linect = 0;
			
			int selection = 10;
			
			
			
			while (selection != 0){
				
				Scanner in = new Scanner(System.in);
				System.out.println("\n\n\t\t\t\t Welcome to Library Mangement System"
						+ "\n\n\t Choose on of the following options press the corresponding number and hit enter "
						+ "\n\n\t 1 Search for a book"
						+ "\t\t\t\t 2 Book Check-Out or Check-In"
						+ "\n\n\t 3 Register a new Borrower"
						+ "\t\t\t 4 To Enter new book in the system"
						+ "\n\n\t 5 Modify copies of existing book"
						+ "\t\t 6 To search or Modify borrower information"
						+ "\n\n\t\t\t\t\t\t 0 To Exit"
						+ "");
				
				System.out.println("\nEnter Selection:");
				selection = in.nextInt();
				
				if (selection == 1){
				try {
				// Create a connection to the local MySQL server, with the "company" database selected.
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
		
				// Create a SQL statement object and execute the query.
				Statement stmt = conn.createStatement();
			
				stmt.executeQuery("use library;");
				
				Scanner in2 = new Scanner(System.in);
				
				//System.out.println("Enter book id: ");
				
				// String id = in.next();
				System.out.println("Enter book_id (Enter null if unknown): ");
				book_id = in.next();
				
				System.out.println("Enter title (Enter null if unknown): ");
				title = in2.nextLine();
			
				System.out.println("Enter Author name (Enter null if unknown): ");
				author_name = in2.nextLine();
				
				// System.out.println(title);
				
				//String query = "SELECT book_id, branch_id, no_of_copies FROM book NATURAL JOIN book_copies WHERE title LIKE '%" +title +"%'";
				
				// String query = "SELECT book_id, branch_id, no_of_copies FROM book NATURAL JOIN book_copies WHERE title LIKE '%" +title +"%' OR book_id ='" + book_id + "' OR author_name LIKE '%" + author_name + "%';";
				
				String query  = "SELECT b.book_id, b.title, bc.branch_id, SUM(bc.No_of_copies) as total_num, SUM(bc.No_of_copies) - (SELECT COUNT(*) FROM BOOK_LOANS as bl WHERE b.Book_id = bl.Book_id AND bc.branch_id = bl.branch_id) as available_num FROM BOOK as b INNER JOIN BOOK_AUTHORS as ba ON b.book_id = ba.book_id INNER JOIN BOOK_COPIES as bc ON b.book_id = bc.book_id WHERE b.book_id= '"
						+ book_id + "' OR title LIKE '%"
								+ title + "%' OR author_name LIKE '%"
										+ author_name + "%' GROUP BY bc.branch_id, b.book_id;";
				
				ResultSet rs = stmt.executeQuery(query);
				//System.out.println("%15s", "Book_id","%50s", "Title","Branch_id","Tot_Copies","Avl_Copies" );
				System.out.println("\n");
				
				System.out.format("%10s", "Book_id");
				// System.out.print("\t");
				System.out.format("%50s", "Title");
			//	System.out.print("\t");
				System.out.format("%5s", "  Branch_id");
			//	System.out.print("\t   ");
				System.out.format("%5s", "  Tot_num");
				// System.out.print("\t   ");
				System.out.format("%5s", "  Avl_num");
				System.out.println("\n");
				
				// Iterate through the result set.
				while (rs.next()) {
					// Keep track of the line/tuple count
					linect++;
					
					book_id = rs.getString("book_id");
					title = rs.getString("title");
					branch_id = rs.getString("branch_id");
					tot_num = rs.getString("total_num");
					avl_num = rs.getString("available_num");
					
	
					System.out.format("%10s", book_id);
					// System.out.print("\t");
					System.out.format("%50s", title);
				//	System.out.print("\t");
					System.out.format("%9s", branch_id);
				//	System.out.print("\t   ");
					System.out.format("%7s", tot_num);
					// System.out.print("\t   ");
					System.out.format("%7s", avl_num);
					System.out.println();
					
				} // End while(rs.next())
	
				// Always close the recordset and connection.
				rs.close();
				conn.close();
				System.out.println("\n***Search Completed Successully!");
			} 
			catch(SQLException ex) {
				System.out.println("Error in connection: " + ex.getMessage());
			}
		}
				
				if (selection == 2){
					try{
						System.out.println("Enter 1 for Check Out and 2 for Check In: ");
						Scanner in4 = new Scanner(System.in);
						int checkopt = in.nextInt();
						in.nextLine();
						
						
						// Create a connection to the local MySQL server, with the "company" database selected.
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
				
						// Create a SQL statement object and execute the query.
						Statement stmt = conn.createStatement();
					
						stmt.executeQuery("use library;");
						
					
						
						if (checkopt == 1){
							
							System.out.println("Enter Card No. of the Borrower: ");
							
							card_no = in4.next();
						
						String query = "SELECT COUNT(*) FROM BOOK_LOANS WHERE card_no='" + card_no + "';";
						// System.out.println("Success");
						
						ResultSet rs1 = stmt.executeQuery(query);
						int count = 0;
						while (rs1.next()){
						count = rs1.getInt("COUNT(*)");
						// System.out.println("" +count);
						}
						
						if (count < 3){
						System.out.println("Enter book id: ");
						book_id = in.next();
						System.out.println("Enter branch id: ");
						branch_id = in.next();
						
						
						query = "SELECT SUM(bc.No_of_copies) as total_num, "
								+ "SUM(bc.No_of_copies) - (SELECT COUNT(*) FROM BOOK_LOANS as bl WHERE b.Book_id = bl.Book_id AND bc.branch_id = bl.branch_id) as available_num "
								+ "FROM BOOK as b INNER JOIN BOOK_AUTHORS as ba ON b.book_id = ba.book_id INNER JOIN BOOK_COPIES as bc ON b.book_id = bc.book_id "
								+ "WHERE b.book_id='" + book_id + "' AND bc.branch_id='" + branch_id + "';";
						
						
						int tot = 0, avl = 0;
						rs1 = stmt.executeQuery(query);
						while (rs1.next()){
							tot = rs1.getInt("total_num");
							avl = rs1.getInt("available_num");
						}
						
						if(avl != 0 && avl > 0){
						
						query = "INSERT INTO book_loans VALUES ('" + book_id + "','" + branch_id + "','" + card_no + "',NOW(),NOW() + INTERVAL 14 DAY)";
					
						stmt.executeUpdate(query);
						System.out.println("***Book Checked out Successfully!");
						}
						
						else{
							System.out.println("No more book Copies available at this branch, please use Search tool to find availability information and try with a different branch");
						}
						
						}
						
						else
							System.out.println("Borrower Exceeded limit of three books, please try checking out some books and try again");
					
						}
					
						if (checkopt == 2){
							System.out.println("Enter Card No. of the Borrower (Enter null if unknown): ");
							
							card_no = in4.next();
							
							System.out.println("Enter First name of the Borrower (Enter null if unknown): ");
							
							fname = in4.next();
							System.out.println("Enter Last name of the Borrower (Enter null if unknown): ");
							lname = in4.next();
							
							System.out.println("Enter book id for check in (Enter null if unknown): ");
							book_id = in4.next();
							
							String query = "SELECT br.card_no, br.fname, br.lname, bl.book_id, bl.branch_id, bl.date_out, bl.due_date "
									+ "FROM book_copies bc NATURAL JOIN book_loans bl NATURAL JOIN borrower br "
									+ "WHERE br.card_no='" + card_no + "' OR bl.book_id='" + book_id + "' OR br.fname LIKE '%" + fname + "%' OR br.lname LIKE '%" + lname + "%';";
							
							ResultSet rs = stmt.executeQuery(query);
							
							System.out.println("Please find the matching results for entered information (card_no, Fname, Lname, Book_id, Branch_id, date_out, due_date in order).");
							String date_out = null, due_date = null;
							branch_id = null;
							
							System.out.println();
							
							while (rs.next()){
								card_no = rs.getString("card_no");
								fname = rs.getString("fname");
								lname = rs.getString("lname");
								book_id = rs.getString("book_id");
								branch_id = rs.getString("branch_id");
								date_out = rs.getString("date_out");
								due_date = rs.getString("due_date");
								System.out.println();
								
								System.out.print(card_no);
								System.out.print("\t   ");
								System.out.print(fname);
								System.out.print("\t   ");
								System.out.print(lname);
								System.out.print("\t   ");
								System.out.print(book_id);
								System.out.print("\t   ");
								System.out.print(branch_id);
								System.out.print("\t   ");
								System.out.print(date_out);
								System.out.print("\t   ");
								System.out.print(due_date);
								System.out.print("\t   ");
	
							}
							System.out.println("\n\nSelect a card_no and book_id for checkout");
							System.out.println("\nEnter card number: ");
							card_no = in4.next();
							System.out.println("Enter book_id: ");
							book_id = in4.next();
							System.out.println("Enter branch id: ");
							branch_id = in4.next();
							
							
							query = "DELETE FROM book_loans WHERE card_no='" + card_no + "' AND book_id='" + book_id + "' AND branch_id='" + branch_id + "';";
							
							stmt.executeUpdate(query);
							System.out.println("***Book checked in successfully!");
								
						}
					}
					
					catch(SQLException ex2){
						System.out.println("Error in records: " + ex2.getMessage());
					}
				}
				
				if (selection == 3){
					try{
						// Create a connection to the local MySQL server, with the "company" database selected.
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
				
						// Create a SQL statement object and execute the query.
						Statement stmt = conn.createStatement();
					
						stmt.executeQuery("use library;");
						
						Scanner in3 = new Scanner(System.in);
						
						//System.out.println("Assign a new 10 digit card number for the Borrower: ");
						//card_no = in3.next();
						
						System.out.println("Fname (Cannot be null and no special characters allowed): ");
						fname =  in3.next();
						
						System.out.println("Lname (Cannot be null and no special characters allowed): ");
						lname = in3.next();
						
						
						System.out.println("Enter address in one line (Cannot be null): ");
						Scanner line = new Scanner(System.in);
						address = line.nextLine();
						
					
						
						
						String query = "SELECT fname,lname,address FROM borrower WHERE fname='"
								+ fname + "' AND lname='" + lname + "' AND address='" + address + "';";
						
						ResultSet rs = stmt.executeQuery(query);
						
						
						if (rs.next()){
							System.out.println("User already exists, please try again");
						}
						else{
							
							System.out.println("Enter Phone number (null if unknown):");
							phone = line.nextLine();
							
							query = "SELECT MAX(card_no) FROM borrower";
							rs = stmt.executeQuery(query);
							int max = 0;
							while (rs.next()){
								max = Integer.parseInt(rs.getString("MAX(card_no)"));
							}
							max = max +1;
							
							card_no = Integer.toString(max);
							query = "INSERT INTO borrower VALUES ('" + card_no + "','" + fname + "','" + lname + "','" + address + "','" + phone + "');";
						
							stmt.executeUpdate(query);
							
							System.out.println("\n***User information entered into the database successfully");
						}
						
						
						
					}
					catch(SQLException ex1){
						System.out.println("Error in records: " + ex1.getMessage());
					}
				}
			
			if (selection == 4){
				
				try{
					// Create a connection to the local MySQL server, with the "company" database selected.
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
			
					// Create a SQL statement object and execute the query.
					Statement stmt = conn.createStatement();
				
					stmt.executeQuery("use library;");
					
					Scanner in4 = new Scanner(System.in);
					
					System.out.println("\nEnter Book Title");
					
					title = in4.nextLine();
					System.out.println("Enter total number of authors for the book");
					
					int aCount = in4.nextInt();
					in4.nextLine();
					//if (aCount > 1){
						String[] auth = new String[aCount]; 
						
						for (int i=0;i<aCount;i++){
							System.out.println("\nEnter Name of Author " +(i+1) + ":");
							auth[i] = in4.nextLine();

						}
						int [] brcopies = new int[num_of_branches];
						
						for(int i=0;i<num_of_branches;i++){
							System.out.println("Enter the number of copies of this book in branch " + (i+1) + ":");
							brcopies[i] = in4.nextInt();
							in4.nextLine();
						}
						
						
						// }
						// System.out.println("Enter Author name: ");
						// author_name = in4.nextLine();
					
					
						
						String query = "SELECT MAX(book_id) FROM book";
					
					ResultSet rs = stmt.executeQuery(query);
					int max = 0;
					while(rs.next()){
						max = Integer.parseInt(rs.getString("MAX(book_id)"));
					}
					
					max = max + 1;
					
					book_id = Integer.toString(max);
					
					query = "INSERT INTO book VALUES ('"
							+ book_id + "','" + title + "');";
					
					stmt.executeUpdate(query);
					
					for (int i=0;i<aCount;i++){
						query = "INSERT INTO book_authors VALUES ('"
								+ book_id + "','" + auth[i] + "');";
						
						stmt.executeUpdate(query);
						
					}
					
					for (int i=0;i<num_of_branches;i++){
						query = "INSERT INTO book_copies VALUES ('"
								+ book_id + "','" + (i+1) + "','" + brcopies[i] + "');";
						stmt.executeUpdate(query);
					}
										
					
					System.out.println("\nBook added successfully");
					
					
				}
				
				catch (SQLException e){
					System.out.println("Error in records: " +e.getMessage());
				}
			}
			
			if (selection ==5){
				try{
					// Create a connection to the local MySQL server, with the "company" database selected.
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
			
					// Create a SQL statement object and execute the query.
					Statement stmt = conn.createStatement();
				
					stmt.executeQuery("use library;");
					
					Scanner in5 = new Scanner(System.in);
					
					System.out.println("\nEnter Book_id");
					book_id = in5.next();
					System.out.println("\nEnter Branch_id");
					branch_id = in5.next();
					System.out.println("Enter new number of copies");
					int new_cop = in5.nextInt();
					in5.nextLine();
					
					String query = "UPDATE book_copies SET no_of_copies='" + new_cop + "' WHERE book_id='" + book_id + "' AND branch_id='" + branch_id +"';";
					
					stmt.executeUpdate(query);
					
					System.out.println("\nBook copies updated succesfully");
					
					
				}
				catch (SQLException e){
					System.out.println("Error in records: " +e.getMessage());
				}
			}
			
			if(selection == 6){
				
					try{
						System.out.println("Enter 1 for Search or 2 To Modify borrower: ");
						Scanner in6 = new Scanner(System.in);
						int checkopt = in6.nextInt();
						in6.nextLine();
						
						
						// Create a connection to the local MySQL server, with the "company" database selected.
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
				
						// Create a SQL statement object and execute the query.
						Statement stmt = conn.createStatement();
					
						stmt.executeQuery("use library;");
						
						String query;
						if (checkopt == 1){
							System.out.println("Enter the card number (Enter null if unknown):");
							card_no = in6.next();
							System.out.println("Enter First name (Enter null if unknown):");
							fname = in6.next();
							System.out.println("Enter Last name (Enter null if unknown):");
							lname = in6.next();
							
							query = "SELECT br.card_no, br.fname, br.lname, br.address FROM borrower br WHERE br.card_no='" + card_no + "' OR br.fname LIKE '%" + fname + "%' OR br.lname LIKE '%" + lname + "%';";
							
							ResultSet rs = stmt.executeQuery(query);
							
							System.out.println("\n\nPlease find information matching with the details entered below");
							
							System.out.println("Find card_no, fname, lname, address in order");
							
							System.out.println("\n");
							
							while(rs.next()){
								card_no = rs.getString("card_no");
								System.out.print(card_no);
								fname = rs.getString("fname");
								System.out.print("\t\t" +fname);
								lname = rs.getString("lname");
								System.out.print("\t\t" +lname);
								address = rs.getString("address");
								System.out.print("\t\t" +address);
							
							}
						}
					if (checkopt == 2){
						System.out.println("Enter card number of the borrower:");
						card_no = in6.next();
						System.out.println("\n\nWhat information would you like to modify(only address and phone number modifications are allowed):");
						System.out.println("\nEnter 1 for address and 2 for phone no:");
						int subcheck = in6.nextInt();
						in6.nextLine();
						
						// int yn = 0;
						
						if (subcheck == 1){
							System.out.println("Enter the new address in one line:");
							address = in6.nextLine();
							//System.out.println("New Address:" +address + "\tcard_no:" +card_no);
							query = "UPDATE borrower SET address='" + address +"' WHERE card_no='" + card_no + "';";
							
							// UPDATE borrower SET address='trolled' WHERE card_no='9042';
							stmt.executeUpdate(query);
							System.out.println("Would you like to modify phone number?(Enter 'yes' 1 or'no' 2):");
							
							int yn = in6.nextInt();
							in6.nextLine();
							
							if (yn == 1){
								System.out.println("Enter the new phone number:");
								phone = in6.nextLine();
								query = "UPDATE borrower SET phone='" + phone +"' WHERE card_no='" + card_no + "';";
								stmt.executeUpdate(query);
							}
							
							
						}
						if (subcheck == 2){
							System.out.println("Enter the new phone number:");
							phone = in6.nextLine();
							query = "UPDATE borrower SET phone='" + phone +"' WHERE card_no='" + card_no + "';";
							stmt.executeUpdate(query);
						}
						
					System.out.println("\n\n\t\t\t\t ********User details modified successfully");
					}
						
					}
					catch (SQLException e){
						System.out.println("Error in records: " +e.getMessage());
					}
					}
			
			if (selection > 6 | selection < 0){
				System.out.println("Invalid option please try again");
			}
			}
		System.out.println ("Program terminating!");
		}
}