/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf2demo;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "validateForm")
@RequestScoped
public class ValidateFormJSFBean {
    private String name;
    private String ssn;
    private String ageString;
    private String heightString;
    
    public ValidateFormJSFBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getAgeString() {
        return ageString;
    }

    public void setAgeString(String ageString) {
        this.ageString = ageString;
    }

    public String getHeightString() {
        return heightString;
    }

    public void setHeightString(String heightString) {
        this.heightString = heightString;
    }
    
    public String getResponse() {
        if (name == null || ssn == null || ageString == null
                || heightString == null) {
            return "";
        } else {
            return "You entered " + " Name: " + name + " SSN: " + ssn + " Age: " + 
                    ageString + " Height: " + heightString;
        }
    }
    
}
