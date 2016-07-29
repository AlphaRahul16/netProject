package com.qait.automation.report;

import javax.mail.PasswordAuthentication;

/**
 *
 * @author prashant
 */
public class Authenticator extends javax.mail.Authenticator {
        
    private PasswordAuthentication authentication;

    public Authenticator(String username, String password) {
        authentication = new PasswordAuthentication(username, password);
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }
}

