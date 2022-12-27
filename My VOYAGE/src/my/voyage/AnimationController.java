/*
implemented by: Manar al-abdli
modified by: Razan al-refaey
-------------------------
this controller is especial because it is for the animation.

the source is: 
https://www.youtube.com/watch?v=UdGiuDDi7Rg&t=4s
*/
//==================================================================
package my.voyage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
//==================================================================
public class AnimationController implements Initializable {
    @FXML
    AnchorPane animationClick;
    @FXML
    ImageView Car_img;
    @FXML
    ImageView tree_img;
    @FXML
    ImageView bag_img1;
    @FXML
    ImageView bag_img2;
    @FXML
    ImageView name_img;
    @FXML
    private ImageView Back_img;
//================================================================== 
    @FXML
    private void showSceneOne(MouseEvent event) throws IOException {
         Utility.sceneChanger(getClass(), event, "Welcome.fxml");}
//==================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // transition for car
        TranslateTransition transition_car = new TranslateTransition();
        transition_car.setNode(Car_img);
        transition_car.setDuration(Duration.millis(1700));
        transition_car.setByX(390);
        transition_car.play();
        
        // transition for tree
        TranslateTransition transition_tree = new TranslateTransition();
        transition_tree.setNode(tree_img);
        transition_tree.setDuration(Duration.millis(1700));
        transition_tree.setByX(-300);
        transition_tree.play();
        
       // transition for bag1
        TranslateTransition transition_bag1 = new TranslateTransition();
        transition_bag1.setNode(bag_img1);
        transition_bag1.setByX(260);
        transition_bag1.setDuration(Duration.millis(1900));
        transition_bag1.play();
        
        // transition for bag2
        TranslateTransition transition_bag2 = new TranslateTransition();
        transition_bag2.setNode(bag_img2);
        transition_bag2.setByX(400);
        transition_bag2.setDuration(Duration.millis(1900));
        transition_bag2.play();
        
        // transition for name
        TranslateTransition transition_name = new TranslateTransition();
        transition_name.setNode(name_img);
        transition_name.setByY(100);
        transition_name.setDuration(Duration.millis(2000));
        transition_name.play();
    }  
//==================================================================
    
}
