package near2u.com.near2u.entities;

/**
 * Class that represents a post on a topic.
 */
public class Post {

    Integer id;
    String text;

    public Post(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
