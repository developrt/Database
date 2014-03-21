import javax.swing.*;

import com.mysql.jdbc.PreparedStatement;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.EventObject;

public class buttoninput extends JFrame
						implements ActionListener	
{		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Label	instruction;
	private Label	company;
	private TextField companyfield;
	private Button  okButton;
	private Button	clearformButton;
	private Label	Name;
	private TextField namefield;
	private Label	Address;
	private TextField addressfield;
	private Label	City;
	private TextField cityfield;
	private Label	State;
	private TextField statefield;
	private Label   Zipcode;
	private TextField zipcodefield;
	private Label	Phone;
	private TextField phonefield;
	private Label	Fax;
	private TextField faxfield;
	private Label	Email;
	private TextField emailfield;
	private MenuBar	menubar;
	private Menu	fileMenu, editMenu, helpMenu;
	private MenuItem openMI, uploadMI, exitMI;
	private MenuItem deleteMI;
	private MenuItem helpMI;
	private Object msgLine;
	
	buttoninput()
	{
		super();
		setTitle("Mailing List");
		setSize(400,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200,100);
		setBackground(java.awt.Color.lightGray);
		
				
		instruction = new Label("Enter customer information");
		company 	= new Label("Company");
		companyfield= new TextField(5);
		okButton	= new Button("Ok");
		Name		= new Label("Name");
		namefield   = new TextField(5);
		Address		= new Label("Address");
		addressfield = new TextField(5);
		City		= new Label("City");
		cityfield   = new TextField(5);
		State		= new Label("State");
		statefield  = new TextField(5); 
		Zipcode		= new Label("Zipcode");
		zipcodefield = new TextField(5);
		Phone		= new Label("Phone");
		phonefield  = new TextField(5);
		Fax			= new Label("Fax");
		faxfield	= new TextField(5);
		Email		= new Label("Email");
		emailfield  = new TextField(5);
		clearformButton = new Button("Clear Form");
		menubar		= new MenuBar();
		fileMenu	= new Menu("File");
		editMenu	= new Menu("Edit");
		helpMenu	= new Menu("Help");
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(helpMenu);
		
		openMI		= new MenuItem("Open");
		uploadMI	= new MenuItem("Upload");
		exitMI		= new MenuItem("Exit");
		deleteMI	= new MenuItem("Delete");
		helpMI		= new MenuItem("Help");
		
		fileMenu.add(openMI);
		fileMenu.add(uploadMI);
		fileMenu.add(exitMI);
		editMenu.add(deleteMI);
		helpMenu.add(helpMI);
		
		
		setLayout(new GridLayout(16,16));
		setMenuBar(menubar);
		setVisible(true);
		add(instruction);
		add(new Label(" "));
		add(new Label(" "));
		add(new Label(""));
		add(company);
		add(companyfield);
		add(new Label("Name"));
		add(namefield);
		add(new Label("Address"));
		add(addressfield);
		add(new Label("City"));
		add(cityfield);
		add(new Label("State"));
		add(statefield);
		add(new Label("Zipcode"));
		add(zipcodefield);
		add(new Label("Phone"));
		add(phonefield);
		add(new Label("Fax"));
		add(faxfield);
		add(new Label("Email"));
		add(emailfield);
		add(new Label(""));
		add(new Label(""));
		add(new Label(""));
		add(okButton);
		add(new Label(""));
		add(clearformButton);
		pack();
		setVisible(true);
		okButton.addActionListener(this);}
		
		
	public void actionPerformed1(ActionEvent evt) 
	{
		Object part = evt.getSource();
		if ((part == okButton))
			okButton.setEnabled(true);
		{
		try {String userName = "root"; 
		String password = ""; 
		String url = "jdbc:mysql://localhost:3306/xxxxxxx";
		Connection conn = DriverManager.getConnection (url, userName, password); 
		Statement st = conn.createStatement();
		
		String company=companyfield.getText();
		String Name=namefield.getText();
		String Address=addressfield.getText();
		String City=cityfield.getText();
		String State=statefield.getText();
		String Zipcode=zipcodefield.getText();
		String Phone=phonefield.getText();
		String Fax=faxfield.getText();
		String Email=emailfield.getText();

		String insertStr="";
		insertStr="insert into customers (company, owner, address, city, state, zipcode, phone, fax, email) values("
			+quotate(company)+","
			+quotate(Name)+","
			+quotate(Address)+","
			+quotate(City)+","
			+quotate(State)+","
			+quotate(Zipcode)+","
			+quotate(Phone)+","
			+quotate(Fax)+","
			+quotate(Email)+","
			+")";

								

		
	} catch (Exception e) {
		System.err.println("Got an exception! ");
		System.err.println(e.getMessage());
	}}
	
}
		
		private String quotate(String company2) {
		// TODO Auto-generated method stub
		return null;
	}
		private String quotate(TextField companyfield2) {
		// TODO Auto-generated method stub
		return null;
	}
		public static void main(String args[])
	{
		buttoninput test = new buttoninput();
	}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		}
