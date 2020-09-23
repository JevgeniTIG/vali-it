package ee.bcs.vali.it.controller;

import com.fasterxml.jackson.databind.node.BigIntegerNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BeautyRepository {


    @Autowired
    private NamedParameterJdbcTemplate dataBase;

    // Registers new user, adding data to 'users' table
    public void registerUser(String userName, String userLastName, String userLogin, String userPassword) {
        String sql = "INSERT INTO users(name, lastname, login, password) " +
                "VALUES (:name, :lastname, :login, :password)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", userName);
        paramMap.put("lastname", userLastName);
        paramMap.put("userlogin", userLogin);
        paramMap.put("password", userPassword);
        dataBase.update(sql, paramMap);
    }


    // Registers new host, adding data to 'hosts' table
    public void registerHost(String hostName, String hostLastName, String hostEmail, String hostPhone,
                             String hostLogin, String hostPassword) {
        String sql = "INSERT INTO hosts(firstname, lastname, hostemail, hostphone, hostlogin, password) " +
                "VALUES (:firstname, :lastname, :hostemail, :hostphone, :hostlogin, :password)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstname", hostName);
        paramMap.put("lastname", hostLastName);
        paramMap.put("hostemail", hostEmail);
        paramMap.put("hostphone", hostPhone);
        paramMap.put("hostlogin", hostLogin);
        paramMap.put("password", hostPassword);
        dataBase.update(sql, paramMap);
    }

    // Logged host fulfills own details, adding data to 'abouthosts' table
    public void fulfillHostDetails(String hostService, String hostAddress, String hostAddressType, BigInteger hostId) {
        String sql = "INSERT INTO abouthosts(service, address, address_type, host_id) " +
                "VALUES (:service, :address, :address_type, :host_id)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("service", hostService);
        paramMap.put("address", hostAddress);
        paramMap.put("address_type", hostAddressType);
        paramMap.put("host_id", hostId);
        dataBase.update(sql, paramMap);
    }





    // Adds services into 'services' table
    public void addService(String currentHostLogin, String serviceName, String serviceDescription, String serviceDuration, BigDecimal servicePrice, String servicePaymentMethod,
                           String serviceRoomType, String serviceAddress) {

        String sql = "INSERT INTO services(service_name, service_description, service_duration, service_price, payment_method, " +
                "room_type, location, host_id) " +
                "VALUES (:service_name, :service_description, :service_duration, :service_price, :payment_method, :room_type, :location, " +
                "(SELECT id FROM hosts WHERE hostlogin = :currentHostLogin))";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("service_name", serviceName);
        paramMap.put("service_description", serviceDescription);
        paramMap.put("service_duration", serviceDuration);
        paramMap.put("service_price", servicePrice);
        paramMap.put("payment_method", servicePaymentMethod);
        paramMap.put("room_type", serviceRoomType);
        paramMap.put("location", serviceAddress);
        paramMap.put("host_id", currentHostLogin);
        paramMap.put("currentHostLogin", currentHostLogin);
        dataBase.update(sql, paramMap);
    }


    //Updates specific service of logged host in table 'services'
    public void deleteLoggedHostService(BigInteger rowNumberToDelete) {

        String sql = "DELETE FROM services WHERE id = :rowNumberToDelete";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("rowNumberToDelete", rowNumberToDelete);
        dataBase.update(sql, paramMap);
    }


    //Login as host
    public String hostLogin(String hostLogin){
        String sql ="SELECT password FROM hosts WHERE hostlogin= :hostlogin";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", hostLogin);
        return dataBase.queryForObject(sql, paramMap, String.class);
    }


    // Welcomes the host that is currently logged in
    public List welcomeHost(String hostLogin){
        //Content of the table 'hosts': firstname, lastname
        String sql = "SELECT firstname, lastname " +
                "FROM hosts WHERE hostlogin= :hostlogin";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", hostLogin);
        List<HostData> resultList = dataBase.query(sql, paramMap, new WelcomeHostRowMapper());
        return resultList;
    }

    //Shows services of logged host
    public List showLoggedHostServices(String currentHostLogin) {
        String sql = "SELECT service_name, service_description, service_duration, service_price, payment_method," +
                "room_type, location, id " +
                "FROM services WHERE host_id= (SELECT id FROM hosts WHERE hostlogin = :currentHostLogin)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", currentHostLogin);
        paramMap.put("currentHostLogin", currentHostLogin);

        List<ServiceData> resultList = dataBase.query(sql, paramMap, new MyServicesRowMapper());
        return resultList;
    }


    //Shows suitable services
    public List showSuitableServices(String serviceLocation, String serviceName, BigDecimal servicePrice){


        String sql = "SELECT hosts.firstname || ' ' || hosts.lastname full_name, service_name, service_description, service_duration, service_price, " +
                "payment_method, room_type, location FROM services join hosts on services.host_id= hosts.id WHERE location= :location AND " +
                "service_price<= :service_price AND service_name= :service_name";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("location", serviceLocation);
        paramMap.put("service_name", serviceName);
        paramMap.put("service_price", servicePrice);
        List<ServiceData> resultList = dataBase.query(sql, paramMap, new SuitableServicesRowMapper());
        return resultList;
    }

    // Returns the username of the host that is currently logged in
    public BigInteger loggedHostId(String hostLogin){
        String sql = "SELECT id FROM hosts WHERE hostlogin= :hostlogin";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", hostLogin);
        return dataBase.queryForObject(sql, paramMap, BigInteger.class);
    }


}



