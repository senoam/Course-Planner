package ca.cmpt213.as5.restapi;

public class ApiAboutWrapper {
    public String appName;
    public String authorName;

    public ApiAboutWrapper(String appName, String authorName) {
        this.appName = appName;
        this.authorName = authorName;
    }
}