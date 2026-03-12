package kh.edu.paragoniu.librarymanagementsystem.book;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse create(CreateBookRequest request) {
        Optional<Book> existing = bookRepository
                .findByTitleAndIsbn(request.title().trim(), request.isbn().trim());

        if (existing.isPresent()) {
            Book book = existing.get();
            book.setQuantity(book.getQuantity() + 1);
            book.setAvailableQuantity(book.getAvailableQuantity() + 1);
            book.setAvailable(true);
            return BookResponse.from(bookRepository.save(book));
        }

        Book book = new Book();
        book.setTitle(request.title().trim());
        book.setAuthor(request.author().trim());
        book.setIsbn(request.isbn().trim());
        book.setQuantity(1);
        book.setAvailableQuantity(1);
        book.setAvailable(true);
        return BookResponse.from(bookRepository.save(book));
    }

    public List<BookResponse> list() {
        return bookRepository.findAll().stream().map(BookResponse::from).toList();
    }
}
