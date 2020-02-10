package hu.idomsoft.common.dto;

public class ProcessPersonResponse {

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
