package com.heroku.java.controller;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroku.java.DAO.CustomerDAO;
import com.heroku.java.DAO.EmployeeDAO;
import com.heroku.java.bean.Customer;
import com.heroku.java.bean.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.*;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
import java.util.List;

@Controller
public class employeeController {   
    private final DataSource dataSource;
    

    public employeeController(DataSource dataSource) {
        this.dataSource = dataSource;
        
    }
    
    @GetMapping("/addStaff")
    public String addstaffPage(HttpSession session) {
        return "admin/addstaff";
        // if(session.getAttribute("username") != null){ 
            
        // }else{ 
        //     System.out.println("Session expired or invalid");
        //     return "login"; 
        // } 
    }
    @PostMapping("/addStaff")
    public String addAccEmp(HttpSession session, @ModelAttribute("addStaff") Employee employee) {
    try {
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        employeeDAO.addEmployee(employee);
        return "redirect:/listStaff"; 
    } catch (Exception e) {
        e.printStackTrace();
        // Handle the exception or display an error message as per your requirement
        return "admin/homeadmin";
    }
}

    //list staff
    @GetMapping("/listStaff")
    public String listStaff(HttpSession session, Employee employee,Model model) {
      EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
      try {
          List<Employee> employees = employeeDAO.listEmployee();
          model.addAttribute("employees", employees);
      } catch (SQLException e) {
          e.printStackTrace();
          // Handle the exception or display an error message as per your requirement
          return "error";
      }

    return "admin/listStaff";
    }

    //updateStaff
    @GetMapping("/updateStaff")
    public String updateStaff(@RequestParam("id") int employeeId,Model model) {
      try {
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Employee employee = employeeDAO.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "admin/updateStaff";
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("damn error bro");
        return "error";
    }
    }

    @PostMapping("/updateStaff")
    public String updateStaff(@ModelAttribute("updateStaff") Employee employee) {
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
            employeeDAO.updateEmployee(employee);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Damn error BRO!");
        }
        return "redirect:/listStaff"; // Replace with the appropriate redirect URL after updating the staff details
    }

    @PostMapping("/deleteStaff")
    public String deleteStaff(@RequestParam("id") int employeeId) {
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
            employeeDAO.deleteEmployee(employeeId);
            return "redirect:/listStaff";
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
            // Handle the exception or display an error message to the user
            // You can redirect to an error page or display a meaningful message
            return "error";
        }
    }

    @GetMapping("/profileadmin")
    public String profileadmin(HttpSession session, Model model) {
        String adminUsername = (String) session.getAttribute("username");
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        Employee employee = employeeDAO.getEmployeeByUsername(adminUsername);
        System.out.println("success");
        model.addAttribute("employee", employee);
        return "admin/profileadmin";
    }

    @GetMapping("/viewCustomer")
    public String listCustomer(Model model) {
    try {
        EmployeeDAO employeeDAO = new EmployeeDAO(dataSource);
        List<Customer> customers = employeeDAO.getAllCustomers();
        model.addAttribute("customers", customers);
        return "admin/listCustomer";
    } catch (SQLException e) {
        System.out.println("Error retrieving customers: " + e.getMessage());
        return "index";
    }
}

    @GetMapping("/searchCustomer")
    public String searchCustomer(@RequestParam(name = "searchValue", required = false) String searchValue, Model model) {
        try {
            // Perform the search based on the searchValue
            CustomerDAO customerDAO = new CustomerDAO(dataSource);
            List<Customer> searchResults = customerDAO.searchCustomersByName(searchValue);
    
            // Add the search results and the searchValue to the model
            model.addAttribute("customers", searchResults);
            model.addAttribute("searchValue", searchValue);
        } catch (SQLException e) {
            // Handle the SQLException, log it, or rethrow it as a RuntimeException if needed
            e.printStackTrace(); // You may want to log the exception instead
            // You can also redirect to an error page or handle it in a way that makes sense for your application
            model.addAttribute("error", "An error occurred during the search: " + e.getMessage());
        }
    // Return the view name to display the search results
    return "admin/listCustomer";
    }
}