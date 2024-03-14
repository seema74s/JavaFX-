package com.example.imagegalleryapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class HelloApplication extends Application {

    private List<String> imagePaths = Arrays.asList(
            "images/img1.jpg",
            "images/img2.jpg",
            "images/img3.jpg",
            "images/img4.jpg",
            "images/img5.jpg",
            "images/img6.jpg"
    );
    private int currentIndex = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        for (int i = 0; i < Math.min(6, imagePaths.size()); i++) {
            String imagePath = imagePaths.get(i);
            Image image = new Image(getClass().getClassLoader().getResource(imagePath).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            int row = i / 3;
            int col = i % 3;
            gridPane.add(imageView, col, row);

            int finalI = i;
            imageView.setOnMouseEntered(event -> showButtons(imageView));
            imageView.setOnMouseExited(event -> hideButtons(imageView));

            imageView.setOnMouseClicked(event -> {
                currentIndex = finalI;
                showImage(primaryStage, imagePaths.get(finalI));
            });
        }

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            if (currentIndex < imagePaths.size() - 1) {
                currentIndex++;
                showImage(primaryStage, imagePaths.get(currentIndex));
            }
        });

        Button prevButton = new Button("Previous");
        prevButton.setOnAction(event -> {
            if (currentIndex > 0) {
                currentIndex--;
                showImage(primaryStage, imagePaths.get(currentIndex));
            }
        });

        Button backButton = new Button("Back to Thumbnails");
        backButton.setOnAction(event -> start(primaryStage));

        HBox navigationBox = new HBox(10, prevButton, nextButton, backButton);
        VBox fullSizeBox = new VBox(new javafx.scene.control.Label("Full Size Image"), navigationBox);
        fullSizeBox.setVisible(false);

        borderPane.setTop(new VBox(new javafx.scene.control.Label("Thumbnails"), gridPane));
        borderPane.setCenter(fullSizeBox);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Gallery");
        primaryStage.show();
    }

    private void showButtons(ImageView imageView) {
        VBox parent = (VBox) imageView.getParent().getParent();
        HBox navigationBox = (HBox) parent.getChildren().get(1);
        navigationBox.setVisible(true);
    }

    private void hideButtons(ImageView imageView) {
        VBox parent = (VBox) imageView.getParent().getParent();
        HBox navigationBox = (HBox) parent.getChildren().get(1);
        navigationBox.setVisible(false);
    }

    private void showImage(Stage primaryStage, String imagePath) {
        Image image = new Image(getClass().getClassLoader().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600); // Set the width to fit the artboard
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            if (currentIndex < imagePaths.size() - 1) {
                currentIndex++;
                showImage(primaryStage, imagePaths.get(currentIndex));
            }
        });

        Button prevButton = new Button("Previous");
        prevButton.setOnAction(event -> {
            if (currentIndex > 0) {
                currentIndex--;
                showImage(primaryStage, imagePaths.get(currentIndex));
            }
        });

        Button backButton = new Button("Back to Thumbnails");
        backButton.setOnAction(event -> start(primaryStage));

        HBox navigationBox = new HBox(10, prevButton, nextButton, backButton);
        VBox fullSizeBox = new VBox(new javafx.scene.control.Label("Full Size Image"), imageView, navigationBox);
        fullSizeBox.setAlignment(Pos.CENTER); // Center the VBox content
        fullSizeBox.setPadding(new Insets(10)); // Add padding for better spacing

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(fullSizeBox);

        Scene scene = new Scene(borderPane, 600, 600); // Set the scene size to fit the full-size image
        boolean add = scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
