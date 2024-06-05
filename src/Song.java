import java.util.List;

public class Song extends MusicalComposition {
    private String genre;

    public Song(String title, String artist, int duration, String genre) {
        super(title, artist, duration);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return super.toString() + ", Жанр: " + genre;
    }
}

