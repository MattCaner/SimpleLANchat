package app.viewTool;

public enum Views {

    Login("login", "login", "Login"),
    MainView("mainView", "", "RPGDatabase");

    public final String path;
    public final String cssPath;
    public final String title;

    Views(String path, String cssPath, String title){
        this.path = path;
        this.cssPath = cssPath;
        this.title = title;
    }
}
