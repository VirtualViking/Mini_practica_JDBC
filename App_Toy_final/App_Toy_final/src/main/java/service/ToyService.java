package service;

import mapping.dtos.PublicInfoToyDTO;
import model.Category;
import model.Toy;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ToyService {

    void addToy(PublicInfoToyDTO toy);
    void addToy(Toy toy);
    Toy getToyById(Long id);
    List<Toy> getAllToysDb();
    void updateToy(Toy toy);
    void deleteToyByName(String name);
    Map<Category,Integer> getToysAmountByCategory();
    List<PublicInfoToyDTO> getAllToys();
    int getTotalToysAmount();
    double getTotalToysPrice();
    Optional<Toy> getToyByName(String name);

    //Disminuir existencias de un juguete
    void increaseAmountToy(String toyName, int amount);
    void decreaseAmountToy(String toyName, int amount);

    //Informar tipo del cual hay mas juguestes
    Category getMostAmountToyCategory();
    //Informar tipo del cual hay menos juguestes
    Category getLessAmountToyCategory();

    //Obtener los juguetes con valor mayor a un valor dado
    List<Toy> getToysWithPriceGreaterThan(double price);

    //Ordernar de matyor a menor por cantidad de juguetes por tipo

    Map<Category, Integer>  sortToysByAmountGroupedByCategory();



}
