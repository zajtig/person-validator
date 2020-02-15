package hu.idomsoft.common.service;

import hu.idomsoft.common.dao.Allampolg;
import hu.idomsoft.common.dao.AllampolgDictionary;
import hu.idomsoft.common.dao.AllampolgDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllampolgDictionaryService {

  @Autowired private AllampolgDictionaryRepository allampolgDictionaryRepository;

  private static final String DICTIONARY_NAME = "kodszotar21_allampolg";

  public boolean validateAllampKod(String allampKod) {
    Optional<AllampolgDictionary> dictionary =
        allampolgDictionaryRepository.findById(DICTIONARY_NAME);
    if (dictionary.isPresent()) {
      AllampolgDictionary allampolgDictionary = dictionary.get();
      for (Allampolg allampolg : allampolgDictionary.getRows()) {
        if (allampKod.equals(allampolg.getKod())) {
          return true;
        }
      }
    }
    return false;
  }

  public String decodeAllampKod(String allampKod) {
    Optional<AllampolgDictionary> dictionary =
        allampolgDictionaryRepository.findById(DICTIONARY_NAME);
    if (dictionary.isPresent()) {
      AllampolgDictionary allampolgDictionary = dictionary.get();
      for (Allampolg allampolg : allampolgDictionary.getRows()) {
        if (allampKod.equals(allampolg.getKod())) {
          return allampolg.getAllampolgarsag();
        }
      }
    }
    return null;
  }
}
