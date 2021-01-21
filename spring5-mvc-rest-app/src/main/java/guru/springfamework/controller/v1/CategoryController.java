package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/categories/")
  @ResponseStatus(HttpStatus.OK)
  public CategoryListDTO getAllCategories() {
    return new CategoryListDTO(categoryService.getAllCategories());
  }
  @GetMapping("/category/{name}")
  @ResponseStatus(HttpStatus.OK)
  public CategoryDTO getCategoryByName(@PathVariable String name) {
    return categoryService.getCategoryByName(name);
  }
}
