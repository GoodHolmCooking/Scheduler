package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DivisionList {
    private ObservableList<Division> divisions = FXCollections.observableArrayList();

    /**
     * adds a division to the list.
     * @param division the division to add.
     */
    public void addDivision(Division division){
        divisions.add(division);
    }

    /**
     * gets a divison id based on the divison's name.
     * @param desiredName the name of the division to find.
     * @return the id of the division.
     */
    public int getID(String desiredName) {
        int desiredId = 0;
        for (Division division : divisions) {
            if (division.getName().equals(desiredName)) {
                desiredId = division.getDiv_id();
            }
        }

        return desiredId;
    }

    /**
     * Retrieves divisions based off of a country id.
     * @param id the id of the country whose divisions need to be fetched.
     * @return a list of divisions found for a specified country id.
     */
    public ObservableList<String> getDivisionNames(int id) {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (Division division : divisions) {
            if (division.getCountry_id() == id) {
                String name = division.getName();
                divisionNames.add(name);
            }
        }

        return divisionNames;
    }

    /**
     * finds the index a division based on a supplied id.
     * @param id the id of the division to find.
     * @return the index of the desired division.
     */
    public int getIndex(int id) {
        int desiredIndex = 0;
        int loopIndex = 0;

        for (Division division : divisions) {
            if (division.getDiv_id() == id) {
                desiredIndex = loopIndex;
                break;
            }
            else {
                loopIndex += 1;
            }
        }

        return desiredIndex;
    }
}
