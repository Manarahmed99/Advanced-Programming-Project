/*
implemented by: Manar al-abdli
documented by: Razan al-refaey
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 2 event to handle and they are:
(SignUpToHomePage - backToMain)

SignUpToHomePage: this handler will pull the enterd values in the sign up form and start the validation process.
the validation will be on: empty fields, type mismatch and on some constrains like # of digits in password and phone.
after the validation ir cretes new user object has the entered values then we save this object in the database.
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
//==================================================================
public class SignUpController implements Initializable {

    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnReturnMain2;
    @FXML
    private TextField tfSignUpName;
    @FXML
    private TextField tfSignUpEmail;
    @FXML
    private TextField tfSignUpPhoneNumber;
    @FXML
    private TextField tfSignUpPassword;
    @FXML
    private TextField tfSignUpConfirmedPassword;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private RadioButton rbmale;
    @FXML
    private Label error_msg;
    @FXML
    private ToggleGroup rbToggleGroup;
//==================================================================
    private String gender;
    User user1;
//==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
//==================================================================  
    @FXML
    private void SignUpToHomePage(ActionEvent event) throws IOException {
        //radio buttons
        if(rbFemale.isSelected())
            gender ="Female";   
        else if(rbmale.isSelected())
            gender ="male";
        
        if(tfSignUpName.getText().isEmpty()||tfSignUpEmail.getText().isEmpty()|| tfSignUpPhoneNumber.getText().isEmpty()||
               tfSignUpPassword.getText().isEmpty()||tfSignUpConfirmedPassword.getText().isEmpty() || (! rbmale.isSelected()) && (!rbFemale.isSelected()))
            error_msg.setText("Please fill out the complete form");
        else if(!tfSignUpName.getText().matches("[a-zA-Z]+([ ][a-zA-Z ]*)*"))
            error_msg.setText("Plases Enter Your Name ");
        else if(!tfSignUpEmail.getText().matches("[a-zA-Z][A-Za-z0-9+_.-]+@[a-z]*[.][a-z]*"))
            error_msg.setText("Incorrect Email");
        else if(Utility.isExist(tfSignUpEmail.getText()))
            error_msg.setText("The email is already in use"); 
        else if(!tfSignUpPhoneNumber.getText().matches("^[0-9]{10}$"))
            error_msg.setText("Incorrect Phone Number,must 10 digits");
        else if(!tfSignUpConfirmedPassword.getText().matches(tfSignUpPassword.getText()))
            error_msg.setText("Password not Matches");
        else if(!tfSignUpPassword.getText().matches("^(?=.*[0-9]).{4,15}$"))
            error_msg.setText("Incorrect Password,At least 4 digits");
        else{
            error_msg.setText(" ");
            user1= new User(tfSignUpName.getText(),tfSignUpPhoneNumber.getText(),tfSignUpEmail.getText(),tfSignUpPassword.getText(),gender);
            Utility.insertNewUser(user1);

            System.out.println(tfSignUpName.getText());
            System.out.println(tfSignUpEmail.getText());
            System.out.println(gender);
            System.out.println(tfSignUpPhoneNumber.getText());
            System.out.println(tfSignUpPassword.getText());
            System.out.println(tfSignUpConfirmedPassword.getText());

            //-Sending data 
            //Home
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            HomeController controller = loader.getController();
            controller.display(user1);
            Scene scene = new Scene(root);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
            window.setScene(scene);
            window.show();
        }
    }
    //==================================================================
    @FXML
    private void backToMain(ActionEvent event) throws IOException {
        Utility.sceneChanger(getClass(), event, "Welcome.fxml");}
    //==================================================================
}
