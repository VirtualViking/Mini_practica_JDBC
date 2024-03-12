package service.imp;

import mapping.dtos.PublicInfoToyDTO;
import mapping.exceptions.ToyException;
import mapping.mappers.ToyMapper;
import model.Category;
import model.Toy;
import persistence.PersistencePath;
import repositories.ToyRepository;
import service.ToyService;
import utils.FileUtil;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static mapping.exceptions.ToyExceptionMessage.*;

public class ToyServiceImp implements ToyService {

    private final List<Toy> toys = new ArrayList<>();
    private final ToyMapper toyMapper;
    private final ToyRepository toyRepository;

    public ToyServiceImp(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
        toyMapper = new ToyMapper();
    }

    @Override
    public void addToy(PublicInfoToyDTO toy) {
        Optional<Toy> toyOptional = getToyByName(toy.toyName());


        CompletableFuture.runAsync(() -> {
            System.out.println("Verifying if the toy already exists...");
            if (toyOptional.isPresent()) {
                throw new ToyException(TOY_ALREADY_EXISTS + toy.toyName());//Trhow CompletionException instead of ToyException
            }
        }).join(); //Unir el hilo principal con el hilo que se ejecuta en segundo plano

        sleep(2000);
        System.out.println("Toy does not exist, adding it to the list...");
        toys.add(toyMapper.fromDTOtoEntity(toy));
        writeToys();
    }

    @Override
    public void addToy(Toy toy) {
        toyRepository.save(toy);
    }

    @Override
    public Toy getToyById(Long id) {
        return toyRepository.findById(id);
    }

    @Override
    public List<Toy> getAllToysDb() {
        return toyRepository.findAll();
    }

    @Override
    public void updateToy(Toy toy) {
        toyRepository.update(toy);
    }

    @Override
    public void deleteToyByName(String name) {
        Optional<Toy> toyOptional = getToyByName(name);

        CompletableFuture.runAsync(() -> {
            System.out.println("Verifying if the toy exists...");
            if (toyOptional.isEmpty()) {
                throw new ToyException(TOY_DOES_NOT_EXIST);
            }
        }).join();

        sleep(2000);
        System.out.println("Toy exists, removing it from the list...");

        if (toyOptional.isEmpty()) {
            throw new ToyException(TOY_DOES_NOT_EXIST);
        }
        toys.remove(toyOptional.get());
        writeToys();
    }

    @Override
    public Map<Category, Integer> getToysAmountByCategory() {
        return toys.stream()
                .collect(Collectors.groupingBy(Toy::getToyCategory, Collectors.summingInt(Toy::getToyAmount)));
    }

    @Override
    public List<PublicInfoToyDTO> getAllToys() {
        return toys.stream()
                .map(toy -> toyMapper.fromEntityToDTO(toy))
                .toList();
    }

    @Override
    public int getTotalToysAmount() {
        return toys.stream()
                .map(toy -> toy.getToyAmount())
                .reduce(0, (acc, element) -> acc + element);
    }

    @Override
    public double getTotalToysPrice() {
        System.out.println("Calculating total toys price...");
        return CompletableFuture
                .supplyAsync(() -> simulateSlowGetTotalToysPrice())
                .exceptionally(e->0.0)
                .join();


    }


    @Override
    public Optional<Toy> getToyByName(String name) {
        return toys.stream().filter(t -> t.toyName.equalsIgnoreCase(name)).findAny();

    }

    @Override
    public void increaseAmountToy(String toyName, int amount) {

        CompletableFuture.supplyAsync(() -> {
            System.out.println("Verifying if the toy exists...");
            sleep(2000);
            Optional<Toy> toyOptional = getToyByName(toyName);
            if (toyOptional.isEmpty()) {
                throw new ToyException(TOY_DOES_NOT_EXIST);
            }
            return toyOptional.get();
        }).thenApply(toy->{
            toy.setToyAmount(toy.getToyAmount() + amount);
            return toy;
        }).thenAccept(toy->{
            System.out.println("Toy " + toy.toyName +  " amount increased successfully!");
            writeToys();
        }).join();


    }

    @Override
    public void decreaseAmountToy(String toyName, int amount) {
        Optional<Toy> toyOptional = getToyByName(toyName);
        if (toyOptional.isPresent()) {
            Toy toy = toyOptional.get();
            if (toy.getToyAmount() - amount < 0) {
                throw new ToyException(AMOUNT_MUST_BE_GREATER_THAN_TOY_AMOUNT);
            }
            toy.setToyAmount(toy.getToyAmount() - amount);
        } else {
            throw new ToyException(TOY_DOES_NOT_EXIST);
        }
        writeToys();

    }

    @Override
    public Category getMostAmountToyCategory() {
        Map<Category, Integer> amountsByCategory = getToysAmountByCategory();
        Category maxCategory = amountsByCategory.keySet().iterator().next();
        Integer maxAmount = amountsByCategory.get(maxCategory);
        for (Map.Entry<Category, Integer> map : amountsByCategory.entrySet()) {
            if (map.getValue() > maxAmount) {
                maxAmount = map.getValue();
                maxCategory = map.getKey();
            }
        }
        return maxCategory;
    }

    @Override
    public Category getLessAmountToyCategory() {
        Map<Category, Integer> amountsByCategory = getToysAmountByCategory();
        Category maxCategory = amountsByCategory.keySet().iterator().next();
        Integer maxAmount = amountsByCategory.get(maxCategory);

        for (Map.Entry<Category, Integer> map : amountsByCategory.entrySet()) {
            if (map.getValue() < maxAmount) {
                maxAmount = map.getValue();
                maxCategory = map.getKey();
            }
        }
        return maxCategory;
    }


    @Override
    public List<Toy> getToysWithPriceGreaterThan(double price) {
        return toys.stream().filter(toy -> toy.getToyPrice() > price).toList();
    }

    @Override
    public Map<Category, Integer> sortToysByAmountGroupedByCategory() {
        Map<Category, Integer> amountsByCategory = getToysAmountByCategory();
        return amountsByCategory.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private double simulateSlowGetTotalToysPrice() {
        sleep(4000);
        return toys.stream()
                .map(toy -> toy.getToyPrice())
                .reduce(0.0, (acc, element) -> acc + element);

    }

    public void writeToys() {
        FileUtil.writeToys(PersistencePath.TOY_FILE_PATH, toys);
    }
}