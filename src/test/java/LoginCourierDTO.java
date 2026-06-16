public class LoginCourierDTO {
    private String login;
    private String password;
    private String firstName;

    public LoginCourierDTO(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public LoginCourierDTO() {
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
