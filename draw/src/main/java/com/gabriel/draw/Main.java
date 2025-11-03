package com.gabriel.draw;

import com.gabriel.draw.view.Splash;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame splashFrame = new JFrame("Drawing Application");
            Splash splashPanel = new Splash();
            splashFrame.add(splashPanel);
            splashFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            splashFrame.setExtendedState(splashFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            splashFrame.setVisible(true);
        });
    }
}