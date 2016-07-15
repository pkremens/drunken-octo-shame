package cz.hradek.kremilek.hamcrest;

/**
 * http://www.vogella.com/tutorials/Hamcrest/article.html
 */
public class Todo {

    private final long id;
    private String summary;
    private String description;
    private int year;

    public Todo(long id, String summary, String description) {
        this.id = id;
        this.summary = summary;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != todo.id) return false;
        if (year != todo.year) return false;
        if (summary != null ? !summary.equals(todo.summary) : todo.summary != null) return false;
        return description != null ? description.equals(todo.description) : todo.description == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                '}';
    }
}