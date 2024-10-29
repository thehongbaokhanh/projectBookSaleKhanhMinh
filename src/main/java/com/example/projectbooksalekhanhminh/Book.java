package com.example.projectbooksalekhanhminh;

public class Book {
    private String id;
    private String name;
    private String image;
    private String author;
    private int publishedYear;
    private String discription;
    private int category;
    private boolean price;
    private int stokeQuantity;

    public Book(String id, String name, String image, String author, int publishedYear, String discription, int category, boolean price, int stokeQuantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.author = author;
        this.publishedYear = publishedYear;
        this.discription = discription;
        this.category = category;
        this.price = price;
        this.stokeQuantity = stokeQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isPrice() {
        return price;
    }

    public void setPrice(boolean price) {
        this.price = price;
    }

    public int getStokeQuantity() {
        return stokeQuantity;
    }

    public void setStokeQuantity(int stokeQuantity) {
        this.stokeQuantity = stokeQuantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", discription='" + discription + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", stokeQuantity=" + stokeQuantity +
                '}';
    }
}
