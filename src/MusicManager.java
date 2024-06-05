import java.util.*;

public class MusicManager {
    private static final Map<String, Album> albums = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline character

                switch (choice) {
                    case 1:
                        createAlbum(scanner);
                        break;
                    case 2:
                        displayAlbums();
                        break;
                    case 3:
                        calculateTotalDuration();
                        break;
                    case 4:
                        sortAlbumsByTitle();
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
            } catch (Exception e) {
                System.out.println("Введите указанную цифру!");
                scanner.nextLine(); // consume the rest of the line
            }
        }

        System.out.println("Пока!");
    }

    private static void showMenu() {
        System.out.println("Меню:");
        System.out.println("1. Создать альбом");
        System.out.println("2. Вывести все альбомы");
        System.out.println("3. Рассчитать общую продолжительность");
        System.out.println("4. Сортировка альбомов по названию");
        System.out.println("5. Найти треки по продолжительности");
        System.out.println("6. Выход");
        System.out.print("Сделайте выбор: ");
    }

    private static void createAlbum(Scanner scanner) {
        try {
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
                int songDuration = validateDuration(scanner.nextLine());
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
            albums.put(title, album);
            System.out.println("Альбом успешно добавлен.");
        } catch (InvalidDurationException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка при создании альбома: " + e.getMessage());
        }
    }

    private static void displayAlbums() {
        if (albums.isEmpty()) {
            System.out.println("Список альбомов пуст.");
        } else {
            System.out.println("Все альбомы:");
            for (Album album : albums.values()) {
                System.out.println(album);
            }
        }
    }

    private static void calculateTotalDuration() {
        int totalDuration = albums.values().stream().mapToInt(MusicalComposition::getDuration).sum();
        System.out.printf("Общая продолжительность всех альбомов: %d секунд%n", totalDuration);
    }

    private static void sortAlbumsByTitle() {
        List<Album> sortedAlbums = new ArrayList<>(albums.values());
        sortedAlbums.sort(Comparator.comparing(Album::getTitle));

        System.out.println("Альбомы отсортированы по названию:");
        for (Album album : sortedAlbums) {
            System.out.println(album);
        }
    }

    private static void findTracksByDuration(Scanner scanner) {
        try {
            System.out.print("Введите минимальную продолжительность (секунды): ");
            String minDurationInput = scanner.nextLine();
            int minDuration = validateDuration(minDurationInput);

            System.out.print("Введите максимальную продолжительность (секунды): ");
            String maxDurationInput = scanner.nextLine();
            int maxDuration = validateDuration(maxDurationInput);

            List<MusicalComposition> matchingCompositions = new ArrayList<>();
            for (Album album : albums.values()) {
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
        } catch (InvalidDurationException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка при поиске треков по продолжительности: " + e.getMessage());
        }
    }

    private static int validateDuration(String durationInput) throws InvalidDurationException {
        try {
            int duration = Integer.parseInt(durationInput);
            if (duration < 1 || duration > 86400) {
                throw new InvalidDurationException("Продолжительность должна быть в диапазоне от 1 до 86400 секунд.");
            }
            return duration;
        } catch (NumberFormatException e) {
            throw new InvalidDurationException("Неверный формат продолжительности. Пожалуйста, введите число.");
        }
    }

    static class InvalidDurationException extends Exception {
        public InvalidDurationException(String message) {
            super(message);
        }
    }
}