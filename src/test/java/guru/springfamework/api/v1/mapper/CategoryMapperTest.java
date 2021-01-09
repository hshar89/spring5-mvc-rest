package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import junit.framework.TestCase;

import org.junit.Test;

public class CategoryMapperTest extends TestCase {

  public static final String NAME ="Joe";
  public static final long id = 1l;
  CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
  @Test
  public void testCategoryToCategoryDTO() {
    Category category = new Category();
    category.setName(NAME);
    category.setId(id);

    CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
    assertEquals(Long.valueOf(1l),categoryDTO.getId());
    assertEquals(NAME,categoryDTO.getName());
  }
}