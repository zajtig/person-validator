package hu.idomsoft.common.dao;

import java.util.Collection;


public class OkmanytipusDictionary extends Dictionary{

    private Collection<Okmanytipus> rows;

    public Collection<Okmanytipus> getRows() {
        return rows;
    }

    public void setRows(Collection<Okmanytipus> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "OkmanytipusDictionary{" +
                "rows=" + rows +
                '}';
    }
}