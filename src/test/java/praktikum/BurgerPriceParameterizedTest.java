package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    // Входные данные для теста
    private final float bunPrice;
    private final float ingredientPrice1;
    private final float ingredientPrice2;
    private final float expectedPrice;

    private Burger burger;

    // Конструктор для передачи параметров в тест
    public BurgerPriceParameterizedTest(float bunPrice, float ingredientPrice1, float ingredientPrice2, float expectedPrice) {
        this.bunPrice = bunPrice;
        this.ingredientPrice1 = ingredientPrice1;
        this.ingredientPrice2 = ingredientPrice2;
        this.expectedPrice = expectedPrice;
    }

    // Набор тестовых данных
    @Parameterized.Parameters(name = "Bun={0}, Ing1={1}, Ing2={2} => Price={3}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {20.0f, 50.0f, 30.0f, 120.0f},   // булка 20, ингредиенты 50 и 30 → итог 120
                {100.0f, 0.0f, 0.0f, 200.0f},   // булка 100, ингредиенты по 0 → итог 200
                {40.0f, 10.0f, 5.0f, 95.0f}     // булка 40, ингредиенты 10 и 5 → итог 95
        });
    }

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void getPriceParameterizedTest() {
        // Создаём реальный объект Bun
        Bun bun = new Bun("test bun", bunPrice);

        // Создаём реальные объекты Ingredient
        Ingredient ingredient1 = new Ingredient(IngredientType.SAUCE, "sauce1", ingredientPrice1);
        Ingredient ingredient2 = new Ingredient(IngredientType.FILLING, "filling1", ingredientPrice2);

        // Устанавливаем булку
        burger.setBuns(bun);

        // Добавляем ингредиенты
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        // Проверяем итоговую цену
        assertEquals(
                "Цена рассчитана некорректно: ожидали " + expectedPrice + ", а получили " + burger.getPrice(),
                expectedPrice,
                burger.getPrice(),
                0.01
        );
    }
}