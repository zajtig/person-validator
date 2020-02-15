package hu.idomsoft.common.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dictionary")
public abstract class Dictionary {

  @Id private String dictname;

  public String getDictname() {
    return dictname;
  }

  public void setDictname(String dictname) {
    this.dictname = dictname;
  }

  @Override
  public String toString() {
    return "Dictionary{" + "dictname='" + dictname + '\'' + '}';
  }
}
