package com.vortex.domain.entities;

public class LoginRequest  {

    /** The email. */
    private String email;

    /** The password. */
    private String pass;

    /**
     * Instantiates a new login request.
     */
    public LoginRequest() {
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the pass.
     *
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * Sets the pass.
     *
     * @param pass the new pass
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

}
