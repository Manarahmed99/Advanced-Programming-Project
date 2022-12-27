/*
implemented by: Maarib al-sulimani
documented by: Razan al-refaey
modified by: Razan al-refaey & shahad malibari
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 2 event to handle and they are:
( saveEditProfile - cancelEditProfile )

saveEditProfile: this method will checks the UI controls and make some validation on it.
then it is updated the user object in the database and send the updated user to the Profile Controller
so the updated user is kept in the flow of the application.
then the user will be driven to the Profile interface.

cancelEditProfile:the user object will be send back then user will be driven to the Profile interface.

*other method:
display:  which can be called from other controllers in the class to send 
a user object to this controllers so we can send it to other controllers or make use of some data linked to this object.

displayEditProfileInfo:receive the user object and extract its attributes and assign them to the UI controls
to be displayed.

checkTf: checks if the send textfield empty or not.

checkTfNum: checks if the send textfield has only numbers or not.

showAlert: method to dislpay the alert.
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
//==================================================================
public class EditProfileController implements Initializable {  
    @FXML
    private Label lblEditProfileGender;
    @FXML
    private Label lblEditProfilePhone ;  
    @FXML
    private Label lblEditProfileName;   
    @FXML
    private Label lblEditProfileEmail;  
    @FXML
    private Label lblNewPassword;    
    @FXML
    private Label lblConfirmedPassword;      
    @FXML
    private TextField tfEditProfileName;   
    @FXML
    private TextField tfEditProfileEmail;    
    @FXML
    private TextField tfEditProfilePhoneNumber;    
    @FXML
    private TextField tfNewPassword;    
    @FXML
    private TextField tfConfirmedPassword;    
    @FXML
    private Button btnSaveEditProfile;
    @FXML
    private Button btnCancelEditProfile;
    @FXML
    private RadioButton rbmale;
    @FXML
    private ToggleGroup rbToggleGroup;
    @FXML
    private RadioButton rbFemale;
    //==================================================================
    private String gender;
    private User user;  
    //==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {}   
    //==================================================================
    @FXML
    private void saveEditProfile(ActionEvent event) throws IOException {
        //radio buttons
        if(rbFemale.isSelected())
            gender ="Female";   
        else if(rbmale.isSelected())
            gender ="male";
        
        if(checkTf(tfEditProfileName)&&checkTf(tfEditProfileEmail)&&checkTf(tfEditProfilePhoneNumber)){
            if(tfEditProfileEmail.getText().matches("[a-zA-Z][A-Za-z0-9+_.-]+@[a-z]*[.][a-z]*")){
                if(tfEditProfilePhoneNumber.getText().matches("^[0-9]{10}$")){
                    //session for update info for user
                    user.setUserName(tfEditProfileName.getText());
                    user.setGender(gender);
                    user.setEmail(tfEditProfileEmail.getText());
                    user.setPhoneNumber(tfEditProfilePhoneNumber.getText());
                    if(!(tfNewPassword.getText().equals(""))){
                        if(tfNewPassword.getText().matches("^(?=.*[0-9]).{4,15}$")){
                            if (tfNewPassword.getText().equals( tfConfirmedPassword.getText()) ){
                                user.setPassword(tfNewPassword.getText());
                                Utility.updateUser(user);
                                //profile page
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("Profile.fxml"));
                                Parent root = loader.load();
                                ProfileController controller = loader.getController();
                                controller.display(user);
                                Scene scene = new Scene(root);
                                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
                                window.setScene(scene);
                                window.show();
                            }
                            else
                                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please Confirm password !");
                        }
                        else
                            showAlert(Alert.AlertType.ERROR, "Form Error!", "Incorrect Password,At least 4 digits");
                    }
                }
                else
                    showAlert(Alert.AlertType.ERROR, "Form Error!", "Incorrect Phone Number,must be 10 digits!");
            }
            else
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Incorrect Email!");
        }
        else
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please Fill empty text fields !");
    }
    //==================================================================
    @FXML
    private void cancleEditProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Profile.fxml"));
        Parent root = loader.load();
        ProfileController controller = loader.getController();
        controller.display(user); 
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();
    }
    //================================================================== 
    //set profile text fields
    public void displayEditProfileInfo() {
        if("Female".equals(user.getGender()))
           rbFemale.setSelected(true);
       else
           rbmale.setSelected(true);
       tfEditProfileName.setText(user.getUserName());
       tfEditProfileEmail.setText(user.getEmail());
       tfEditProfilePhoneNumber.setText(user.getPhoneNumber());
       tfNewPassword.setText(user.getPassword());
       tfConfirmedPassword.setText(user.getPassword());
    }
    //==================================================================
    //error message
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    //==================================================================
    //check if textField is empty
    private boolean checkTf(TextField tf){
        return !tf.getText().isEmpty();
    }
    //==================================================================
    //check if textField is digits only
    private boolean checkTfNum(TextField tf){
        return tf.getText().matches("[0-9]+");
    }
    //==================================================================
    //receive user
    public void display(User user1) {
        user = user1; 
        displayEditProfileInfo();
    }
    //==================================================================
}

