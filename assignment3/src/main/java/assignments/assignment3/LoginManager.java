package assignments.assignment3;

import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

// LoginManager Class: Manages the login system for the program, return system yang diperllukan besdasarkan role yang diberikan

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem; 

    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    public UserSystemCLI getSystem(String role){
        // Masa errornya cuma harus dibalik doang :))
        if(role == "Customer"){
            return customerSystem;
        }else{
            return adminSystem;
        }
    }
}