package service.imp;

import model.Sale;
import repositories.SaleRepository;
import service.SaleService;
import service.ToyService;

import java.util.List;

public class SaleServiceImp implements SaleService {

  private final ToyService toyService;
  private final SaleRepository saleRepository;

  public SaleServiceImp(ToyService toyService, SaleRepository saleRepository) {
    this.toyService = toyService;
    this.saleRepository = saleRepository;
  }
  @Override
  public void addSale(Sale sale) {
    validateStock(sale);
    decreaseStock(sale);

    saleRepository.saveSale(sale);
    }


  @Override
  public List<Sale> getAllSales() {
    return null;
  }

  public void validateStock(Sale sale) {
    sale.getSaleDetails()
            .stream()
            .filter(saleDetail -> saleDetail.getToy().getToyAmount() < saleDetail.getQuantity())
            .forEach(saleDetail -> {
      throw new RuntimeException("Not enough stock for toy " + saleDetail.getToy().getToyName());
    });
  }
  public void decreaseStock(Sale sale) {
    sale.getSaleDetails()
            .forEach(saleDetail -> {
      saleDetail.getToy().setToyAmount(saleDetail.getToy().getToyAmount() - saleDetail.getQuantity());
      toyService.updateToy(saleDetail.getToy());
    });
  }
}
