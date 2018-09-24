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
    private void addShifts(List<Shift> shiftList, HtmlPage schedulePage) throws FailedToolException {
        HtmlPage currentPage = schedulePage;
        for (Shift shift : shiftList) {
            try{
                currentPage = navigateToCurrentDate(shift, currentPage);
                HtmlPage addShiftPage = getAddShiftPage(currentPage);

                HtmlSelect jobSelector = addShiftPage.getElementByName("SkillId");
                HtmlOption jobTitle = jobSelector.getOptionByText(getShiftTitle(shift.getStaffType()));
                jobTitle.click();

                HtmlSelect colorSelector = addShiftPage.getElementByName("Color");
                HtmlOption colorOption = colorSelector.getOptionByText(getShiftColor(shift.getStaffType()));
                colorOption.click();

                String[] times = shift.getTime().split("-");
                HtmlTextInput startTime = addShiftPage.getElementByName("ShiftStartTime");
                startTime.setText(times[0]);
                HtmlTextInput endTime = addShiftPage.getElementByName("ShiftEndTime");
                endTime.setText(times[1]);

                HtmlTextInput description = addShiftPage.getElementByName("ShiftName");
                description.setText(shift.getCourseType().equals("") ? "Zips" : shift.getCourseType() +
                        " Course " +
                        (shift.getCourseType().equalsIgnoreCase("Apprentice") ? "(Apprentice)" : ""));

                HtmlCheckBoxInput dayOfShift = addShiftPage.getElementByName(getDayOfShift(shift.getDayOfWeek()));
                dayOfShift.click();

                HtmlTextInput numberOfShifts = addShiftPage.getElementByName("ShiftWorkersNeeded");
                numberOfShifts.setText("" + shift.getNumShifts());

                HtmlButtonInput submit = addShiftPage.getElementByName("SubmitBtn1");
                submit.click();
            }catch (IOException e){
                throw new FailedToolException("Failed to add Shift " + shift);
            }
        }
    }

    /**
     * Returns the color the shift will appear based on the staff type.
     * @param staffType type of staff working the shift.
     * @return string representing the color.
     */
    private String getShiftColor(String staffType) {
        if(staffType.equalsIgnoreCase("Tower Tech")){
            return "navy";
        }else if(staffType.equalsIgnoreCase("Zip Tech")){
            return "maroon";
        }else if(staffType.equalsIgnoreCase("Facilitator")){
            return "purple";
        }else {
            return "aqua";
        }
    }

    /**
     * Returns the title of the position found on whentowork.
     * @param staffType type of staff for this shift.
     * @return string on the correct position.
     */
    private String getShiftTitle(String staffType) {
        if(staffType.equalsIgnoreCase("Tower Tech")){
            return "Team Training Tower Tech";
        }else if(staffType.equalsIgnoreCase("Zip Tech")){
            return "Team Training Zip Tech";
        }else if(staffType.equalsIgnoreCase("Facilitator")){
            return "Team Training Facilitator";
        }else {
            return "Team Training Apprentice";
        }
    }

    /**
     * Returns the correct checkbox based on the day of week the shift occurs.
     * @param dayOfWeek string to represent the day of week the shift is on.
     * @return String name of the correct checkbox.
     */
    private String getDayOfShift(String dayOfWeek) {
        switch (dayOfWeek){
            case "Su":
                return "AddDay0";
            case "M":
                return "AddDay1";
            case "Tu":
                return "AddDay2";
            case "W":
                return "AddDay3";
            case "Th":
                return "AddDay4";
            case "F":
                return "AddDay5";
            case "Sa":
                return "AddDay6";
            default:
                return null;
        }
    }

    /**
     * Opens the add shifts page on whentowork.
     * @param currentPage page the application is currently on
     * @return the page that has the Add Shifts form on it.
     * @throws FailedToolException if IOException is thrown.
     */
    private HtmlPage getAddShiftPage(HtmlPage currentPage) throws FailedToolException{
            HtmlTable table = null;
            final DomNodeList inputs = currentPage.getElementsByTagName("table");
            final Iterator<DomNode> iter = inputs.iterator();
            while(iter.hasNext()){
                HtmlTable tempTable = (HtmlTable) iter.next();
                if(!tempTable.getBgcolorAttribute().equals("")) table = tempTable;
            }

            HtmlTableRow row = table.getRow(0);
            HtmlTableCell addShiftsCell = null;
            for(HtmlTableCell tempCell : row.getCells()){
                if(tempCell.toString().contains("Add Shifts")) addShiftsCell = tempCell;
            }

            try{
                return addShiftsCell.click();
            }catch (IOException e){
                throw new FailedToolException("Failed to open Add Shifts page.");
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
        String month = getMonth(date[0]);
        String day = date[1];
        int count = 0;
        while(count < 52){
            HtmlTable dateTable = currentPage.getHtmlElementById("daysTable");
            HtmlTableRow dateRow = dateTable.getRow(0);
            for (HtmlTableCell cell : dateRow.getCells()){
                if(cell.asText().contains(month+"-"+day)){
                    return currentPage;
                }
            }

            HtmlAnchor anchor = null;
            Iterator<HtmlAnchor> iterator = currentPage.getAnchors().iterator();
            while (iterator.hasNext()){
                anchor = iterator.next();
                if(anchor.toString().contains("Forward one week")){
                    try {
                        currentPage = anchor.click();
                        break;
                    } catch (IOException e){
                        throw new FailedToolException("Failed to advance to the next week\n" +
                                "Current URL is " + currentPage.getUrl());
                    }
                }
            }

            count++;
        }
        throw new FailedToolException("Date not Found: " + shift.getDate());
    }

    /**
     * Returns the appropriate string for the month of this shift.
     * @param str the month of this shift
     * @return
     */
    private String getMonth(String str) {
        switch (str){
            case "1":
                return "Jan";
            case "2":
                return "Feb";
            case "3":
                return "Mar";
            case "4":
                return "Apr";
            case "5":
                return "May";
            case "6":
                return "Jun";
            case "7":
                return "Jul";
            case "8":
                return "Aug";
            case "9":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
            default:
                return null;
        }
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
