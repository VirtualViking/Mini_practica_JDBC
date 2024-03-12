package model;

import java.util.List;

public class Sale {
  private Long id;
  private User employee;
  private User customer;
  private List<SaleDetail> saleDetails;

  public Sale(Long id, User employee, User customer, List<SaleDetail> saleDetails) {
    this.id = id;
    this.employee = employee;
    this.customer = customer;
    this.saleDetails = saleDetails;
  }

  public Sale() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getEmployee() {
    return employee;
  }

  public void setEmployee(User employee) {
    this.employee = employee;
  }

  public User getCustomer() {
    return customer;
  }

  public void setCustomer(User customer) {
    this.customer = customer;
  }

  public List<SaleDetail> getSaleDetails() {
    return saleDetails;
  }

  public void setSaleDetails(List<SaleDetail> saleDetails) {
    this.saleDetails = saleDetails;
  }
}
