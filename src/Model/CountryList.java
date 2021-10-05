package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CountryList {
    private ObservableList<Country> countries = FXCollections.observableArrayList();

    /**
     *
     * @param country the country to add.
     */
    public void addCountry(Country country) {
        countries.add(country);
    }

    /**
     *
     * @param id the id of the country whose index needs to be fetched.
     * @return the index of the desired country.
     */
    public int getIndex(int id) {
        int desiredIndex = 0;
        int loopIndex = 0;

        for (Country country : countries) {
            if (country.getId() == id) {
                desiredIndex = loopIndex;
                break;
            }
            else {
                loopIndex += 1;
            }
        }

        return desiredIndex;
    }

    /**
     * Finds the country's id based on the name.
     * @param countryToFind country whose id needs to be found.
     * @return the id of the searched country.
     */
    public int getId(String countryToFind) {
        int foundId = 0;
        for (Country country : countries) {
            if (country.getName().equals(countryToFind)) {
                foundId = country.getId();
            }
        }

        return foundId;
    }


    /**
     *
     * @return a list of of country names.
     */
    public ObservableList<String> getCountryList() {
        ObservableList<String> countryNames = FXCollections.observableArrayList();

        for (Country country : countries) {
            String name = country.getName();
            countryNames.add(name);
        }

        return countryNames;
    }
}
