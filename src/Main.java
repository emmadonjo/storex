import java.util.*;
import models.Product;
import helpers.InputHelper;
import helpers.Option;

public class Main {
    private static final List<Product> products = new ArrayList<>();
    private static final List<Option> options = new ArrayList<>();

    public static void main(String[] args) {
        populateOptions();
        run();
    }

    private  static void run(){
        Optional<Option> option = displayOptions();
        while (option.isEmpty()){
            System.out.println("The selected option is invalid.");
            option = displayOptions();
        }

        runAction(option.get());
    }

    private static void runAction(Option option){
        switch(option.key){
            case "1":
                listProducts();
                break;
            case "2":
                addProduct();
                break;
            case "3":
                searchProducts();
                break;
            case "4":
                getProduct();
                break;
            case "5":
                deleteProduct();
                break;
            case "0":
                System.exit(1);
                break;
            default:
                run();
        }
    }

    private static Optional<Option> displayOptions(){
        System.out.println("Enter a number for the action you want to perform.");
        for(Option option: options) {
            System.out.println(option.key + " - " + option.description);
        }
        String optionKey = InputHelper.textInput("Enter an option");
        while (optionKey.isEmpty() || optionKey.length() > 2) {
            optionKey = InputHelper.textInput("Enter an option");
        }

        String finalOptionKey = optionKey;
        return options.stream()
            .filter(option -> option.key.equals(finalOptionKey))
            .findFirst();
    }

    private static void listProducts(){
        if (products.isEmpty()){
            System.out.println("No product has been added.\n");
            run();
        }
        showProducts(products);

        run();
    }

    private static void addProduct() {
        String name = InputHelper.textInput("Enter product name");
        while (name.isEmpty()){
            System.out.println("Product name must be between 4 and 255 characters.");
            name = InputHelper.textInput("Product name");
        }

        int quantity = InputHelper.intInput("Enter quantity");

        while(quantity < 1 || quantity > 1000) {
            System.out.println("Quantity must be between 1 and 1000");
            quantity = InputHelper.intInput("Enter quantity");
        }

        float price = InputHelper.floatInput("Enter price");

        while (price < 1 || price > 1000) {
            System.out.println("Price must be between 1 and 1000");
            price = InputHelper.floatInput("Enter price");
        }
        Product product = new Product(uniqueId(), name, quantity, price);
        products.add(product);

        System.out.println(product.name + " added successfully.\n");
        run();
    }

    private static void searchProducts(){
        String searchTerm = InputHelper.textInput("Enter search term");

        while(searchTerm.isEmpty()) {
            System.out.println("Enter a valid search term.");
            searchTerm = InputHelper.textInput("Enter search term");
        }

        String finalSearchTerm = searchTerm;
        List<Product> searchResult = products.stream()
            .filter(product -> product.name.contains(finalSearchTerm))
            .toList();

        if (searchResult.isEmpty()) {
            System.out.println("No found product was found.\n");
            run();
        }

        showProducts(searchResult);
        run();
    }

    private static void getProduct(){
        String productId = InputHelper.textInput("Product ID");

        Optional<Product> searchedProduct = products.stream()
            .filter(p -> p.id.equals(productId))
            .findFirst();

        if (searchedProduct.isEmpty()) {
            System.out.println("No product with the given ID was found.");

            run();
        }

        Product product = searchedProduct.get();

        System.out.println();
        System.out.println("ID: " + product.id);
        System.out.println("NAME: " + product.name);
        System.out.println("QTY: " + product.quantity);
        System.out.println("PRICE: " + String.format("%-10.2f", product.price));
        System.out.println();

        run();
    }

    private static void deleteProduct(){
        String productId = InputHelper.textInput("Product ID");

        Optional<Product> searchedProduct = products.stream()
            .filter(p -> p.id.equals(productId))
            .findFirst();

        if (searchedProduct.isEmpty()) {
            System.out.println("No product with the given ID was found.");

            run();
        }

       Product product = searchedProduct.get();
        products.remove(product);

        System.out.println();
        System.out.printf("Product with the ID  %-36s has been successfully deleted.%n", product.id);
        System.out.println();

        run();
    }

    private static void  populateOptions(){
        options.add(new Option("1", "List products"));
        options.add(new Option("2", "Add products"));
        options.add(new Option("3", "Search products"));
        options.add(new Option("4", "Get product"));
        options.add(new Option("5", "Delete product"));
        options.add(new Option("0", "Exit"));
    }

    private  static String uniqueId(){
        return UUID.randomUUID().toString();
    }

    private  static void showProducts(List<Product> productList){
        if (productList.isEmpty()) {
            System.out.println("No product was found.");
            return;
        }
        System.out.println(products.size() + " product(s) found:\n");

        System.out.println("-".repeat(100));
        String header = String.format("%-4s %-40s %-40s %-5s %-10s", "SN", "ID", "NAME", "QTY", "PRICE");
        System.out.println(header);

        System.out.println("-".repeat(100));
        for(int index = 0; index < productList.toArray().length; index++) {
            Product product = productList.get(index);
            String row = String.format("%-4s %-40s %-40s %-5d %-10.2f", index+1, product.id, product.name, product.quantity, product.price);

            System.out.println(row);
            System.out.println("-".repeat(100));
        }
    }
}
