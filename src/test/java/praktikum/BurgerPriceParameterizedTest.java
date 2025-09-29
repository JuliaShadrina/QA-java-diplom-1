package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

 // Параметризованный тест для проверки корректности расчёта цены бургера

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    // Входные данные для теста
    private final float bunPrice;
    private final float saucePrice;
    private final float fillingPrice;
    private final float expectedPrice;

    private Burger burger;

    // Моки зависимостей для зависимостей Bun и Ingredient
    private Bun bun;
    private Ingredient sauce;
    private Ingredient filling;

    // Конструктор для передачи параметров в тест
    public BurgerPriceParameterizedTest(float bunPrice, float saucePrice, float fillingPrice, float expectedPrice) {
        this.bunPrice = bunPrice;
        this.saucePrice = saucePrice;
        this.fillingPrice = fillingPrice;
        this.expectedPrice = expectedPrice;
    }

    // Набор тестовых данных
    @Parameterized.Parameters(name = "Bun={0}, Sauce={1}, Filling={2} => Price={3}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {20.0f, 50.0f, 30.0f, 120.0f},   // булка 20, соус 50, начинка 30 → итог 120
                {100.0f, 0.0f, 0.0f, 200.0f},   // булка 100, без ингредиентов → итог 200
                {40.0f, 10.0f, 5.0f, 95.0f}     // булка 40, соус 10, начинка 5 → итог 95
        });
    }

    @Before
    public void setUp() {
        burger = new Burger();

        // Создаём моки для зависимостей
        bun = Mockito.mock(Bun.class);
        sauce = Mockito.mock(Ingredient.class);
        filling = Mockito.mock(Ingredient.class);

        // Настраиваем моки для булки
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(bun.getName()).thenReturn("test bun");

        // Настраиваем моки для соуса
        Mockito.when(sauce.getPrice()).thenReturn(saucePrice);
        Mockito.when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(sauce.getName()).thenReturn("test sauce");

        // Настраиваем моки для начинки
        Mockito.when(filling.getPrice()).thenReturn(fillingPrice);
        Mockito.when(filling.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(filling.getName()).thenReturn("test filling");
    }

    @Test
    public void getPriceParameterizedTest() {
        // Устанавливаем булку
        burger.setBuns(bun);

        // Добавляем ингредиенты
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        // Проверяем итоговую цену
        assertEquals(
                "Цена рассчитана некорректно: ожидали " + expectedPrice + ", а получили " + burger.getPrice(),
                expectedPrice,
                burger.getPrice(),
                0.01
        );
    }
}