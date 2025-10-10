package main;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class Book {

    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Book)) {
            return false;
        }
        Book newBook = (Book) obj;
        return this.author.equalsIgnoreCase(newBook.author)
                && this.title.equalsIgnoreCase(newBook.title)
                && this.year == (newBook.year);
    }
    @Override
    public int hashCode() {
        return 31 * this.author.hashCode() + this.title.hashCode() + year;
    }

    @Override
    public String toString() {
        return this.author + " : " + this.title + ", " + this.year;
    }

}
