package guru.springfamework.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "This is my Customer Controller")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

  public static final String BASE_URL = "/api/v1/customers";

  @Autowired
  private CustomerService customerService;

  @ApiOperation(value = "This will get a list of customers.", notes = "These are some notes about the API.")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO findCustomerById(@PathVariable String id) {
    return customerService.getCustomerById(Long.valueOf(id));
  }

  @GetMapping
  public ResponseEntity<CustomerListDTO> getListofCustomers() {

    return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(customerService.getAllCustomers()),
        HttpStatus.OK);

  }

  @PostMapping
  public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
    return new ResponseEntity<>(customerService.createNewCustomer(customerDTO),
        HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
    return new ResponseEntity<>(customerService.saveCustomerByDTO(id, customerDTO), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
    return new ResponseEntity<>(customerService.patchCustomer(id, customerDTO), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> patchCustomer(@PathVariable Long id) {
    customerService.deleteCustomerById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
