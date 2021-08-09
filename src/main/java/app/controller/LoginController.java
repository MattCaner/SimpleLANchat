package app.controller;

import app.viewTool.ViewManager;
import app.viewTool.ViewOnloadEvent;
import app.viewTool.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class LoginController implements ViewOnloadEvent {
    @FXML
    private Button loginBtn;

    @FXML
    private TextField userInput;

    @FXML
    private Label status;

    public void initialize() {

        loginBtn.setOnAction(e -> {
            login();
        });
    }

    public void login() {
            try{
                String user = userInput.getText();
                System.out.println(user);
                ViewManager.registerDefaultViews();
                ViewManager.setScene(Views.MainView);
            } catch(Exception e){
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }


    }

}
