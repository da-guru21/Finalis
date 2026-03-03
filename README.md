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

---

_Initialized: March 2, 2026_

# Finalis
