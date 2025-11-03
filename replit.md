# Drawing Application (Rubicon)

## Overview
A feature-rich Java Swing drawing application that allows users to create and manipulate various shapes with advanced property editing and undo/redo capabilities.

## Project Structure
This is a multi-module Maven project with the following modules:
- **draw**: Main drawing application with GUI
- **drawfx**: Core drawing functionality and models
- **prop**: Property sheet component for editing shape properties
- **fontchooser**: Custom font selection dialog
- **batik**: SVG rendering support

## Recent Changes (November 2025)
1. ✅ Fixed missing resource warnings for toolbar icons
2. ✅ Enhanced splash screen with custom background and logo
3. ✅ Implemented comprehensive property sheet that displays actual shape properties
4. ✅ Added full undo/redo support for property changes using Command pattern
5. ✅ Improved visual design and user experience

## Features

### Splash Screen
- Modern gradient background design
- Custom drawing application logo
- Quick access buttons for New Drawing and Open File
- Smooth transition to main drawing interface

### Drawing Tools
- **Shapes**: Rectangle, Ellipse, Line, Text, Image
- **Selection Tool**: Select and manipulate existing shapes
- **Property Editing**: Real-time property modification through property sheet
- **Undo/Redo**: Full support for all drawing operations including:
  - Shape creation and deletion
  - Shape movement and scaling
  - Property modifications (color, size, position, etc.)

### Property Sheet
The property sheet displays and allows modification of:
- Object Type (Drawing or Shape)
- Current Shape Mode
- Fore Color and Fill Color
- Line Thickness
- Position (X, Y Location)
- Size (Width, Height)
- Gradient settings (Start Color, End Color, IsGradient)
- Visibility
- Text content and font properties
- Selection status

All property changes are undoable and support redo.

### Command Pattern Implementation
- `AddShapeCommand`: Creating shapes
- `DeleteShapeCommand`: Deleting shapes
- `MoveShapeCommand`: Moving shapes
- `ScaleShapeCommand`: Resizing shapes
- `SetPropertyCommand`: Modifying any shape property
- `SetDrawModeCommand`: Changing drawing modes

## Technology Stack
- Java 17
- Maven 3.8+
- Java Swing for GUI
- Apache Batik for SVG support
- Lombok for reducing boilerplate code
- Custom property sheet framework

## Building and Running
```bash
# Build all modules
mvn clean install

# Run the application
cd draw && mvn exec:java -Dexec.mainClass="com.gabriel.draw.Main"
```

## Architecture
The application follows a clean architecture with:
- **MVC Pattern**: Separation of concerns between views, controllers, and models
- **Command Pattern**: All undoable operations are implemented as commands
- **Service Layer**: Business logic encapsulated in service classes
- **Renderer Pattern**: Each shape has its own renderer

## User Experience Enhancements
- Intuitive toolbar with icon-based shape selection
- Real-time property feedback in property sheet
- Smooth shape manipulation with visual feedback
- Professional color scheme and modern UI design
- Keyboard shortcuts for undo (Ctrl+Z) and redo
- Context-sensitive property display

## Development Notes
- LSP diagnostics are present due to the Java environment setup but do not affect functionality
- The application uses VNC output for the GUI in the Replit environment
- All Maven dependencies are automatically managed
- Resources are properly packaged in the JAR files

## Future Enhancements
- Export to various image formats (PNG, JPEG, SVG)
- Layer support
- More shape types (polygons, curves, arrows)
- Style presets
- Keyboard shortcuts panel
- Recent files list in splash screen
