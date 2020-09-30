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

    //Adds a service to table 'experienced_services'
    public void addExperiencedService(BigInteger serviceId, String currentMemberLogin) {
        String sql = "INSERT INTO experienced_services (service_id, rating, host_id, logged_member_id)\n" +
                "VALUES ((SELECT id FROM services WHERE id= :serviceId),\n" +
                "        (SELECT rating FROM hosts WHERE id= (SELECT host_id FROM services WHERE id= :serviceId))," +
                "(SELECT host_id FROM services WHERE id= (SELECT host_id FROM services WHERE id= :serviceId)), " +
                "(SELECT id FROM hosts WHERE hostlogin= :loggedMemberLogin))";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("serviceId", serviceId);
        paramMap.put("loggedMemberLogin", currentMemberLogin);
        paramMap.put("hostlogin", currentMemberLogin);
        dataBase.update(sql, paramMap);
    }

    //Rates a service experienced by logged member
    public double rateService(BigInteger serviceId) {
        String sql = "SELECT rating FROM hosts WHERE id= " +
                "(SELECT host_id FROM experienced_services WHERE service_id= :serviceId)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("service_id", serviceId);
        paramMap.put("serviceId", serviceId);
        return dataBase.queryForObject(sql, paramMap, Double.class);
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
                "room_type, location, id " +
                "FROM services WHERE host_id= (SELECT id FROM hosts WHERE hostlogin = :currentHostLogin)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", currentHostLogin);
        paramMap.put("currentHostLogin", currentHostLogin);

        List<ServiceData> resultList = dataBase.query(sql, paramMap, new MyServicesRowMapper());
        return resultList;
    }

    //Shows services history of logged member
    public List<ServiceData> showLoggedMemberServicesHistory(String currentMemberLogin) {
        String sql = "SELECT hosts.firstname || ' ' || hosts.lastname full_name, hosts.rating, service_description, es.rating\n" +
                "FROM services\n" +
                "    join hosts on services.host_id= hosts.id\n" +
                "    join experienced_services es on services.id = es.host_id\n" +
                "WHERE es.logged_member_id IN(SELECT id FROM hosts WHERE hostlogin=:currentMemberLogin)";


        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hostlogin", currentMemberLogin);
        paramMap.put("currentMemberLogin", currentMemberLogin);
        List<ServiceData> resultList = dataBase.query(sql, paramMap, new ServicesHistoryRowMapper());
        return resultList;
    }


    //Shows suitable services
    public List<ServiceData> showSuitableServices(String serviceLocation, String serviceName, BigDecimal servicePrice){


        String sql = "SELECT services.id, hosts.firstname || ' ' || hosts.lastname full_name, hosts.rating, service_name, service_description, service_duration, service_price, " +
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

    //Updates rating
    public void updateRating(double hostRating, String memberBeingLogged) {
        String sql = "UPDATE hosts SET rating= :hostRating WHERE id=(SELECT host_id FROM services WHERE id=\n" +
                "                                       (SELECT service_id FROM experienced_services\n" +
                "                                       WHERE logged_member_id=(SELECT id FROM hosts\n" +
                "                                       WHERE hostlogin= :memberBeingRated)))";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("rating", hostRating);
        paramMap.put("hostRating", hostRating);
        paramMap.put("hostlogin", memberBeingLogged);
        paramMap.put("memberBeingRated", memberBeingLogged);
        dataBase.update(sql, paramMap);
    }
}



