package ee.bcs.vali.it.controller;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.Principal;
import java.util.List;


@RestController
public class Controller {
    @Autowired
    private BeautyService beautyService;


    //Posts new user to table 'users'
    @PostMapping("register_new_user")
    public void registerUser(@RequestBody UserData request) {
        beautyService.registerUser(request.getUserName(), request.getUserLastName(), request.getUserEmail(),
                request.getUserPhone(), request.getUserLogin(), request.getUserPassword());
    }


    //Posts new host to table 'hosts'
    @PostMapping("register_new_host")
    public void registerHost(@RequestBody HostData request) {
        beautyService.registerHost(request.getHostName(), request.getHostLastName(), request.getHostEmail(),
                request.getHostPhone(), request.getHostLogin(), request.getHostPassword());
    }


    //Posts host's additional details into table 'abouthosts'
    @PostMapping("host_details")
    public void fulfillHostDetails(@RequestBody HostData request) {
        beautyService.fulfillHostDetails(request.getHostService(), request.getHostAddress(),
                request.getHostAddressType(), request.getHostId());
    }


    //Posts services into table 'services'
    @PostMapping("add_service")
    public void addService(@RequestBody ServiceData request, Principal principal) {
        beautyService.addService(principal.getName(), request.getServiceName(), request.getServiceDescription(), request.getServiceDuration(),
                request.getServicePrice(), request.getServicePaymentMethod(),
                request.getServiceRoomType(), request.getServiceAddress());
    }


    // Returns the username aka login of the user that is currently logged in
    @RequestMapping(value = "/hostname", method = RequestMethod.GET)
    @ResponseBody
    public String currentHostName(Principal principal) {
        return principal.getName();
    }


    // Welcomes the user that is currently logged in
    @PostMapping("welcome_host")
    public List welcomeHost(Principal principal) {
        return beautyService.welcomeHost(principal.getName());
    }


    //Login of host
    @PostMapping("hostLogin")
    public String hostLogin(@RequestBody HostData request) {
        return beautyService.hostLogin(request.getHostPassword());
    }

    //Login of user
    @PostMapping("userLogin")
    public String userLogin(@RequestBody UserData request) {
        return beautyService.userLogin(request.getUserPassword());
    }

    //Shows services of logged host
    @PostMapping("show_logged_host_services")
    public List showLoggedHostServices(Principal principal) {
        return beautyService.showLoggedHostServices(principal.getName());
    }


    //Shows suitable services
    @GetMapping("show_suitable_services")
    public List showSuitableServices(@RequestParam String serviceAddress, @RequestParam String serviceName,
                                     @RequestParam BigDecimal servicePrice){

        return beautyService.showSuitableServices(serviceAddress, serviceName, servicePrice);
    }


    //Deletes specific service of logged host in table 'services'
    @PostMapping("delete_service")
    public void deleteLoggedHostService(@RequestBody ServiceData request) {
        beautyService.deleteLoggedHostService(request.getServiceId());
    }


    // Returns the username of the host that is currently logged in
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }


    // Returns the username of the host that is currently logged in
    @RequestMapping(value = "/logged_host_id", method = RequestMethod.GET)
    @ResponseBody
    public BigInteger loggedHostId(Principal principal) {
        return beautyService.loggedHostId(principal.getName());
    }


}
