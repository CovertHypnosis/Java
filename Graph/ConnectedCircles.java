import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ConnectedCircles extends Application {
	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(new CirclePane(), 450, 350);	
		primaryStage.setTitle("Connected Circles");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/** Pane for displaying circles */
	class CirclePane extends Pane {
		public CirclePane() {
			this.setOnMouseClicked(e -> {
				if (!isInsideACircle(new Point2D(e.getX(), e.getY()))) {
					getChildren().add(new Circle(e.getX(), e.getY(), 20));
					colorIfConnected();
				}
			});
		}

		/** Returns true if the point is inside an existing circle */
		private boolean isInsideACircle(Point2D p) {
			for (Node circle: this.getChildren())
				if (circle.contains(p))
					return true;

			return false;
		}

		/** Color all circles if they are connected */
		private void colorIfConnected() {
			if (getChildren().size() == 0)
				return;

			List<AbstractGraph.Edge> edges = new ArrayList<>();
			for (int i = 0; i < getChildren().size(); i++)
				for (int j = i + 1; j < getChildren().size(); j++)
					if (overlaps((Circle)(getChildren().get(i)),
							(Circle)(getChildren().get(j)))) {
						edges.add(new AbstractGraph.Edge(i, j));
						edges.add(new AbstractGraph.Edge(j, i));
					}

			Graph<Node> graph = new UnweightedGraph<>((List<Node>)getChildren(), edges);
			AbstractGraph<Node>.Tree tree = graph.dfs(0);
			boolean isAllCirclesConnected = getChildren().size() == tree.getNumberOfVerticesFound();

			for (Node circle: getChildren()) {
				if (isAllCirclesConnected) {
					((Circle)circle).setFill(Color.RED);
					((Circle)circle).setStroke(Color.BLACK);
				} else {
					((Circle)circle).setStroke(Color.BLACK);
					((Circle)circle).setFill(Color.WHITE);
				}
			}
		}

	}

	public static boolean overlaps(Circle circle1, Circle circle2) {
		return new Point2D(circle1.getCenterX(), circle1.getCenterY()).
				distance(circle2.getCenterX(), circle2.getCenterY()) 
				<= circle1.getRadius() + circle2.getRadius();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
