package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** Product class */
public class Product {
    /**
     * This list contains the associated parts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min, max;

/** The constructors are created for the product ID, name, price, stock, min, and max attributes. */
    //constructors or attributes
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    //getters and setters/ methods
    /**
     * @return id
     */
    public int getId() {
        return id;
    }
/** setter for ID*/
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    /** setter for name*/
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }
    /** setter for price */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }
    /** setter for stock */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * @return min value
     */
    public int getMin() {
        return min;
    }
    /** setter for min value */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return max value
     */
    public int getMax() {
        return max;
    }
    /** setter for max value */
    public void setMax(int max) {
        this.max = max;
    }
    /** adds associatedPart */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /** deletes associatedPart */
    public void deleteAssociatedPart(Part selectAssociatedPart) {
        associatedParts.remove(selectAssociatedPart);
    }
    /**
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
