package gameoutput;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import hand.Hand;
import player.Player;

public class GameFile {
	public static void writeCSVData(String fileName, Player player, int winAmount) {
		Hand playerHand = player.getHand();
		try {
			File file = new File("files/" + fileName);
			PrintWriter output = new PrintWriter(file);

			output.print("Player ID,Player Name,Hand,Hand Descr,Win Amount,Bank\n");
			
			output.print(player.getId() + "," + player.getName() + "," + playerHand.toString() + "," + playerHand.getHandDescr() + "," + winAmount + "," + player.getBank() + "\n");
			output.close();
		} catch (IOException ex) {
			System.out.println("File could not be output.");
		}
	}
	
	public static void writeBinaryData(String fileName, Player player, int winAmount) {
		Hand playerHand = player.getHand();
		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream("files/" + fileName, true));
			output.writeUTF(player.getId());
			output.writeUTF(player.getName());
			output.writeUTF(playerHand.getHandDescr());
			output.writeInt(winAmount);
			output.writeInt(player.getBank());
			
			output.close();
		} catch (IOException e) {
			System.out.println("Error writing data");
		}
	}
}
