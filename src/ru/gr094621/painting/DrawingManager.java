package ru.gr094621.painting;

import ru.gr094621.painting.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawingManager {
    private Painter painter;
    private BufferedImage bufferedImage;
    private JPanel targetPanel;
    private Thread drawingThread;
    private volatile boolean running = true;

    public DrawingManager(Painter painter, JPanel targetPanel) {
        this.painter = painter;
        this.targetPanel = targetPanel;

        bufferedImage = new BufferedImage(painter.getWidth(), painter.getHeight(),
                BufferedImage.TYPE_INT_RGB);
    }

    public void startDrawing() {
        drawingThread = new Thread(() -> {
            while (running) {
                Graphics g = bufferedImage.getGraphics();

                g.setColor(Color.WHITE);
                g.fillRect(0, 0, painter.getWidth(), painter.getHeight());

                painter.paint(g);

                g.dispose();

                SwingUtilities.invokeLater(() -> {
                    Graphics panelGraphics = targetPanel.getGraphics();
                    if (panelGraphics != null) {
                        panelGraphics.drawImage(bufferedImage, 0, 0, null);
                        panelGraphics.dispose();
                    }
                });

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        drawingThread.start();
    }

    public void stopDrawing() {
        running = false;
        if (drawingThread != null) {
            drawingThread.interrupt();
        }
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}