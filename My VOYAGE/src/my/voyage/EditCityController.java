/*
implemented by: Mafaz basalamah
documented by: Razan al-refaey
modified by: shahad malibari & Razan al-refay
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 3 event to handle and they are:
( saveCityUpdate - cancalCityUpdate -  uploadCityUpdatePhoto )

saveCityUpdate: this method will checks the UI controls and make some validation on it.
then it is updated the city object in the database.
then it is send the user and city objects back to the ShowDetails controller to maintain the data there.
then return the user to the ShowDetail interface.

cancalCityUpdate: it is send the user and city objects back to the ShowDetails controller to maintain the data there.
then return the user to the ShowDetail interface.

uploadCityUpdatePhoto: it will open a showOpenDialog and use a file chooser to make the user choose a photo
to update the old path.

*other method:
display (user):  which can be called from other controllers in the class to send 
a user object to this controllers so we can send it to other controllers or make use of some data linked to this object.

display (city):  receive the city  object and extract all its attributes and assign them to the UI controls
in the form so the user can edit them.

checkTf: checks if the send textfield empty or not.

checkTfNum: checks if the send textfield has only numbers or not.

checkDp: checks if the send datepicker empty or not.

**the source of the file chooser is:
https://stackoverflow.com/questions/41927994/open-image-from-filechooser-in-javafx

*/
//==================================================================
package my.voyage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//==================================================================
public class EditCityController implements Initializable {

    @FXML
    private Button btnSaveCityUpdate;
    @FXML
    private Button btnCancelUpdateCity;
    @FXML
    private Label lblFormCityBudget;
    @FXML
    private Label lblFormCityADate;
    @FXML
    private Label lblFormCityRDate;
    @FXML
    private Label lblFormCityName;
    @FXML
    private Label lblFormCityHotel;
    @FXML
    private Label lblFormCityPhoto;
    @FXML
    private Label lblFormCityMembers;
    @FXML
    private TextField tfFormCityName;
    @FXML
    private TextField tfFormCityBudget;
    @FXML
    private TextField tfFormCityHotel;
    @FXML
    private TextField tfFormCityMembers;
    @FXML
    private DatePicker dpFormCityADate;
    @FXML
    private DatePicker dpFormCityRDate;
    @FXML
    private Button btnCityPhoto;
    @FXML
    private Label alert;
    //=================================================================
    private final FileChooser fileChooser = new FileChooser();
    private String photoPath;
    private User user;
    private City city;
    //==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    //==================================================================
    @FXML
    private void saveCityUpdate(ActionEvent event) throws IOException {
        if(checkTf(tfFormCityName)&& checkTf(tfFormCityBudget)&& checkTf(tfFormCityMembers)&&checkDp(dpFormCityADate)&&checkDp(dpFormCityRDate)){
            if(checkTfNum(tfFormCityBudget)&&checkTfNum(tfFormCityMembers)){                    
                Integer budget = Integer.valueOf(tfFormCityBudget.getText());
                Integer members = Integer.valueOf(tfFormCityMembers.getText());
                city.setCityName(tfFormCityName.getText());
                city.setCityBudget(budget);
                city.setHotel(tfFormCityHotel.getText());
                city.setArrivalDate(dpFormCityADate.getValue().toString());
                city.setReturnDate(dpFormCityRDate.getValue().toString());
                city.setCityPhotoURL(photoPath);
                city.setMembers(members);
                Utility.updateCity(city); 
                //showdetails
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ShowDetails.fxml"));
                Parent root = loader.load();
                ShowDetailsController controller = loader.getController();
                controller.display(user);
                controller.display(city);
                Scene scene = new Scene(root);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
                window.setScene(scene);
                window.show();
            }
            else
                alert.setText("Error: Enter numbers only \nin budget and members textfields");
        }
        else
            alert.setText("Error: Empty textfields");
    }
    //==================================================================
    @FXML
    private void cancelUpdateCity(ActionEvent event) throws IOException {
        //send objects to next scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ShowDetails.fxml"));
        Parent root = loader.load();
        ShowDetailsController controller = loader.getController();
        controller.display(user);
        controller.display(city);
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();
    }
    //==================================================================
    @FXML
    private void uploadCityUpdatePhoto(ActionEvent event) {
        Stage stage = null;
        File file = fileChooser.showOpenDialog(stage);
        if (file != null)
            photoPath = file.toString();
        else
            photoPath = "";
        if(photoPath.equals(""))
            lblFormCityPhoto.setText("Add Photo");
        else
            lblFormCityPhoto.setText("Add Photo (selected)");
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
    //check if datePicker is empty
    private boolean checkDp(DatePicker dp){
        return !(dp.getValue()==null);
    }
    //==================================================================
    //receive user
    void display(User sentObject){
        user=sentObject;
        System.out.println("user name is: "+user.getUserName());
    }
    //==================================================================
    //receive city
    void display(City sentObject){
        city=sentObject;
        System.out.println("city name is: "+city.getCityName());
        
        String budget = String.valueOf(city.getCityBudget());
        tfFormCityName.setText(city.getCityName());
        tfFormCityBudget.setText(budget);
        LocalDate date1 = LocalDate.parse(city.getArrivalDate());
        dpFormCityADate.setValue(date1);
        LocalDate date2 = LocalDate.parse(city.getReturnDate());
        dpFormCityRDate.setValue(date2);
        tfFormCityHotel.setText(city.getHotel());
        String members = String.valueOf(city.getMembers());
        tfFormCityMembers.setText(members);
        photoPath = city.getCityPhotoURL();
        if(photoPath == null)
            lblFormCityPhoto.setText("Add Photo");
        else
            lblFormCityPhoto.setText("Add Photo (selected)");
    }
    //==================================================================
}
