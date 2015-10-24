package com.hfad.starbuzz;

/**
 * Created by ankursrivastava on 7/21/15.
 */
public class Drink {

    private String name;
    private String description;
    private int imageResourceId;

    private Drink(String name, String desc, int id){
        this.name = name;
        this.description = desc;
        this.imageResourceId = id;
    }

    public static final Drink[] drinks = {new Drink("Chai", "Indian Tea", R.drawable.latte),
                                          new Drink("Filter Coffee", "Indian Coffee", R.drawable.filter),
                                          new Drink("Cappuccino", "Cappuccino", R.drawable.cappuccino)};

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String toString(){
        return this.name+" "+this.description;
    }
}
