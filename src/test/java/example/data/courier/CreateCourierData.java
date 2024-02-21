package example.data.courier;

public class CreateCourierData {
    protected String login;

    protected String password;

    protected String firstName;

    public CreateCourierData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }
}
