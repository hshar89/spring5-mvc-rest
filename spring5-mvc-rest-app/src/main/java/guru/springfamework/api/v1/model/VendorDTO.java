package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VendorDTO {
  @ApiModelProperty(value = "Name of the Vendor", required = true)
  private String name;

  @JsonProperty("vendor_url")
  private String vendorUrl;

  public VendorDTO(String name, String vendorUrl) {
    this.name = name;
    this.vendorUrl = vendorUrl;
  }

  public VendorDTO() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVendorUrl() {
    return vendorUrl;
  }

  public void setVendorUrl(String vendorUrl) {
    this.vendorUrl = vendorUrl;
  }

  @Override
  public String toString() {
    return "VendorDTO{" +
        "name='" + name + '\'' +
        ", vendorUrl='" + vendorUrl + '\'' +
        '}';
  }
}

