package com.gabriel.draw.view;

import com.gabriel.draw.util.ImageLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Splash extends JPanel implements MouseListener {
    private BufferedImage image;
    private BufferedImage logo;  
    private GPanel gPanel;
    private JButton newButton;
    private JButton openButton;
    private ImageLoader imageLoader;
    private int width;
    private int height;
    
    // Constants for button dimensions
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private static final int SPACING = 70;  // Space between buttons

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (newButton != null && openButton != null) {  // Only position if buttons exist
            positionButtons();
        }
    }

    private void positionButtons() {
        // Calculate positions
        int rightHalfCenter = getWidth() * 3 / 4;
        int centerY = getHeight() / 2;
        int buttonX = rightHalfCenter - BUTTON_WIDTH / 2;
        
        // Position buttons vertically centered
        int startY = centerY - (BUTTON_HEIGHT + SPACING/2);
        if (newButton != null) {
            newButton.setBounds(buttonX, startY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        if (openButton != null) {
            openButton.setBounds(buttonX, startY + BUTTON_HEIGHT + SPACING, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    public Splash() {
        try {
            imageLoader = new ImageLoader();
            image = imageLoader.loadImage("/Splash_BG.png");
            logo = imageLoader.loadImage("/Splash_White-Logo.png");  

            height = image.getHeight();
            width = image.getWidth();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        setLayout(null);
        setSize(width, height);
        
        newButton = new JButton("New");
        styleButton(newButton);
        newButton.addMouseListener(this);
        add(newButton);
        
        openButton = new JButton("Open");
        styleButton(openButton);
        openButton.addMouseListener(this);
        add(openButton);
        
        // Initial positioning after all components are created
        positionButtons();
    }
    
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            double scale = Math.max((double) getWidth() / image.getWidth(), 
                                    (double) getHeight() / image.getHeight());
            int w = (int) (image.getWidth() * scale);
            int h = (int) (image.getHeight() * scale);
            g.drawImage(image, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, this);
        }

        // Draw logo on the left side, slightly above center
            if (logo != null) {
                // Scale logo proportionally to be about 1/3 of the panel width
                double logoScale = (getWidth() / 3.0) / logo.getWidth();
                int logoWidth = (int)(logo.getWidth() * logoScale);
                int logoHeight = (int)(logo.getHeight() * logoScale);

                // Position: left-center but slightly upward
                int logoX = getWidth() / 4 - logoWidth / 2;   // horizontally centered in the left half
                int logoY = getHeight() / 2 - logoHeight / 2 - getHeight() / 10; // a bit above vertical center

                g.drawImage(logo, logoX, logoY, logoWidth, logoHeight, this);
            }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == gPanel) {
            openDrawingFrame();
        } 
        else if(e.getSource() == newButton) {
            // Create new drawing
            openDrawingFrame();
        } 
        else if(e.getSource() == openButton) {
            // Open file chooser
            openFileDialog();
        }
    }
    
    private void openDrawingFrame() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        DrawingFrame mf = new DrawingFrame();
        mf.setExtendedState(mf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mf.setVisible(true);
        topFrame.dispose();
    }
    
    private void openFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Drawing File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Drawing Files (*.draw, *.json)", "draw", "json"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // TODO: Pass the file to DrawingFrame or load it
            // For now, just open the drawing frame
            openDrawingFrame();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}