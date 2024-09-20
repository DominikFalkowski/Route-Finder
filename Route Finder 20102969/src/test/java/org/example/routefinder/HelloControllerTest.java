package org.example.routefinder;

import javafx.geometry.Point2D;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {

    private final int width = 500;
    private final int height = 500;

    @Test
    void testDrawLine() {
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        Point2D start = new Point2D(10, 10);
        Point2D end = new Point2D(100, 100);
        HelloController controller = new HelloController();
        controller.drawLine(pixelWriter, width, height, start, end, Color.BLUE);

        PixelReader pixelReader = writableImage.getPixelReader();
        assertEquals(Color.BLUE, pixelReader.getColor(10, 10));
        assertEquals(Color.BLUE, pixelReader.getColor(100, 100));
    }

    @Test
    void testDrawMarker() {
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        Point2D point = new Point2D(50, 50);
        HelloController controller = new HelloController();
        controller.drawMarker(pixelWriter, point, Color.RED, width, height);

        PixelReader pixelReader = writableImage.getPixelReader();
        assertEquals(Color.RED, pixelReader.getColor(50, 50));

        // Check some points around the center for the marker
        assertEquals(Color.RED, pixelReader.getColor(55, 50));
        assertEquals(Color.RED, pixelReader.getColor(50, 55));
        assertEquals(Color.RED, pixelReader.getColor(45, 50));
        assertEquals(Color.RED, pixelReader.getColor(50, 45));
    }
}
