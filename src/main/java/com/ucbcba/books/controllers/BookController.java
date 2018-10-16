package com.ucbcba.books.controllers;


import com.ucbcba.books.entities.Author;
import com.ucbcba.books.entities.Book;
import com.ucbcba.books.entities.BookCategory;
import com.ucbcba.books.services.AuthorService;
import com.ucbcba.books.services.BookCategoryService;
import com.ucbcba.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {
    BookService bookService;
    BookCategoryService bookCategoryService;

    AuthorService authorService;


    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }


    @Autowired
    public void setBookCategoryService(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String index( Model model) {
        List<Book> books  = (List) bookService.listAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @RequestMapping(value = "/book/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        Book book = bookService.findBook(id);
        model.addAttribute("book", book);
        List<BookCategory> bookCategories  =
                (List) bookCategoryService.listAllBookCategories();

        List<Author> authors  =
                (List) authorService.listAllAuthors();
        model.addAttribute("authors", authors);
        model.addAttribute("bookCategories", bookCategories);
        return "editBook";
    }

    @RequestMapping(value = "/book/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, Model model) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @RequestMapping(value = "/book/like/{id}", method = RequestMethod.GET)
    public String like(@PathVariable Integer id, Model model) {
        Book book= bookService.findBook(id);
        book.setLikes(book.getLikes()+1);
        bookService.saveBook(book);
        return "redirect:/books";
    }
    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Integer id, Model model) {
        Book book = bookService.findBook(id);
        model.addAttribute("book", book);
        return "showBook";
    }
    @RequestMapping(value = "/book/new", method = RequestMethod.GET)
    public String newBook( Model model) {
        List<BookCategory> bookCategories  =
                (List) bookCategoryService.listAllBookCategories();

        List<Author> authors  =
                (List) authorService.listAllAuthors();

        model.addAttribute("book", new Book());

        model.addAttribute("errorLikes", "");
        model.addAttribute("authors", authors);
        model.addAttribute("bookCategories", bookCategories);
        model.addAttribute("errorMostrar", "");

        return "newBook";
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         Model model) {

        System.out.println("Book category " + book.getBookCategory().getId());
        if(bindingResult.hasErrors()){
            String errorLikes =bindingResult.getFieldError("likes").getDefaultMessage();
            model.addAttribute("errorLikes", errorLikes);
            return "newBook";
        }
        System.out.println("Book author " + book.getAuthor().getId());
        bookService.saveBook(book);
        return "redirect:/books";
    }
}
