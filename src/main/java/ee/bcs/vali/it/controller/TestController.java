package ee.bcs.vali.it.controller;

import ee.bcs.vali.it.Lesson2;
import ee.bcs.vali.it.Lesson3;
import ee.bcs.vali.it.OneTest;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static java.time.ZoneOffset.UTC;

@RestController
public class TestController {
    // one
    @GetMapping(value="sum")
    public int sum(@RequestParam("b") int param1UserEntersInABrowser){

        return sum(3, param1UserEntersInABrowser);
    }
    // two
    @GetMapping("/sum2")
    public int sum2(@RequestParam("bb") int param2UserEntersInABrowser){

        return sum2(1, param2UserEntersInABrowser);
    }
    // three
    @GetMapping("/test")
    public TestData test(@RequestParam("name") String name){
        TestData testData = new TestData();
        testData.setName(name);
        testData.setAge(45);
        testData.setAddress("Narva");

        return testData;
    }
    // four
    @GetMapping("/fairytail")
    public String fairyTail(@RequestParam("param") String name){
        return "Once upon a time there lived a nice man " + name + ". And then happened a big tragedy." +
                name + " went for a swim and huge shark ate him. The end.";

    }

    // five
    @GetMapping("/fibonumber")
    public int fibonacci(@RequestParam("fibonaccinumber") int number){
        return Lesson2.fibonacci(number);
    }

    // six
    @GetMapping("/factorial")
    public int factorial(@RequestParam("id") int num){
        return Lesson3.factorial(num);
    }

    // seven
    @GetMapping("/arraysort")
    public int [] sort(@RequestParam("id") int [] z){
        return Lesson3.sort(z);
    }

    // eight
    @GetMapping("/reverse")
    public String reversestring(@RequestParam("id") String wierdword){
        return Lesson3.reverseString(wierdword);
    }

    // nine
    @GetMapping("/logintest")
    public String loginme(@RequestParam("id") String userInput){
        return OneTest.loginme(userInput);
    }

    // ten
    @GetMapping("/loginform")
    public String login(@RequestParam("id") int userInput){
        return OneTest.loginform(userInput);
    }

    // eleven
    @GetMapping("/2darray")
    public String array2d(@RequestParam("id") int userInput){
        return OneTest.array2d(userInput);
    }

    // twelve
    @GetMapping("/localtime")
    public String localTime(@RequestParam("id") String userInput){
        String timeOnDisplay = "";
        SimpleDateFormat date_time_format = new SimpleDateFormat("dd-MM-yyyy EEEE hh:mm:ss a");
        TimeZone time_zone;
        Date date= new Date();
        time_zone = TimeZone.getTimeZone("GMT");
        if(userInput.equals("time_est")){
            timeOnDisplay = date_time_format.format(date);

            return timeOnDisplay;
        }
        else if(userInput.equals("time_la")){
            time_zone =TimeZone.getTimeZone("GMT-7");
            date_time_format.setTimeZone(time_zone);
            timeOnDisplay = date_time_format.format(date);
        }
        return timeOnDisplay;
    }
    ////////////////////////
    @GetMapping("/testdto")
    public TestDTO func (@RequestParam("nickName") String userInput1, @RequestParam("bool") boolean userInput2,
                         @RequestParam("life") int userInput3){
        TestDTO test = new TestDTO();
        test.setNickName(userInput1);
        test.setCanFly(userInput2);
        test.setLifePack(userInput3);
        return test;

    }

    @PostMapping("/testdto")
    public void func (@RequestBody TestDTO output1){
        System.out.println(output1.getNickName());
        System.out.println(output1.getLifePack());
        System.out.println(output1.isCanFly());

    }
    ///////////////////////

    private static final List<Employee> employees = new ArrayList<>();

    @GetMapping("employee")
    public List<Employee> getAllEmployee() {
        return employees;
    }
    @PostMapping("employee")
    public void addEmployees(@RequestBody Employee employee){
        employees.add(employee);
    }

    @GetMapping("employee/{id}")
    public Employee getEmployeesID(@PathVariable("id") int employeeIDtoGet){
        return employees.get(employeeIDtoGet);
    }

    @PutMapping("employee/{id}")
    public void putEmployees(@RequestBody Employee employee, @PathVariable ("id") int employeeID){
        employees.set(employeeID, employee);
    }
    @DeleteMapping("employee/{id}")
    public void delEmployees(@PathVariable("id") int employeeIDtoDelete){
        employees.remove(employeeIDtoDelete);
    }





    //////////////////////

    public static int sum(int a, int b){
        int result = 0;
        for(int i = a; i< b; i++){
            result = result + i*b;
        }
        return result;
    }

    public static int sum2(int a, int bb){

        int result = a+bb;

        return result;
    }





}
