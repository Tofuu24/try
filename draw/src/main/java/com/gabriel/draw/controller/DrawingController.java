package com.gabriel.draw.controller;

import com.gabriel.draw.component.PropertySheet;
import com.gabriel.draw.model.*;
import com.gabriel.draw.model.Image;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.draw.view.DrawingStatusPanel;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.util.Normalizer;
import com.gabriel.drawfx.SelectionMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.ScaleShapeCommand;
import com.gabriel.drawfx.command.CommandService;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;

public class DrawingController implements MouseListener, MouseMotionListener, KeyListener {
    Point start;
    private Point end;
    private Point dragOrigin;

    private final AppService appService;
    private final Drawing drawing;

    @Setter
    private DrawingView drawingView;

    @Setter
    private DrawingStatusPanel drawingStatusPanel;

    @Setter
    private PropertySheet propertySheet;

    private Shape currentShape = null;
    // store original shape bounds during a scale operation
    private Map<Shape, Object[]> preScaleState = new HashMap<>();

    public DrawingController(AppService appService, DrawingView drawingView) {
        this.appService = appService;
        this.drawing = appService.getDrawing();
        this.drawingView = drawingView;
        drawingView.addMouseListener(this);
        drawingView.addMouseMotionListener(this);
        drawingView.addKeyListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Ensure the drawing view has focus when clicked
        if (drawingView != null) {
            drawingView.requestFocusInWindow();
        }

        if (appService.getDrawMode() == DrawMode.Idle) {
            start = e.getPoint();
            ShapeMode currentShapeMode = appService.getShapeMode();
            if (currentShapeMode == ShapeMode.Select) {
                appService.search(start, !e.isControlDown());

            } else {
                if (currentShape != null) {
                    currentShape.setSelected(false);
                }
                switch (currentShapeMode) {
                    case Line:
                        currentShape = new Line(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Rectangle:
                        currentShape = new Rectangle(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Text:
                        currentShape = new Text(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setText(drawing.getText());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                        appService.setDrawMode(DrawMode.MousePressed);
                        break;
                    case Ellipse:
                        currentShape = new Ellipse(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Image:
                        currentShape = new Image(start);
                        currentShape.setImageFilename(drawing.getImageFilename());
                        currentShape.setColor(appService.getColor());
                        currentShape.setThickness(appService.getThickness());
                        break;
                    default:
                        // no-op for unhandled modes
                        break;
                }
            }
            // remember the original press location for undo registration
            dragOrigin = new Point(start);
            // clear any previous pre-scale state
            preScaleState.clear();
            appService.setDrawMode(DrawMode.MousePressed);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        end = e.getPoint();
        if (appService.getDrawMode() == DrawMode.MousePressed) {
            if (appService.getShapeMode() == ShapeMode.Select) {
                Shape selectedShape = drawing.getSelectedShape();
                if (selectedShape != null) {
                    if (selectedShape.getSelectionMode() == SelectionMode.None) {
                        List<Shape> shapes = drawing.getShapes();
                        for (Shape shape : shapes) {
                            if (shape.isSelected()) {
                                // Render the shape in its new position
                                shape.getRendererService().render(drawingView.getGraphics(), shape, false);
                            }
                        }
                        // Register a single MoveShapeCommand per selected shape representing the drag from initial press `dragOrigin` to final `end`
                        Point cmdStart = dragOrigin != null ? new Point(dragOrigin) : new Point(start);
                        for (Shape shape : shapes) {
                            if (shape.isSelected()) {
                                MoveShapeCommand cmd = new MoveShapeCommand(appService, shape, cmdStart, new Point(end), drawingView::repaint);
                                CommandService.register(cmd);
                            }
                        }
                    } else {
                        // Finalize scaling for selected shape and register scale commands
                        appService.scale(selectedShape, start, end);
                        Normalizer.normalize(selectedShape);

                        if (!preScaleState.isEmpty()) {
                            for (Map.Entry<Shape, Object[]> entry : preScaleState.entrySet()) {
                                Shape s = entry.getKey();
                                Object[] data = entry.getValue();
                                Point oldLoc = (Point) data[0];
                                int oldW = (int) data[1];
                                int oldH = (int) data[2];

                                Point newLoc = new Point(s.getLocation());
                                int newW = s.getWidth();
                                int newH = s.getHeight();

                                ScaleShapeCommand cmd = new ScaleShapeCommand(s, oldLoc, oldW, oldH, newLoc, newW, newH, drawingView::repaint);
                                CommandService.register(cmd);
                            }
                            preScaleState.clear();
                        }
                    }
                    drawingView.repaint();
                }
            } else {
                // finishing shape creation
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                appService.scale(currentShape, end);
                currentShape.setText(drawing.getText());
                currentShape.setFont(drawing.getFont());
                currentShape.setGradient(drawing.isGradient());
                currentShape.setFill(drawing.getFill());
                currentShape.setStartColor(drawing.getStartColor());
                currentShape.setEndColor(drawing.getEndColor());
                Normalizer.normalize(currentShape);
                appService.create(currentShape);
                currentShape.setSelected(true);
                drawing.setSelectedShape(currentShape);
                drawing.setShapeMode(ShapeMode.Select);
                drawingView.repaint();
            }
            appService.setDrawMode(DrawMode.Idle);
        }
        propertySheet.populateTable(appService);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed) {
            end = e.getPoint();
            if (drawing.getShapeMode() == ShapeMode.Select) {
                Shape selectedShape = drawing.getSelectedShape();
                if (selectedShape != null) {
                    if (selectedShape.getSelectionMode() == SelectionMode.None) {
                        // Move all selected shapes (delegated to appService)
                        appService.move(start, end);
                        // Update the start point for the next drag event
                        start.setLocation(end);
                    } else {
                        // Beginning or continuing a scale operation: capture pre-scale state if not already captured
                        if (preScaleState.isEmpty()) {
                            List<Shape> shapes = drawing.getShapes();
                            for (Shape s : shapes) {
                                if (s.isSelected()) {
                                    preScaleState.put(s, new Object[]{new Point(s.getLocation()), s.getWidth(), s.getHeight()});
                                }
                            }
                        }
                        appService.scale(selectedShape, start, end);
                        Normalizer.normalize(selectedShape);
                    }
                }
                start = end;

            } else {
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                appService.scale(currentShape, end);
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        drawingStatusPanel.setPoint(e.getPoint());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            Shape selectedShape = drawing.getSelectedShape();
            if (selectedShape != null) {
                appService.delete(selectedShape);
                drawingView.repaint();
            }
            // Consume the event to prevent any default behavior
            e.consume();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
