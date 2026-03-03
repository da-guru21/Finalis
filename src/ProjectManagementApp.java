import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class ProjectManagementApp extends JFrame {
    private List<Project> projects;
    private Project selectedProject;
    private Workspace selectedWorkspace;

    // UI Components
    private DefaultListModel<String> projectListModel;
    private DefaultListModel<String> workspaceListModel;
    private DefaultListModel<String> taskListModel;

    private JList<String> projectList;
    private JList<String> workspaceList;
    private JList<String> taskList;

    private JTextArea detailsArea;
    private JTextArea workspaceDetailsArea;
    private JTextArea taskDetailsArea;
    private JButton addProjectBtn, deleteProjectBtn, editProjectBtn;
    private JButton addWorkspaceBtn, deleteWorkspaceBtn, editWorkspaceBtn;
    private JButton addTaskBtn, deleteTaskBtn, editTaskBtn;

    // Combo box references for syncing across tabs
    private JComboBox<String> workspacesTabProjectCombo;
    private JComboBox<String> tasksTabProjectCombo;
    private JComboBox<String> tasksTabWorkspaceCombo;

    public ProjectManagementApp() {
        System.out.println("[DEBUG] ProjectManagementApp constructor invoked");
        setTitle("Project Management App - Hierarchical");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        projects = new ArrayList<>();

        // Create main panel with tabbed pane for navigation
        JTabbedPane tabbedPane = new JTabbedPane();

        // Projects Tab
        tabbedPane.addTab("Projects", createProjectsPanel());

        // Workspaces Tab
        tabbedPane.addTab("Workspaces", createWorkspacesPanel());

        // Tasks Tab
        tabbedPane.addTab("Tasks", createTasksPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createProjectsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left panel for project list
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Projects"));

        projectListModel = new DefaultListModel<>();
        projectList = new JList<>(projectListModel);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectList.addListSelectionListener(e -> {
            selectedProject = null;
            if (projectList.getSelectedIndex() != -1) {
                selectedProject = projects.get(projectList.getSelectedIndex());
            }
            updateProjectDetails();
        });

        JScrollPane scrollPane = new JScrollPane(projectList);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel for projects
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        addProjectBtn = new JButton("Add Project");
        deleteProjectBtn = new JButton("Delete Project");
        editProjectBtn = new JButton("Edit Project");

        addProjectBtn.addActionListener(e -> addProject());
        deleteProjectBtn.addActionListener(e -> deleteProject());
        editProjectBtn.addActionListener(e -> editProject());

        buttonPanel.add(addProjectBtn);
        buttonPanel.add(deleteProjectBtn);
        buttonPanel.add(editProjectBtn);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Right panel for details
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Project Details"));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        rightPanel.add(detailsScroll, BorderLayout.CENTER);

        // Add panels to main
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createWorkspacesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel for project selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Project:"));

        workspacesTabProjectCombo = new JComboBox<>();
        workspacesTabProjectCombo.addActionListener(e -> {
            int index = workspacesTabProjectCombo.getSelectedIndex();
            if (index >= 0 && index < projects.size()) {
                selectedProject = projects.get(index);
                refreshWorkspaceList();
            }
        });
        topPanel.add(workspacesTabProjectCombo);

        // Middle panel for workspace list
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBorder(BorderFactory.createTitledBorder("Workspaces"));

        workspaceListModel = new DefaultListModel<>();
        workspaceList = new JList<>(workspaceListModel);
        workspaceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        workspaceList.addListSelectionListener(e -> {
            selectedWorkspace = null;
            if (workspaceList.getSelectedIndex() != -1 && selectedProject != null) {
                selectedWorkspace = selectedProject.getWorkspaces().get(workspaceList.getSelectedIndex());
            }
            updateWorkspaceDetails();
        });

        JScrollPane scrollPane = new JScrollPane(workspaceList);
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel for workspaces
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        addWorkspaceBtn = new JButton("Add Workspace");
        deleteWorkspaceBtn = new JButton("Delete Workspace");
        editWorkspaceBtn = new JButton("Edit Workspace");

        addWorkspaceBtn.addActionListener(e -> addWorkspace());
        deleteWorkspaceBtn.addActionListener(e -> deleteWorkspace());
        editWorkspaceBtn.addActionListener(e -> editWorkspace());

        buttonPanel.add(addWorkspaceBtn);
        buttonPanel.add(deleteWorkspaceBtn);
        buttonPanel.add(editWorkspaceBtn);
        middlePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Right panel for details
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Workspace Details"));

        workspaceDetailsArea = new JTextArea();
        workspaceDetailsArea.setEditable(false);
        workspaceDetailsArea.setLineWrap(true);
        workspaceDetailsArea.setWrapStyleWord(true);
        workspaceDetailsArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane detailsScroll = new JScrollPane(workspaceDetailsArea);
        rightPanel.add(detailsScroll, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);

        refreshProjectCombo(workspacesTabProjectCombo);

        return panel;
    }

    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel for project & workspace selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        topPanel.add(new JLabel("Project:"));

        tasksTabProjectCombo = new JComboBox<>();
        tasksTabProjectCombo.addActionListener(e -> {
            int index = tasksTabProjectCombo.getSelectedIndex();
            if (index >= 0 && index < projects.size()) {
                selectedProject = projects.get(index);
                refreshWorkspaceCombo(tasksTabWorkspaceCombo);
                refreshTaskList();
            }
        });
        topPanel.add(tasksTabProjectCombo);

        topPanel.add(new JLabel("Workspace:"));
        tasksTabWorkspaceCombo = new JComboBox<>();
        tasksTabWorkspaceCombo.addActionListener(e -> {
            int index = tasksTabWorkspaceCombo.getSelectedIndex();
            if (index >= 0 && selectedProject != null && index < selectedProject.getWorkspaces().size()) {
                selectedWorkspace = selectedProject.getWorkspaces().get(index);
                refreshTaskList();
            }
        });
        topPanel.add(tasksTabWorkspaceCombo);

        // Middle panel for task list
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBorder(BorderFactory.createTitledBorder("Tasks"));

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.addListSelectionListener(e -> updateTaskDetails());

        JScrollPane scrollPane = new JScrollPane(taskList);
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel for tasks
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        addTaskBtn = new JButton("Add Task");
        deleteTaskBtn = new JButton("Delete Task");
        editTaskBtn = new JButton("Edit Task");

        addTaskBtn.addActionListener(e -> addTask());
        deleteTaskBtn.addActionListener(e -> deleteTask());
        editTaskBtn.addActionListener(e -> editTask());

        buttonPanel.add(addTaskBtn);
        buttonPanel.add(deleteTaskBtn);
        buttonPanel.add(editTaskBtn);
        middlePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Right panel for details
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Task Details"));

        taskDetailsArea = new JTextArea();
        taskDetailsArea.setEditable(false);
        taskDetailsArea.setLineWrap(true);
        taskDetailsArea.setWrapStyleWord(true);
        taskDetailsArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane detailsScroll = new JScrollPane(taskDetailsArea);
        rightPanel.add(detailsScroll, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);

        refreshProjectCombo(tasksTabProjectCombo);

        return panel;
    }

    private void refreshProjectCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        for (Project p : projects) {
            combo.addItem(p.getName());
        }
    }

    private void refreshWorkspaceList() {
        workspaceListModel.clear();
        if (selectedProject != null) {
            for (Workspace ws : selectedProject.getWorkspaces()) {
                workspaceListModel.addElement(ws.getName());
            }
        }
    }

    private void refreshWorkspaceCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        if (selectedProject != null) {
            for (Workspace ws : selectedProject.getWorkspaces()) {
                combo.addItem(ws.getName());
            }
        }
    }

    private void refreshTaskList() {
        taskListModel.clear();
        if (selectedWorkspace != null) {
            for (Task task : selectedWorkspace.getTasks()) {
                taskListModel.addElement(task.toString());
            }
        }
    }

    // Project management methods
    private void addProject() {
        String name = JOptionPane.showInputDialog(this, "Enter project name:");
        if (name != null && !name.trim().isEmpty()) {
            String description = JOptionPane.showInputDialog(this, "Enter project description:");
            Project project = new Project(name, description != null ? description : "");
            projects.add(project);
            projectListModel.addElement(name);
            updateAllProjectCombos();
        }
    }

    private void deleteProject() {
        int index = projectList.getSelectedIndex();
        if (index != -1) {
            projects.remove(index);
            projectListModel.remove(index);
            updateAllProjectCombos();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a project to delete.");
        }
    }

    private void editProject() {
        int index = projectList.getSelectedIndex();
        if (index != -1) {
            Project project = projects.get(index);
            String newName = JOptionPane.showInputDialog(this, "Edit project name:", project.getName());
            if (newName != null && !newName.trim().isEmpty()) {
                project.setName(newName);
                projectListModel.set(index, newName);
                updateAllProjectCombos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a project to edit.");
        }
    }

    private void updateAllProjectCombos() {
        if (workspacesTabProjectCombo != null) {
            refreshProjectCombo(workspacesTabProjectCombo);
        }
        if (tasksTabProjectCombo != null) {
            refreshProjectCombo(tasksTabProjectCombo);
        }
    }

    private void updateProjectDetails() {
        if (selectedProject != null) {
            detailsArea.setText("Project: " + selectedProject.getName() + "\n\n" +
                    "Description: " + selectedProject.getDescription() + "\n\n" +
                    "Workspaces: " + selectedProject.getWorkspaces().size() + "\n\n" +
                    "Created: " + selectedProject.getFormattedDate());
        } else {
            detailsArea.setText("");
        }
    }

    // Workspace management methods
    private void addWorkspace() {
        if (selectedProject == null) {
            JOptionPane.showMessageDialog(this, "Please select a project first.");
            return;
        }
        String name = JOptionPane.showInputDialog(this, "Enter workspace name:");
        if (name != null && !name.trim().isEmpty()) {
            String description = JOptionPane.showInputDialog(this, "Enter workspace description:");
            Workspace workspace = new Workspace(name, description != null ? description : "");
            selectedProject.addWorkspace(workspace);
            refreshWorkspaceList();
            if (tasksTabWorkspaceCombo != null) {
                refreshWorkspaceCombo(tasksTabWorkspaceCombo);
            }
        }
    }

    private void deleteWorkspace() {
        int index = workspaceList.getSelectedIndex();
        if (index != -1 && selectedProject != null) {
            selectedProject.removeWorkspace(index);
            refreshWorkspaceList();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a workspace to delete.");
        }
    }

    private void editWorkspace() {
        int index = workspaceList.getSelectedIndex();
        if (index != -1 && selectedProject != null) {
            Workspace workspace = selectedProject.getWorkspaces().get(index);
            String newName = JOptionPane.showInputDialog(this, "Edit workspace name:", workspace.getName());
            if (newName != null && !newName.trim().isEmpty()) {
                workspace.setName(newName);
                refreshWorkspaceList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a workspace to edit.");
        }
    }

    private void updateWorkspaceDetails() {
        if (selectedWorkspace != null) {
            workspaceDetailsArea.setText("Workspace: " + selectedWorkspace.getName() + "\n\n" +
                    "Description: " + selectedWorkspace.getDescription() + "\n\n" +
                    "Tasks: " + selectedWorkspace.getTasks().size() + "\n\n" +
                    "Created: " + selectedWorkspace.getFormattedDate());
        } else {
            workspaceDetailsArea.setText("");
        }
    }

    // Task management methods
    private void addTask() {
        if (selectedWorkspace == null) {
            JOptionPane.showMessageDialog(this, "Please select a workspace first.");
            return;
        }
        String title = JOptionPane.showInputDialog(this, "Enter task title:");
        if (title != null && !title.trim().isEmpty()) {
            String description = JOptionPane.showInputDialog(this, "Enter task description:");
            Task task = new Task(title, description != null ? description : "");
            selectedWorkspace.addTask(task);
            refreshTaskList();
        }
    }

    private void deleteTask() {
        int index = taskList.getSelectedIndex();
        if (index != -1 && selectedWorkspace != null) {
            selectedWorkspace.removeTask(index);
            refreshTaskList();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
        }
    }

    private void editTask() {
        int index = taskList.getSelectedIndex();
        if (index != -1 && selectedWorkspace != null) {
            Task task = selectedWorkspace.getTasks().get(index);
            String newTitle = JOptionPane.showInputDialog(this, "Edit task title:", task.getTitle());
            if (newTitle != null && !newTitle.trim().isEmpty()) {
                task.setTitle(newTitle);
                refreshTaskList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to edit.");
        }
    }

    private void updateTaskDetails() {
        int index = taskList.getSelectedIndex();
        if (index != -1 && selectedWorkspace != null) {
            Task task = selectedWorkspace.getTasks().get(index);
            taskDetailsArea.setText("Task: " + task.getTitle() + "\n\n" +
                    "Description: " + task.getDescription() + "\n\n" +
                    "Status: " + task.getStatus() + "\n\n" +
                    "Created: " + task.getFormattedDate());
        } else {
            taskDetailsArea.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjectManagementApp());
    }
}
