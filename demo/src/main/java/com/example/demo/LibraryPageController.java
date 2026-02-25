package com.example.demo;

import com.example.demo.book.BookService;
import com.example.demo.book.CreateBookRequest;
import com.example.demo.borrow.BorrowService;
import com.example.demo.member.CreateMemberRequest;
import com.example.demo.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LibraryPageController {

    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowService borrowService;

    public LibraryPageController(BookService bookService,
                                 MemberService memberService,
                                 BorrowService borrowService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowService = borrowService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookService.list());
        model.addAttribute("members", memberService.list());
        return "index";
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
        return "redirect:/";
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
        return "redirect:/";
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
        return "redirect:/";
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
        return "redirect:/";
    }
}
