package game;
import java.util.ArrayList;
import cards.Card;
import deck.Deck;
import gameobjects.GameOptions;
import gameobjects.GameTimer;
import gameobjects.PayoutTable;
import gameobjects.ScoreBoard;
import gameobjects.Wager;
import gameoutput.GameData;
import gameoutput.GameFile;
import gameoutput.RandomPlayer;
import hand.Hand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import player.*;

public class Tioli extends Pane {
    // Constants / Settings
    private int SCREEN_WIDTH = 1000;
    private int SCREEN_HEIGHT = 550;
    private int WAGER_CHANGE = 10;
    private int DEAL_SIZE = 5;
    private int GAME_LENGTH = 15;

    // UI Objects
    private Player player;
    private Dealer dealer;
    private PlayerArea playerArea;
    private DealerArea dealerArea;
    private BorderPane gameScreen;
    private HBox header;
    private VBox centerSection;
    private VBox rightSection;
    private PayoutTable payoutTable;
    private Wager wager;
    private ScoreBoard scoreboard;
    private Stage primaryStage;
    private Button btnDeal;
    private Button btnTakeIt;
    private Button btnLeaveIt;
    private Button btnExit;
    private GameTimer timerObj;
    private GameOptions gameOptions;

    // Game Variables
    private static int maxTioliCards = 5;
    private int tioliCardsDealt;
    
    public Tioli(){
    	// Instantiate all objects
        gameScreen = new BorderPane();
        player = RandomPlayer.getPlayer();
        dealer = new Dealer(new Deck(52));
        playerArea = new PlayerArea(player, DEAL_SIZE, "tioli");
        dealerArea = new DealerArea(dealer, "tioli");
        payoutTable = new PayoutTable("tioli", "blue");
        wager = new Wager(player, WAGER_CHANGE, "red");
        scoreboard = new ScoreBoard(player);
        btnDeal = new Button("Deal");
        btnTakeIt = new Button("Take It");
        btnLeaveIt = new Button("Leave It");
        btnExit = new Button("Exit");
        timerObj = new GameTimer(GAME_LENGTH, btnLeaveIt);
        gameOptions = new GameOptions(playerArea, dealerArea, timerObj);
        
        // Startup methods
        createHeader();
        createCenterSection();
        createRightSection();
        setButtonStyles();
        createEventListeners();
        
        // Show game
        showGame();

        gameScreen.setTop(header);
        gameScreen.setCenter(centerSection);
        gameScreen.setRight(rightSection);
        gameScreen.setLeft(gameOptions);
    }

    private void createHeader(){
        Text text = new Text("Welcome to Tioli, " + player.getName());
        header = new HBox();
        header.getChildren().add(text);
        header.setAlignment(Pos.BASELINE_CENTER);
    }

    private void createCenterSection(){
        centerSection = new VBox();
        VBox vbButtonContainer = new VBox(btnTakeIt, btnLeaveIt);
        
        // Add dealer and player area to vbox
        centerSection.getChildren().addAll(timerObj, dealerArea, playerArea, btnDeal);
        dealerArea.getChildren().add(vbButtonContainer);
        vbButtonContainer.setAlignment(Pos.BASELINE_CENTER);
    	vbButtonContainer.setSpacing(10);
    	dealerArea.setAlignment(Pos.CENTER);
        playerArea.setAlignment(Pos.CENTER);
        btnDeal.setAlignment(Pos.BASELINE_CENTER);
        centerSection.setAlignment(Pos.CENTER);
    }

    private void createRightSection(){
        rightSection = new VBox();
        rightSection.getChildren().addAll(payoutTable, wager, scoreboard, btnExit);
        rightSection.setAlignment(Pos.CENTER);
        this.getChildren().add(rightSection);
    }
    
    
    private void setButtonStyles() {
    	// BtnDeal Style
    	btnDeal.setPrefWidth(150);
    	btnDeal.setPrefHeight(40);
    	btnDeal.setStyle("-fx-font-size: 20px;");

    	// BtnExit Style
    	btnExit.setPrefWidth(150);
    	btnExit.setPrefHeight(40);
    	btnExit.setStyle("-fx-font-size: 20px;\n -fx-margin: 10px;");
    	
    	// btnTakeIt & btnLeaveIt
    	String tioliButtonStyle = "-fx-font-size: 15px;\n -fx-padding: 5px 5px;";
    	btnTakeIt.setStyle(tioliButtonStyle);
    	btnLeaveIt.setStyle(tioliButtonStyle);
    	
    	btnTakeIt.setPrefWidth(100);
    	btnTakeIt.setPrefHeight(15);
    	btnLeaveIt.setPrefWidth(100);
    	btnLeaveIt.setPrefHeight(15);
    }
    
    private void createEventListeners() {
    	// btnDeal
    	btnDeal.setOnAction(e -> startDeal());
    	
    	btnTakeIt.setOnAction(e -> takeIt());

    	btnLeaveIt.setOnAction(e -> leaveIt());

		btnExit.setOnAction(e -> exitGame());
    }
    
    private void showGame(){
        Scene scene = new Scene(gameScreen, SCREEN_WIDTH, SCREEN_HEIGHT); // gets passed a BorderPane object
        primaryStage = new Stage();
        primaryStage.setTitle("Chris' Tioli");
        primaryStage.setScene(scene); // set scene as child of stage
        primaryStage.show();
    }
    
    private void exitGame() {
    	primaryStage.close();
    }
    
    private void startDeal(){
    	// EC9
    	if(!(wager.getWagerAmount() > player.getBank())) {
    		disableTioliButtons();
            clearCards();
            dealer.reshuffleDeck();
            scoreboard.setWinAmount(0);
            dealPlayer();
            evaluateHand();
            playerArea.enableCardSelect();
            dealDealer();
            timerObj.startTimer();
            
            playerArea.showCards();
            playerArea.showHandDescr();
    	}
    }
    
    private void dealDealer() {
		dealer.dealCard(dealer);		
		dealerArea.showTioliCard();
		tioliCardsDealt++;
	}

	private void dealPlayer(){
        for (int i = 0; i < DEAL_SIZE; i++) {
            dealer.dealCard(player);
        }
    }

    private void evaluateHand(){
    	player.getHand().evaluateHand();;
    }

    private void clearCards(){
    	// Clear cards and send to discard
    	Hand currentHand = player.getHand();
    	int handSize = currentHand.getCards().length;
    	for(int i = 0; i < DEAL_SIZE; i++) {
    		playerArea.removeCardImage(i);
    		
    		if((i+1) == DEAL_SIZE && handSize > 0) {
    			dealerArea.showDiscardedCard(currentHand.getCard(i));
    		}
    	}
        dealer.gatherUsedCards(player);
    }
    
    private void takeIt() {
    	if (playerArea.getSelectedCount() > 0) {
    		timerObj.stopTimer();
    		dealerArea.removeTioliImage();
    		Card takeItCard = dealer.getHand().removeCard(0);
    		boolean[] cardSelected = playerArea.getCardSelected();
    		
    		for(int i = 0; i < cardSelected.length; i++) {
    			if (cardSelected[i]) {
    				Hand playerHand = player.getHand();
    				playerArea.removeCardImage(i);
    				dealerArea.showDiscardedCard(playerHand.getCard(i));
    				dealer.takeUsedCard(player, i);
    				playerHand.setCard(i, takeItCard);
    			}
    		}
    		evaluateHand();
    		playerArea.showHandDescr();
    		playerArea.clearSelected();
    		playerArea.showCards();
    		if (tioliCardsDealt < maxTioliCards) {
    			dealDealer();
    			timerObj.startTimer();
    		} else {
    			endHand();
    		}
    	}
    }
    
    private void leaveIt() {
    	timerObj.stopTimer();
		dealerArea.removeTioliImage();
		Card leaveItCard = dealer.getHand().removeCard(0);
		
		playerArea.clearSelected();
		playerArea.showHandDescr();
		dealerArea.showDiscardedCard(leaveItCard);
		if (tioliCardsDealt < maxTioliCards) {
			dealDealer();
			timerObj.startTimer();
		} else {
			endHand();
		}
    }
    
    // EC 6, 7, 8
    private void disableTioliButtons() {
    	gameOptions.disableTimerButton();
    	gameOptions.disableDrawAmountInput();
    	btnTakeIt.setDisable(false);
    	btnLeaveIt.setDisable(false);
    	btnDeal.setDisable(true);
    	wager.setWagerEditable(false);
    	
    }
    
    private void enableTioliButtons() {
    	gameOptions.enableTimerButton();
    	gameOptions.enableDrawAmountInput();
    	btnTakeIt.setDisable(true);
    	btnLeaveIt.setDisable(true);
    	btnDeal.setDisable(false);
    	wager.setWagerEditable(true);
    }
    
    // Public Methods
    public void endHand() {
    	playerArea.clearSelected();
    	playerArea.disableCardSelect();
    	enableTioliButtons();
    	
    	int wagerAmount = wager.getWagerAmount();
    	int currentBalance = player.getBank();
    	int payoutAmount = payoutTable.getPayout(player.getHand(), wagerAmount);
    	
    	tioliCardsDealt = 0;
    	player.setBank(currentBalance + payoutAmount);
    	scoreboard.setWinAmount(payoutAmount);
    	scoreboard.updateBank();
    	
    	GameFile.writeCSVData("playerdata.txt", player, payoutAmount);
    	GameFile.writeBinaryData("playerdata.dat", player, payoutAmount);
    	updateDatabase();
    }
    
    public void updateDatabase() {
    	GameData database = new GameData();
    	database.updateBank(player);
    	database.insertResults(player, payoutTable.getPayout(player.getHand(), wager.getWagerAmount()));
    	database.close();
    }
    
    public static int getMaxTioliCards() {
    	return maxTioliCards;
    }
    
    public static void setMaxTioliCards(int maxTioliCards) {
    	Tioli.maxTioliCards = maxTioliCards;
    	System.out.println(maxTioliCards);
    }
}