package hu.idomsoft.personprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.idomsoft.common.dto.*;
import hu.idomsoft.common.proxy.DocumentServiceProxy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class PersonProcessorApplicationTests {

	static {
		System.setProperty("mongodb.port", "27019");
	}

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DocumentServiceProxy documentServiceProxy;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void processPersonOk() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();

		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, validationErrors.isEmpty());
	}

	@Test
	public void processPersonVisNev1() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setVisNev("Nagy");

		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "visNev"));
	}

	@Test
	public void processPersonVisNev2() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setVisNev("Nagy Géza1");

		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "visNev"));
	}

	@Test
	public void processPersonSzulNev() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setSzulNev("Nagy Géza1");

		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "szulNev"));
	}

	@Test
	public void processPersonSzulANev() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setaNev("Dr. Nagy");
		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "aNev"));
	}

	@Test
	public void processPersonSzulDat() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setSzulDat(new SimpleDateFormat("yyyy-MM-dd").parse("1895-01-01"));
		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "szulDat"));
	}

	@Test
	public void processPersonNeme1() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setNeme("");

		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "neme"));
	}

	@Test
	public void processPersonNeme2() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setNeme("Q");

		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "neme"));
	}

	@Test
	public void processPersonAllampKod() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setAllampKod("AAA");

		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "allampKod"));
	}

	@Test
	public void processPersonAllampDekod() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.setAllampKod("HUN");

		String allampDekod = processPerson(szemelyDTO).getSzemelyDTO().getAllampDekod();
		Assert.assertEquals("MAGYARORSZÁG ÁLLAMPOLGÁRA", allampDekod);
	}

	@Test
	public void processOkmLista() throws Exception {
		SzemelyDTO szemelyDTO = createSzemelyDTO();
		szemelyDTO.getOkmLista().addAll(createOkmanyDTOList());

		List<ValidationError> validationErrors = getAllValidationError(processPerson(szemelyDTO).getSzemelyDTO());
		System.out.println(validationErrors);
		Assert.assertEquals(true, existsValidationErrorOnField(validationErrors, "okmLista"));
	}

	private ProcessPersonResponse processPerson(SzemelyDTO szemelyDTO) throws Exception {
		ProcessDocumentResponse processDocumentResponse = new ProcessDocumentResponse();
		List<OkmanyDTO> processedDocuments = processDocuments(szemelyDTO.getOkmLista());
		processDocumentResponse.setOkmanyDTOList(processedDocuments);
		Mockito.when(documentServiceProxy.processDocument(Mockito.any())).thenReturn(processDocumentResponse);
		return processPersonResponse(szemelyDTO);
	}

	private boolean existsValidationErrorOnField(List<ValidationError> validationErrors, String fieldName) {
		for (ValidationError validationError: validationErrors) {
			if (fieldName.equals(validationError.getField())) {
				return true;
			}
		}
		return false;
	}

	private List<OkmanyDTO> processDocuments(List<OkmanyDTO> documents) {
		List<OkmanyDTO> result = new ArrayList<>(documents);
		for (OkmanyDTO okmanyDTO : result) {
			okmanyDTO.setErvenyes(true);
		}
		return result;
	}

	private ProcessPersonResponse processPersonResponse(SzemelyDTO szemelyDTO) throws Exception {
		String responseString = mockMvc.perform( MockMvcRequestBuilders
				.post("/processPerson")
				.content(objectMapper.writeValueAsString(szemelyDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(responseString, ProcessPersonResponse.class);
	}

	private List<ValidationError> getAllValidationError(SzemelyDTO szemelyDTO) {
		List<ValidationError> result = new ArrayList<>();
		for (OkmanyDTO okmanyDTO : szemelyDTO.getOkmLista()) {
			result.addAll(okmanyDTO.getValidationErrors());
		}
		result.addAll(szemelyDTO.getValidationErrors());
		return result;
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

	private List<OkmanyDTO> createOkmanyDTOList() throws ParseException, IOException, URISyntaxException {
		List<OkmanyDTO> result = new ArrayList<>();
		OkmanyDTO okmanyDTO = new OkmanyDTO();
		okmanyDTO.setOkmTipus("1");
		okmanyDTO.setOkmanySzam("123456AB");
		okmanyDTO.setLejarDat(new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-12"));
		okmanyDTO.setOkmanyKep(ArrayUtils.toObject(FileUtils.readFileToByteArray(new File(getClass().getClassLoader().getResource("arckep_jo.jpg").toURI()))));
		result.add(okmanyDTO);
		return result;
	}
}