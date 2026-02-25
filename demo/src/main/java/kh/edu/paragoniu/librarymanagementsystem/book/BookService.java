package kh.edu.paragoniu.librarymanagementsystem.book;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse create(CreateBookRequest request) {
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setAvailable(true);
        return BookResponse.from(bookRepository.save(book));
    }

    public List<BookResponse> list() {
        return bookRepository.findAll().stream().map(BookResponse::from).toList();
    }
}
