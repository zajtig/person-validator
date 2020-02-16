package hu.idomsoft.common.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ValidationError implements Serializable {

  @ApiModelProperty(notes = "Melyik mezőhöz tartozik a hibaüzenet")
  private String field;

  @ApiModelProperty(notes = "Hibaüzenet")
  private String message;

  public ValidationError() {}

  public ValidationError(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ValidationError{" + "field='" + field + '\'' + ", message='" + message + '\'' + '}';
  }
}
