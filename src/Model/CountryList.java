package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CountryList {
    private ObservableList<Country> countries = FXCollections.observableArrayList();

    public void addCountry(Country country) {
        countries.add(country);
    }

    public String getCountry(int id) {
        String foundCountry = null;

        for (Country country : countries) {
            if (country.getId() == id) {
                foundCountry = country.getName();
                break;
            }
        }

        return foundCountry;
    }

    public int getId(String countryToFind) {
        int foundId = 0;
        for (Country country : countries) {
            if (country.getName().equals(countryToFind)) {
                foundId = country.getId();
            }
        }

        return foundId;
    }


    public ObservableList<String> getCountryList() {
        ObservableList<String> countryNames = FXCollections.observableArrayList();

        for (Country country : countries) {
            String name = country.getName();
            countryNames.add(name);
        }

        return countryNames;
    }
}
