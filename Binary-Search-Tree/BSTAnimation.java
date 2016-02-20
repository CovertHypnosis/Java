import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BSTAnimation extends Application {
	@Override
	public void start(Stage primaryStage) {
		BST<Integer> tree = new BST<>();
		
		BorderPane pane = new BorderPane();
		BTView view = new BTView(tree);
		pane.setCenter(view);
		
		TextField tfKey = new TextField();
		tfKey.setPrefColumnCount(3);
		tfKey.setAlignment(Pos.BASELINE_RIGHT);
		Button btInsert = new Button("Insert");
		Button btDelete = new Button("Delete");
		HBox hBox = new HBox(5);
		hBox.getChildren().addAll(new Label("Enter a key: "), tfKey, btInsert, btDelete);
		hBox.setAlignment(Pos.CENTER);
		pane.setBottom(hBox);
		
		btInsert.setOnAction(e -> {
			int key = Integer.parseInt(tfKey.getText());
			if (tree.search(key)) {
				view.displayTree();
				view.setStatus(key + " is already in the tree");
			} else {
				tree.insert(key);
				view.displayTree();
				view.setStatus(key + " is inserted in the tree");
			}
		});
		
		btDelete.setOnAction(e -> {
			int key = Integer.parseInt(tfKey.getText());
			if (!tree.search(key)) {
				view.displayTree();
				view.setStatus(key + " is not in the tree");
			} else {
				tree.delete(key);
				view.displayTree();
				view.setStatus(key + " is deleted from the tree");
			}
		});
		
		pane.widthProperty().addListener(e -> {view.displayTree();});
		pane.heightProperty().addListener(e -> {view.displayTree();});
		
		Scene scene = new Scene(pane, 450, 250);
		primaryStage.setTitle("BSTAnimation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
