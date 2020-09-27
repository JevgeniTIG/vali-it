package ee.bcs.vali.it.controller;

import com.fasterxml.jackson.databind.node.BigIntegerNode;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ServiceData {
    private BigInteger serviceId;
    private String serviceName;
    private String serviceDescription;
    private String serviceDuration;
    private BigDecimal servicePrice;
    private String servicePaymentMethod;
    private String serviceRoomType;
    private String serviceAddress;
    private BigInteger hostId;
    private String serviceFullName;
    private double serviceRating;


    public BigInteger getServiceId() {
        return serviceId;
    }

    public void setServiceId(BigInteger serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(String serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServicePaymentMethod() {
        return servicePaymentMethod;
    }

    public void setServicePaymentMethod(String servicePaymentMethod) {
        this.servicePaymentMethod = servicePaymentMethod;
    }

    public String getServiceRoomType() {
        return serviceRoomType;
    }

    public void setServiceRoomType(String serviceRoomType) {
        this.serviceRoomType = serviceRoomType;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public BigInteger getHostId() {
        return hostId;
    }

    public void setHostId(BigInteger hostId) {
        this.hostId = hostId;
    }

    public String getServiceFullName() {
        return serviceFullName;
    }

    public void setServiceFullName(String serviceFullName) {
        this.serviceFullName = serviceFullName;
    }

    public double getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(double serviceRating) {
        this.serviceRating = serviceRating;
    }
}
