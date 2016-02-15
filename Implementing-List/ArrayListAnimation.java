import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ArrayListAnimation extends Application {
	private MyArrayList<Integer> list = new MyArrayList<>();
	private ListView view = new ListView();
	private Button btSearch = new Button("Search");
	private Button btInsert = new Button("Insert");
	private Button btDelete = new Button("Delete");
	private Button btTrimToSize = new Button("TrimToSize");
	private TextField tfNumber = new TextField();
	private TextField tfIndex = new TextField();
	
	@Override
	public void start(Stage primaryStage) {
		HBox hBox = new HBox(5);
		hBox.getChildren().addAll(new Label("Enter a value: "), tfNumber, new Label("Enter an index: "),
				tfIndex, btSearch, btInsert, btDelete, btTrimToSize);
		hBox.setAlignment(Pos.CENTER);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(view);
		borderPane.setBottom(hBox);
		
		Label lblStatus = new Label();
		borderPane.setTop(lblStatus);
		BorderPane.setAlignment(lblStatus, Pos.CENTER);
		
		Scene scnee = new Scene(borderPane, 500, 250);
		primaryStage.setTitle("ArrayList animation");
		primaryStage.setScene(scnee);
		primaryStage.show();
		
		view.repaint();
		tfNumber.setPrefColumnCount(2);
		tfIndex.setPrefColumnCount(2);
		
		btSearch.setOnAction(e -> {
			lblStatus.setText("");
			if (!list.contains(Integer.parseInt(tfNumber.getText()))) {
				lblStatus.setText("key is not in the list");
			} else {
				lblStatus.setText("key is in the list");
			}
			
			view.repaint();
		});
		
		btInsert.setOnAction(e -> {
			lblStatus.setText("");
			if (tfIndex.getText().trim().length() > 0)
				list.add(Integer.parseInt(tfIndex.getText(), Integer.parseInt(tfNumber.getText())));
			else
				list.add(Integer.parseInt(tfNumber.getText()));
			
			view.repaint();
		});
		
		btDelete.setOnAction(e -> {
			lblStatus.setText("");
			if (!list.contains(Integer.parseInt(tfNumber.getText()))) {
				lblStatus.setText("Key is not in the list");
			} else {
				lblStatus.setText("key is deleted from the list");
				list.remove(new Integer(Integer.parseInt(tfNumber.getText())));
			}
			
			view.repaint();
		});
		
		btTrimToSize.setOnAction(e -> {
			list.trimToSize();
			view.repaint();
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public class ListView extends Pane {
		private int startingX = 20;
		private int startingY = 20;
		private int boxWidth = 30;
		private int boxHeight = 20;
		
		protected void repaint() {
			getChildren().clear();
			
			int x = startingX + 10;
			int y = startingY + 10;
			
			getChildren().add(new Text(startingX + 80, startingY, "size = " + list.size() + " and capacity = "
					+ list.getCapacity()));
			if (list.size() == 0) {
				getChildren().add(new Text(startingX, startingY, "list is empty."));
			} else {
				getChildren().add(new Text(startingX, startingY, "Array list"));
				
				for (int i = 0; i < list.size(); i++) {
					Rectangle rectangle = new Rectangle(x, y, boxWidth, boxHeight);
					rectangle.setFill(Color.WHITE);
					rectangle.setStroke(Color.BLACK);
					getChildren().add(rectangle);
					getChildren().add(new Text(x + 10, y + 15, list.get(i) + ""));
					x = x + boxWidth;
				}
			}
		}
	}
	
	
}
