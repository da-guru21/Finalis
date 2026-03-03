import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private List<Task> tasks;

    public Workspace(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdDate = LocalDateTime.now();
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdDate.format(formatter);
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks.size() +
                ", createdDate=" + getFormattedDate() +
                '}';
    }
}
