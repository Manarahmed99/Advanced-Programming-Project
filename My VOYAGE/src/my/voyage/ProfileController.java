/*
implemented by: Maarib al-sulimani
documented by: Razan al-refaey
modified by: Razan al-refaey & shahad malibari
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 6 event to handle and they are:
(goHome - goBudgetReport - goProfile - goTodoList - logout - editProfile )

goHome: sends the received user object to the home controller again to maintain the data exist in the home interface.
goBudgetReport: sends the received user object to BudgetReport controller so it can use the object
in some operations there.
goProfile: sends the received user object to itself again to maintain the data in the controller.
goTodoList: sends the received user object to todoList controller so it can use the object
in some operations there.

logout: drive the user to the welcome interface
editProfile: sends the received user object to EditProfile interface

*other method:
display:  which can be called from other controllers in the class to send 
a user object to this controllers so we can send it to other controllers or make use of some data linked to this object.

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
import javafx.stage.Stage;
      
public class ProfileController implements Initializable {

    @FXML
    private Label lblProfileNameText;
    @FXML
    private Label lblProfileName;
    @FXML
    private Label lblProfileGenderText;
    @FXML
    private Label lblProfileGender;
    @FXML
    private Label lblProfilePhoneText;
    @FXML
    private Label lblProfilePhone;  
    @FXML
    private Label lblProfileEmailText;
    @FXML
    private Label lblProfileEmail;
    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnEditProfile;
    @FXML
    private Button testid;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnTodoList;
    @FXML
    private Button btnBudgetReport;
    
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private void editProfile(ActionEvent event) throws IOException {
        System.out.println("to be send user is: "+user.getUserName());
        // pass data to scene 12 ((Edit profile))
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditProfile.fxml"));
        Parent root = loader.load();
        EditProfileController controller = loader.getController();
        controller.display(user); 
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void goHome(ActionEvent event) throws IOException {
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

    @FXML
    private void goProfile(ActionEvent event) throws IOException {
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
    
    @FXML
    private void goTodoList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TodoList.fxml"));
        Parent root = loader.load();
        TodoListController controller = loader.getController();
        controller.display(user);
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void goBudgetReport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BudgetReport.fxml"));
        Parent root = loader.load();
        BudgetReportController controller = loader.getController();
        controller.display(user);
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void logOut(ActionEvent event) throws IOException {
        Utility.sceneChanger(getClass(), event, "Animation.fxml");
    }
    
    //set profile labels
    public void displayProfileInfo() {    
        System.out.println("recived user is: "+user.getUserName());
        lblProfileName.setText(user.getUserName());
        lblProfileGender.setText(user.getGender());
        lblProfileEmail.setText(user.getEmail());
        lblProfilePhone.setText(user.getPhoneNumber());
    }
    
    //receive user
    public void display(User user1) {
        user = user1; 
        displayProfileInfo();
    }
}

