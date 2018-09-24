package com.zack.webInterface;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;
import com.zack.dataModel.Shift;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * WebTool is the class that handles all of the functionality required to access
 * and input data onto the WhenToWork website.
 *
 * @author Zack Dreitzler
 * @version .2
 */
public class WebTool {

    /**
     * Acts as a web browser and adds the shifts to WhenToWork.
     */
    public void run(List<Shift> shiftList, List<String> details) throws FailedToolException{
        try(final WebClient client = new WebClient()) {
            final HtmlPage loginPage = client.getPage("https://whentowork.com/logins.htm");
            final HtmlPage homePage = loginToWebsite(details.get(0), details.get(1), loginPage);

            //Click the schedules button to advance to scheduling page.
            final DomElement schedules = homePage.getElementById("mnuSchedules");
            final HtmlPage schedulePage = schedules.click();

            addShifts(shiftList, schedulePage);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds the shifts in the list of shifts to whentowork.
     * @param shiftList the list of shifts.
     * @param schedulePage the base schedules page.
     */
    private void addShifts(List<Shift> shiftList, HtmlPage schedulePage) {
        HtmlPage currentPage = schedulePage;
        for (Shift shift : shiftList){
            currentPage = navigateToCurrentDate(shift, currentPage);
        }
    }

    /**
     * Pulls up the correct page that has the shift's date.
     * @param shift the shift with the date needed
     * @param currentPage current page the application is on
     * @return page with the shift's date.
     */
    private HtmlPage navigateToCurrentDate(Shift shift, HtmlPage currentPage) throws FailedToolException{
        String[] date = shift.getDate().split("/");
        String day = date[1];
        int count = 0;
        while(count < 52){
            HtmlTable dateTable = currentPage.getHtmlElementById("daysTable");
            HtmlTableRow dateRow = dateTable.getRow(0);
            for (HtmlTableCell cell : dateRow.getCells()){
                if(cell.asText().contains(day)) return currentPage;
            }
            count++;
        }
        throw new FailedToolException("Date not Found: " + shift.getDate());
    }

    /**
     * Logs the user into the WhenToWork website.
     * @param user the username for username field.
     * @param pass the password for password field.
     * @param loginPage the current html page the user is on.
     * @return htmlPage the page loaded after successful login.
     * @throws FailedToolException is login fails.
     */
    private HtmlPage loginToWebsite(String user, String pass, HtmlPage loginPage) throws FailedToolException {
        final HtmlForm form = loginPage.getFormByName("signin");
        final HtmlTextInput username = form.getInputByName("UserId1");
        final HtmlPasswordInput password = form.getInputByName("Password1");
        final HtmlButton login = form.getButtonByName("Submit1");

        username.setText(user);
        password.setText(pass);

        HtmlPage htmlPage;

        try{
            htmlPage = login.click();
        }catch (IOException e){
            throw new FailedToolException("There was a problem with WhenToWorks Login page.");
        }

        String newPage = htmlPage.getUrl().toString().substring(0,33);

        if (newPage.equals("https://WhenToWork.com/logins.htm")){
            throw new FailedToolException("Failed to login. Username or Password is incorrect.");
        }else{
            return htmlPage;
        }
    }
}
