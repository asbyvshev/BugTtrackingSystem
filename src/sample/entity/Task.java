package sample.entity;

import sample.entity.base.BaseEntity;
import sample.entity.base.TaskType;

public class Task extends BaseEntity {
    private String topic;
    private String description;
    private String priority;
    private Project project;
    private User executor;
    private TaskType type;

    public Task(int id, String topic, String description, String priority,
                Project project, User executor, TaskType type) {
        setId(id);
        this.topic = topic;
        this.description = description;
        this.priority = priority;
        this.project = project;
        this.executor = executor;
        this.type = type;
    }

    public Task(String topic, String description, String priority, Project project, User executor, TaskType type) {
        this.topic = topic;
        this.description = description;
        this.priority = priority;
        this.project = project;
        this.executor = executor;
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }
}
