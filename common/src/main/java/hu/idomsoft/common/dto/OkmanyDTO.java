/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.idomsoft.common.dto;

import hu.idomsoft.common.validator.DocumentNumber;
import hu.idomsoft.common.validator.DocumentType;
import hu.idomsoft.common.validator.Jpeg;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/** @author Ruzsinak */
@DocumentNumber(fieldName = "okmanySzam")
public class OkmanyDTO implements Serializable {

  /** @return the okmTipus */
  public String getOkmTipus() {
    return okmTipus;
  }

  /** @param okmTipus the okmTipus to set */
  public void setOkmTipus(String okmTipus) {
    this.okmTipus = okmTipus;
  }

  /** @return the okmanySzam */
  public String getOkmanySzam() {
    return okmanySzam;
  }

  /** @param okmanySzam the okmanySzam to set */
  public void setOkmanySzam(String okmanySzam) {
    this.okmanySzam = okmanySzam;
  }

  /** @return the okmanyKep */
  public Byte[] getOkmanyKep() {
    return okmanyKep;
  }

  /** @param okmanyKep the okmanyKep to set */
  public void setOkmanyKep(Byte[] okmanyKep) {
    this.okmanyKep = okmanyKep;
  }

  /** @return the lejarDat */
  public Date getLejarDat() {
    return lejarDat;
  }

  /** @param lejarDat the lejarDat to set */
  public void setLejarDat(Date lejarDat) {
    this.lejarDat = lejarDat;
  }

  /** @return the ervenyes */
  public Boolean isErvenyes() {
    return ervenyes;
  }

  /** @param ervenyes the ervenyes to set */
  public void setErvenyes(Boolean ervenyes) {
    this.ervenyes = ervenyes;
  }

  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  public void setValidationErrors(List<ValidationError> validationErrors) {
    this.validationErrors = validationErrors;
  }

  private static final long serialVersionUID = 1L;

  @NotBlank
  @Size(min = 1, max = 1)
  @DocumentType
  private String okmTipus;

  @NotBlank private String okmanySzam;

  @NotEmpty
  @Jpeg(height = 1063, width = 827)
  private Byte[] okmanyKep;

  private Date lejarDat;

  private Boolean ervenyes;

  private List<ValidationError> validationErrors = new ArrayList<>();

  @Override
  public String toString() {
    return "OkmanyDTO{"
        + "okmTipus='"
        + okmTipus
        + '\''
        + ", okmanySzam='"
        + okmanySzam
        + '\''
        + ", okmanyKep="
        + Arrays.toString(okmanyKep)
        + ", lejarDat="
        + lejarDat
        + ", ervenyes="
        + ervenyes
        + ", validationErrors="
        + validationErrors
        + '}';
  }
}
