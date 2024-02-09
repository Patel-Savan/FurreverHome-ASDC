package com.furreverhome.Furrever_Home.utils;

import lombok.Data;

import java.util.Date;
@Data
public class ErrorDetails {

    private Date date;

    private String Message;

    private String requestDescription;

    public ErrorDetails() {
    }
}
