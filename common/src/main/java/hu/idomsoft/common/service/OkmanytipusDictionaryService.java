package hu.idomsoft.common.service;

import hu.idomsoft.common.dao.Okmanytipus;
import hu.idomsoft.common.dao.OkmanytipusDictionary;
import hu.idomsoft.common.dao.OkmanytipusDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OkmanytipusDictionaryService {

  @Autowired private OkmanytipusDictionaryRepository okmanytipusDictionaryRepository;

  private static final String DICTIONARY_NAME = "kodszotar46_okmanytipus";

  public boolean validateOkmTipus(String okmTipus) {
    Optional<OkmanytipusDictionary> dictionary =
        okmanytipusDictionaryRepository.findById(DICTIONARY_NAME);
    if (dictionary.isPresent()) {
      OkmanytipusDictionary okmanytipusDictionary = dictionary.get();
      for (Okmanytipus okmanytipus : okmanytipusDictionary.getRows()) {
        if (okmTipus.equals(okmanytipus.getKod())) {
          return true;
        }
      }
    }
    return false;
  }
}
