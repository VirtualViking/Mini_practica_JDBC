package model;

public class SaleDetail {
  private Long id;
  private Toy toy;
  private int quantity;

  public SaleDetail(Long id, Toy toy, int quantity) {
    this.id = id;
    this.toy = toy;
    this.quantity = quantity;
  }

  public SaleDetail() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Toy getToy() {
    return toy;
  }

  public void setToy(Toy toy) {
    this.toy = toy;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
