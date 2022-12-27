/*
implemented by: Manar al-abdli
documented by: Razan al-refaey
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 2 event to handle and they are:
(SignUpToHomePage - backToMain)

loginToHomePage: this handler will pull the enterd values in the login form and start the validation process.
the validation will be on: empty fields, type mismatch and on some constrains like # of digits in password.
after the validation the user's information will be pulled from the database
then we will send this user object to the home controller so the user's data can be pulled and updated in 
the all controllers.

backToMain: this handler will return the user to the previous interface.
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//==================================================================
public class LoginController implements Initializable {
     @FXML
    private TextField tfLoginEmail;
     @FXML
    private TextField tfLoginPassWord;
     @FXML
    private Button btnLogin;
     @FXML
    private Label error_msg;
//==================================================================     
    static User user; 
//================================================================== 
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
//==================================================================   
    @FXML
    private void loginToHomePage(ActionEvent event) throws IOException {
        if(!(checkTf(tfLoginEmail) | checkTf(tfLoginPassWord)))
            error_msg.setText("Please Enter Your Email and Password ");
        else if(!tfLoginEmail.getText().matches("[a-zA-Z][A-Za-z0-9+_.-]+@[a-z]*[.][a-z]*"))
                error_msg.setText("Incorrect Email");
            else if(!Utility.isExist(tfLoginEmail.getText()))  
                error_msg.setText("You don't have an account"); 
                else{
                    // Retrieve this user from DB
                    user = Utility.getUser(tfLoginEmail.getText());
                    if(tfLoginPassWord.getText().equals(user.getPassword())){
                        //Home
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("Home.fxml"));
                        Parent root = loader.load();
                        HomeController controller = loader.getController();
                        controller.display(user);
                        Scene scene = new Scene(root);
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
                        window.setScene(scene);
                        window.show();
                    }
                    else
                        error_msg.setText("Incorrect Password");
        }
        
      }
//==================================================================
      @FXML
    private void backToMain(ActionEvent event) throws IOException {
        Utility.sceneChanger(getClass(), event, "Welcome.fxml");}
//==================================================================
    //check if textField is empty
    private boolean checkTf(TextField tf){
        return !tf.getText().isEmpty();
    }
//==================================================================
}