package kh.edu.paragoniu.librarymanagementsystem.admin;

import kh.edu.paragoniu.librarymanagementsystem.book.BookService;
import kh.edu.paragoniu.librarymanagementsystem.book.CreateBookRequest;
import kh.edu.paragoniu.librarymanagementsystem.borrow.BorrowService;
import kh.edu.paragoniu.librarymanagementsystem.member.CreateMemberRequest;
import kh.edu.paragoniu.librarymanagementsystem.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminPageController {

    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowService borrowService;

    public AdminPageController(BookService bookService,
                               MemberService memberService,
                               BorrowService borrowService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowService = borrowService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("books", bookService.list());
        model.addAttribute("members", memberService.list());
        return "admin/dashboard";
    }

    @PostMapping("/ui/books")
    public String createBook(@RequestParam String title,
                             @RequestParam String author,
                             @RequestParam String isbn,
                             RedirectAttributes redirectAttributes) {
        try {
            var book = bookService.create(new CreateBookRequest(title.trim(), author.trim(), isbn.trim()));
            redirectAttributes.addFlashAttribute("message", "Book created (ID: " + book.id() + ").");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/ui/members")
    public String createMember(@RequestParam String name,
                               @RequestParam String email,
                               RedirectAttributes redirectAttributes) {
        try {
            var member = memberService.create(new CreateMemberRequest(name.trim(), email.trim()));
            redirectAttributes.addFlashAttribute("message", "Member created (ID: " + member.id() + ").");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/ui/borrow")
    public String borrowBook(@RequestParam Long bookId,
                             @RequestParam Long memberId,
                             RedirectAttributes redirectAttributes) {
        try {
            var borrow = borrowService.borrow(bookId, memberId);
            redirectAttributes.addFlashAttribute("message", "Borrowed successfully (Borrow ID: " + borrow.id() + ").");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/ui/return")
    public String returnBook(@RequestParam Long borrowId,
                             RedirectAttributes redirectAttributes) {
        try {
            var borrow = borrowService.returnBook(borrowId);
            redirectAttributes.addFlashAttribute("message", "Returned successfully (Borrow ID: " + borrow.id() + ").");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

}
