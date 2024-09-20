package org.example.routefinder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;

public class HelloController {
    @FXML private TextField startingPointField, destinationField;
    @FXML private ImageView mapImageView;
    @FXML private Button findRouteButton;

    private Point2D startPoint = null;
    private Point2D endPoint = null;
    private WritableImage originalImageCopy;

    public void initialize() {
        findRouteButton.setOnAction(this::onFindRouteButtonClicked);
        mapImageView.setOnMouseClicked(this::handleMapClick);
        saveOriginalImage();
    }

    private void handleMapClick(MouseEvent event) {
        Point2D imageCoordinates = transformCoordinates(event.getX(), event.getY());
        System.out.println("Clicked coordinates: " + imageCoordinates);

        if (startPoint == null) {
            startPoint = imageCoordinates;
            updateTextField(startingPointField, startPoint);
            drawMarkerOnImageView(startPoint, Color.RED);
        } else if (endPoint == null) {
            endPoint = imageCoordinates;
            updateTextField(destinationField, endPoint);
            drawMarkerOnImageView(endPoint, Color.BLUE);
            findAndDrawRoute();
        }
    }

    private Point2D transformCoordinates(double x, double y) {
        double imageWidth = mapImageView.getImage().getWidth();
        double imageHeight = mapImageView.getImage().getHeight();

        double viewWidth = mapImageView.getBoundsInParent().getWidth();
        double viewHeight = mapImageView.getBoundsInParent().getHeight();

        double scaleX = imageWidth / viewWidth;
        double scaleY = imageHeight / viewHeight;

        double imageX = x * scaleX;
        double imageY = y * scaleY;

        return new Point2D(imageX, imageY);
    }

    private void updateTextField(TextField textField, Point2D point) {
        textField.setText(String.format("X: %.2f, Y: %.2f", point.getX(), point.getY()));
    }

    private void saveOriginalImage() {
        Image originalImage = mapImageView.getImage();
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();
        originalImageCopy = new WritableImage(width, height);
        PixelWriter pixelWriter = originalImageCopy.getPixelWriter();
        PixelReader pixelReader = originalImage.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelWriter.setColor(x, y, pixelReader.getColor(x, y));
            }
        }
    }

    private void drawMarkerOnImageView(Point2D point, Color color) {
        WritableImage writableImage = new WritableImage(originalImageCopy.getPixelReader(), (int) originalImageCopy.getWidth(), (int) originalImageCopy.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        drawMarker(pixelWriter, point, color, (int) writableImage.getWidth(), (int) writableImage.getHeight());
        mapImageView.setImage(writableImage);
    }

    @FXML
    private void onFindRouteButtonClicked(ActionEvent event) {
        findAndDrawRoute();
    }

    private void findAndDrawRoute() {
        if (startPoint == null || endPoint == null) {
            showAlert("Input Error", "Please select both a starting point and an end point on the map.");
            return;
        }

        drawRoute(startPoint, endPoint);
    }

    private void drawRoute(Point2D startPoint, Point2D endPoint) {
        WritableImage writableImage = new WritableImage(originalImageCopy.getPixelReader(), (int) originalImageCopy.getWidth(), (int) originalImageCopy.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // Draw the line directly between startPoint and endPoint
        drawLine(pixelWriter, (int) writableImage.getWidth(), (int) writableImage.getHeight(), startPoint, endPoint, Color.BLUE);

        // Draw start and end markers again
        drawMarker(pixelWriter, startPoint, Color.RED, (int) writableImage.getWidth(), (int) writableImage.getHeight());
        drawMarker(pixelWriter, endPoint, Color.BLUE, (int) writableImage.getWidth(), (int) writableImage.getHeight());

        mapImageView.setImage(writableImage);
    }

    public void drawLine(PixelWriter pixelWriter, int width, int height, Point2D start, Point2D end, Color color) {
        int x0 = (int) start.getX();
        int y0 = (int) start.getY();
        int x1 = (int) end.getX();
        int y1 = (int) end.getY();
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            if (x0 >= 0 && x0 < width && y0 >= 0 && y0 < height) {
                pixelWriter.setColor(x0, y0, color);
            }
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public void drawMarker(PixelWriter pixelWriter, Point2D point, Color color, int width, int height) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int radius = 10; // Radius for a larger circle
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i * i + j * j <= radius * radius) {
                    int px = x + i;
                    int py = y + j;
                    if (px >= 0 && px < width && py >= 0 && py < height) {
                        pixelWriter.setColor(px, py, color);
                    }
                }
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
