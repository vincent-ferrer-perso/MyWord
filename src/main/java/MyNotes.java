

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.*;

import java.awt.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.DirectoryChooser;
import sun.applet.Main;

import javax.imageio.ImageIO;

import static javafx.geometry.Pos.*;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;


public class MyNotes extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    private MenuBar menuBar;
    private Menu menuFile;
    private Menu menuPositionTexte;
    private Menu menuCopier;

    private HBox commandsShortcut;
    private Button saveShortcut;


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

    private String currentWorkingDir;
    private FileReader fileRead;
    private FileWriter fileWriter;
    private FileChooser fileChooser;


    private Scene scene;
    private VBox root;





    private Desktop desktop;

    Optional<ButtonType> creerAlerteConfirmation(Alert nomAlerte, String titreAlerte, String action,String complement) {
        nomAlerte.setAlertType(CONFIRMATION);
        nomAlerte.setTitle(titreAlerte);
        nomAlerte.setHeaderText(null);
        nomAlerte.setContentText("Voulez-vous "+action+" ce fichier " + complement);
        Optional<ButtonType> answer = nomAlerte.showAndWait();
        return answer;
    }


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

        EventHandler<ActionEvent> ecouteurSave = event -> {
            if (primaryStage.getTitle() == "MyNotes") {
                fileChooser = new FileChooser();
                File file = fileChooser.showSaveDialog(primaryStage);
                currentWorkingDir = file.getParent();
                try {
                    fileWriter = new FileWriter(file);
                    fileWriter.write(textArea.getText());
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setTitle(file.getName());
            }else{
                try {

                    FileWriter fw = new FileWriter(currentWorkingDir+"/"+primaryStage.getTitle());
                    fw.write(textArea.getText());
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };//ecouteurSave

        EventHandler<ActionEvent> ecouteurOpen = event -> {
            Alert alertOuvrirSansSauvegarder =new Alert(CONFIRMATION);
            Optional<ButtonType> rep;
            rep =  creerAlerteConfirmation(alertOuvrirSansSauvegarder,
                    "Ouvrir un autre fichier",
                    "sauvegarder",
                    "avant d'ouvrir un autre fichier");
            if (rep.get() == ButtonType.OK) {
                ecouteurSave.handle(event);
                textArea.clear();
                fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(primaryStage);
                BufferedReader br;
                String ligneLu;
                try {
                    fileRead = new FileReader(file);
                    br = new BufferedReader(fileRead);
                    currentWorkingDir = file.getParent();
                    while ((ligneLu = br.readLine()) != null) {
                        textArea.appendText(ligneLu + '\n');
                    }
                    br.close();
                    primaryStage.setTitle(file.getName());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };//ecouteurOpen

        EventHandler<ActionEvent> ecouteurNewFile = event -> {
            Alert alertOuvrirSansSauvegarder =new Alert(CONFIRMATION);
            Optional<ButtonType> rep;
            rep =  creerAlerteConfirmation(alertOuvrirSansSauvegarder,
                                   "Ouvrir un nouveau fichier",
                                      "sauvegarder",
                                 "avant de créer un nouveau fichier");
            if (rep.get() == ButtonType.OK){
                ecouteurSave.handle(event);
            }
            start(primaryStage);
        };//ecouteurSaveNewFile


        newFile = new MenuItem("Nouveau");
        open    = new MenuItem("Ouvrir");
        save    = new MenuItem("Enregistrer"  );

        left   = new MenuItem("Left"  );
        center = new MenuItem("Center");
        right  = new MenuItem("Right" );

        copy   = new MenuItem("Copier");
        cut    = new MenuItem("Couper");
        paste  = new MenuItem("Coller");

        saveShortcut = new Button();

        menuFile = new Menu("Fichier");
        menuFile.getItems().addAll(newFile,open,save);
        menuPositionTexte = new Menu("Position");
        menuPositionTexte.getItems().addAll(left,center,right);
        menuCopier = new Menu("Copier");
        menuCopier.getItems().addAll(copy,cut,paste);

        menuBar = new MenuBar(menuFile,menuPositionTexte,menuCopier);


        saveShortcut.setStyle("-fx-background-image: disquette'.'png;");//pas image


        commandsShortcut = new HBox(saveShortcut);


        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefRowCount(150);



        root = new VBox(menuBar,commandsShortcut,textArea);
        root.setFillWidth(true);

        scene = new Scene(root);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(320);
        root.setPrefHeight(350);



        newFile.setOnAction(ecouteurNewFile);
        newFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));

        open.setOnAction(ecouteurOpen);
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        save.setOnAction(ecouteurSave);
        saveShortcut.setOnAction(ecouteurSave);
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        center.setOnAction(ecouteurCentrer);
        center.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        copy.setOnAction(ecouteurCopy);
        copy.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        cut.setOnAction(ecouteurCut);
        cut.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        paste.setOnAction(ecouteurPaste);
        paste.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));





        primaryStage.setTitle("MyNotes");
        primaryStage.setScene(scene);
        primaryStage.show();






    }


}
