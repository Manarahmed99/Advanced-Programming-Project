/*
implemented by: Manar al-abdli
documented by: Razan al-refaey
-------------------------
this class hendle 2 events:
( viewSignUpForm - viewLoginForm )
viewSignUpForm: will drive the user to SignUp interface.
viewLoginForm: will drive the user to Login interface.
*/
//==================================================================
package my.voyage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
//==================================================================
public class WelcomeController implements Initializable {

    @FXML
    private Button btnSignUpForm;
    @FXML
    private Button btnLoginForm;
//==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
//==================================================================   
    @FXML
    private void viewSignUpForm(ActionEvent event) throws IOException {
        Utility.sceneChanger(getClass(), event, "SignUp.fxml");}
//==================================================================
    @FXML
    private void viewLoginForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show(); 
//==================================================================   
    }    
}