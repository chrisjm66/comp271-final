package gameobjects;
import game.PlayerArea;
import game.Tioli;
import game.DealerArea;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameOptions extends VBox {
	// Constants
	private final String FONT_FAMILY = "Arial";
	private final int FONT_SIZE = 18;
	
	// Local Objects
	RadioButton rbRed = new RadioButton("Red");
	RadioButton rbBlue = new RadioButton("Blue");
	VBox rbContainer = new VBox(10, rbRed, rbBlue);
	CheckBox showTimer = new CheckBox("Show Timer");
	CheckBox useTimer = new CheckBox("Use Timer");
	TextField txtSetTime = new TextField();
	Label lblSetTime = new Label("Set Time");
	HBox setTimeContainer = new HBox(5, lblSetTime, txtSetTime);
	ComboBox<Integer> cbDrawCounts = new ComboBox<Integer>();
	Label lblDrawCounts; // instantiated in createComboBox
	Text headerText = new Text("Game Options");
	
	// Game Objects
	PlayerArea playerArea;
	DealerArea dealerArea;
	GameTimer timer;
	
	// Constructor
	public GameOptions(PlayerArea playerArea, DealerArea dealerArea, GameTimer timer) {
		this.playerArea = playerArea;
		this.dealerArea = dealerArea;
		this.timer = timer;
		
		// Setup UI Objects
		createCardBackOptions();
		createShowTimerOption();
		createUseTimerOption();
		createSetTimer();
		createComboBox();
		
		// Style and add objects to this (VBox)
		setStyle();
		this.getChildren().addAll(headerText, rbContainer, showTimer, useTimer, setTimeContainer, lblDrawCounts);
		
	}

	// EC 4.1?
	private void setStyle() {
		this.setSpacing(10);
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.CENTER_LEFT);
		setTimeContainer.setAlignment(Pos.CENTER_LEFT);
		txtSetTime.setPrefSize(50, 15);
		cbDrawCounts.setPrefSize(75, 20);
		
		// Set Fonts
		setFont(rbRed);
		setFont(rbBlue);
		setFont(showTimer);
		setFont(useTimer);
		setFont(lblSetTime);
		setFont(lblDrawCounts);
		setFont(headerText);
		txtSetTime.setFont(Font.font(FONT_FAMILY, FONT_SIZE));
		
	}
	
	private void setFont(Labeled textObject) {
		textObject.setFont(Font.font(FONT_FAMILY, FONT_SIZE));
	}
	
	private void setFont(Text textObject) {
		textObject.setFont(Font.font(FONT_FAMILY, FONT_SIZE));
	}
	
	private void createCardBackOptions() {
		// Set Toggle Group
		ToggleGroup group = new ToggleGroup();
		rbRed.setToggleGroup(group);
		rbBlue.setToggleGroup(group);
		rbRed.setSelected(true);
		
		// Listeners
		rbRed.setOnAction(e -> dealerArea.setCardBack("red"));
		rbBlue.setOnAction(e -> dealerArea.setCardBack("blue"));
	}

	private void createShowTimerOption() {
		showTimer.setSelected(true); // default
		
		// Listener
		showTimer.setOnAction(e -> timer.setVisible(showTimer.isSelected()));	
	}

	
	private void createUseTimerOption() {
		useTimer.setSelected(true); // default
		
		// Listener
		useTimer.setOnAction(e -> {
			boolean isSelected = useTimer.isSelected();
			timer.setUseTimer(isSelected);
			showTimer.setVisible(isSelected);
			
			// EC 4.1
			showTimer.setSelected(false);
			showTimer.fireEvent(new ActionEvent()); // fires event, I also could have just checked if showTimer is true and fired it to do the same thing
		});
	}

	private void createSetTimer() {
		// Setup
		txtSetTime.setText("15");
		timer.setStartSeconds(15);
		
		// Listener
		txtSetTime.setOnKeyPressed(e -> {
			switch(e.getCode()) {
				case ENTER:
				case TAB:
					timer.setStartSeconds(Integer.parseInt(txtSetTime.getText()));
					timer.refreshTimerDisplay();
			default:
				break;
			}
			
		});
		
	}
	
	private void createComboBox() {
		lblDrawCounts = new Label("Draw Amount", cbDrawCounts);
		lblDrawCounts.setContentDisplay(ContentDisplay.BOTTOM);
		cbDrawCounts.getItems().addAll(4, 5, 6);
		cbDrawCounts.setValue(5); // default
		
		// Listener
		cbDrawCounts.setOnAction(e -> {
			Tioli.setMaxTioliCards(cbDrawCounts.getValue());
		});
	}

	
	public void disableTimerButton() {
		useTimer.setDisable(true);
		txtSetTime.setDisable(true);
	}
	
	public void enableTimerButton() {
		useTimer.setDisable(false);
		txtSetTime.setDisable(false);
	}
	
	public void disableDrawAmountInput() {
		cbDrawCounts.setDisable(true);
	}
	
	public void enableDrawAmountInput() {
		cbDrawCounts.setDisable(false);
	}
}