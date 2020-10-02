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


    //Posts new host to table 'hosts'
    @PostMapping("register_new_member")
    public void registerMember(@RequestBody HostData request) {
        beautyService.registerMember(request.getHostName(), request.getHostLastName(), request.getHostEmail(),
                request.getHostPhone(), request.getHostLogin(), request.getHostPassword());
    }

    //Registers new host and updates hostrole of current member in table 'hosts'
    @PostMapping("register_new_host")
    public void registerHost(Principal principal) {
        beautyService.registerHost(principal.getName());
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
    public List<ServiceData> showSuitableServices(@RequestParam String serviceAddress, @RequestParam String serviceName,
                                     @RequestParam BigDecimal servicePrice){

        return beautyService.showSuitableServices(serviceAddress, serviceName, servicePrice);
    }


    //Shows suitable services when member is logged
    @GetMapping("show_suitable_services_logged")
    public List<ServiceData> showSuitableServicesLogged(@RequestParam String serviceAddress, @RequestParam String serviceName,
                                                  @RequestParam BigDecimal servicePrice, Principal principal){

        return beautyService.showSuitableServicesLogged(serviceAddress, serviceName, servicePrice, principal.getName());
    }


    //Deletes specific service of logged host from table 'services'
    @PostMapping("delete_service")
    public void deleteLoggedHostService(@RequestBody ServiceData request) {
        beautyService.deleteLoggedHostService(request.getServiceId());
    }

    //Adds a service to table 'experienced_services'
    @PostMapping("add_experienced_service")
    public void addExperiencedService(@RequestBody ServiceData request, Principal principal) {
        beautyService.addExperiencedService(request.getServiceId(), principal.getName());
    }

    //Checks if a selected service already exists in table 'experienced_services'
    @PostMapping("check_if_experienced_service_exists")
    public List checkIfExperiencedServiceExists(@RequestBody ServiceData request) {
        return beautyService.checkIfExperiencedServiceExists(request.getServiceId());
    }

    //Shows services history of logged member
    @PostMapping("show_logged_member_services_history")
    public List showLoggedMemberServicesHistory(Principal principal) {
        return beautyService.showLoggedMemberServicesHistory(principal.getName());
    }

    //Updates rating
    @PostMapping("update_rating")
    public void updateRating(@RequestBody ServiceData request){
        beautyService.updateRating(request.getServiceId(), request.getServiceRating());
    }

    //Gets current rating
    @PostMapping("get_rating")
    public double getRating(@RequestBody ServiceData request){
        return beautyService.getRating(request.getServiceId());
    }



    // Returns the username of the host that is currently logged in
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @ResponseBody
    @GetMapping
    public String currentUserName(Principal principal, @RequestBody HostData request) {

        String currentMemberName = principal.getName();
        return beautyService.currentUserName(currentMemberName);
    }


    // Returns the username of the host that is currently logged in
    @RequestMapping(value = "/logged_host_id", method = RequestMethod.GET)
    @ResponseBody
    public BigInteger loggedHostId(Principal principal) {
        return beautyService.loggedHostId(principal.getName());
    }


}
