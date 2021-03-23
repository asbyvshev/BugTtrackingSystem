package sample.entity;

import sample.entity.base.BaseEntity;
import sample.entity.base.TaskType;

public class Task extends BaseEntity {
    private String topic;
    private String description;
    private byte priority;
    private Project project;
    private User executor;
    private TaskType type;

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

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Project getProgect() {
        return project;
    }

    public void setProgect(Project progect) {
        this.project = progect;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }
}
