import mapping.mappers.ToyMapper;
import model.Toy;
import model.Category;
import org.junit.jupiter.api.*;
import repositories.imp.ToyRepositoryImp;
import service.ToyService;
import service.imp.ToyServiceImp;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

public class ToyServiceTest {

    private static ToyService toyService = new ToyServiceImp(new ToyRepositoryImp());

    private static ToyMapper toyMapper = new ToyMapper();
    @BeforeAll
    static void addToyForTesting(){
        Toy toy = new Toy("Buzz",10, Category.UNISEX,100.0);
        toyService.addToy(toyMapper.fromEntityToDTO(toy));
    }

    @Test
    void addToy(){
        int EXPECT_LIST_SIZE = 1;
        Assertions.assertEquals(EXPECT_LIST_SIZE, toyService.getAllToys().size());
    }

    @Test
    void shouldAddToyAlreadyExists(){
        Toy toy = new Toy("Buzz",10, Category.UNISEX,100.0);
        Assertions.assertThrows(CompletionException.class, ()->{
            toyService.addToy(toyMapper.fromEntityToDTO(toy));
        });
    }
    @Test
    void shouldDeleteToyInvalidName(){
        String INVALID_NAME = "Invalid name";
        Assertions.assertThrows(RuntimeException.class, ()->{
            toyService.deleteToyByName(INVALID_NAME);
        });
    }

    @Test
    void deleteToy(){
        toyService.deleteToyByName("Buzz");
        int EXPECT_LIST_SIZE = 0;
        Assertions.assertEquals(EXPECT_LIST_SIZE, toyService.getAllToys().size());
    }


    @Test
    void getToysAmountByCategory(){
        Map<Category, Integer> expected = new HashMap<>();
        expected.put(Category.UNISEX, 10);

        Assertions.assertEquals(expected, toyService.getToysAmountByCategory());
    }
    @Test
    void getTotalToysAmount(){
        int EXPECT_TOTAL_AMOUNT = 10;
        Assertions.assertEquals(EXPECT_TOTAL_AMOUNT, toyService.getTotalToysAmount());
    }

    @Test
    void getTotalToysPrice(){
        double EXPECT_TOTAL_PRICE = 1000.0;
        Assertions.assertEquals(EXPECT_TOTAL_PRICE,toyService.getTotalToysPrice());
    }

    @Test
    void increaseAmountToy(){
        Toy toy = new Toy("Woody",10, Category.UNISEX,100.0);
        toyService.addToy(toyMapper.fromEntityToDTO(toy));

        int EXPECTED_TOY_AMOUNT = 15;
        toyService.increaseAmountToy("Woody",5);

        Assertions.assertEquals(EXPECTED_TOY_AMOUNT,toy.getToyAmount());
    }
    @Test
    void shouldIncreaseAmountInvalidName(){
        String INVALID_NAME = "Invalid name";
        Assertions.assertThrows(RuntimeException.class, ()->{
            toyService.increaseAmountToy(INVALID_NAME,10);
        });
    }
}