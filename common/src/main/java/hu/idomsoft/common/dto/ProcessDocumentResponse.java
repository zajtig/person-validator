package hu.idomsoft.common.dto;

import java.io.Serializable;
import java.util.List;

public class ProcessDocumentResponse implements Serializable {

    private List<OkmanyDTO> okmanyDTOList;

    public List<OkmanyDTO> getOkmanyDTOList() {
        return okmanyDTOList;
    }

    public void setOkmanyDTOList(List<OkmanyDTO> okmanyDTOList) {
        this.okmanyDTOList = okmanyDTOList;
    }

    @Override
    public String toString() {
        return "ProcessDocumentResponse{" +
                "okmanyDTOList=" + okmanyDTOList +
                '}';
    }
}