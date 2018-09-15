package com.zack.webInterface;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.zack.dataModel.Shift;

import java.io.IOException;
import java.util.List;

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
            final HtmlPage page2 = loginToWebsite(details.get(0), details.get(1), loginPage);

        } catch (IOException e){
            System.out.println(e.getMessage());
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
