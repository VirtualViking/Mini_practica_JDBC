package service;

import model.Sale;

import java.util.List;

public interface SaleService {
  void addSale(Sale sale);
  List<Sale> getAllSales();
}
