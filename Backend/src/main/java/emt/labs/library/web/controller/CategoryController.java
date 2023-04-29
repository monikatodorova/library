package emt.labs.library.web.controller;

import emt.labs.library.model.Book;
import emt.labs.library.model.enumerations.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping
    public String getCategoriesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("categories", Category.values());
        model.addAttribute("bodyContent", "categories");
        return "master-template";
    }

}
