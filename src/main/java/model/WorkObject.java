package model;

public class WorkObject {
    private String title;
    private String content;
    private boolean locked;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public WorkObject(String title, String content, boolean locked) {
        this.title = title;
        this.content = content;
        this.locked = locked;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
