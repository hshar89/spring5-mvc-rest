package guru.springfamework.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

  @Mock
  CustomerRepository customerRepository;

  CustomerMapper customerMapper = CustomerMapper.INSTANCE;

  CustomerServiceImpl customerService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    customerService = new CustomerServiceImpl(customerRepository, customerMapper);
  }


  @Test
  public void testGetAllCustomers() {
    //given
    Customer customer1 = new Customer();
    customer1.setId(1l);
    customer1.setFirstname("Michale");
    customer1.setLastname("Weston");

    Customer customer2 = new Customer();
    customer2.setId(2l);
    customer2.setFirstname("Sam");
    customer2.setLastname("Axe");

    when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

    //when
    List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

    //then
    assertEquals(2, customerDTOS.size());
  }

  @Test
  public void testGetCustomerById() {
    Customer customer1 = new Customer();
    customer1.setId(1l);
    customer1.setFirstname("Michale");
    customer1.setLastname("Weston");

    when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

    //when
    CustomerDTO customerDTO = customerService.getCustomerById(1L);

    assertEquals("Michale", customerDTO.getFirstname());
  }

  @Test
  public void testSaveCustomer() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname("Michale");
    customerDTO.setLastname("Weston");

    Customer customer1 = new Customer();
    customer1.setId(1l);
    customer1.setFirstname(customerDTO.getFirstname());
    customer1.setLastname(customerDTO.getLastname());


    when(customerRepository.save(any())).thenReturn(customer1);

    //when
    CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

    assertEquals(customer1.getFirstname(), savedDto.getFirstname());
    assertEquals("api/v1/customers/1", savedDto.getCustomerUrl());
  }

  @Test
  public void saveCustomerByDTO() throws Exception {

    //given
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname("Jim");

    Customer savedCustomer = new Customer();
    savedCustomer.setFirstname(customerDTO.getFirstname());
    savedCustomer.setLastname(customerDTO.getLastname());
    savedCustomer.setId(1l);

    when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

    //when
    CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

    //then
    assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
    assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
  }

  @Test
  public void deleteCustomerById() throws Exception {

    Long id = 1L;

    customerRepository.deleteById(id);

    verify(customerRepository, times(1)).deleteById(anyLong());
  }
}