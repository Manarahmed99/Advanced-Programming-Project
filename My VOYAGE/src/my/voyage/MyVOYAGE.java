/*
MY VOYAGE
-------------------------
team members: 
Razan al-refaey
Shahad malibari
Maarib al-sulimani
Manar al-abdli
Mafaz basalamah

** the source of the audio code is :
//https://www.youtube.com/watch?v=BZhvnASk1b0
*/
//==================================================================
package my.voyage;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
//==================================================================
public class MyVOYAGE extends Application { 
    @Override
    public void start(Stage stage) throws Exception {
        bgSounds();
        Parent root = FXMLLoader.load(getClass().getResource("Animation.fxml")); 
        Scene scene = new Scene(root);
        stage.setTitle("My Voyage");
        stage.setScene(scene);
        stage.show();}
    //==================================================================
        /**
         * @param args the command line arguments
         */
    public static void main(String[] args) {
            launch(args);  }
    //==================================================================
    MediaPlayer mediaPlayer; 
    public void bgSounds() {
        String s = "src\\my\\voyage\\oceanSound.mp3";
        Media h = new Media(new File(s).toURI().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(5);
        mediaPlayer.play();
    }
    //==================================================================
    @Override
    public void stop() {    System.exit(0);    }
    //==================================================================
}
