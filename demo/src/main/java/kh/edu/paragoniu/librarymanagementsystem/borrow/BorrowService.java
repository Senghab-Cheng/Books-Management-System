package kh.edu.paragoniu.librarymanagementsystem.borrow;

import kh.edu.paragoniu.librarymanagementsystem.book.Book;
import kh.edu.paragoniu.librarymanagementsystem.book.BookRepository;
import kh.edu.paragoniu.librarymanagementsystem.common.BusinessException;
import kh.edu.paragoniu.librarymanagementsystem.common.NotFoundException;
import kh.edu.paragoniu.librarymanagementsystem.member.Member;
import kh.edu.paragoniu.librarymanagementsystem.member.MemberRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public BorrowService(BookRepository bookRepository,
                         MemberRepository memberRepository,
                         BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    @Transactional
    public BorrowResponse borrow(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found: " + bookId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found: " + memberId));

        if (!book.isAvailable()) {
            throw new BusinessException("Book is not available");
        }

        book.setAvailable(false);

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBook(book);
        borrowRecord.setMember(member);
        borrowRecord.setBorrowDate(LocalDate.now());

        return BorrowResponse.from(borrowRecordRepository.save(borrowRecord));
    }

    @Transactional
    public BorrowResponse returnBook(Long borrowId) {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowId)
                .orElseThrow(() -> new NotFoundException("Borrow record not found: " + borrowId));

        if (borrowRecord.getReturnDate() != null) {
            throw new BusinessException("Book already returned");
        }

        borrowRecord.setReturnDate(LocalDate.now());
        borrowRecord.getBook().setAvailable(true);

        return BorrowResponse.from(borrowRecordRepository.save(borrowRecord));
    }
}
