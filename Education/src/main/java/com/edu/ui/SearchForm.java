package com.edu.ui;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;


@SessionScoped
@Named
public class SearchForm implements Serializable {
    private Date hiredAfter;

    public Date getHiredAfter() {
        return hiredAfter;
    }

    public void setHiredAfter(Date hiredAfter) {
        this.hiredAfter = hiredAfter;
    }
}
