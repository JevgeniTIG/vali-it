package ee.bcs.vali.it.controller;

import com.fasterxml.jackson.databind.node.BigIntegerNode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String service_name;
    private String service_description;
    private String service_duration;
    private BigDecimal service_price;
    private String payment_method;
    private String room_type;
    private String location;
    private BigInteger host_id;

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public String getService_duration() {
        return service_duration;
    }

    public void setService_duration(String service_duration) {
        this.service_duration = service_duration;
    }

    public BigDecimal getService_price() {
        return service_price;
    }

    public void setService_price(BigDecimal service_price) {
        this.service_price = service_price;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigInteger getHost_id() {
        return host_id;
    }

    public void setHost_id(BigInteger host_id) {
        this.host_id = host_id;
    }
}