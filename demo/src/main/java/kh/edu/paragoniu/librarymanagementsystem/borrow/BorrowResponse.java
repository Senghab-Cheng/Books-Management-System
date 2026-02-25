package kh.edu.paragoniu.librarymanagementsystem.borrow;

import java.time.LocalDate;

public record BorrowResponse(
        Long id,
        Long bookId,
        Long memberId,
        LocalDate borrowDate,
        LocalDate returnDate
) {
    public static BorrowResponse from(BorrowRecord borrowRecord) {
        return new BorrowResponse(
                borrowRecord.getId(),
                borrowRecord.getBook().getId(),
                borrowRecord.getMember().getId(),
                borrowRecord.getBorrowDate(),
                borrowRecord.getReturnDate()
        );
    }
}
