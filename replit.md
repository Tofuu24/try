# Drawing Application (Rubicon)

## Overview
This is a multi-module Java Swing desktop drawing application that allows users to create and edit drawings with various shapes (lines, rectangles, ellipses, text, and images).

## Project Information
- **Type**: Java Swing GUI Desktop Application
- **Build System**: Maven (multi-module project)
- **Java Version**: 17 (target), 19 (runtime)
- **Main Class**: `com.gabriel.draw.Main`

## Project Structure
This is a multi-module Maven project with the following modules:

1. **drawfx** - Core drawing functionality and services
2. **draw** - Main drawing application with UI components
3. **prop** (property) - Property panel/editor components
4. **fontchooser** - Font selection dialog (created during setup)
5. **batik** - Apache Batik SVG utilities

## Recent Changes (November 2, 2025)
- Created missing `fontchooser` module with:
  - Added `pom.xml` for the module
  - Implemented `FontDialog` class for font selection functionality
- Created missing command classes in `draw` module:
  - `DeleteShapeCommand.java` - Handles shape deletion with undo/redo
  - `MoveShapeCommand.java` - Handles shape movement with undo/redo
  - `ScaleShapeCommand.java` - Handles shape scaling with undo/redo
- Successfully built entire multi-module project
- Configured workflow to run the GUI application via VNC

## How to Build
```bash
mvn clean install
```

## How to Run
The application runs as a VNC desktop GUI application. The workflow is already configured:
- **Workflow Name**: Drawing App
- **Command**: `cd draw && mvn exec:java -Dexec.mainClass="com.gabriel.draw.Main"`
- **Output Type**: VNC (desktop GUI)

## Features
- Multiple drawing tools: Line, Rectangle, Ellipse, Text, Image
- Color picker for stroke and fill
- Font selector for text
- Undo/Redo functionality via command pattern
- Save/Load drawings in XML format
- Shape selection and manipulation (move, scale)
- Property panel for editing shape properties

## Dependencies
- Lombok (compile-time code generation)
- Apache Batik (SVG utilities)
- Java Swing (GUI framework)

## Notes
- The application is designed to run in a VNC desktop environment
- Some image resources may be missing (imagefile.png, font.png) which causes minor warnings but doesn't affect core functionality
- The LSP diagnostics are expected as they relate to Swing/AWT packages that are available at runtime but not indexed by the language server in this environment
