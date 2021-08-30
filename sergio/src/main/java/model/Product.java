package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
public class Product {
    @Id
    @Column("id")
    private Integer id;

    @Column("code")
    private Integer code;

    @Column("full_name")
    private String name;

    @Column("price")
    private Float price;

    @Column("quantity")
    private Integer quantity;

    private Product(Integer id, Integer code, String name, Float price, Integer quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product crear(Integer id, Integer code, String name, Float price, Integer quantity) {
        return new Product(id, code, name, price, quantity);
    }

    public static Product crear(Integer code, String name, Float price, Integer quantity) {
        return new Product(null, code, name, price, quantity);
    }

    public static Product crear(String line) {
        String[] args = line.split(" ");

        Integer code = Integer.parseInt(args[0]);
        String name = args[1];
        Float price = Float.parseFloat(args[2]);
        Integer amount = Integer.parseInt(args[3]);

        return Product.crear(code, name, price, amount);
    }

    public static Product parse(String line) {
        String[] args = line.split(" ");

        Integer id = Integer.parseInt(args[0]);
        Integer code = Integer.parseInt(args[1]);
        String name = args[2];
        Float price = Float.parseFloat(args[3]);
        Integer amount = Integer.parseInt(args[4]);

        return new Product(id, code, name, price, amount);
    }

    public static Product crear() {
        return new Product(null, null, null, null, null);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getcode() {
        return this.code;
    }

    public void setcode(Integer code) {
        this.code = code;
    }

    public String getname() {
        return this.name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getquantity() {
        return this.quantity;
    }

    public void setquantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + "code=" + code + ", name=" + name + ", price=" + price
                + ", quantity=" + quantity + '}';
    }

    public Object[] toArrObject() {
        Object[] res = { id + "", code + "", name + "", price + "", quantity + "" };
        return res;
    }

}
