package ru.gr094621;

import javax.swing.*;
import ru.gr094621.painting.DrawingManager;
import ru.gr094621.painting.Painter;

public class MainWindow {
    private JPanel graphPanel;
    private DrawingManager drawingManager;
    public MainWindow(Painter painter){
        drawingManager = new DrawingManager(painter, graphPanel);
        drawingManager.startDrawing();
    }
}
