package reports;


import java.awt.Color;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import gameoutput.GameData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import player.Player;

public class GameReport {
	//Window dimensions - adjust as needed
	int windowWidth = 1000;
	int windowHeight = 550;
	
	private ResultSet query;
	private Stage reportStage = new Stage();
	private GameData database;
	private BorderPane reportPane;
	private Player player;
	private Label lbHeader;
	private ScrollPane spTable;
	private VBox vbScrollingTable;
	private HBox hbHeader;
	private HBox hbHeaderText;
	private Label lbGameId;
	private Label lbHandDesc;
	private Label lbAmountWon;
	private Label lbBank;
	private VBox vbTableContainer;
	private VBox vbBottomContainer;
	private HBox hbButtonContainer;
	private Label lbDataStatus;
	private Button btSave;
	private Button btExit;

	
	public GameReport(Player player) {
		this.player = player;
		database = new GameData();
		initializeGuiObjects();
		styleGuiObjects();
		populateTable();
		createEventListeners();
		//This stays as the last line of the constructor
		showScene();
	}
	
	private void createEventListeners() {
		btExit.setOnAction(e -> exitReport());
		btSave.setOnAction(e -> saveData());
	}

	private void saveData() {
		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream("files/report.dat", false)); // no appending since all data is already stored in the database, and we are inserting all of that data
			query.first();
			
			 do {
				output.writeUTF(query.getString(1));
				output.writeUTF(player.getName());
				output.writeUTF(query.getString(3));
				output.writeInt(query.getInt(4));
				output.writeInt(query.getInt(5));
			} while(query.next());
			output.close();
			
			query.last(); // move to last row to get # of rows on next line
			lbDataStatus.setText(query.getRow() + " rows added to reports.dat");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void populateTable() {
		// Get data from DB
		query = database.getReportData(player);
		
		try {
			while (query.next()) {
				HBox row = createTableRow(query.getString(1), query.getString(3), query.getString(4), query.getString(5));
				vbScrollingTable.getChildren().add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private HBox createTableRow(String gameId, String handDesc, String amountWon, String playerBank) {
		// Create objects
		HBox hbRow = new HBox(150);
		Label lbGameId = new Label(gameId);
		Label lbHandDesc = new Label(handDesc);
		Label lbAmountWon = new Label(amountWon);
		Label lbBank = new Label(playerBank);
		lbGameId.setPrefWidth(115);
		lbHandDesc.setPrefWidth(115);
		lbAmountWon.setPrefWidth(115);
		lbBank.setPrefWidth(115);
		hbRow.getChildren().addAll(lbGameId, lbHandDesc, lbAmountWon, lbBank);
		// Style objects
		hbRow.setPadding(new Insets(5, 0, 5, 0));
		return hbRow;
	}

	private void initializeGuiObjects() {
		// Declare objects - NOTE: All styles including padding are set in styleGuiObjects.
		reportPane = new BorderPane();
		hbHeaderText = new HBox();
		lbHeader = new Label();
		spTable = new ScrollPane();
		vbScrollingTable = new VBox();
		hbHeader = new HBox(10);
		lbGameId = new Label("Game ID");
		lbHandDesc = new Label("Hand Description");
		lbAmountWon = new Label("Amount Won");
		lbBank = new Label("Bank");
		vbTableContainer = new VBox();
		vbBottomContainer = new VBox();
		hbButtonContainer = new HBox();
		lbDataStatus = new Label();
		btSave = new Button("Save");
		btExit = new Button("Exit");
		
		// Add objects to containers
		hbHeaderText.getChildren().add(lbHeader);
		hbHeader.getChildren().addAll(lbGameId, lbHandDesc, lbAmountWon, lbBank);
		vbTableContainer.getChildren().addAll(hbHeader, spTable);
		hbButtonContainer.getChildren().addAll(btSave, btExit);
		vbBottomContainer.getChildren().addAll(hbButtonContainer, lbDataStatus);
		
		// Add containers to borderpane
		reportPane.setTop(hbHeaderText);
		reportPane.setCenter(vbTableContainer);
		reportPane.setBottom(vbBottomContainer);
		
	}
	
	private void styleGuiObjects() {
		// Header
		hbHeaderText.setAlignment(Pos.BASELINE_CENTER);
		lbHeader.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		lbHeader.setStyle("-fx-text-fill: purple;");
		lbHeader.setPadding(new Insets(20, 20, 20, 20));
		
		// Table/Center
		vbTableContainer.setAlignment(Pos.BASELINE_CENTER);
		vbTableContainer.setPadding(new Insets(20, 20, 10, 20));
		spTable.setPrefHeight(1000);
		spTable.setContent(vbScrollingTable);
		vbScrollingTable.setSpacing(10);
		hbHeader.setSpacing(150);
		for(int i = 0; i < hbHeader.getChildren().size(); i++) {
			if (hbHeader.getChildren().get(i) instanceof Label) { // this is so i can run the
				((Label)hbHeader.getChildren().get(i)).setFont(Font.font(20));
				hbHeader.getChildren().get(i).setStyle("-fx-text-fill: purple;");
			}
		}
		
		// Bottom
		vbBottomContainer.setAlignment(Pos.CENTER);
		hbButtonContainer.setAlignment(Pos.CENTER);
		vbBottomContainer.setSpacing(10);
		vbBottomContainer.setPadding(new Insets(0, 0, 10, 0));
		hbButtonContainer.setSpacing(20);
		btSave.setPrefHeight(40);
		btSave.setPrefWidth(90);
		btExit.setPrefHeight(40);
		btExit.setPrefWidth(90);
		lbDataStatus.setFont(Font.font("Arial", FontWeight.BOLD, 20.0));
		lbDataStatus.setStyle("-fx-text-fill: Green;");
	}
	//This method will be called by the Exit/Close button's listener
	private void exitReport() {
		//BE SURE TO CLOSE THE DATABASE CONNECTION.
		database.close();

		//Closes the window
		reportStage.close();
	}
	
	//This method will make our report show
	private void showScene() {
		
		//Create a scene object and put the base pane in it
		Scene scene = new Scene(reportPane, windowWidth, windowHeight);
		
		//Create a report title which should include the player's name
		lbHeader.setText(player.getName() + "'s Game Report");
		
		
		//Add the scene to the stage (use the Stage attribute Declared above) 
		reportStage.setScene(scene);
		
		
		//Show the Stage
		reportStage.show();
		
		
	}
}
