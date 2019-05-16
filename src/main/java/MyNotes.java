

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.*;

import java.awt.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.DirectoryChooser;
import sun.applet.Main;

import static javafx.geometry.Pos.*;


public class MyNotes extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    private MenuBar menuBar;
    private Menu menuFile;
    private Menu menuPositionTexte;
    private Menu menuCopier;

    private MenuItem newFile;
    private MenuItem open;
    private MenuItem save;
    private MenuItem left;
    private MenuItem center;
    private MenuItem right;
    private MenuItem copy;
    private MenuItem cut;
    private MenuItem paste;

    private TextArea textArea;

    private FileReader fileRead;
    private FileWriter fileWriter;
    private FileChooser fileChooser;


    private Scene scene;
    private VBox root;





    private Desktop desktop;




    EventHandler<ActionEvent> ecouteurCentrer = event -> {
        //à implementer
    };

    EventHandler<ActionEvent> ecouteurCopy = event -> {
        textArea.copy();
    };
    EventHandler<ActionEvent> ecouteurCut = event -> {
        textArea.cut();
    };
    EventHandler<ActionEvent> ecouteurPaste = event -> {
        textArea.paste();
    };



    @Override
    public void start(Stage primaryStage) {

        EventHandler<ActionEvent> ecouteurOpen = event -> {
            fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            BufferedReader br;
            String ligneLu;
            try{
                fileRead = new FileReader(file);
                br = new BufferedReader(fileRead);
                while((ligneLu = br.readLine()) != null)
                {
                    textArea.appendText(ligneLu+'\n');
                }
                br.close();
                primaryStage.setTitle(file.getName());
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        };

        EventHandler<ActionEvent> ecouteurSave = event -> {
            fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(primaryStage);
            try {
                fileWriter = new FileWriter(file);
                fileWriter.write(textArea.getText());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        };

        EventHandler<ActionEvent> ecouteurNewFile = event -> {//pas fonctionnel
            Stage newWindow = new Stage();
            newWindow.initOwner(primaryStage);
            newWindow.setTitle("Second Stage");
            newWindow.show();
        };


        newFile = new MenuItem("Nouveau");
        open    = new MenuItem("Ouvrir");
        save    = new MenuItem("Enregistrer"  );

        left   = new MenuItem("Left"  );
        center = new MenuItem("Center");
        right  = new MenuItem("Right" );

        copy   = new MenuItem("Copier");
        cut    = new MenuItem("Couper");
        paste  = new MenuItem("Coller");

        menuFile = new Menu("Fichier");
        menuFile.getItems().addAll(newFile,open,save);
        menuPositionTexte = new Menu("Position");
        menuPositionTexte.getItems().addAll(left,center,right);
        menuCopier = new Menu("Copier");
        menuCopier.getItems().addAll(copy,cut,paste);

        menuBar = new MenuBar(menuFile,menuPositionTexte,menuCopier);

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefRowCount(150);



        root = new VBox(menuBar,textArea);
        root.setFillWidth(true);

        scene = new Scene(root);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(320);
        root.setPrefHeight(350);



        newFile.setOnAction(ecouteurNewFile);
        open.setOnAction(ecouteurOpen);
        save.setOnAction(ecouteurSave);
        center.setOnAction(ecouteurCentrer);
        copy.setOnAction(ecouteurCopy);
        cut.setOnAction(ecouteurCut);
        paste.setOnAction(ecouteurPaste);





        primaryStage.setTitle("MyNotes");
        primaryStage.setScene(scene);
        primaryStage.show();






    }


}
