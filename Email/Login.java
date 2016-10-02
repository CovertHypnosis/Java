package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {
	BorderPane pane = new BorderPane();
	// Properties
	Text txtTitle = new Text("Sign in Form");
	TextField tfUsername = new TextField();
	Text txtStatus = new Text();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane grid = new GridPane();
		pane.setCenter(grid);
		pane.setTop(txtTitle);
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		// Text that goes on top of login form
		
		txtTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
		PasswordField pwBox = new PasswordField();
		grid.add(txtTitle, 0, 0, 2, 1);
		grid.add(new Label("Username:"), 0, 1);
		grid.add(tfUsername, 1, 1);
		grid.add(new Label("Password:"), 0, 2);
		grid.add(pwBox, 1, 2);
		
		Button btnSignIn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btnSignIn);
		grid.add(hbBtn, 1, 4);
		
		
		txtStatus.setId("txtStatus");
		grid.add(txtStatus, 1, 6);

		
		Scene scene = new Scene(pane, 300, 275);
		scene.getStylesheets().add(Login.class.getResource("Login.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login Panel");
		primaryStage.show();
		
		btnSignIn.setOnAction(e -> {
			txtStatus.setText("");
			if (Email.isValidEmailAddress(tfUsername.getText().trim()) && (pwBox.getText().length() > 4)) {
				txtStatus.setText("Signing in!");
				txtStatus.setFill(Color.GREEN);
				Email.setUSERNAME(tfUsername.getText().trim());
				Email.setPASSWORD(pwBox.getText().trim());
				pane.getChildren().remove(grid);
				takeUserToNextStage();
			} else {
				txtStatus.setText("Try Again!");
				txtStatus.setFill(Color.DARKRED);
			}
		});
	}
	
	private void takeUserToNextStage() {
		VBox box = new VBox(10);
		HBox vbTo = new HBox(10);
		HBox vbSubject = new HBox(10);
		HBox vbMessage = new HBox(10);
		pane.setCenter(box);
		pane.setTop(txtTitle);
		TextField tfTo = new TextField();
		TextField tfSubject = new TextField();
		TextArea taMessage = new TextArea();
		Label lblTo = new Label("To: ");
		Label lblSubject = new Label("Subject: ");
		Label lblMessage = new Label("Message: ");
		
		vbTo.getChildren().addAll(lblTo, tfTo);
		vbSubject.getChildren().addAll(lblSubject, tfSubject);
		vbMessage.getChildren().addAll(lblMessage, taMessage);
		box.getChildren().addAll(vbTo, vbSubject, vbMessage);
		VBox vBox = new VBox(10);
		Button btSend = new Button("Send");
		btSend.setAlignment(Pos.CENTER);
		vBox.getChildren().add(btSend);
		txtTitle.setText("Hello " + tfUsername.getText().trim() + ", Welcome To Send Form");
		pane.setBottom(vBox);
		
		box.getChildren().addAll(lblTo, tfTo, lblSubject, tfSubject, lblMessage, taMessage);

		
		btSend.setOnAction(e -> {
			if (tfSubject.getText().trim().length() > 1 && taMessage.getText().trim().length() > 1
					&& Email.isValidEmailAddress(tfTo.getText().trim())) {
				txtStatus.setText("Sending!");
				txtStatus.setFill(Color.GREEN);
				Email.setRECIPIENT(tfTo.getText().trim());
				Email.setSUBJECT(tfSubject.getText().trim());
				Email.setMESSAGE(taMessage.getText().trim());
				Email.sendFromGmail();
			} else {
				txtStatus.setText("Try Again!");
				txtStatus.setFill(Color.DARKRED);
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
		
}
