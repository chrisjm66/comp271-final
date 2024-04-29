package gameoutput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import player.Player;

public class GameData {
	protected Connection connection;
	protected Statement statement;
	protected ResultSet results;
	
	public GameData() {
		try {
			// Connect to DB
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection =  DriverManager.getConnection("jdbc:mysql://localhost/tioli", "root", "password");
			System.out.println("Database connected");
			
			// Create statement
			// I could not get line 22 (playerData.last();) to work in RandomPlayer without these two parameters in my code. According to oracle, these allow for selecting any part of the database desired and not just iterate forward. I tried installing the older version of SQL connector (5.1.24) as listed in the assignments but that did not help either.
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println("Connection Failed");
		} catch (ClassNotFoundException e) {
			System.out.println("Class invalid");
		} catch (Exception e) {
			System.out.println("Other error");
		}
	}
	
	public void updateBank(Player player) {
		String statementString = "UPDATE player SET bank = " + player.getBank() + " WHERE player_id = '" + player.getId() + "'";
		try {
			statement.executeUpdate(statementString);
		} catch (SQLException e) {
			System.out.println("SQL Error - " + e);
		}
	}
	
	public void insertResults(Player player, int amountWon) {
		// String format: id, handdesc, winamount, bank
		String statementString = String.format("INSERT INTO game_results (game_id, player_id, hand_descr, amount_won, player_bank) VALUES(DEFAULT, '%s', '%s', '%s', '%s')", player.getId(), player.getHand().getHandDescr(), amountWon, player.getBank());
		try {
			statement.executeUpdate(statementString);
		} catch (SQLException e) {
			System.out.println("SQL Error - " + e);
		}
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("SQL Error - " + e);
		}
	}
}	
