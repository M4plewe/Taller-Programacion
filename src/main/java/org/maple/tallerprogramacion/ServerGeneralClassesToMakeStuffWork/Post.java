package org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork;

public class Post {
    private int id;
    private int userId;
    private int forumId;
    private String title;
    private String content;
    private String createdAt;

    public Post(int id, int userId, int forumId, String title, String content, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.forumId = forumId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getForumId() {
        return forumId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
