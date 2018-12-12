package messages;

public class ContentMessage extends Message {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentMessage(String action, String content) {
        setAction(action);
        this.content = content;
    }

    public ContentMessage(String content){
        this.content = content;
        setAction("content");
    }
}
