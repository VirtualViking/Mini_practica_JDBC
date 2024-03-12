package repositories;

import model.Sale;

import java.util.List;

public interface SaleRepository {
  List<Sale> findAll();
  void saveSale(Sale sale);
}
