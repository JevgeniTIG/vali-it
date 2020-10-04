package ee.bcs.vali.it.controller;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.util.List;
import java.util.OptionalDouble;

import static javax.swing.UIManager.getInt;

@Service
public class BeautyService {
    @Autowired
    private BeautyRepository beautyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Registers new host
    public void registerMember(String hostName, String hostLastName, String hostEmail, String hostPhone,
                             String hostLogin, String hostPassword) {
        beautyRepository.registerMember(hostName, hostLastName, hostEmail, hostPhone,
                hostLogin, passwordEncoder.encode(hostPassword));
    }


    //Adds services into table 'services'
    public void addService(String currentHostLogin, String serviceName, String serviceDescription, String serviceDuration, BigDecimal servicePrice, String servicePaymentMethod,
                           String serviceRoomType, String serviceAddress) {
        if (!(serviceName.equals("Choose")) && servicePrice != null && !StringUtils.isEmpty(serviceAddress)){
            beautyRepository.addService(currentHostLogin, serviceName, serviceDescription, serviceDuration, servicePrice, servicePaymentMethod,
                    serviceRoomType, serviceAddress);
        }
    }




    // Welcomes the host that is currently logged in
    public List welcomeHost(String userName){
        return beautyRepository.welcomeHost(userName);
    }



    //Login as host
    public String hostLogin(String hostPassword) {
        return passwordEncoder.encode(hostPassword);
    }

    //Login as user
    public String userLogin(String userPassword) {
        return passwordEncoder.encode(userPassword);
    }

    //Shows services of logged host
    public List showLoggedHostServices(String currentHostLogin) {
        return beautyRepository.showLoggedHostServices(currentHostLogin);
    }

    //Shows services history of logged member
    public List showLoggedMemberServicesHistory(String currentMemberLogin) {
        return beautyRepository.showLoggedMemberServicesHistory(currentMemberLogin);
    }

    //Shows suitable services
    public List<ServiceData> showSuitableServices(String serviceLocation, String serviceName, BigDecimal servicePrice){
        return beautyRepository.showSuitableServices(serviceLocation, serviceName, servicePrice);
    }

    //Shows suitable services when member is logged
    public List<ServiceData> showSuitableServicesLogged(String serviceLocation, String serviceName, BigDecimal servicePrice, String loggedMemberLogin){
        return beautyRepository.showSuitableServicesLogged(serviceLocation, serviceName, servicePrice, loggedMemberLogin);
    }

    //Deletes specific service of logged host in table 'services'
    public void deleteLoggedHostService(BigInteger rowNumberToDelete) {
        beautyRepository.deleteLoggedHostService(rowNumberToDelete);
    }

    //Adds a service to table 'experienced_services'
    public void addExperiencedService(BigInteger serviceId, String currentMemberLogin) {
        beautyRepository.addExperiencedService(serviceId, currentMemberLogin);
    }

    // Returns the username of the host that is currently logged in
    public BigInteger loggedHostId(String hostLogin) {
        return beautyRepository.loggedHostId(hostLogin);
    }


    public String currentUserName(String currentUser) {
        return beautyRepository.currentUserName(currentUser);
    }

    //Registers new host and updates hostrole of current member in table 'hosts'
    public void registerHost(String currentMemberLogin) {
        beautyRepository.registerHost(currentMemberLogin);
    }

    //Updates rating
    public void updateRating(BigInteger serviceId, double serviceRating) {

        double averageRating;

            averageRating = (beautyRepository.getRating(serviceId) + serviceRating)/2;
            int averageRatingInt = (int) averageRating;
            double fractional = averageRating - averageRatingInt;
            if (fractional<0.25){
                averageRating = averageRatingInt;
            }else if (fractional>=0.25){
                averageRating = averageRatingInt+0.5;
            }

        beautyRepository.updateRating(serviceId, averageRating);
    }

    //Gets current rating
    public double getRating(BigInteger serviceId) {
            return beautyRepository.getRating(serviceId);
    }

    //Checks if a selected service already exists in table 'experienced_services'
    public List<ServiceData> checkIfExperiencedServiceExists(BigInteger serviceId) {
        return beautyRepository.checkIfExperiencedServiceExists(serviceId);
    }


}
