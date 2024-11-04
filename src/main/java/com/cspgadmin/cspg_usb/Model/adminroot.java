package com.cspgadmin.cspg_usb.Model;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "adminroot")

public class adminroot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;

    private String password;

    public adminroot(Long id, String password) {
        this.id = id;
        this.password = password;
    }   
    
    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
