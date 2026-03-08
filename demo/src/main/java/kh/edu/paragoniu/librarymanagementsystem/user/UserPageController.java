package kh.edu.paragoniu.librarymanagementsystem.user;

import kh.edu.paragoniu.librarymanagementsystem.book.BookService;
import kh.edu.paragoniu.librarymanagementsystem.borrow.BorrowService;
import kh.edu.paragoniu.librarymanagementsystem.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserPageController {

    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowService borrowService;

    public UserPageController(BookService bookService,
                              MemberService memberService,
                              BorrowService borrowService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowService = borrowService;
    }

    @GetMapping("/user/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("books", bookService.list());
        model.addAttribute("members", memberService.list());
        return "user/dashboard";
    }

    @PostMapping("/user/borrow")
    public String borrow(@RequestParam Long bookId,
                         @RequestParam Long memberId,
                         RedirectAttributes redirectAttributes) {
        try {
            var borrow = borrowService.borrow(bookId, memberId);
            redirectAttributes.addFlashAttribute("message", "Borrowed successfully (Borrow ID: " + borrow.id() + ").");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/user/dashboard";
    }

    @PostMapping("/user/return")
    public String returnBook(@RequestParam Long borrowId,
                             RedirectAttributes redirectAttributes) {
        try {
            var borrow = borrowService.returnBook(borrowId);
            redirectAttributes.addFlashAttribute("message", "Returned successfully (Borrow ID: " + borrow.id() + ").");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/user/dashboard";
    }
}
