/*
implemented by: Maarib al-sulimani
documented by: Razan al-refaey
modified by: Razan al-refaey & shahad malibari
-------------------------
this is a Controller class to implement every thing in the linked FXML file

this Controller has 8 event to handle and they are:
(goHome - goBudgetReport - goProfile - goTodoList - AddToToDoList - confirmPopWindow - moveFromToDoToDone - moveFromDoneToToDo)

goHome: sends the received user object to the home controller again to maintain the data exist in the home interface.
goBudgetReport: sends the received user object to BudgetReport controller so it can use the object
in some operations there.
goProfile: sends the received user object to itself again to maintain the data in the controller.
goTodoList: sends the received user object to todoList controller so it can use the object
in some operations there.

AddToToDoList: this take the new instance of the TodoList object and add it to the viewlist & into the databse.
confirmPopWindow: clears the 2 listviews and delete all user's tasks from the database.
moveFromToDoToDone :  will get the user selection from the listview then we will add it to the Done
listview and remove it from ToDo listview.then we update the state of the task in the database.
moveFromDoneToToDo : will get the user selection from the listview then we will add it to the ToDo
listview and remove it from Done listview.then we update the state of the task in the database.

other methods: 
*display: which can be called from other controllers in the class to send 
a user object to this controllers so we can send it to other controllers or make use of some data linked to this object.

*sendUserToDoList : this method will retrive all user's tasks from the database and start the categorizing
if the task boolean attribute "finished" = true then it will be added to the Done listview, otherwise
it will be added to the ToDo listview.
*/
//==================================================================
package my.voyage;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//==================================================================
public class TodoListController implements Initializable {

    @FXML
    private Button btnFromToDoToDone;
    @FXML
    private Button btnFromDoneToToDo;
    @FXML
    private Button btnClearLv;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnTodoList;
    @FXML
    private Button btnBudgetReport;
    @FXML
    private Button btnAddLv;
    @FXML
    private TextField tfAddLv;
    @FXML
    private ListView<String> lvDone  ;
    @FXML
    private ListView<String> lvTodo;
    //==================================================================
    private ObservableList<String> obToDo = FXCollections.observableArrayList();
    private ObservableList<String> obDone = FXCollections.observableArrayList();
    private User user;
    //==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    //==================================================================
    @FXML
    private void moveFromToDoToDone(ActionEvent event) {     //false -> true
        String str = (String) lvTodo.getSelectionModel().getSelectedItem();
        if (str != null){
            lvTodo.getSelectionModel().clearSelection();
            obToDo.remove(str);
            lvTodo.setItems(obToDo);
            obDone.add(str);
            lvDone.setItems(obDone);
            List<TodoList> IList = Utility.getTodoListByEmail(user.getEmail());
               
            for(TodoList i: IList){         
                if(i.getDescription().equals(str)){                 
                    i.setFinished(true);
                    Utility.updateTask(i);
                    break;
                }
            }
        }
    }
    //==================================================================
    @FXML
    private void moveFromDoneToToDo(ActionEvent event) {
        String str = (String) lvDone.getSelectionModel().getSelectedItem();
        if (str != null){
            lvDone.getSelectionModel().clearSelection();
            obDone.remove(str);
            lvDone.setItems(obDone);
            obToDo.add(str);
            lvTodo.setItems(obToDo);
            List<TodoList> IList = Utility.getTodoListByEmail(user.getEmail());
            for(TodoList i: IList){
                if(i.getDescription().equals(str)){
                    i.setFinished(false);
                    Utility.updateTask(i);
                    break;
                }        
            }
        }
    }
    //==================================================================
    @FXML
    private void confirmPopUpWindow(ActionEvent event) { 
         Utility.clearTasks(user);
         obDone.clear();
         lvDone.setItems(obDone);
         obToDo.clear();   
         lvTodo.setItems(obToDo);
    }
    //==================================================================
    @FXML
    private void AddToToDoList(ActionEvent event) {   
        obToDo.add(tfAddLv.getText());
        lvTodo.setItems(obToDo);
        TodoList todo = new TodoList(user,tfAddLv.getText(), false);
        Utility.insertNewTask(todo);
        tfAddLv.setText("");
    }
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
    //load todo list into listviews
    public void sendUserToDoList(){
        List<TodoList> list = Utility.getTodoListByEmail(user.getEmail());
        if(!list.isEmpty()){
            for(TodoList i: list){
                if(i.isFinished()){
                    obDone.add(i.getDescription()); 
                    lvDone.setItems(obDone);
                }
                else{
                    obToDo.add(i.getDescription());
                    lvTodo.setItems(obToDo);
                } 
            }
            for(TodoList i: list){
                System.out.println(i.getDescription());
            }
        }  
    }
    //==================================================================
    //receive user
    public void display(User user1) {
        user = user1; 
        sendUserToDoList();}
    //==================================================================
}