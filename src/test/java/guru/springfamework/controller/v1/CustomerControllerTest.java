package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controller.RestResponseEntityExceptionHandler;
import static guru.springfamework.controller.v1.AbstractRestControllerTest.asJsonString;
import guru.springfamework.exception.ResourceNotFoundException;
import guru.springfamework.service.CustomerService;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CustomerControllerTest {

  public static final String NAME = "Test";

  @Mock
  CustomerService customerService;

  @InjectMocks
  CustomerController customerController;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(customerController)
        .setControllerAdvice(new RestResponseEntityExceptionHandler())
        .build();
  }

  @Test
  public void testGetListofCustomers() throws Exception {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname("Test");
    customerDTO.setLastname("Testy");
    customerDTO.setCustomerUrl("blank.com");

    CustomerDTO customerDTO2 = new CustomerDTO();
    customerDTO2.setFirstname("Test2");
    customerDTO2.setLastname("Testy2");
    customerDTO2.setCustomerUrl("blank2.com");

    List<CustomerDTO> customerDTOList = Arrays.asList(customerDTO, customerDTO2);

    when(customerService.getAllCustomers()).thenReturn(customerDTOList);

    mockMvc.perform(get("/api/v1/customers/")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.customers", hasSize(2)));

  }

  @Test
  public void testGetCustomerById() throws Exception {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname("Test");
    customerDTO.setLastname("Testy");
    customerDTO.setCustomerUrl("blank.com");

    when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
    mockMvc.perform(get("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstname", equalTo(NAME)));
  }

  @Test
  public void createNewCustomer() throws Exception {
    //given
    CustomerDTO customer = new CustomerDTO();
    customer.setFirstname("Fred");
    customer.setLastname("Flintstone");

    CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstname(customer.getFirstname());
    returnDTO.setLastname(customer.getLastname());
    returnDTO.setCustomerUrl("/api/v1/customers/1");

    when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

    //when/then
    mockMvc.perform(post("/api/v1/customers/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstname", equalTo("Fred")))
        .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
  }

  @Test
  public void testUpdateCustomer() throws Exception {
    //given
    CustomerDTO customer = new CustomerDTO();
    customer.setFirstname("Fred");
    customer.setLastname("Flintstone");

    CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstname(customer.getFirstname());
    returnDTO.setLastname(customer.getLastname());
    returnDTO.setCustomerUrl("/api/v1/customers/1");

    when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

    //when/then
    mockMvc.perform(put("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstname", equalTo("Fred")))
        .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
        .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
  }

  @Test
  public void testPatchCustomer() throws Exception {

    //given
    CustomerDTO customer = new CustomerDTO();
    customer.setFirstname("Fred");

    CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstname(customer.getFirstname());
    returnDTO.setLastname("Flintstone");
    returnDTO.setCustomerUrl("/api/v1/customers/1");

    when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

    mockMvc.perform(patch("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstname", equalTo("Fred")))
        .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
        .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
  }

  @Test
  public void testDeleteCustomer() throws Exception {

    mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(customerService).deleteCustomerById(anyLong());
  }
  @Test
  public void testNotFoundException() throws Exception {

    when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

    mockMvc.perform(get(CustomerController.BASE_URL + "/222")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}