package com.example.foodies;

import com.example.foodies.Models.MainModel;

import java.util.ArrayList;
import java.util.Collections;

public class FoodItemsList {
    public static ArrayList<MainModel> getFoodItems() {
        ArrayList<MainModel> list = new ArrayList<>();
        list.add(new MainModel(R.drawable.tower, "Tower Burger", "350", "Tower Burger Cooked with Fresh Meat and cheese"));
        list.add(new MainModel(R.drawable.pizza, "Pizza", "1000", "Delicious Pizza with a variety of toppings"));
        list.add(new MainModel(R.drawable.fries, "Potato Fries", "200", "Crispy and seasoned Potato Fries"));
        list.add(new MainModel(R.drawable.frieds, "Chicken Fried's", "900", "Tasty Fried Chicken with a crispy coating"));
        list.add(new MainModel(R.drawable.drinks, "Soft Drink", "150", "Refreshing Soft Drinks"));

        list.add(new MainModel(R.drawable.tower, "Cheeseburger", "400", "Classic Cheeseburger with melted cheese"));
        list.add(new MainModel(R.drawable.tower, "Veggie Burger", "300", "Delicious Vegetarian Burger with fresh veggies"));
        list.add(new MainModel(R.drawable.tower, "BBQ Burger", "450", "Tangy BBQ Sauce-infused Burger with grilled onions"));
        list.add(new MainModel(R.drawable.tower, "Mushroom Burger", "380", "Savory Burger with sautéed mushrooms"));
        list.add(new MainModel(R.drawable.tower, "Double Bacon Burger", "550", "Indulgent Burger with double the bacon"));

        list.add(new MainModel(R.drawable.pizza, "Margherita Pizza", "600", "Classic pizza with tomato sauce, mozzarella cheese, and basil"));
        list.add(new MainModel(R.drawable.pizza, "Pepperoni Pizza", "800", "Pizza topped with pepperoni slices and melted cheese"));
        list.add(new MainModel(R.drawable.pizza, "Vegetarian Pizza", "700", "Pizza loaded with various vegetables and cheese"));
        list.add(new MainModel(R.drawable.pizza, "BBQ Chicken Pizza", "900", "Pizza with BBQ sauce, grilled chicken, and onions"));
        list.add(new MainModel(R.drawable.pizza, "Hawaiian Pizza", "750", "Pizza topped with ham, pineapple, and cheese"));

        list.add(new MainModel(R.drawable.fries, "Curly Fries", "250", "Spiral-shaped crispy fries with seasoning"));
        list.add(new MainModel(R.drawable.fries, "Sweet Potato Fries", "300", "Crispy fries made from sweet potatoes"));
        list.add(new MainModel(R.drawable.fries, "Garlic Parmesan Fries", "350", "Fries coated in garlic and parmesan cheese"));
        list.add(new MainModel(R.drawable.fries, "Chili Cheese Fries", "400", "Fries topped with chili and melted cheese"));
        list.add(new MainModel(R.drawable.fries, "Truffle Fries", "450", "Fries drizzled with truffle oil and sprinkled with parmesan cheese"));

        list.add(new MainModel(R.drawable.frieds, "Spicy Chicken Fingers", "950", "Crispy and spicy chicken fingers"));
        list.add(new MainModel(R.drawable.frieds, "Popcorn Chicken", "750", "Bite-sized fried chicken pieces"));
        list.add(new MainModel(R.drawable.frieds, "Cajun Fried Chicken", "850", "Fried chicken seasoned with Cajun spices"));
        list.add(new MainModel(R.drawable.frieds, "Korean Fried Chicken", "900", "Korean-style crispy fried chicken"));
        list.add(new MainModel(R.drawable.frieds, "Garlic Parmesan Wings", "800", "Chicken wings coated in garlic and parmesan sauce"));

        list.add(new MainModel(R.drawable.icetea, "Iced Tea", "120", "Refreshing iced tea"));
        list.add(new MainModel(R.drawable.lemonade,  "Lemonade", "130", "Freshly squeezed lemonade"));
        list.add(new MainModel(R.drawable.mango,  "Mango Smoothie", "180", "Creamy mango smoothie"));
        list.add(new MainModel(R.drawable.smoothie,  "Strawberry Milkshake", "200", "Delicious strawberry milkshake"));
        list.add(new MainModel(R.drawable.cola,  "Coca-Cola", "100", "Classic Coca-Cola beverage"));
        Collections.shuffle(list);
        return list;
    }
}
