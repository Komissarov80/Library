package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library {
    // карта bookMap содержит книги, которые есть в библиотеке, в том числе количество доступных.
    // при получении читателем книги, количество доступных книг уменьшается.
    // что бы в bookMap оставить сведения о книге даже в случае отсутствия в наличии (все взяли)
    // по 0 индексу книга не доступня для получения и при первичном добавлении, добавляется два раза.
    private final Map<Integer, List<Book>> bookMap = new HashMap<>();
    private static final Map<Integer, Owner> ownerMap = new HashMap<>();
    private static final Map<Owner, List<Book>> libraryMap = new HashMap<>();
    private final Queue<Integer> idFreeBook = new LinkedList<>();
    private final Queue<Integer> idFreeOwner = new LinkedList<>();
    private static int idBook = 1;
    private static int idOwner = 1;

    public Library() {
        addBook(new Book("Война и мир", "Толстой", 1890));
        addBook(new Book("Анна Каренина", "Толстой", 1890));
        addBook(new Book("Отцы и дети", "Чехов", 1890));
        addBook(new Book("Му-му", "Чехов", 1890));
        createOwner("Виталий", "Комиссаров", "почта", "+79788817005");
    }

    public boolean isExistBook(Book book) {
        return bookMap.entrySet().stream().map(x -> x.getValue()).anyMatch(x -> x.contains(book));
    }

    public boolean isExistBook(int id) {
        System.out.println(bookMap.get(id));
        return bookMap.containsKey(id);
    }

    public int howMuchBookAvailable(Book book) {
        return bookMap.get(getIdBook(book)).size() - 1;
    }

    public int howMuchBookAvailable(int idBook) {
        return bookMap.get(idBook).size() - 1;
    }

    public boolean isExistOwner(Owner owner) {
        return ownerMap.entrySet().stream().map(x -> x.getValue()).anyMatch(x -> x.equals(owner));
    }

    public boolean isExistOwner(int id) {
        return ownerMap.containsKey(id);
    }

    public boolean isExistBook(String title, String author) {
        return bookMap.entrySet().stream().flatMap(x -> x.getValue().stream())
                .anyMatch(y -> y.getTitle().equalsIgnoreCase(title) && y.getAuthor().equalsIgnoreCase(author));
    }

    public void findBooks(String title, String author, int year) {
        if (!title.equals("0") && !author.equals("0") && year != 0) {
            findBooksByAuthorTitleYear(author, title, year);
        }
        if (!title.equals("0") && !author.equals("0") && year == 0) {
            findBooksByAuthorTitle(title, author);
        }
        if (!title.equals("0") && author.equals("0") && year == 0) {
            findBooksByTitle(title);
        }
        if (title.equals("0") && !author.equals("0") && year == 0) {
            findBooksByAuthor(author);
        }
        if (title.equals("0") && author.equals("0") && year != 0) {
            findBooksByYear(year);
        }
    }

    public Map<Integer, List<Book>> findBooksByPredicate(Predicate<Book> condition) {
        return bookMap.entrySet().stream().filter(x -> x.getValue().stream().anyMatch(condition)).collect(
                Collectors.toMap(Map.Entry::getKey,
                        y -> y.getValue().stream().filter(condition).collect(Collectors.toList())));
    }

    public void findBooksByAuthorTitleYear(String author, String title, int year) {
        Map<Integer, List<Book>> map = findBooksByPredicate(book -> book.getAuthor().equalsIgnoreCase(author) && book.getTitle()
                .equalsIgnoreCase(title) && book.getYear() == year);
//        List<Book> books = bookMap.entrySet().stream().flatMap(x -> x.getValue().stream())
//                .filter(x -> x.getAuthor().toLowerCase(Locale.ROOT).equals(author))
//                .filter(x -> x.getTitle().toLowerCase(Locale.ROOT).equals(title)).filter(x -> x.getYear() == year)
//                .collect(Collectors.toList());
        if (map.isEmpty()) {
            System.out.println("books by author " + author + " and by title " + title + " and by year " + year + " not found");
            return;
        }
        boolean isavailable = map.values().size() > 1;
        if (!isavailable) {
            System.out.println("There no available book");
            return;
        }
        makeChoise(map);
    }

    public void findBooksByAuthorTitle(String author, String title) {
        Map<Integer, List<Book>> map = findBooksByPredicate(book -> book.getAuthor().equalsIgnoreCase(author) &&
                book.getTitle().equalsIgnoreCase(title));
        if (map.isEmpty()) {
            System.out.println("books by author " + author + " and by title " + title + " not found");
            return;
        }
        boolean isavailable = map.values().size() > 1;
        if (!isavailable) {
            System.out.println("There no available book");
            return;
        }
        makeChoise(map);
    }

    public void findBooksByAuthor(String author) {
        Map<Integer, List<Book>> map = findBooksByPredicate(book -> book.getAuthor().equalsIgnoreCase(author));
        if (map.isEmpty()) {
            System.out.println("books by author " + author + " not found");
            return;
        }
        boolean isavailable = map.values().size() > 1;
        if (!isavailable) {
            System.out.println("There no available book");
            return;
        }
        makeChoise(map);
    }

    public void findBooksByTitle(String title) {
        Map<Integer, List<Book>> map = findBooksByPredicate(book -> book.getTitle().equalsIgnoreCase(title));
        if (map.isEmpty()) {
            System.out.println("books by title " + title + " not found");
            return;
        }
        boolean isavailable = map.values().size() > 1;
        if (!isavailable) {
            System.out.println("There no available book");
            return;
        }
        makeChoise(map);
    }

    public void findBooksByYear(int year) {
        Map<Integer, List<Book>> map = findBooksByPredicate(book -> book.getYear() == year);
        if (map.isEmpty()) {
            System.out.println("books by year " + year + " not found");
            return;
        }
        boolean isavailable = map.values().size() > 1;
        if (!isavailable) {
            System.out.println("There no available book");
            return;
        }
            makeChoise(map);
    }

    public void makeChoise(Map<Integer, List<Book>> foundBooks) {
        Scanner scan = new Scanner(System.in);
        Owner owner = null;
        int key = 1;
        for (Map.Entry<Integer, List<Book>> entry : foundBooks.entrySet()) {
            System.out.println("number of book: " + entry.getKey() + " book: " + entry.getValue().get(0));
        }
        System.out.println("For choise book enter your id or 0 for registration");
        int input = scan.nextInt();
        if (input == 0) {
            owner = createOwner();
        }
        if (input != 0) {
            owner = getOwner(input);
        }

        System.out.println("For choise book enter a number of book or 0 for finish");
        int number = scan.nextInt();
        while (number != 0) {
            if (number > bookMap.size() || number < 0) {
                System.out.println("wrong book number try again or enter 0 for exit");
                number = scan.nextInt();
                continue;
            }
            ownerGetBook(owner, number);
            System.out.println("For choise book enter a number of book or 0 for finish");
            number = scan.nextInt();
        }
    }

    public Book getBook(int key) {
        return bookMap.get(key).get(0);
    }

    private int setIdBook() {
        if (idFreeBook.size() > 0) {
            return idFreeBook.poll();
        } else {
            return idBook++;
        }
    }

    private int getIdBook(Book book) {
        if (!isExistBook(book)) {
            throw new IllegalArgumentException("In getIdBook. There no book with " + book);
        }
        return bookMap.entrySet().stream().filter(x -> x.getValue().contains(book)).map(x -> x.getKey()).findFirst()
                .get();
    }

    public void deleteBook(int id) {
        idFreeBook.offer(id);
        bookMap.remove(id);
    }

    // карта bookMap содержит книги, которые есть в библиотеке, в том числе количество доступных.
    // при получении читателем книги, количество доступных книг уменьшается.
    // что бы в bookMap оставить сведения о книге даже в случае отсутствия в наличии (все взяли)
    // по 0 индексу книга не доступня для получения и при первичном добавлении, добавляется два раза.
    public void addBook(Book book) {
        if (isExistBook(book)) {
            int id = getIdBook(book);
            bookMap.computeIfPresent(id, (k, v) -> {
                v.add(book);
                return v;
            });
        } else {
            // по 0 индексу книга не доступня для получения и при первичном добавлении, добавляется два раза.
            int id = setIdBook();
            List<Book> list = new ArrayList<>(List.of(book, book));
            bookMap.put(id, list);
        }
    }

    public Owner getOwner(int id) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            if (id == 0) {
                break;
            }
            if (!ownerMap.containsKey(id)) {
                System.out.println("Не верный номер читателя попробуйте еще раз или введите 0 для выхода");
                id = scan.nextInt();
                continue;
            } else {
                return ownerMap.get(id);
            }
        }
        throw new IllegalArgumentException("Читатель не найден");
    }

    private int setIdOwner() {
        if (!idFreeOwner.isEmpty()) {
            return idFreeOwner.poll();
        } else {
            return idOwner++;
        }
    }

    private int getIdOwner(Owner owner) {
        if (!isExistOwner(owner)) {
            throw new IllegalArgumentException("There no owner with name " + owner.getFirstName());
        }
        return ownerMap.entrySet().stream().filter(x -> x.getValue().equals(owner)).mapToInt(Map.Entry::getKey)
                .findFirst().getAsInt();
    }

    private void createOwner(String firstName, String lastName, String eMail, String telephone) {
        Owner owner = new Owner(firstName, lastName, eMail, telephone);
        if (isExistOwner(owner)) {
            throw new IllegalArgumentException(
                    "Can't add. There is in library owner with name " + owner.getFirstName());
        }
        ownerMap.putIfAbsent(setIdOwner(), owner);
    }

    private Owner createOwner() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your firstname");
        String firstName = scan.nextLine();
        System.out.println("Enter your lastname");
        String lastName = scan.nextLine();
        System.out.println("Enter your email");
        String eMail = scan.nextLine();
        System.out.println("Enter your telephone");
        String telephone = scan.nextLine();
        Owner owner = new Owner(firstName, lastName, eMail, telephone);
        if (isExistOwner(owner)) {
            throw new IllegalArgumentException(
                    "Can't add. There is in library owner with name " + owner.getFirstName());
        }
        ownerMap.putIfAbsent(setIdOwner(), owner);
        return owner;
    }

    private void deleteOwner(Owner owner) {
        if (!isExistOwner(owner)) {
            throw new IllegalArgumentException("Can't delete. There no owner with name " + owner.getFirstName());
        }
        int id = getIdOwner(owner);
        libraryMap.remove(owner);
        ownerMap.remove(id);
        idFreeOwner.offer(id);
    }

    public void showAllBooks() {
        if (bookMap.isEmpty()) {
            System.out.println("There are no books in the library");
            return;
        }
        bookMap.forEach((k, v) -> {
            System.out.print(k);
            System.out.println(" : " + v.get(0) + " quantity: " + (v.size() - 1));
        });
    }

    public List<Book> getBooksOwner(Owner owner) {
        return libraryMap.entrySet().stream().filter(x -> x.getKey().equals(owner)).map(x -> x.getValue()).findFirst()
                .get();
    }

    public void ownerGetBook(Owner owner, int keyBook) {
        int index = bookMap.get(keyBook).size() - 1;
        Book book = bookMap.get(keyBook).get(index);
        if (isExistOwner(owner)) {
            libraryMap.computeIfPresent(owner, (k, books) -> {
                books.add(book);
                return books;
            });
            libraryMap.computeIfAbsent(owner, k -> new ArrayList<>()).add(book);
            bookMap.get(keyBook).remove(index);
            System.out.println("You get a book " + book);
        } else {
            System.out.println("There no book or owner with this id");
        }
    }

    public void ownerRefundBook(int idOwner, int idBook) {
        Owner owner = ownerMap.get(idOwner);
        Book book = bookMap.get(idBook).get(0);
        libraryMap.computeIfPresent(owner, (k, books) -> {
            books.remove(book);
            bookMap.get(idBook).add(book);
            return books;
        });
        List<Book> list = libraryMap.get(idOwner);
        if (libraryMap.get(idOwner) == null) {
            libraryMap.remove(idOwner);
        }
    }

    public void showBooksOwner(int key) {
        libraryMap.entrySet().forEach(x -> {
            System.out.print(x.getKey() + " : ");
            for(Book b : x.getValue()) {
                System.out.println(b);
            }
        });
    }

}
