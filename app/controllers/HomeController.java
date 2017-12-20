package controllers;

import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {

        return ok("Your new application is ready.");
    }

    public Result loadDictionary() {

        return ok("Your new application is ready.");
    }

    // http://localhost:8080/categorize?phrase=Vice+President+of+Sales+and+Marketing
    public Result categorize(String phrase) {

        return ok("Your phrase is: "+phrase);
    }

}