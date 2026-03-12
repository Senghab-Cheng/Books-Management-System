package kh.edu.paragoniu.librarymanagementsystem.book;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        int quantity,
        int availableQuantity,
        boolean available
) {
    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getQuantity(),
                book.getAvailableQuantity(),
                book.isAvailable()
        );
    }
}
