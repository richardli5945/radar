package com.solidstategroup.radar.web.pages;


import com.solidstategroup.radar.model.exception.EmailAddressNotFoundException;

public class PatientForgottenPasswordPage extends ForgottenPasswordPage{

    @Override
    protected String getUsesType() {
        return "Patients";
    }

    @Override
    public void sendPassword(String username) throws EmailAddressNotFoundException {
        userManager.sendForgottenPasswordToPatient(username);
    }
}