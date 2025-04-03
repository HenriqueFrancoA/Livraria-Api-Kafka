package br.com.henrique.bookservice.models.dto;

public class BookDto {

    private String title;
    private String isbn;

    public BookDto() {
    }

    public BookDto(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
