package gameoutput;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import player.Player;

public class GameFile {
	public static void writeCSVData(String fileName, Player player, int winAmount) {
		try {
			File file = new File("files/" + fileName);
			PrintWriter output = new PrintWriter(file);

			output.print("Player ID,Player Name,Hand,Hand Descr,Win Amount,Bank\n");
			
			output.print(player.getId() + "," + player.getName() + "," + player.getHand().toString() + "," + player.getHand().getHandDescr() + "," + winAmount + "," + player.getBank() + "\n");
			output.close();
		} catch (IOException ex) {
			System.out.println("File could not be output.");
		}
	}
}
