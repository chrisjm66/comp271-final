import game.Tioli;
import javafx.application.Application;
import javafx.stage.Stage;

public class PlayPoker extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage){
        new Tioli();
    }
}
