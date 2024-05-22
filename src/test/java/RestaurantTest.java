import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    int initialMenuSize;
    Restaurant time;

    @BeforeEach
    public void setup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        initialMenuSize = restaurant.getMenu().size();

        time = Mockito.spy(restaurant);
    }
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime noon = LocalTime.NOON;
        Mockito.when(time.getCurrentTime()).thenReturn(noon);
        boolean checkRestaurantStatus = time.isRestaurantOpen();
        assertEquals(true,checkRestaurantStatus);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime midNight = LocalTime.MIDNIGHT;
        Mockito.when(time.getCurrentTime()).thenReturn(midNight);
        boolean checkRestaurantStatus = time.isRestaurantOpen();
        assertEquals(false,checkRestaurantStatus);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // TDD

    // When adding items from menu,
    // It should return the order value,
    // it should take itemName as parameter,
    // it should search the item in menu and provide item cost,
    // it should show order value 0 when no item is selected
    // it should increase the order value as more items are added from menu

    @Test
    public void when_adding_an_items_from_menu_it_should_return_order_value(){
        int costWhenNothingIsAdded = restaurant.addItemToGetOrderValue("");
        assertEquals(0,costWhenNothingIsAdded);

        int costWhenOneItemIsAdded = restaurant.addItemToGetOrderValue("Vegetable lasagne");
        assertEquals(269,costWhenOneItemIsAdded);

        int costWhenAdditionalItemIsAdded = restaurant.addItemToGetOrderValue("Sweet corn soup");
        assertEquals(388,costWhenAdditionalItemIsAdded);
    }
}