package com.lance.view.bean;

import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class CategoryBean {
    public CategoryBean() {
        super();
    }

    public void listGrpDiscListener(RowDisclosureEvent rowDisclosureEvent) {
        // Add event code here...
        System.out.println("listGrpDiscListener");
        System.out.println(rowDisclosureEvent.getSource());
        System.out.println(rowDisclosureEvent.getSource().getClass().getName());
    }

    public void listSelectListener(SelectionEvent selectionEvent) {
        // Add event code here...
        System.out.println("listSelectListener");
        System.out.println(selectionEvent.getSource());
        System.out.println(selectionEvent.getSource().getClass().getName());
    }

    public String b1_action() {
        System.out.println("ok");
        return null;
    }

    public String submitMyTalent_action() {
        
        return "next";
    }

    public void onSelectAgreement(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }
}
