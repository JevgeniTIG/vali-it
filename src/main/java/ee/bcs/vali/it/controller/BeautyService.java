package ee.bcs.vali.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class BeautyService {
    @Autowired
    private BeautyRepository beautyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    // Registers new user
    public void registerUser(String userName, String userLastName, String userLogin, String userPassword) {
        beautyRepository.registerUser(userName, userLastName, userLogin, passwordEncoder.encode(userPassword));
    }


    // Registers new host
    public void registerHost(String hostName, String hostLastName, String hostEmail, String hostPhone,
                             String hostLogin, String hostPassword) {
        beautyRepository.registerHost(hostName, hostLastName, hostEmail, hostPhone,
                hostLogin, passwordEncoder.encode(hostPassword));
    }

    // Adds host details into table 'abouthosts'
    public void fulfillHostDetails(String hostService, String hostAddress, String hostAddressType, BigInteger hostId){
        beautyRepository.fulfillHostDetails(hostService, hostAddress, hostAddressType, hostId);
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

    //Shows services of logged host
    public List showLoggedHostServices(String currentHostLogin) {
        return beautyRepository.showLoggedHostServices(currentHostLogin);
    }

    //Shows suitable services
    @PostMapping("show_suitable_services")
    public List showSuitableServices(String serviceLocation, String serviceName, BigDecimal servicePrice){
        return beautyRepository.showSuitableServices(serviceLocation, serviceName, servicePrice);
    }

    //Deletes specific service of logged host in table 'services'
    public void deleteLoggedHostService(BigInteger rowNumberToDelete) {
        beautyRepository.deleteLoggedHostService(rowNumberToDelete);
    }


        // Returns the username of the host that is currently logged in
    public BigInteger loggedHostId(String hostLogin) {
        return beautyRepository.loggedHostId(hostLogin);
    }


}
