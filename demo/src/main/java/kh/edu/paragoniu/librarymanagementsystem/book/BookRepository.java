package kh.edu.paragoniu.librarymanagementsystem.book;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndIsbn(String title, String isbn);
}
