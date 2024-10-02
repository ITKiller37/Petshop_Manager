/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Print.model;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Dang
 */
public class ParameterReportPayment {
    private String staff;
    private String customer;
    private String phone;
    private double total;
    private InputStream qrcode;
    private List<FieldReportPayment> fields;

    public ParameterReportPayment() {
    }

    public ParameterReportPayment(String staff, String customer, String phone, double total, InputStream qrcode, List<FieldReportPayment> fields) {
        this.staff = staff;
        this.customer = customer;
        this.phone = phone;
        this.total = total;
        this.qrcode = qrcode;
        this.fields = fields;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public InputStream getQrcode() {
        return qrcode;
    }

    public void setQrcode(InputStream qrcode) {
        this.qrcode = qrcode;
    }

    public List<FieldReportPayment> getFields() {
        return fields;
    }

    public void setFields(List<FieldReportPayment> fields) {
        this.fields = fields;
    }
    
    
}
