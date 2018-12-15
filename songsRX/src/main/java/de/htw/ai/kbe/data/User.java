package de.htw.ai.kbe.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
    private Integer id;
    private String userId;
    private String lastName;
    private String firstName;

    // needed for JAXB
    public User() {
    }

    // Example of a builder:
   /* public static class Builder {
        // required parameter
        private Integer id;
        private String userId;
        // optional parameters
        private String firstName;
        private String lastName;
        public Builder(Integer id, String userId) {
            this.id = id;
            this.userId = userId;
        }
        public Builder firstName(String val) {
            firstName = val;
            return this;
        }
        public Builder lastName(String val) {
            lastName = val;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
    private User(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }*/

    // also needed for JAXB
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}