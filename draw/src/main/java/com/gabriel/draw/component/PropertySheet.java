package com.gabriel.draw.component;

import com.gabriel.draw.controller.PropertyEventListener;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.property.PropertyOptions;
import com.gabriel.property.PropertyPanel;
import com.gabriel.property.cell.SelectionCellComponent;
import com.gabriel.property.property.*;
import com.gabriel.property.property.selection.Item;
import com.gabriel.property.property.selection.SelectionProperty;
import com.gabriel.property.validator.CompoundValidator;
import com.gabriel.property.validator.StringValidator;
import com.gabriel.property.validator.doubleNumber.DoubleRangeValidator;
import com.gabriel.property.validator.doubleNumber.DoubleValidator;
import com.gabriel.property.validator.doubleNumber.DoubleZeroPolicyValidator;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PropertySheet extends PropertyPanel {
    PropertyPanel propertyTable;
    private SelectionProperty shapeProp;
    Item RectangleItem;
    Item EllipseItem;
    Item LineItem;
    Item TextItem;
    Item SelectItem;


    public void setShapeProp(ShapeMode shapeMode ){
        SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();
        if (shapeMode ==ShapeMode.Rectangle) {
            selectionComponent.setCellEditorValue(RectangleItem);
        } else if (shapeMode == ShapeMode.Ellipse) {
            selectionComponent.setCellEditorValue(EllipseItem);
        } else if (shapeMode == ShapeMode.Line) {
            selectionComponent.setCellEditorValue(LineItem);
        } else if (shapeMode == ShapeMode.Select) {
            selectionComponent.setCellEditorValue(SelectItem);
        }
    }

    public PropertySheet(PropertyOptions options){
        super(options);
        shapeProp = new SelectionProperty<>(
                "Current Shape",
                new ArrayList<>(Arrays.asList(
                        new Item<>(ShapeMode.Rectangle, "Rectangle"),
                        new Item<>(ShapeMode.Ellipse, "Ellipse"),
                        new Item<>(ShapeMode.Line, "Line"),
                        new Item<>(ShapeMode.Text, "Text"),
                        new Item<>(ShapeMode.Select, "Select")
                ))
        );
    }

    public void populateTable(AppService appService) {
        Drawing drawing = appService.getDrawing();
        propertyTable = this;
        propertyTable.addEventListener(new PropertyEventListener(appService));

        propertyTable.clear();
        Shape shape  = appService.getSelectedShape();
        String objectType;
        if ( shape == null) {
            objectType = "Drawing";
        }
        else {
            objectType = "Shape";
        }

        StringProperty targetProp = new StringProperty("Object Type", objectType);
        propertyTable.addProperty(targetProp);

        RectangleItem = new Item<ShapeMode>(ShapeMode.Rectangle, "Rectangle");
        EllipseItem = new Item<ShapeMode>(ShapeMode.Ellipse, "Ellipse");
        LineItem =    new Item<ShapeMode>(ShapeMode.Line, "Line");
        SelectItem =    new Item<ShapeMode>(ShapeMode.Select, "Select");
        TextItem =    new Item<ShapeMode>(ShapeMode.Text, "Text");
        shapeProp = new SelectionProperty<>(
                "Current Shape",
                new ArrayList<>(Arrays.asList(
                        RectangleItem,
                        EllipseItem,
                        LineItem,
                        SelectItem,
                        TextItem
                ))
        );

        propertyTable.addProperty(shapeProp);

        SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();
        ShapeMode shapeMode = appService.getShapeMode();
        if(shapeMode == ShapeMode.Rectangle) {
            selectionComponent.setCellEditorValue(RectangleItem);
        }
        else if(shapeMode == ShapeMode.Ellipse) {
            selectionComponent.setCellEditorValue(EllipseItem);
        }
        else if(shapeMode == ShapeMode.Line) {
            selectionComponent.setCellEditorValue(LineItem);
        }
        else if(shapeMode == ShapeMode.Select) {
            selectionComponent.setCellEditorValue(SelectItem);
        }
        shapeProp.setValue(shape);

        Color foreColor = shape != null ? shape.getColor() : appService.getColor();
        ColorProperty currentColorProp = new ColorProperty("Fore color", foreColor);
        propertyTable.addProperty(currentColorProp);

        Color fillColor = shape != null ? shape.getFill() : appService.getFill();
        ColorProperty currentFillProp = new ColorProperty("Fill color",  fillColor);
        propertyTable.addProperty(currentFillProp);

        int lineThickness = shape != null ? shape.getThickness() : appService.getThickness();
        IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", lineThickness);
        propertyTable.addProperty(lineThicknessProp);

        int xLocation = shape != null ? shape.getLocation().x : appService.getXLocation();
        IntegerProperty xlocProp = new IntegerProperty("X Location", xLocation);
        propertyTable.addProperty(xlocProp);

        int yLocation = shape != null ? shape.getLocation().y : appService.getYLocation();
        IntegerProperty ylocProp = new IntegerProperty("Y Location", yLocation);
        propertyTable.addProperty(ylocProp);

        int width = shape != null ? shape.getWidth() : appService.getWidth();
        IntegerProperty widthProp = new IntegerProperty("Width", width);
        propertyTable.addProperty(widthProp);

        int height = shape != null ? shape.getHeight() : appService.getHeight();
        IntegerProperty heightProp = new IntegerProperty("Height", height);
        propertyTable.addProperty(heightProp);

        if(shape!=null) {
            BooleanProperty selectedProp = new BooleanProperty("is Selected", shape.isSelected());
            propertyTable.addProperty(selectedProp);
            
            boolean isGradient = shape.isGradient();
            BooleanProperty isGradientProp = new BooleanProperty("IsGradient", isGradient);
            propertyTable.addProperty(isGradientProp);
            
            if(isGradient) {
                Color startColor = shape.getStartColor();
                ColorProperty currentStartColorProp = new ColorProperty("Start color", startColor);
                propertyTable.addProperty(currentStartColorProp);

                Color endColor = shape.getEndColor();
                ColorProperty currentEndColorProp = new ColorProperty("End color", endColor);
                propertyTable.addProperty(currentEndColorProp);
            }
            
            boolean isVisible = shape.isVisible();
            BooleanProperty isVisibleProp = new BooleanProperty("IsVisible", isVisible);
            propertyTable.addProperty(isVisibleProp);
            
            String text = shape.getText();
            if(text != null && !text.isEmpty()) {
                StringProperty stringProp = new StringProperty("Text", text);
                propertyTable.addProperty(stringProp);
                
                Font font = shape.getFont();
                if(font != null) {
                    stringProp = new StringProperty("Font family", font.getFamily());
                    propertyTable.addProperty(stringProp);

                    IntegerProperty intProp = new IntegerProperty("Font style", font.getStyle());
                    propertyTable.addProperty(intProp);

                    intProp = new IntegerProperty("Font size", font.getSize());
                    propertyTable.addProperty(intProp);
                }
            }
        }
    }
}
