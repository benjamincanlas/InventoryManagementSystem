package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Inventory class */
public class Inventory {
    //declare fields
    /**
     * This list contains the parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * This list contains the products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * The generated index for the part id starts at 1.
     */
    private static int autoId = 1;
    /**
     * The generated index for our product id starts at 1.
     */
    private static int productAutoId = 1;
    /**
     * @return This part id will be returned and generated when called.
     */
    public static int getAutoId() {
        return autoId++;
    }
    /**
     * @return This product id will be returned and generated when called.
     */
    public static int getProductAutoId() {
        return productAutoId++;
    }
    /**
     * @param newPart to be added
     */
     public static void addPart(Part newPart) {
         allParts.add(newPart);
     }
    /**
     * @param newProduct to be added
     */
     public static void addProduct(Product newProduct) {
         allProducts.add(newProduct);
     }
    /**
     * @return The part that will be searched in the search fields.
     */
     public static Part lookupPart(int partId) {
         for(Part part : allParts) {
             if(part.getId() == partId)
                 return part;
         }
         return null;
     }
    /**
     * @return The product that will be searched in the search fields.
     */
     public static Product lookupProduct(int productId) {
         for(Product product : allProducts) {
             if(product.getId() == productId)
                 return product;
         }
         return null;

     }
    /**
     * @return The part results from search results based on the stored values in <Part>. Here we indicated partial values and lower case values will be accepted.
     */
     public static ObservableList<Part> lookupPart(String partName) {
         ObservableList<Part> partResults = FXCollections.observableArrayList();
         for (Part part : allParts) {
             if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                 partResults.add(part);
             }
         }
         return partResults;
     }
    /**
     * @return The product results from search results based on the stored values in <Product>. Here we indicated partial values and lower case values will be accepted.
     */
     public static ObservableList<Product> lookupProduct(String productName) {
         ObservableList<Product> productResults = FXCollections.observableArrayList();
         for (Product product : allProducts) {
             if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                 productResults.add(product);
             }
         }
         return productResults;
     }

    /**
     * @param selectedPart set to be modified
     */
     public static void updatePart(int index, Part selectedPart) {
         allParts.set(index, selectedPart);
     }

    /**
     * @param newProduct set to be modified
     */
    public static void updateProduct(int index, Product newProduct) {
         allProducts.set(index, newProduct);
     }
    /**
     * @return The part that was selected and deleted.
     */
     public static boolean deletePart(Part selectedPart) {
         return allParts.remove(selectedPart);
     }
    /**
     * @return The product that was selected and deleted.
     */
     public static boolean deleteProduct(Product selectedProduct) {
         return allProducts.remove(selectedProduct);
     }

    /**
     * @return parts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * @return products list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}