package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  VendorRepository vendorRepository;

  CustomerService customerService;

  @Before
  public void setUp() throws Exception {
    System.out.println("Loading Customer data");
    System.out.println(customerRepository.findAll().size());

    Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
    bootstrap.run();
    customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
  }

  @Test
  public void patchCustomerUpdateFirstName(){
    String updateName = "UpdatedName";
    long id = getCustomerIdValue();
    Customer original = customerRepository.getOne(id);
    assertNotNull(original);
    String originalFN = original.getFirstname();
    String originalLN = original.getLastname();

    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname(updateName);

    customerService.patchCustomer(id,customerDTO);
    Customer updated = customerRepository.findById(id).get();
    assertNotNull(updated);
    assertEquals(updateName, updated.getFirstname());
    assertThat(originalFN, not(equalTo(updated.getFirstname())));
  }

  @Test
  public void patchCustomerUpdateLastName(){
    String updatedName = "UpdatedName";
    long id = getCustomerIdValue();

    Customer originalCustomer = customerRepository.getOne(id);
    assertNotNull(originalCustomer);

    //save original first/last name
    String originalFirstName = originalCustomer.getFirstname();
    String originalLastName = originalCustomer.getLastname();

    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setLastname(updatedName);

    customerService.patchCustomer(id, customerDTO);

    Customer updatedCustomer = customerRepository.findById(id).get();

    assertNotNull(updatedCustomer);
    assertEquals(updatedName, updatedCustomer.getLastname());
    assertThat(originalFirstName, equalTo(updatedCustomer.getFirstname()));
    assertThat(originalLastName, not(equalTo(updatedCustomer.getLastname())));
  }

  private Long getCustomerIdValue(){
    List<Customer> customers = customerRepository.findAll();
    System.out.println("Customers found: "+customers.size());
    return customers.get(0).getId();
  }
}
