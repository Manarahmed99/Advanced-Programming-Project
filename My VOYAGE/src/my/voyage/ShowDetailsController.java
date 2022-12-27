/*
implemented by: Shahad malibari
modified by: Razan al-refaey
documented by: Razan al-refaey
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 2 event to handle and they are:
(editCityForm - editLocationForm - locationVisited )

editCityForm: this handler will send the received user and city objects to EditCity controller
controller so that controller can use them in some process needed there.
then it will drive the user to EditCity interface.

editLocationForm:this handler will send the received user,city and location objects to EditLocation controller
controller so that controller can use them in some process needed there.
then it will drive the user to EditLocation interface.

backToMain: this handler will send back the user object so the data still exist
in that controller and the return the user to the Home interface.

*other methods:
setCity: this method is called from diplay(City) method, this method receive the city object
and extract all its attributes and assign them in UI controls (City Card).

comboChange: we have a combobox that contain all the locations linked to the received city.
this method has a listner on a combobox so when ever it changes the values
in the Location Card will change.

setLocations:this method receive the city so it can retrive the linked locations to that city
from the database and add it to the combobox.

display(User): which can be called from other controllers in the class to send 
a User object to this controllers so we can send it to other controllers or make 
use of some data linked to this object.

display(City): which can be called from other controllers in the class to send 
a City object to this controllers so we can send it to other controllers or make 
use of some data linked to this object.

** the source of inputDialog is : 
https://coderslegacy.com/java/javafx-input-dialogs/

**the source of open a browser is:
https://www.youtube.com/watch?v=SlE0dCuO5yc

*/
//==================================================================
package my.voyage;

import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
//==================================================================
public class ShowDetailsController implements Initializable {

    @FXML
    private ImageView cityPhoto;
    @FXML
    private Label lblDetailedCityName;
    @FXML
    private Label lblDeatiledCityBudget;
    @FXML
    private Label lblDetailedCityHotel;
    @FXML
    private Label lblDetailedCityMembers;
    @FXML
    private Label lblDetailedCityADate;
    @FXML
    private Label lblDetailledCityRDate;
    @FXML
    private ImageView editCity;
    @FXML
    private ComboBox<String> cboLocations;
    @FXML
    private ImageView locationPhoto;
    @FXML
    private Label lblDetailedLocationBudget;
    @FXML
    private Label lblDetailedLocationTime;
    @FXML
    private Label lblDetailedLocationLink;
    @FXML
    private Label lblDetailedLocationDate;
    @FXML
    private ImageView editLocation;
    @FXML
    private Button btnLocationVisited;
    @FXML
    private Button btnReturnHome;
    @FXML
    private BorderPane locationPane;
    @FXML
    private Rectangle locationRec;
    @FXML
    private Button btnLink;
    //==================================================================
    private final List <Location> locations=new ArrayList();
    private User user;
    private City city;
    private Location loc;
    //==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    //==================================================================
    @FXML
    private void editCityForm(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCity.fxml"));
        Parent root = loader.load();
        EditCityController controller = loader.getController();
        controller.display(user);
        controller.display(city);
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();
    }
    //==================================================================
    @FXML
    private void editLocationForm(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditLocation.fxml"));
        Parent root = loader.load();
        EditLocationController controller = loader.getController();
        controller.display(user);
        controller.display(city);
        controller.display(loc);
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();
    }
    //==================================================================
    @FXML
    private void locationVisited(ActionEvent event) throws IOException {
        TextInputDialog inputdialog = new TextInputDialog();
        inputdialog.setHeaderText("Money paid at this location: ");
        inputdialog.showAndWait();
        String m=inputdialog.getEditor().getText();
        if(!(m.equals(""))){
            if(m.matches("[0-9]+")){
                loc.setLocationBudget(Integer.valueOf(m));
                Utility.updateLocation(loc);
                lblDetailedLocationBudget.setText("Budget: "+loc.getLocationBudget());
            }
        }
    }
    //==================================================================
    @FXML
    private void backToHome(ActionEvent event) throws IOException {
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
    //==================================================================
    @FXML
    private void openLink(ActionEvent event) throws URISyntaxException {
        try {
            if(!"".equals(loc.getLocationLink()))
            Desktop.getDesktop().browse(new URI(loc.getLocationLink()));
        } catch (IOException ex) {
            System.out.println("Error: invalid link");
        }
    }
    //==================================================================
    //combobox listener
    public void comboChange(){
        cboLocations.valueProperty().addListener(e-> {
            int index = cboLocations.getSelectionModel().getSelectedIndex();
            loc=locations.get(index);
            locationPhoto.setImage(null);
            if(loc.getLocationPhoto()!=null){
                Image image;
                try {
                    image = new Image(new FileInputStream(loc.getLocationPhoto()));
                    locationPhoto.setImage(image);
                    
                } catch (FileNotFoundException ex) {
                    System.out.println("Error: Location photo is not found");
                }
            }
            lblDetailedLocationBudget.setText("Budget: "+loc.getLocationBudget());
            lblDetailedLocationTime.setText("Time: "+loc.getVisitTime());
            lblDetailedLocationDate.setText("Date: "+loc.getVisitDate());
            if("".equals(loc.getLocationLink())){
                btnLink.setVisible(false);
                lblDetailedLocationLink.setVisible(false);
            }
            else{
                btnLink.setVisible(true);
                lblDetailedLocationLink.setVisible(true);
            }
        });
    }
    //==================================================================
    //set city labels
    private void setCity(){
        if(city.getCityPhotoURL()!=null){
            Image image;
            try {
                image = new Image(new FileInputStream(city.getCityPhotoURL()));
                cityPhoto.setImage(image);

            } catch (FileNotFoundException ex) {
                System.out.println("Error: City photo is not found");
            }
        }
        lblDetailedCityName.setText("City name: "+city.getCityName());
        lblDeatiledCityBudget.setText("Budget: "+city.getCityBudget());
        lblDetailedCityMembers.setText("Number of members: "+city.getMembers());
        lblDetailledCityRDate.setText("Return date: "+city.getReturnDate());
        lblDetailedCityADate.setText("Arrived date: "+city.getArrivalDate());
        if(city.getHotel().equals(""))
            lblDetailedCityHotel.setVisible(false);
        else
            lblDetailedCityHotel.setText("Hotel name: "+city.getHotel());
    }
    //==================================================================
    //set location label
    private void setLocations(){
        List<Location> list=Utility.getLocationListByEmail(user.getEmail());
        for(Location l:list){
            if(l.getCityId().getCityName().equals(city.getCityName()) && ( (l.getCityId().getUser().getUserId() ) == user.getUserId()) ){
                cboLocations.getItems().add(l.getLocationName());
                locations.add(l);
            }
        }
        if(locations.isEmpty()){
            locationPane.setVisible(false);
            locationRec.setVisible(false);
        }
        else{
            comboChange();
            cboLocations.getSelectionModel().selectFirst();
        }
    }
    //==================================================================
    //receive user
    void display(User sentObject){
        user=sentObject;
        System.out.println("user name is: "+user.getUserName());
    }
    //==================================================================
    //receive city
    void display(City sentObject) {
        city=sentObject;
        System.out.println("city name is: "+city.getCityName());
        setCity();
        setLocations();
    }
    //==================================================================
}
