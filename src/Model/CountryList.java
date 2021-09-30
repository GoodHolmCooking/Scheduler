package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class CountryList {
    HashMap<Integer, String> countries = new HashMap<Integer, String>();

    public void addCountry(int id, String country) {
        countries.put(id, country);
    }

    public String getCountry(int id) {
        return countries.get(id);
    }

    public ObservableList<String> getCountryList() {
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        countryNames.addAll(countries.values());
        return countryNames;
    }
}
