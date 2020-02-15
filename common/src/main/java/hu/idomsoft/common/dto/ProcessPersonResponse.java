package hu.idomsoft.common.dto;

import java.io.Serializable;

public class ProcessPersonResponse implements Serializable {

    private SzemelyDTO szemelyDTO;

    public SzemelyDTO getSzemelyDTO() {
        return szemelyDTO;
    }

    public void setSzemelyDTO(SzemelyDTO szemelyDTO) {
        this.szemelyDTO = szemelyDTO;
    }

    @Override
    public String toString() {
        return "ProcessPersonResponse{" +
                "szemelyDTO=" + szemelyDTO +
                '}';
    }
}
