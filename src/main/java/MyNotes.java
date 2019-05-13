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
import javafx.stage.Stage;



public class MyNotes extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    private Button save;
    private Button left;
    private Button center;
    private Button right;

    private TextArea textArea;

    private VBox root;
    private HBox commands;

    EventHandler<ActionEvent> ecouteurCentrer = event -> {
        textArea.setStyle("text-align: center;");
    };



    @Override
    public void start(Stage primaryStage) {
        save   = new Button("Enregistrer"  );
        left   = new Button("Left"  );
        center = new Button("Center");
        right  = new Button("Right" );


        commands = new HBox(save,left,center,right);
        commands.setAlignment(Pos.TOP_CENTER);
        commands.setPadding(new Insets(10));
        commands.setSpacing(20);
        commands.setPadding(new Insets(10));

        textArea = new TextArea();
        textArea.setPrefSize(450,250);
        textArea.setMaxSize(450,250);

        root = new VBox(commands,textArea);


        center.setOnAction(ecouteurCentrer);

        Scene scene = new Scene(root);
        primaryStage.setTitle("MyNotes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
