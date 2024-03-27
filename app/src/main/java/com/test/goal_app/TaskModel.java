package com.test.goal_app;


// Task Model to help with data transfer
public class TaskModel {

    private int id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private String deadlineDate;
    private Boolean isCompleted;
    private Boolean isDeleted;
    private String createdDate;
    private String completedDate;
    private int parentTaskID;


    public TaskModel(){}

    public TaskModel(int id, String name, String shortDescription, String longDescription, String deadlineDate,
                     Boolean isCompleted, Boolean isDeleted, String createdDate, String completedDate, int parentTaskID){


        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.deadlineDate = deadlineDate;
        this.isCompleted = isCompleted;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.parentTaskID = parentTaskID;
    }


    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sD=" + shortDescription +
                ", lD=" + longDescription +
                ", Deadline=" + deadlineDate +
                '}';
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public int getParentTaskID() {
        return parentTaskID;
    }

    public void setParentTaskID(int parentTaskID) {
        this.parentTaskID = parentTaskID;
    }
}
