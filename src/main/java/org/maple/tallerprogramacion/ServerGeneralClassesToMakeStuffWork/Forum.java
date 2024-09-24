package org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork;

public class Forum {
    private int id;
    private int userId;
    private String name;
    private String description;
    private String createdAt;

    public Forum(int id, int userId, String name, String description, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters y setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCreatedAt() { return createdAt; }
}
