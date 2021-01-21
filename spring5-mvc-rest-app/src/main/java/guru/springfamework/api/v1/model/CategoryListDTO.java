package guru.springfamework.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class CategoryListDTO {

  List<CategoryDTO> categories;

  public CategoryListDTO(List<CategoryDTO> categories) {
    this.categories = categories;
  }

  public List<CategoryDTO> getCategoryDTOList() {
    return categories;
  }

  public void setCategoryDTOList(List<CategoryDTO> categoryDTOList) {
    this.categories = categories;
  }
}
