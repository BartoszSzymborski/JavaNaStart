package pl.javastart.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Producer implements Serializable {
    private static final long serialVersionUID =  5028602174496510333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private double totalIncome;
    @OneToMany(mappedBy = "producer")
    private List<Product>products;

    public Producer() {
    }

    public Producer(String name, String country, double totalIncome, List<Product> products) {
        this.name = name;
        this.country = country;
        this.totalIncome = totalIncome;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
