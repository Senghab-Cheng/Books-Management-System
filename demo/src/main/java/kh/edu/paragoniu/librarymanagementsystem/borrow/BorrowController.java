package kh.edu.paragoniu.librarymanagementsystem.borrow;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/borrow/{bookId}/member/{memberId}")
    public BorrowResponse borrow(@PathVariable Long bookId, @PathVariable Long memberId) {
        return borrowService.borrow(bookId, memberId);
    }

    @PostMapping("/return/{borrowId}")
    public BorrowResponse returnBook(@PathVariable Long borrowId) {
        return borrowService.returnBook(borrowId);
    }
}
