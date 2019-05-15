import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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
    private MenuItem save;
    private MenuItem left;
    private MenuItem center;
    private MenuItem right;
    private MenuItem copy;
    private MenuItem cut;
    private MenuItem paste;

    private TextArea textArea;


    private Scene scene;
    private VBox root;





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

        EventHandler<ActionEvent> ecouteurNewFile = event -> {//pas fonctionnel
            Stage newWindow = new Stage();
            newWindow.initOwner(primaryStage);
            newWindow.setTitle("Second Stage");
            newWindow.show();
        };


        newFile = new MenuItem("Nouveau");
        save   = new MenuItem("Enregistrer"  );

        left   = new MenuItem("Left"  );
        center = new MenuItem("Center");
        right  = new MenuItem("Right" );

        copy   = new MenuItem("Copier");
        cut    = new MenuItem("Couper");
        paste  = new MenuItem("Coller");

        menuFile = new Menu("Fichier");
        menuFile.getItems().addAll(newFile,save);
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
        center.setOnAction(ecouteurCentrer);
        copy.setOnAction(ecouteurCopy);
        cut.setOnAction(ecouteurCut);
        paste.setOnAction(ecouteurPaste);





        primaryStage.setTitle("MyNotes");
        primaryStage.setScene(scene);
        primaryStage.show();






    }


}
