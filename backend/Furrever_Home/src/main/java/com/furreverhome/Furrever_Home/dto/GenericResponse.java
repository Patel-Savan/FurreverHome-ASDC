package com.furreverhome.Furrever_Home.dto;

public record GenericResponse(String message, String error) {

  public GenericResponse(String message) {
    this(message, null);
  }

}
