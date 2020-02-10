/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.idomsoft.common.dto;

import hu.idomsoft.common.validator.Age;
import hu.idomsoft.common.validator.Documents;
import hu.idomsoft.common.validator.Name;
import hu.idomsoft.common.validator.Nationality;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ruzsinak
 */
public class SzemelyDTO implements Serializable{

    /**
     * @return the visNev
     */
    public String getVisNev() {
        return visNev;
    }

    /**
     * @param visNev the visNev to set
     */
    public void setVisNev(String visNev) {
        this.visNev = visNev;
    }

    /**
     * @return the szulNev
     */
    public String getSzulNev() {
        return szulNev;
    }

    /**
     * @param szulNev the szulNev to set
     */
    public void setSzulNev(String szulNev) {
        this.szulNev = szulNev;
    }

    /**
     * @return the aNev
     */
    public String getaNev() {
        return aNev;
    }

    /**
     * @param aNev the aNev to set
     */
    public void setaNev(String aNev) {
        this.aNev = aNev;
    }

    /**
     * @return the szulDat
     */
    public Date getSzulDat() {
        return szulDat;
    }

    /**
     * @param szulDat the szulDat to set
     */
    public void setSzulDat(Date szulDat) {
        this.szulDat = szulDat;
    }

    /**
     * @return the neme
     */
    public String getNeme() {
        return neme;
    }

    /**
     * @param neme the neme to set
     */
    public void setNeme(String neme) {
        this.neme = neme;
    }

    /**
     * @return the allampKod
     */
    public String getAllampKod() {
        return allampKod;
    }

    /**
     * @param allampKod the allampKod to set
     */
    public void setAllampKod(String allampKod) {
        this.allampKod = allampKod;
    }

    /**
     * @return the allampDekod
     */
    public String getAllampDekod() {
        return allampDekod;
    }

    /**
     * @param allampDekod the allampDekod to set
     */
    public void setAllampDekod(String allampDekod) {
        this.allampDekod = allampDekod;
    }

    /**
     * @return the okmLista
     */
    public List<OkmanyDTO> getOkmLista() {
        return okmLista;
    }

    /**
     * @param okmLista the okmLista to set
     */
    public void setOkmLista(List<OkmanyDTO> okmLista) {
        this.okmLista = okmLista;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    private static final long serialVersionUID = 4L;

    @NotBlank
    @Pattern(regexp = "^[a-záéíóöőúűA-ZÄÁÉÍÓÖŐÚŰ./'\\- ]+$")
    @Name(fieldName = "visNev")
    private String visNev;

    @NotBlank
    @Pattern(regexp = "^[a-záéíóöőúűA-ZÄÁÉÍÓÖŐÚŰ./'\\- ]+$")
    @Name(fieldName = "szulNev")
    private String szulNev;

    @NotBlank
    @Pattern(regexp = "^[a-záéíóöőúűA-ZÄÁÉÍÓÖŐÚŰ./'\\- ]+$")
    @Name(fieldName = "aNev")
    private String aNev;

    @NotNull
    @Age(min = 18, max = 120, fieldName = "szulDat")
    private Date szulDat;

    @NotBlank
    @Pattern(regexp = "^[FN]+$")
    private String neme;

    @NotBlank
    @Size(min = 3, max = 3)
    @Nationality(fieldName = "allampKod")
    private String allampKod;
    
    private String allampDekod;

    @Documents(fieldName = "okmLista")
    private List<OkmanyDTO> okmLista;

    private List<ValidationError> validationErrors;

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Override
    public String toString() {
        return "SzemelyDTO{" +
                "visNev='" + visNev + '\'' +
                ", szulNev='" + szulNev + '\'' +
                ", aNev='" + aNev + '\'' +
                ", szulDat=" + szulDat +
                ", neme='" + neme + '\'' +
                ", allampKod='" + allampKod + '\'' +
                ", allampDekod='" + allampDekod + '\'' +
                ", okmLista=" + okmLista +
                ", validationErrors=" + validationErrors +
                '}';
    }
}