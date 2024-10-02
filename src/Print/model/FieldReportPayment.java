/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Print.model;

/**
 *
 * @author Dang
 */
public class FieldReportPayment {
    private String name;
    private int qty;
    private double price;
    private double total;

    public FieldReportPayment() {
    }

    public FieldReportPayment(String name, int qty, double price, double total) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
