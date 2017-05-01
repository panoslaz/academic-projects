package near2u.com.near2u.entities;

/**
 * Class that represents a topic of the forum.
 */
public class Topic {

    Integer id;
    String title;

    public Topic(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
