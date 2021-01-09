package guru.springfamework.api.v1.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class CustomerDTO {
  private String firstname;
  private String lastname;
  @JsonProperty("customer_url")
  private String customerUrl;

  public CustomerDTO(String firstname, String lastname, String customerUrl) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.customerUrl = customerUrl;
  }

  public CustomerDTO() {
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getCustomerUrl() {
    return customerUrl;
  }

  public void setCustomerUrl(String customerUrl) {
    this.customerUrl = customerUrl;
  }

  @Override
  public String toString() {
    return "CustomerDTO{" +
        "firstname='" + firstname + '\'' +
        ", lastname='" + lastname + '\'' +
        ", customerUrl='" + customerUrl + '\'' +
        '}';
  }
}
