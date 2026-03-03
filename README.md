# Project Management Desktop Application

A Java-based desktop application for managing projects efficiently.

## Features

- **Add Projects**: Create new projects with names and descriptions
- **Edit Projects**: Modify existing project details
- **Delete Projects**: Remove projects from the list
- **View Details**: Display project information including creation date

## Project Structure

```
Project Management/
├── src/
│   ├── ProjectManagementApp.java    (Main GUI application)
│   └── Project.java                 (Project data model)
├── bin/                             (Compiled classes)
├── lib/                             (External libraries)
└── README.md
```

## Requirements

- Java Development Kit (JDK) 8 or higher
- No external dependencies (uses Java Swing)

## Building and Running

### Auto-Compile (VS Code)

The **Extension Pack for Java** is configured to auto-compile on save. Simply edit and save a `.java` file—it automatically compiles to `bin/`.

### Manual Compile

```bash
javac -d bin src/*.java
```

### Run

```bash
java -cp bin ProjectManagementApp
```

## Usage

1. **Add a Project**: Click "Add Project" button and enter project details
2. **View Details**: Select a project from the list to see its details
3. **Edit a Project**: Select a project and click "Edit Project"
4. **Delete a Project**: Select a project and click "Delete Project"

## Future Enhancements

- Task management within projects
- Project status tracking
- Data persistence (save/load projects)
- Team member assignment
- Deadline and progress tracking

## Customizing Appearance

You can change the application window title and icon by editing `ProjectManagementApp.java`:

1. Modify the `APP_TITLE` constant near the top of the class.
2. Set `ICON_RESOURCE` to the resource path of your icon, for example `"/finalis_icon.jpg"` or `"/app_icon.png"`.
   * If the file is included on the classpath (copied under `bin/` or inside a JAR), the program will load it via `getResource(...)`.
   * If the resource lookup fails, the code falls back to loading a file from the working directory using the same path (e.g. `./finalis_icon.jpg`).

To make the icon available at runtime you can either:

- Manually copy the image into `bin/` after compilation (e.g. `cp src/app_icon.png bin/`).
- Run the program from the project root or from `src/`; the code will look in both `.` and `./src`.
- Adjust your build process or IDE settings to include non-Java files in the output directory.

When using `java -cp bin ProjectManagementApp`, ensure the icon file is accessible from one of those locations. The constant `ICON_RESOURCE` should match the filename you copied.

After making changes, recompile and run the application to see the updated title and icon.

---

_Initialized: March 2, 2026_

# Finalis
