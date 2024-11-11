package com.example.projectbooksalekhanhminh;

public class Product {
    private int id;
    private String name;
    private String image;
    private String author;
    private int publishedYear;
    private String description;
    private String category;
    private double price;
    private int quantity;
    private boolean status;

    public Product(int id, String name, String image, String author, int publishedYear, String description, String category, double price, int quantity, boolean status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.author = author;
        this.publishedYear = publishedYear;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity =quantity;
        this.status = status;
    }

    public Product(int id, String name, String image, String author, int publishedYear, String description, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.author = author;
        this.publishedYear = publishedYear;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity =quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String discription) {
        this.description = discription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int stokeQuantity) {
        this.quantity = stokeQuantity;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", discription='" + description + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", stokeQuantity=" + quantity +
                '}';
    }
}
