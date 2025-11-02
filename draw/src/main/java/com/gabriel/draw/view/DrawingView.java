package com.gabriel.draw.view;

import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import lombok.Setter;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class DrawingView extends JPanel {

    AppService appService;

    public DrawingView(AppService appService){
        this.appService = appService;
        setFocusable(true);
        requestFocusInWindow();
        
        // Remove the JTextArea if it's not needed
        // JTextArea textArea = new JTextArea();
        // add(textArea);
        // textArea.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Drawing drawing = appService.getDrawing();
        List<Shape> shapes  = drawing.getShapes();
        for(Shape shape : shapes){
            shape.getRendererService().render(g, shape, false);
        }
    }


}
