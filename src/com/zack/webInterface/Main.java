package com.zack.webInterface;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.Iterator;

public class Main {
    
    public static void main(String[] args) {
	    try(final WebClient client = new WebClient()){
	        final HtmlPage page1 = client.getPage("https://whentowork.com/logins.htm");


	        //Get Form, fields, and submit button
            final HtmlForm form = page1.getFormByName("signin");
            final HtmlTextInput username = form.getInputByName("UserId1");
            final HtmlPasswordInput password = form.getInputByName("Password1");
            final HtmlButton login = form.getButtonByName("Submit1");

            //enter pass and userid then submit
            username.setText("");
            password.setText("");
            final HtmlPage page2 = login.click();


            //Get schedules link and click
            final DomElement schedules = page2.getElementById("mnuSchedules");
            final HtmlPage page3 = schedules.click();

            //Navigate to correct date with URL + $DATE.
            //TODO

            //open add shifts form
            //get Table
            HtmlTable table = null;
            final DomNodeList inputs = page3.getElementsByTagName("table");
            final Iterator<DomNode> iter = inputs.iterator();
            while(iter.hasNext()){
                HtmlTable tempTable = (HtmlTable) iter.next();
                if(!tempTable.getBgcolorAttribute().equals("")) table = tempTable;
            }
            //get Cell
            HtmlTableRow row = table.getRow(0);
            HtmlTableCell addShiftsCell = null;
            for(HtmlTableCell tempCell : row.getCells()){
                if(tempCell.toString().contains("Add Shifts")) addShiftsCell = tempCell;
            }

            //Click cell;
            HtmlPage addShiftsPage = addShiftsCell.click();


        }catch(IOException e){
            System.out.println("IOException caught " + e.getMessage());
        }
    }
}
