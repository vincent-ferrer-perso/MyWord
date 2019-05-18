

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.*;

import java.awt.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.Window;
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
    private Menu menuTexte;

    private HBox commandsShortcut;
    private Button newFileShortcut;
    private Button openShortcut;
    private Button saveShortcut;
    private MenuItem couleurShortcut;

    private ColorPicker colorPicker;

    private MenuItem newFile;
    private MenuItem open;
    private MenuItem save;
    private MenuItem left;
    private MenuItem center;
    private MenuItem right;
    private MenuItem couleurTexte;
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



    public void OpenFile(Stage stage){
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(stage);
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
            stage.setTitle(file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    ImageView creerImageViewShortcut(String nomImage){
        File fileImgSave = new File("./"+nomImage);
        String localUrl = fileImgSave.toURI().toString();
        Image imgSave = new Image(localUrl);
        ImageView imgView = new ImageView(imgSave);
        imgView.fitHeightProperty().setValue(20);
        imgView.fitWidthProperty().setValue(20);
        return imgView;
    }

    Optional<ButtonType> creerAlerteConfirmation(Alert nomAlerte, String titreAlerte, String action,String complement) {
        nomAlerte.setAlertType(CONFIRMATION);
        nomAlerte.setTitle(titreAlerte);
        nomAlerte.setHeaderText(null);
        nomAlerte.setContentText("Voulez-vous "+action+" ce fichier " + complement);
        nomAlerte.getButtonTypes().setAll(ButtonType.CANCEL,ButtonType.NO,ButtonType.YES);
        ImageView imgInterrogation = creerImageViewShortcut("Interrogation.png");
        imgInterrogation.setFitWidth(50);
        imgInterrogation.setFitHeight(50);
        nomAlerte.setGraphic(imgInterrogation);

        Optional<ButtonType> answer = nomAlerte.showAndWait();
        return answer;
    }


    EventHandler<ActionEvent> ecouteurCentrer = event -> {
        Text text = new Text();
        text.setText(textArea.getText());
                text.setTextOrigin(VPos.CENTER);
    };

    EventHandler<ActionEvent> ecouteurCouleurTexte = event -> {
        colorPicker = new ColorPicker();

        Scene sceneColor = new Scene(colorPicker);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setAlwaysOnTop(true);
        newWindow.setResizable(false);
        newWindow.setScene(sceneColor);
        newWindow.show();
        System.out.println(colorPicker.getValue());
    };

    EventHandler<ActionEvent> ecouteurChangeCouleurTexte = event ->{
        colorPicker = new ColorPicker();
        Text text = new Text();
        text.setFill(colorPicker.getValue());
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
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
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
            if(textArea.getText().length() != 0 || primaryStage.getTitle() != "MyNotes") {
                Alert alertOuvrirSansSauvegarder = new Alert(CONFIRMATION);
                Optional<ButtonType> rep;
                rep = creerAlerteConfirmation(alertOuvrirSansSauvegarder,
                        "Ouvrir un autre fichier",
                        "sauvegarder",
                        "avant d'ouvrir un autre fichier");
                if (rep.get() == ButtonType.YES || rep.get() == ButtonType.NO) {
                    if (rep.get() == ButtonType.YES) {
                        ecouteurSave.handle(event);
                    }
                    textArea.clear();
                    primaryStage.setTitle("MyNotes");
                    start(primaryStage);
                    OpenFile(primaryStage);
                }
            }else
                OpenFile(primaryStage);
        };//ecouteurOpen

        EventHandler<ActionEvent> ecouteurNewFile = event -> {
            if(textArea.getText().length() != 0) {
                Alert alertOuvrirSansSauvegarder = new Alert(CONFIRMATION);
                Optional<ButtonType> rep;
                rep = creerAlerteConfirmation(alertOuvrirSansSauvegarder,
                        "Ouvrir un nouveau fichier",
                        "sauvegarder",
                        "avant de créer un nouveau fichier");
                if (rep.get() == ButtonType.YES) {
                    ecouteurSave.handle(event);
                    start(primaryStage);
                }
                else if(rep.get() == ButtonType.NO){
                    textArea.clear();
                    start(primaryStage);
                }

            }
        };//ecouteurSaveNewFile


        newFile = new MenuItem("Nouveau");
        open    = new MenuItem("Ouvrir");
        save    = new MenuItem("Enregistrer");

        left   = new MenuItem("Left"  );
        center = new MenuItem("Center");
        right  = new MenuItem("Right" );

        copy   = new MenuItem("Copier");
        cut    = new MenuItem("Couper");
        paste  = new MenuItem("Coller");

        couleurTexte = new MenuItem("Couleur");

        newFileShortcut = new Button();
        openShortcut = new Button();
        saveShortcut = new Button();
        couleurShortcut = new MenuItem();

        menuFile = new Menu("Fichier");
        menuFile.getItems().addAll(newFile,open,save);
        menuPositionTexte = new Menu("Position");
        menuPositionTexte.getItems().addAll(left,center,right);
        menuTexte = new Menu("Texte");
        menuTexte.getItems().addAll(menuPositionTexte,couleurTexte);
        menuCopier = new Menu("Copier");
        menuCopier.getItems().addAll(copy,cut,paste);

        menuBar = new MenuBar(menuFile,menuTexte,menuCopier);
        couleurTexte.setOnAction(ecouteurCouleurTexte);


        newFileShortcut.setGraphic(creerImageViewShortcut("nouveauFichier.png"));
        openShortcut.setGraphic(creerImageViewShortcut("ouvrir.png"));
        saveShortcut.setGraphic(creerImageViewShortcut("disquette.png"));
        couleurShortcut.setGraphic(colorPicker = new ColorPicker());

        Label labelVide = new Label();
        labelVide.setPrefWidth(6);
        couleurShortcut.setOnAction(ecouteurChangeCouleurTexte);

        commandsShortcut = new HBox(labelVide,newFileShortcut,openShortcut,saveShortcut,couleurShortcut.getGraphic());
        commandsShortcut.setSpacing(10);


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
        newFileShortcut.setOnAction(ecouteurNewFile);
        newFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));

        open.setOnAction(ecouteurOpen);
        openShortcut.setOnAction(ecouteurOpen);
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        save.setOnAction(ecouteurSave);
        saveShortcut.setOnAction(ecouteurSave);
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        center.setOnAction(ecouteurCentrer);
        center.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

        couleurTexte.setOnAction(ecouteurCouleurTexte);

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
