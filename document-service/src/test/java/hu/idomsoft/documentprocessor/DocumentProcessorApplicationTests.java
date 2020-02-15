package hu.idomsoft.documentprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.idomsoft.common.dto.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DocumentProcessorApplicationTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  static {
    System.setProperty("mongodb.port", "27020");
  }

  @Test
  public void processDocumentMin() throws Exception {
    SzemelyDTO szemelyDTO = createMinimalSzemelyDTO();

    List<ValidationError> validationErrors =
            getAllValidationError(processDocumentResponse(szemelyDTO).getOkmanyDTOList());
    logger.info("validationErrors:{}", validationErrors.toString());
    Assert.assertEquals(false, validationErrors.isEmpty());
  }

  @Test
  public void processDocumentOk() throws Exception {
    SzemelyDTO szemelyDTO = createSzemelyDTO();

    List<ValidationError> validationErrors =
        getAllValidationError(processDocumentResponse(szemelyDTO).getOkmanyDTOList());
      logger.info("validationErrors:{}", validationErrors.toString());
      Assert.assertEquals(true, validationErrors.isEmpty());
  }

  @Test
  public void processOkmTipus() throws Exception {
    SzemelyDTO szemelyDTO = createSzemelyDTO();
    szemelyDTO.getOkmLista().get(0).setOkmTipus("Q");

    List<ValidationError> validationErrors =
        getAllValidationError(processDocumentResponse(szemelyDTO).getOkmanyDTOList());
    logger.info("validationErrors:{}", validationErrors.toString());
    Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "okmTipus"));
  }

  @Test
  public void processOkmanySzam1() throws Exception {
    SzemelyDTO szemelyDTO = createSzemelyDTO();
    szemelyDTO.getOkmLista().get(0).setOkmanySzam("Q");

    List<ValidationError> validationErrors =
        getAllValidationError(processDocumentResponse(szemelyDTO).getOkmanyDTOList());
    logger.info("validationErrors:{}", validationErrors.toString());
    Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "okmanySzam"));
  }

  @Test
  public void processOkmanySzam2() throws Exception {
    SzemelyDTO szemelyDTO = createSzemelyDTO();
    szemelyDTO.getOkmLista().get(0).setOkmTipus("2");
    szemelyDTO.getOkmLista().get(0).setOkmanySzam("AB1234567");

    List<ValidationError> validationErrors =
        getAllValidationError(processDocumentResponse(szemelyDTO).getOkmanyDTOList());
    logger.info("validationErrors:{}", validationErrors.toString());
    Assert.assertEquals(true, validationErrors.isEmpty());
  }

  @Test
  public void processOkmanySzam3() throws Exception {
    SzemelyDTO szemelyDTO = createSzemelyDTO();
    szemelyDTO.getOkmLista().get(0).setOkmTipus("3");
    szemelyDTO.getOkmLista().get(0).setOkmanySzam("012345678910");

    List<ValidationError> validationErrors =
        getAllValidationError(processDocumentResponse(szemelyDTO).getOkmanyDTOList());
    logger.info("validationErrors:{}", validationErrors.toString());
    Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "okmanySzam"));
  }

  @Test
  public void processOkmanyKep1() throws Exception {
    SzemelyDTO szemelyDTO = createSzemelyDTO();
    szemelyDTO.getOkmLista().get(0).setOkmanyKep(loadBadImage1());

    List<ValidationError> validationErrors =
        getAllValidationError(processDocumentResponse(szemelyDTO).getOkmanyDTOList());
      logger.info("validationErrors:{}", validationErrors.toString());
      Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "okmanyKep"));
  }

  @Test
  public void processOkmanyKep2() throws Exception {
    SzemelyDTO szemelyDTO = createSzemelyDTO();
    szemelyDTO.getOkmLista().get(0).setOkmanyKep(loadBadImage2());

    List<ValidationError> validationErrors =
        getAllValidationError(processDocumentResponse(szemelyDTO).getOkmanyDTOList());
      logger.info("validationErrors:{}", validationErrors.toString());
      Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "okmanyKep"));
  }

  @Test
  public void processErvenyes() throws Exception {
    SzemelyDTO szemelyDTO = createSzemelyDTO();
    szemelyDTO
        .getOkmLista()
        .get(0)
        .setLejarDat(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"));

    Boolean isErvenyes = processDocumentResponse(szemelyDTO).getOkmanyDTOList().get(0).isErvenyes();
    Assert.assertEquals(false, isErvenyes);
  }

  private List<ValidationError> getAllValidationError(List<OkmanyDTO> okmanyDTOList) {
    List<ValidationError> result = new ArrayList<>();
    for (OkmanyDTO okmanyDTO : okmanyDTOList) {
      result.addAll(okmanyDTO.getValidationErrors());
    }
    return result;
  }

  private ProcessDocumentResponse processDocumentResponse(SzemelyDTO szemelyDTO) throws Exception {
    ProcessDocumentRequest request = new ProcessDocumentRequest();
    request.setSzemelyDTO(szemelyDTO);
    String responseString =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/processDocument")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();
    return objectMapper.readValue(responseString, ProcessDocumentResponse.class);
  }

  private boolean existsValidationErrorOnField(
      List<ValidationError> validationErrors, String fieldName) {
    for (ValidationError validationError : validationErrors) {
      if (fieldName.equals(validationError.getField())) {
        return true;
      }
    }
    return false;
  }

  private SzemelyDTO createSzemelyDTO() throws ParseException, IOException, URISyntaxException {
    SzemelyDTO szemelyDTO = new SzemelyDTO();
    szemelyDTO.setOkmLista(createOkmanyDTOList());
    szemelyDTO.setAllampKod("HUN");
    szemelyDTO.setaNev("Dr. Nagy Irén");
    szemelyDTO.setNeme("N");
    szemelyDTO.setSzulDat(new SimpleDateFormat("yyyy-MM-dd").parse("1985-09-19"));
    szemelyDTO.setSzulNev("Nagy Ferenc");
    szemelyDTO.setVisNev("Nagy Ferenc");
    return szemelyDTO;
  }

  private SzemelyDTO createMinimalSzemelyDTO() {
    SzemelyDTO szemelyDTO = new SzemelyDTO();
    szemelyDTO.setOkmLista(new ArrayList<>());
    OkmanyDTO okmanyDTO = new OkmanyDTO();
    szemelyDTO.getOkmLista().add(okmanyDTO);
    return szemelyDTO;
  }

  private List<OkmanyDTO> createOkmanyDTOList()
      throws ParseException, IOException, URISyntaxException {
    List<OkmanyDTO> result = new ArrayList<>();
    OkmanyDTO okmanyDTO = new OkmanyDTO();
    okmanyDTO.setOkmTipus("1");
    okmanyDTO.setOkmanySzam("123456AB");
    okmanyDTO.setLejarDat(new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-12"));
    okmanyDTO.setOkmanyKep(loadGoodImage());
    result.add(okmanyDTO);
    return result;
  }

  private Byte[] loadBadImage1() throws URISyntaxException, IOException {
    return ArrayUtils.toObject(
        FileUtils.readFileToByteArray(
            new File(getClass().getClassLoader().getResource("arckep_rossz.jpg").toURI())));
  }

  private Byte[] loadBadImage2() throws URISyntaxException, IOException {
    return ArrayUtils.toObject(
        FileUtils.readFileToByteArray(
            new File(getClass().getClassLoader().getResource("kep.txt").toURI())));
  }

  private Byte[] loadGoodImage() throws URISyntaxException, IOException {
    return ArrayUtils.toObject(
        FileUtils.readFileToByteArray(
            new File(getClass().getClassLoader().getResource("arckep_jo.jpg").toURI())));
  }
}
