/*
implemented by: Shahad malibari
documented by: Razan al-refaey
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 4 event to handle and they are:
(addCity - cancelNewCity - uploadCityPhoto - keyPress )

addCity: this method will checks the UI controls and make some validation on it.
then it is save the city object in the database.
then it is send the user object back to the Home controller to maintain the data there.
then return the user to the Home interface.

cancelNewCity: it is send the user object back to the Home controller to maintain the data there.
then return the user to the Home interface.

uploadCityPhoto: it will open a showOpenDialog and use a file chooser to make the user choose a photo
to add a path.

keyPress: whenever the user clicks enter the process of adding a new city begins.

*other method:
SendUserToAddCity (user):  which can be called from other controllers in the class to send 
a user object to this controllers so we can send it to other controllers or make use of some data linked to this object.

addC:this method contains the real process af adding a city and it is called from addCity and keyPress.
there is a file will be writen into the city information after the insertaion in the database.

cityExist: takes the received user and pull all cities linked to this user from the database then checks
if the city already exist or not.

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
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//==================================================================
public class AddCityController implements Initializable {

    @FXML
    private Label lblAddNewCity;
    @FXML
    private Button btnAddCity;
    @FXML
    private Button btnCancelNewCity;
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
    private Label error;
    @FXML
    private AnchorPane mainAnchor;
//==================================================================   
    private final FileChooser fileChooser = new FileChooser();
    private String photoPath;
    private static User user;
    private City city;
//==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
//==================================================================
    @FXML
    private void addCity(ActionEvent event) throws IOException {
        addC(event);}
//==================================================================
    @FXML
    private void cancelNewCity(ActionEvent event) throws IOException {
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
    private void uploadCityPhoto(ActionEvent event) {
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
    @FXML
    private void keyPress(KeyEvent event) throws IOException {
        KeyCode key = event.getCode();
        if(key==KeyCode.ENTER)
            addC(event);
    }
//==================================================================    
    //insert city into database
    private void addC(Event event) throws IOException{
        if(checkTf(tfFormCityName)&& checkTf(tfFormCityBudget)&& checkTf(tfFormCityMembers)&&checkDp(dpFormCityADate)&&checkDp(dpFormCityRDate)){
            if(checkTfNum(tfFormCityBudget)&&checkTfNum(tfFormCityMembers)){
                if(cityExists()){
                    Integer budget = Integer.valueOf(tfFormCityBudget.getText());
                    Integer members = Integer.valueOf(tfFormCityMembers.getText());
                    city=new City(tfFormCityName.getText(), tfFormCityHotel.getText(),user,dpFormCityADate.getValue().toString(), dpFormCityRDate.getValue().toString(), budget, members,photoPath);
                    Utility.insertNewCity(city);
                    
                    //write in file
                    try {
                        File file = new File("CitiesInformation.txt");
                        PrintWriter pwFile = new PrintWriter (file);
                        String line="The city Name is: "+tfFormCityName.getText()+"   "+"its Budget is: "+budget+"   "
                                  +"its Members is: "+members+"   "+"its Hotel is: "+tfFormCityHotel.getText()+"   "
                                  +"its A.Date is: "+dpFormCityADate.getValue().toString()+"\n";
                        pwFile.println(line);              
                        pwFile.close();
                        System.out.println("Write in CitiesInformation file is DONE!");
                    } catch (IOException ex) {}
                    
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
                    error.setText("Error: city already exists");
            }
            else
                error.setText("Error: Enter numbers only \nin budget and members textfields");
        }
        else
            error.setText("Error: Empty textfields");
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
    //check if city exists
    private boolean cityExists(){
        List<String> cityList=Utility.getCityNamesByEmail(user.getEmail());
        if(!(cityList.isEmpty())){
            for(String c:cityList){
                if(c.equals(tfFormCityName.getText()))
                    return false;//city exist
            }
        }
        return true;// does not exist
    }
//==================================================================        
    //receive user
    void display(User sentObject){
        user=sentObject;
        System.out.println("user name is: "+user.getUserName());
        dpFormCityADate.setValue(LocalDate.now());
        dpFormCityRDate.setValue(LocalDate.now());
    }
}
