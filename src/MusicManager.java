import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MusicManager {
    private static final List<Album> albums = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (choice) {
                case 1:
                    createAlbum(scanner);
                    break;
                case 2:
                    burnAlbum();
                    break;
                case 3:
                    calculateTotalDuration();
                    break;
                case 4:
                    sortAlbumsByGenre();
                    break;
                case 5:
                    findTracksByDuration(scanner);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте еще раз.");
                    break;
            }
        }

        System.out.println("Пока!");
    }

    private static void showMenu() {
        System.out.println("Меню:");
        System.out.println("1. Создать альбом");
        System.out.println("2. Вывести все альбомы");
        System.out.println("3. Рассчитать общую продолжительность");
        System.out.println("4. Сортировка альбомов по жанру");
        System.out.println("5. Найти треки по продолжительности");
        System.out.println("6. Выход");
        System.out.print("Сделайте выбор: ");
    }

    private static void createAlbum(Scanner scanner) {
        System.out.print("Введите название альбома: ");
        String title = scanner.nextLine();
        System.out.print("Введите имя исполнителя: ");
        String artist = scanner.nextLine();

        List<Song> songs = new ArrayList<>();
        boolean addingSongs = true;
        while (addingSongs) {
            System.out.print("Введите название песни: ");
            String songTitle = scanner.nextLine();
            System.out.print("Введите исполнителя песни: ");
            String songArtist = scanner.nextLine();
            System.out.print("Введите продолжительность песни (секунды): ");
            int songDuration = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Введите жанр песни: ");
            String songGenre = scanner.nextLine();

            Song song = new Song(songTitle, songArtist, songDuration, songGenre);
            songs.add(song);

            System.out.print("Добавить еще песню? (y/n) ");
            String addAnother = scanner.nextLine();
            addingSongs = addAnother.equalsIgnoreCase("y");
        }

        int totalDuration = songs.stream().mapToInt(MusicalComposition::getDuration).sum();
        Album album = new Album(title, artist, totalDuration, songs);
        albums.add(album);
        System.out.println("Альбом успешно добавлен.");
    }

    private static void burnAlbum() {
        for (Album album : albums) {
            System.out.println(album);
        }
    }

    private static void calculateTotalDuration() {
        int totalDuration = albums.stream().mapToInt(MusicalComposition::getDuration).sum();
        System.out.printf("Общая продолжительность всех альбомов: %d секунд%n", totalDuration);
    }

    private static void sortAlbumsByGenre() {
        Collections.sort(albums, (a1, a2) -> {
            // Сравниваем первый жанр между альбомами
            String genre1 = a1.getSongs().get(0).getGenre();
            String genre2 = a2.getSongs().get(0).getGenre();
            return genre1.compareTo(genre2);
        });
        System.out.println("Альбомы отсортированы по жанру.");
    }

    private static void findTracksByDuration(Scanner scanner) {
        System.out.print("Введите минимальную продолжительность (секунды): ");
        int minDuration = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите максимальную продолжительность (секунды): ");
        int maxDuration = scanner.nextInt();
        scanner.nextLine();

        List<MusicalComposition> matchingCompositions = new ArrayList<>();
        for (Album album : albums) {
            for (Song song : album.getSongs()) {
                int duration = song.getDuration();
                if (duration >= minDuration && duration <= maxDuration) {
                    matchingCompositions.add(song);
                }
            }
        }

        if (matchingCompositions.isEmpty()) {
            System.out.println("В заданном диапазоне продолжительности треков не обнаружено.");
        } else {
            System.out.println("Треки в заданном диапазоне продолжительности:");
            for (MusicalComposition composition : matchingCompositions) {
                System.out.println(composition);
            }
        }
    }
}