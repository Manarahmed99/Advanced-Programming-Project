/*
implemented by: shahad malibari
documented by: Razan al-refaey
modified by: Razan al-refaey
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

*other method:
display (user):  which can be called from other controllers in the class to send 
a user object to this controllers so we can send it to other controllers or make use of some data linked to this object.

comboChange: we have a combobox that contain all the cities linked to the received user.
this method has a listner on a combobox so when ever it changes the calculations of the budget begins.

setCity:this method receive the user so it can retrive the linked cities to that user
from the database and add it to the combobox.
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//==================================================================
public class BudgetReportController implements Initializable {

    @FXML
    private Button btnHome;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnTodoList;
    @FXML
    private Button btnBBudgetReport;
    @FXML
    private Label lblTrackedCity;
    @FXML
    private ProgressBar pbBudget;
    @FXML
    private ComboBox<String> cboCitiesCard;
    @FXML
    private Pane pane;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblRemained;
//==================================================================
    private List <City> cities=new ArrayList();
    private List <Location> locations=new ArrayList();
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
    private void goToDo(ActionEvent event) throws IOException {
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
    //combobox listener
    public void comboChange(){
        cboCitiesCard.valueProperty().addListener(e-> {
            locations.clear();
            int index = cboCitiesCard.getSelectionModel().getSelectedIndex();
            city=cities.get(index);
            List<Location> list=Utility.getLocationListByEmail(user.getEmail());
            for(Location l:list){
                if(l.getCityId().getCityName().equals(city.getCityName()) && ( (l.getCityId().getUser().getUserId() ) == user.getUserId()) ){
                    locations.add(l);
                }
            }
            int total=city.getCityBudget();
            lblTrackedCity.setText("City name: "+city.getCityName());
            lblTotal.setText("Total budget: "+total);
            
            int paid=0;
            for(Location l:locations){
                paid=paid+l.getLocationBudget();
            }
            lblRemained.setText("Remain budget: "+(total-paid));
            double progress=(double)paid/total;
            pbBudget.setProgress(progress);
            
        });
    }
//==================================================================    
    //load cities into combobox
    void setCity(){
        cities=Utility.getCityIdByEmail(user.getEmail());
        for(City c:cities){
            cboCitiesCard.getItems().add(c.getCityName());
        }
        if(cities.isEmpty()){
            pane.setVisible(false);
        }
        else{
            comboChange();
            cboCitiesCard.getSelectionModel().selectFirst();
        }
    }
//================================================================== 
    //receive user
    void display(User sentObject){
        user=sentObject;
        System.out.println("user name is: "+user.getUserName());
        setCity();
    }
}
