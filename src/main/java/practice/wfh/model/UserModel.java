package practice.wfh.model;

import javax.validation.constraints.NotBlank;

public class UserModel {
    private String id;
    @NotBlank(message = "Please enter a firstName for the user!")
    private String firstName;
    @NotBlank(message = "Please enter a lastName for the user!")
    private String lastName;

    public UserModel(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
