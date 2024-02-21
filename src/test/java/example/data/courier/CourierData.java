package example.data.courier;


public class CourierData {
    public static CourierData validData() {
        return new CourierData(
                new CreateCourierData("HuraYavYandexS", "jackychan", "Jacky"),
                new LoginCourierData("HuraYavYandexS", "jackychan")
        );
    }

    public static CourierData noLoginData() {
        return new CourierData(
                new CreateCourierData("", "jackychan", "Jacky"),
                new LoginCourierData("", "jackychan")
        );
    }

    public static CourierData noPasswordData() {
        return new CourierData(
                new CreateCourierData("HuraYavYandexS", "", "Jacky"),
                new LoginCourierData("HuraYavYandexS", "")
        );
    }

    private final CreateCourierData createCourierData;
    private final LoginCourierData loginCourierData;

    public CourierData(CreateCourierData createCourierData, LoginCourierData loginCourierData) {
        this.createCourierData = createCourierData;
        this.loginCourierData = loginCourierData;
    }

    public CreateCourierData getCreateCourierData() {
        return createCourierData;
    }

    public LoginCourierData getLoginCourierData() {
        return loginCourierData;
    }
}
