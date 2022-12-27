/*
implemented by: Mafaz basalamah
documented by: Razan al-refaey
modified by: shahad malibari
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 3 event to handle and they are:
( saveLocationUpdate - cancalUpdateLocation -  uploadLocationUpdatePhoto )

saveLocationUpdate: this method will checks the UI controls and make some validation on it.
then it is updated the location object in the database.
then it is send the user and city objects back to the ShowDetails controller to maintain the data there.
then return the user to the ShowDetail interface.

cancalUpdateLocation: it is send the user and city objects back to the ShowDetails controller to maintain the data there.
then return the user to the ShowDetail interface.

uploadCityUpdatePhoto: it will open a showOpenDialog and use a file chooser to make the user choose a photo
to update the old path.

*other method:
display (user):  which can be called from other controllers in the class to send 
a user object to this controllers so we can send it to other controllers or make use of some data linked to this object.

display (city):  which can be called from other controllers in the class to send 
a city object to this controller so we can send it to other controllers or make use of some data linked to this object.

display (location): receive the location object and extract all its attributes and assign them to the UI controls
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//==================================================================
public class EditLocationController implements Initializable {

    @FXML
    private Label lblFormLocationName;
    @FXML
    private Label lblFormLocationBudget;
    @FXML
    private Label lblFormLocationTime;
    @FXML
    private Label lblFormLocationLink;
    @FXML
    private Label lblFormLocationPhoto;
    @FXML
    private TextField tfFormLocationName;
    @FXML
    private TextField tfFormLocationBudget;
    @FXML
    private TextField tfFormLocationLink;
    @FXML
    private Button btnSaveLocationUpdate;
    @FXML
    private Button btnCancelUpdateLocation;
    @FXML
    private Label lblFormLocationDate;
    @FXML
    private DatePicker editLocationDate;
    @FXML
    private Button btnLocaitonUpdatePhoto;
    @FXML
    private Label alert;
    @FXML
    private ComboBox<String> cbMinutes;
    @FXML
    private ComboBox<String> cbHours;
    //==================================================================
    private final FileChooser fileChooser = new FileChooser();
    private String photoPath="";
    private User user;
    private City city;
    private Location location;
    private ObservableList<String> hours = FXCollections.observableArrayList();
    private ObservableList<String> minutes = FXCollections.observableArrayList();
    //==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    //==================================================================
    @FXML
    private void saveLocationUpdate(ActionEvent event) throws IOException {
         if(checkTf(tfFormLocationName)&& checkTf(tfFormLocationBudget)&& checkDp(editLocationDate)){
            if(checkTfNum(tfFormLocationBudget)){
                Integer budget = Integer.valueOf(tfFormLocationBudget.getText());
                int total=budget;
                List<Location> list=Utility.getLocationListByEmail(user.getEmail());
                System.out.println("size of location list in add location handler is: "+list.size());
                if(list.size() > 0){
                    for(Location l:list){
                        if(l.getCityId().getCityName().equals(city.getCityName())&&(l.getLocationId()!=location.getLocationId()))
                            total=total+l.getLocationBudget();
                    }
                    if(total>city.getCityBudget())
                        alert.setText("Error: Budget has exceeded allowed amount");
                    else{
                        String time=cbHours.getValue()+":"+cbMinutes.getValue();
                        location.setLocationName(tfFormLocationName.getText());
                        location.setLocationBudget(budget);
                        location.setVisitTime(time);
                        location.setLocationLink(tfFormLocationLink.getText());
                        location.setLocationPhoto(photoPath);
                        location.setVisitDate(editLocationDate.getValue().toString());
                        Utility.updateLocation(location);
                        //showDetails
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
                }
                else{
                    String time=cbHours.getValue()+":"+cbMinutes.getValue();
                        location.setLocationName(tfFormLocationName.getText());
                        location.setLocationBudget(budget);
                        location.setVisitTime(time);
                        location.setLocationLink(tfFormLocationLink.getText());
                        location.setLocationPhoto(photoPath);
                        location.setVisitDate(editLocationDate.getValue().toString());
                        Utility.updateLocation(location);
                        //showDetails
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
            }
            else
                alert.setText("Error: Enter numbers only \nin budget textfield");
        }
        else
            alert.setText("Error: Empty textfields");
    }
    //==================================================================
    @FXML
    private void cancelUpdateLocation(ActionEvent event) throws IOException {
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
    private void uploadLocationUpdatePhoto(ActionEvent event) {
        Stage stage = null;
        File file = fileChooser.showOpenDialog(stage);
        if (file != null)
            photoPath = file.toString();
        else
            photoPath = "";
        if(photoPath.equals(""))
            lblFormLocationPhoto.setText("Add Photo");
        else
            lblFormLocationPhoto.setText("Add Photo"+" (selected)");
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
    }
    //==================================================================
    //receive location
    void display(Location sentObject){
        location=sentObject;
        System.out.println("location name is: "+location.getLocationName());
        
        String budget = String.valueOf(location.getLocationBudget());
        LocalDate date = LocalDate.parse(location.getVisitDate());
        editLocationDate.setValue(date);
        tfFormLocationName.setText(location.getLocationName());
        tfFormLocationBudget.setText(budget);
        String k;
        outer:for(int i=0;i<=2;i++){
            for(int j=0;j<=9;j++){
                k=String.valueOf(i)+String.valueOf(j);
                if(k.equals("24"))
                    break outer;
                hours.add(k);
            }
        }
        for(int i=0;i<=5;i++){
            for(int j=0;j<=9;j=j+5){
                k=String.valueOf(i)+String.valueOf(j);
                minutes.add(k);
            }
        }
        cbHours.setItems(hours);
        cbHours.setValue(location.getVisitTime().substring(0, 2));
        cbMinutes.setItems(minutes);
        cbMinutes.setValue(location.getVisitTime().substring(3));       
        tfFormLocationLink.setText(location.getLocationLink());
        photoPath = location.getLocationPhoto();
        if(photoPath==null)
            lblFormLocationPhoto.setText("Add Photo");
        else
            lblFormLocationPhoto.setText("Add Photo"+" (selected)");
    }
    //==================================================================
}
