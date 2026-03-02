import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagementApp extends JFrame {
    private List<Project> projects;
    private DefaultListModel<String> projectListModel;
    private JList<String> projectList;
    private JTextArea detailsArea;

    public ProjectManagementApp() {
        setTitle("Project Management App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        projects = new ArrayList<>();
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create left panel for project list
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Projects"));
        
        projectListModel = new DefaultListModel<>();
        projectList = new JList<>(projectListModel);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectList.addListSelectionListener(e -> updateDetails());
        
        JScrollPane scrollPane = new JScrollPane(projectList);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JButton addBtn = new JButton("Add Project");
        JButton deleteBtn = new JButton("Delete Project");
        JButton editBtn = new JButton("Edit Project");
        
        addBtn.addActionListener(e -> addProject());
        deleteBtn.addActionListener(e -> deleteProject());
        editBtn.addActionListener(e -> editProject());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(editBtn);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Create right panel for details
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
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void addProject() {
        String name = JOptionPane.showInputDialog(this, "Enter project name:");
        if (name != null && !name.trim().isEmpty()) {
            String description = JOptionPane.showInputDialog(this, "Enter project description:");
            Project project = new Project(name, description != null ? description : "");
            projects.add(project);
            projectListModel.addElement(name);
        }
    }
    
    private void deleteProject() {
        int index = projectList.getSelectedIndex();
        if (index != -1) {
            projects.remove(index);
            projectListModel.remove(index);
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
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a project to edit.");
        }
    }
    
    private void updateDetails() {
        int index = projectList.getSelectedIndex();
        if (index != -1) {
            Project project = projects.get(index);
            detailsArea.setText("Project: " + project.getName() + "\n\n" +
                              "Description: " + project.getDescription() + "\n\n" +
                              "Created: " + project.getCreatedDate());
        } else {
            detailsArea.setText("");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjectManagementApp());
    }
}
