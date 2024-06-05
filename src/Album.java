import java.util.List;

public class Album extends MusicalComposition {
    private List<Song> songs;

    public Album(String title, String artist, int duration, List<Song> songs) {
        super(title, artist, duration);
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Альбом: ").append(getTitle()).append(", Исполнитель: ").append(getArtist()).append(", Длительность: ").append(getDuration()).append(" секунд, Песни:\n");
        for (Song song : songs) {
            sb.append("- ").append(song).append("\n");
        }
        return sb.toString();
    }
}
