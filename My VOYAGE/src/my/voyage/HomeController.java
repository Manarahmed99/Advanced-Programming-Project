/*
implemented by: Manar al-abdli
modified by: Razan al-refaey - Shahad malibari - Maarib al-sulimani
documented by: Razan al-refaey
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 8 event to handle and they are:
(goHome - goBudgetReport - goProfile - goTodoList - addNewCity - addNewLocation - showCityDetails  - popUpWindow )

goHome: sends the received user object to the home controller again to maintain the data exist in the home interface.
goBudgetReport: sends the received user object to BudgetReport controller so it can use the object
in some operations there.
goProfile: sends the received user object to itself again to maintain the data in the controller.
goTodoList: sends the received user object to todoList controller so it can use the object
in some operations there.

addNewCity: this handler will send the recevid user object to AddCity controller so that controller
can use it when it adds new City. 
then it will drive the user to AddCity interface.

addNewLocation: this handler will send the recevid user object and City object choosen in the combobox
to AddLocation controller so that controller can use it when it adds new Location. 
then it will drive the user to AddLocation interface.

showCityDetails: this handler will send the recevid user object and City object choosen in the combobox
to ShowDetails controller so that controller can use it when it show details.
then it will drive the user to ShowDetails interface.

popUpWindow: this handler will display a pop up window congrats the user.

other methods:
setCity: this method receive user object and use it to pull all the cities of this user from the database
and add it to the combobox.

comboChange:this method has a listener on the value of the combobox, whenever it changes the UI controls which
display the city informations will be changed.

display:this method receive user object to send it to other controllers or to use the user in other methods.
*/
//==================================================================
package my.voyage;

import java.io.IOException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
//==================================================================
public class HomeController implements Initializable {
    @FXML
    private Button btnHome;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnTodoList;
    @FXML
    private Button btnBudgetReport;
    @FXML
    private ImageView btnAddNewCity;
    @FXML
    private ComboBox<String> cboCitiesCard;
    @FXML
    private Button btnAddLocation;
    @FXML
    private Button btnShowDetails;
    @FXML
    private Button btnDone;
    @FXML
    private Label lbCityName;
    @FXML
    private Label lbHotelName;
    @FXML
    private Label lbAdata;
    @FXML
    private Label lbRdata;
    @FXML
    private Rectangle CityRec;
    @FXML
    private BorderPane cityPane;
//==================================================================
    private List <City> cities=new ArrayList();
    private User user;
    private City city;
//==================================================================   
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
//==================================================================   
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
//==================================================================
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
//==================================================================
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
//==================================================================
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
//==================================================================
    @FXML
    private void addNewCity(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddCity.fxml"));
        Parent root = loader.load();
        AddCityController controller = loader.getController();
        controller.display(user);
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();
    }
//==================================================================
    @FXML
    private void addNewLocation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddLocation.fxml"));
        Parent root = loader.load();
        AddLocationController controller = loader.getController();
        controller.display(user);
        controller.display(city);
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(scene);
        window.show();        
    }
//==================================================================
    @FXML
    private void showCityDetails(ActionEvent event) throws IOException {
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
    @FXML //when he is done with the city
    private void popUpWindow(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Look! ", "\n\nWe Hope that you Enjoy your trip. \n\nour loves <3");}
//==================================================================
    //set city labels
    private void setCity(){
        cities=Utility.getCityIdByEmail(user.getEmail());
        for(City c:cities)
            cboCitiesCard.getItems().add(c.getCityName());
        
        if(cities.isEmpty()){
            cityPane.setVisible(false);
            CityRec.setVisible(false);
        }
        else{
            comboChange();
            cboCitiesCard.getSelectionModel().selectFirst();
        }
    }
//==================================================================   
    //combobox listener
    public void comboChange(){
        cboCitiesCard.valueProperty().addListener(e-> {
            int index = cboCitiesCard.getSelectionModel().getSelectedIndex();
            city=cities.get(index);
            if("".equals(city.getHotel()))
                lbHotelName.setVisible(false);
            else{
                lbHotelName.setVisible(true);
                lbHotelName.setText("Hotel Name : "+city.getHotel());
            }
            lbCityName.setText("City Name: "+city.getCityName());
            lbAdata.setText("Arrived Date: "+city.getArrivalDate());
            lbRdata.setText("Return Date: "+ city.getReturnDate());
        });
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
    //receive user
    public void display(User user1) {
        user = user1; 
        setCity();
    }
//==================================================================
}