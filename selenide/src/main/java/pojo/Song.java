package pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Generated("jsonschema2pojo")
public class Song {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("authors")
    @Expose
    private List<Author> authors = new ArrayList<Author>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return Objects.equals(getId(), song.getId()) && Objects.equals(getName(), song.getName()) && Objects.equals(getAuthors(), song.getAuthors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAuthors());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Song.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id").append('=').append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name").append('=').append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("todolist").append('=').append(((this.authors == null)?"<null>":this.authors));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
