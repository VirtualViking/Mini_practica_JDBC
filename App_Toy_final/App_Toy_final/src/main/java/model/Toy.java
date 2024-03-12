package model;


import java.io.Serializable;

public class Toy  implements Comparable<Toy>, Serializable {
    private Long id;
    public String toyName;
    private int toyAmount;
    public Category toyCategory;
    public double toyPrice;

    public Toy(){}
    public Toy(String toyName, int toyAmount, Category toyCategory, double toyPrice) {
        this.toyName = toyName;
        this.toyAmount = toyAmount;
        this.toyCategory = toyCategory;
        this.toyPrice = toyPrice;
    }

    public String getToyName() {
        return toyName;
    }

    public int getToyAmount() {
        return toyAmount;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public void setToyAmount(int toyAmount) {
        this.toyAmount = toyAmount;
    }

    public Category getToyCategory() {
        return toyCategory;
    }

    public void setToyCategory(Category toyCategory) {
        this.toyCategory = toyCategory;
    }

    public double getToyPrice() {
        return toyPrice;
    }

    public void setToyPrice(double toyPrice) {
        this.toyPrice = toyPrice;
    }

    @Override
    public int compareTo(Toy toy) {
        return (int) (this.toyPrice - toy.getToyPrice());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
