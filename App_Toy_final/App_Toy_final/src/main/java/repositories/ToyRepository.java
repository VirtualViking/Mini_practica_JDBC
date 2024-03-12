package repositories;

import model.Toy;

import java.util.List;

public interface ToyRepository {
  List<Toy> findAll();
  Toy findById(Long id);
  void save(Toy toy);
  void update(Toy toy);
}
