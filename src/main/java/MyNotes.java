import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;



import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;


public class MyNotes extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    private Button save;
    private Button left;
    private Button center;
    private Button right;
    private Button copy;
    private Button cut;
    private Button paste;

    private TextArea textArea;


    private VBox root;
    private HBox commands;

    EventHandler<ActionEvent> ecouteurCentrer = event -> {
        textArea.setCenterShape(true);
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
        save   = new Button("Enregistrer"  );

        left   = new Button("Left"  );
        center = new Button("Center");
        right  = new Button("Right" );

        copy   = new Button("Copier");
        cut    = new Button("Couper");
        paste  = new Button("Coller");


        commands = new HBox(save,left,center,right,copy,cut,paste);
        commands.setAlignment(TOP_CENTER);
        commands.setPadding(new Insets(10));
        commands.setSpacing(20);
        commands.setPadding(new Insets(10));

        textArea = new TextArea();
        textArea.setWrapText(true);



        root = new VBox(commands,textArea);
        Scene scene = new Scene(root);
        root.setMinSize(scene.getWidth(),scene.getWidth());




        center.setOnAction(ecouteurCentrer);
        copy.setOnAction(ecouteurCopy);
        cut.setOnAction(ecouteurCut);
        paste.setOnAction(ecouteurPaste);




        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(620);
        primaryStage.setTitle("MyNotes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
