package app;

import app.viewTool.ViewManager;
import app.viewTool.Views;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try{
            ViewManager.init(stage);
            ViewManager.registerScene(Views.Login);
            ViewManager.setScene(Views.Login);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {
        launch();
    }

}
