package guru.springfamework.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class CustomerListDTO {
  List<CustomerDTO> customers;

  public CustomerListDTO() {
  }

  public List<CustomerDTO> getCustomers() {
    return customers;
  }

  public void setCustomers(List<CustomerDTO> customers) {
    this.customers = customers;
  }

  public CustomerListDTO(List<CustomerDTO> customers) {
    this.customers = customers;
  }
}
