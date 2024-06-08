import java.util.Objects;

public abstract class MusicalComposition {
    private String title;
    private String artist;
    private int duration; // in seconds

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public MusicalComposition(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Название: " + title + ", Исполнитель: " + artist + ", Длительность: " + duration + " Секунд";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicalComposition that = (MusicalComposition) o;
        return duration == that.duration && title.equals(that.title) && artist.equals(that.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, duration);
    }
}