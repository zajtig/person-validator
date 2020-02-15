package hu.idomsoft.common.dao;

import java.util.Collection;

public class AllampolgDictionary extends Dictionary {

  private Collection<Allampolg> rows;

  public Collection<Allampolg> getRows() {
    return rows;
  }

  public void setRows(Collection<Allampolg> rows) {
    this.rows = rows;
  }

  @Override
  public String toString() {
    return "AllampolgDictionary{" + "rows=" + rows + '}';
  }
}
