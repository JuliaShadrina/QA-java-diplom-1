package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import praktikum.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockIngredient1;
    @Mock
    private Ingredient mockIngredient2;
    @Mock
    private Ingredient mockIngredient3;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void addIngredientTest() {
        assertTrue("Проверяем, что список ингредиентов пуст", burger.ingredients.isEmpty());
        burger.addIngredient(mockIngredient1);
        assertFalse("Проверяем, что список ингредиентов не пустой", burger.ingredients.isEmpty());
        assertEquals("Проверяем, что список ингредиентов содержит 1 элемент", 1, burger.ingredients.size());
        assertEquals("Добавленный ингредиент не соответствует ожидаемому", mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(mockIngredient1);
        assertFalse("Проверяем, что список ингредиентов не пустой", burger.ingredients.isEmpty());
        assertEquals("Проверяем, что список ингредиентов содержит 1 элемент", 1, burger.ingredients.size());
        burger.removeIngredient(0);
        assertTrue("Проверяем, что список ингредиентов пуст", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        assertEquals("Ингредиент 1 должен быть на позиции 0", 0, burger.ingredients.indexOf(mockIngredient1));
        assertEquals("Ингредиент 2 должен быть на позиции 1", 1, burger.ingredients.indexOf(mockIngredient2));
        burger.moveIngredient(0, 1);
        assertEquals("Ингредиент 1 должен быть на позиции 1", 1, burger.ingredients.indexOf(mockIngredient1));
        assertEquals("Ингредиент 2 должен быть на позиции 0", 0, burger.ingredients.indexOf(mockIngredient2));
    }

    @Test
    public void getPriceWithMocksTest() {
        when(mockBun.getPrice()).thenReturn(20.0f);
        when(mockIngredient1.getPrice()).thenReturn(50.0f);
        when(mockIngredient2.getPrice()).thenReturn(30.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        float expectedPrice = 20.0f * 2 + 50.0f + 30.0f;
        assertEquals(expectedPrice, burger.getPrice(), 0.01);
    }

    @Test
    public void getReceiptWithMocksTest() {

        when(mockBun.getName()).thenReturn("black bun");
        when(mockBun.getPrice()).thenReturn(100.0f);

        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("sour cream");
        when(mockIngredient1.getPrice()).thenReturn(200.0f);

        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("cutlet");
        when(mockIngredient2.getPrice()).thenReturn(100.0f);

        when(mockIngredient3.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient3.getName()).thenReturn("dinosaur");
        when(mockIngredient3.getPrice()).thenReturn(200.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        String expectedReceipt = "(==== black bun ====)\n" +
                "= sauce sour cream =\n" +
                "= filling cutlet =\n" +
                "= filling dinosaur =\n" +
                "(==== black bun ====)\n" +
                "\nPrice: 700,000000\n";

        String actualReceipt = burger.getReceipt().replace("\r\n", "\n");

        assertEquals("Чек сформирован некорректно", expectedReceipt, actualReceipt);
    }

}
