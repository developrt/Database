import java.sql.*;

public class Library {
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

			int linect = 0;

		try {
			// Create a connection to the local MySQL server, with the "company" database selected.
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
	
			// Create a SQL statement object and execute the query.
			Statement stmt = conn.createStatement();
		
			stmt.executeQuery("use library;");

			ResultSet rs = stmt.executeQuery("SELECT book_id, author_name FROM book_authors;");

			// Iterate through the result set.
			while (rs.next()) {
				// Keep track of the line/tuple count
				linect++;
				
				book_id = rs.getString("book_id");
				author_name = rs.getString("author_name");

				System.out.print(book_id);
				System.out.print("\t");
				System.out.print(author_name);
				System.out.println();
				
			} // End while(rs.next())

			// Always close the recordset and connection.
			rs.close();
			conn.close();
			System.out.println("Success!!");
		} 
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}
}