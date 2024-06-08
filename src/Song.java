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
        return String.format("%s - %s (%d секунд) [Жанр: %s]", getTitle(), getArtist(), getDuration(), genre);
    }
}

