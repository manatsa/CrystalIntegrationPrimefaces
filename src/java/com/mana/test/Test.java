/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mana.test;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author mana
 */
@RequestScoped
@ManagedBean(name="test")
public class Test {
    private int location=1;

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
    
    
    public void valueChanged(ValueChangeEvent event)
    {
        location=(event.getNewValue().toString().trim().length()>0)?Integer.parseInt(event.getNewValue().toString()):0;
        System.out.println("Location Changed :"+location);
    }
    
}
