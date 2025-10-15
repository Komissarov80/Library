package main;

import java.util.Locale;
import java.util.Scanner;

public class LibraryConsoleUI {

    Library library;
    Scanner scan;

    public LibraryConsoleUI() {
        library = new Library();
        scan = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printMainMenu();
            int action = scan.nextInt();
            scan.nextLine();
            switch (action) {
                case 1: {
                    System.out.println("Enter title");
                    String title = scan.nextLine();
                    System.out.println("Enter author");
                    String author = scan.nextLine();
                    System.out.println("Enter year of issue");
                    int year = scan.nextInt();
                    scan.nextLine();
                    Book book = new Book(title, author, year);
                    library.addBook(book);
                    continue;
                }
                case 2: {
                    System.out.println("Enter title or 0");
                    String title = scan.nextLine();
                    System.out.println("Enter author or 0");
                    String author = scan.nextLine();
                    System.out.println("Enter year or 0");
                    int year = scan.nextInt();
                   library.findBooks(title, author, year);
                    continue;
                }
                case 3: {
                    library.showAllBooks();
                    continue;
                }
                case 4: {
                    Scanner scan = new Scanner(System.in);
                    while(true) {
                        System.out.println("For refund book enter your owner id or enter 0 for exit");
                        int input = scan.nextInt();
                        if (input == 0) {
                            break;
                        }
                        if (!library.isExistOwner(input)) {
                            System.out.println("wrong id owner retry or enter 0 for exit");
                            continue;
                        }
                        System.out.println("Enter book's id");
                        int idBook = scan.nextInt();
                        if (!library.isExistBook(idBook)) {
                            System.out.println("wrong id book retry or enter 0 for exit");
                            continue;
                        }
                        Book book = library.getBook(idBook);
                        library.ownerRefundBook(input, idBook);
                        break;
                    }
                }
                continue;
                case 5: {
                    System.out.println("Enter firstName");
                    String firstName = scan.nextLine();
                    System.out.println("Enter lastName");
                    String lastName = scan.nextLine();
                    System.out.println("Enter email");
                    String eMail = scan.nextLine();
                    System.out.println("Enter telephone");
                    String telephone = scan.nextLine();
                    Owner owner = new Owner(firstName, lastName, eMail, telephone);
                    continue;
                }
                case 6: {
                    System.out.println("Чтобы посмотреть книги читателя введите номер читателя");
                    int id = scan.nextInt();
                    library.showBooksOwner(id);
                    continue;
                }
                case 7: {
                    library.showBooksAllOwners();
                    continue;
                }
                case 0: {
                    System.out.println("До свидания ");
                    break;
                }
                default:
                    System.out.println("Не верная команда");
                    continue;
            }
            break;
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== Библиотека ===");
        System.out.println("1. Добавить книгу");
        System.out.println("2. Найти книгу");
        System.out.println("3. Показать все книги");
        System.out.println("4. Вернуть книгу");
        System.out.println("5. Зарегистрировать читателя");
        System.out.println("6. Показать книги читателя");
        System.out.println("7. Показать книги всех читателей");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

}
