package example.data.courier;

public class LoginCourierData {

    protected String login;

    protected String password;

    public LoginCourierData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
