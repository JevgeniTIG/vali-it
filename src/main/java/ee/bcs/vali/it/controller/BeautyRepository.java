package ee.bcs.vali.it.controller;

import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BeautyRepository {


    @Autowired
    private NamedParameterJdbcTemplate dataBase;


    // Registers new host, adding data to 'hosts' table
    public void registerMember(String hostName, String hostLastName, String hostEmail, String hostPhone,
                             String hostLogin, String hostPassword) {
        String sql = "INSERT INTO hosts(firstname, lastname, hostemail, hostphone, hostlogin, password, hostrole) " +
                "VALUES (:firstname, :lastname, :hostemail, :hostphone, :hostlogin, :password, :hostrole)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstname", hostName);
        paramMap.put("lastname", hostLastName);
        paramMap.put("hostemail", hostEmail);
        paramMap.put("hostphone", hostPhone);
        paramMap.put("hostlogin", hostLogin);
        paramMap.put("password", hostPassword);
        paramMap.put("hostrole", "member");
        dataBase.update(sql, paramMap);
    }

    //Registers new host and updates hostrole of current member in table 'hosts'
    public void registerHost(String currentMemberLogin) {
        String sql = "UPDATE hosts SET hostrole= :hostrole WHERE hostlogin= :hostlogin";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostrole", "member and host");
        paramMap.put("hostlogin", currentMemberLogin);
        dataBase.update(sql, paramMap);

    }

    // Adds services into 'services' table
    public void addService(String currentHostLogin, String serviceName, String serviceDescription, String serviceDuration, BigDecimal servicePrice, String servicePaymentMethod,
                           String serviceRoomType, String serviceAddress) {

        String sql = "INSERT INTO services(service_name, service_description, service_duration, service_price, payment_method, " +
                "room_type, service_location, host_id) " +
                "VALUES (:service_name, :service_description, :service_duration, :service_price, :payment_method, :room_type, :service_location, " +
                "(SELECT id FROM hosts WHERE hostlogin = :currentHostLogin))";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("service_name", serviceName);
        paramMap.put("service_description", serviceDescription);
        paramMap.put("service_duration", serviceDuration);
        paramMap.put("service_price", servicePrice);
        paramMap.put("payment_method", servicePaymentMethod);
        paramMap.put("room_type", serviceRoomType);
        paramMap.put("service_location", serviceAddress);
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

    //Adds a service to table 'experienced_services'
    public void addExperiencedService(BigInteger serviceId, String currentMemberLogin) {
        String sql ="INSERT INTO experienced_services (rating, service_id, logged_member_id)\n" +
                "                VALUES (\n" +
                "                (SELECT rating FROM services WHERE id= :serviceId) ,\n" +
                "                (SELECT id FROM services WHERE id= :serviceId),\n" +
                "                (SELECT id FROM hosts WHERE hostlogin= :currentMemberLogin))";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("serviceId", serviceId);
        paramMap.put("currentMemberLogin", currentMemberLogin);
        paramMap.put("hostlogin", currentMemberLogin);
        dataBase.update(sql, paramMap);
    }


    //Updates rating
    public void updateRating(BigInteger serviceId, Double serviceRating) {
        String sql = "UPDATE services SET rating= :serviceRating WHERE id= :serviceId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("serviceRating", serviceRating);
        paramMap.put("serviceId", serviceId);
        dataBase.update(sql, paramMap);
    }


    //Login as host
    public String hostLogin(String hostLogin){
        String sql ="SELECT password FROM hosts WHERE hostlogin= :hostlogin";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", hostLogin);
        return dataBase.queryForObject(sql, paramMap, String.class);
    }


    // Welcomes the member that is currently logged in
    public List welcomeHost(String hostLogin){
        //Content of the table 'hosts': firstname, lastname
        String sql = "SELECT firstname, lastname, hostrole, hostlogin " +
                "FROM hosts WHERE hostlogin= :hostlogin";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", hostLogin);
        List<HostData> resultList = dataBase.query(sql, paramMap, new WelcomeHostRowMapper());
        return resultList;
    }

    //Shows services of logged host
    public List showLoggedHostServices(String currentHostLogin) {
        String sql = "SELECT service_name, service_description, service_duration, service_price, payment_method," +
                "room_type, service_location, id " +
                "FROM services WHERE host_id= (SELECT id FROM hosts WHERE hostlogin = :currentHostLogin)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", currentHostLogin);
        paramMap.put("currentHostLogin", currentHostLogin);

        List<ServiceData> resultList = dataBase.query(sql, paramMap, new MyServicesRowMapper());
        return resultList;
    }

    //Shows services history of logged member
    public List<ServiceData> showLoggedMemberServicesHistory(String currentMemberLogin) {

        String sql = "SELECT experienced_services.service_id service_id, hosts.firstname || ' ' || hosts.lastname full_name, experienced_services.rating rating, service_description\n" +
                "FROM services\n" +
                "         join hosts on services.host_id= hosts.id\n" +
                "         join experienced_services on services.id = experienced_services.service_id\n" +
                "WHERE (logged_member_id= (SELECT id FROM hosts WHERE hostlogin= :currentMemberLogin))\n";


        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", currentMemberLogin);
        paramMap.put("currentMemberLogin", currentMemberLogin);
        List<ServiceData> resultList = dataBase.query(sql, paramMap, new ServicesHistoryRowMapper());
        return resultList;
    }


    //Shows suitable services
    public List<ServiceData> showSuitableServices(String serviceLocation, String serviceName, BigDecimal servicePrice){


        String sql = "SELECT services.id, hosts.firstname || ' ' || hosts.lastname full_name, " +
                "rating, service_name, service_description, " +
                "service_duration, service_price, payment_method, room_type, service_location FROM services " +
                "join hosts on services.host_id= hosts.id " +
                "WHERE service_location= :service_location AND service_price<= :service_price AND service_name= :service_name";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("service_location", serviceLocation);
        paramMap.put("service_name", serviceName);
        paramMap.put("service_price", servicePrice);
        List<ServiceData> resultList = dataBase.query(sql, paramMap, new SuitableServicesRowMapper());
        return resultList;
    }

    //Shows suitable services when member is logged
    public List<ServiceData> showSuitableServicesLogged(String serviceLocation, String serviceName, BigDecimal servicePrice, String loggedMemberLogin){


        String sql = "SELECT services.id, hosts.firstname || ' ' || hosts.lastname full_name, " +
                "rating, service_name, service_description, " +
                "service_duration, service_price, payment_method, room_type, service_location FROM services " +
                "join hosts on services.host_id= hosts.id " +
                "WHERE service_location= :service_location AND service_price<= :service_price AND service_name= :service_name AND services.host_id!=(SELECT id FROM hosts WHERE hostlogin= :loggedMemberLogin)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("service_location", serviceLocation);
        paramMap.put("service_name", serviceName);
        paramMap.put("service_price", servicePrice);
        paramMap.put("hostlogin", loggedMemberLogin);
        paramMap.put("loggedMemberLogin", loggedMemberLogin);
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

    // Returns logged member role
    public String currentUserName(String currentUser) {
        String sql = "SELECT hostrole FROM hosts WHERE firstname= :firstname";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstname", currentUser);
        return dataBase.queryForObject(sql, paramMap, String.class);
    }

    //Geolocation
    public void givenIP_whenFetchingCity_thenReturnsCityData()
            throws IOException, GeoIp2Exception {
        String ip = "your-ip-address";
        String dbLocation = "your-path-to-mmdb";

        File database = new File(dbLocation);
        DatabaseReader dbReader = new DatabaseReader.Builder(database)
                .build();

        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        String countryName = response.getCountry().getName();
        String cityName = response.getCity().getName();
        String postal = response.getPostal().getCode();
        String state = response.getLeastSpecificSubdivision().getName();
    }

    //Checks if a selected service already exists in table 'experienced_services'
    public List<ServiceData> checkIfExperiencedServiceExists(BigInteger serviceId) {
        String sql = "SELECT service_id FROM experienced_services WHERE service_id= :serviceId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("serviceId", serviceId);
        List<ServiceData> resultList = dataBase.query(sql, paramMap, new SuitableServicesRowMapper());
        return resultList;


    }
    //Gets current rating
    public double getRating(BigInteger serviceId) {
        String sql = "SELECT rating FROM services WHERE id= :serviceId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("serviceId", serviceId);
        return dataBase.queryForObject(sql, paramMap, double.class);
    }
}



