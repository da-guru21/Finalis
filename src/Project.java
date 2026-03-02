import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Project {
    private String name;
    private String description;
    private LocalDateTime createdDate;
    
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdDate = LocalDateTime.now();
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
    
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdDate.format(formatter);
    }
    
    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + getFormattedDate() +
                '}';
    }
}
