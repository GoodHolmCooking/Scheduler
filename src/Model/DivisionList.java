package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DivisionList {
    private ObservableList<Division> divisions = FXCollections.observableArrayList();

    public void addDivision(Division division){
        divisions.add(division);
    }

    public Division fetchDivision(String desiredName) {
        Division desiredDivision = null;
        for (Division division : divisions) {
            if (division.getName().equals(desiredName)) {
                desiredDivision = division;
            }
        }
        return desiredDivision;
    }

    public int getID(String desiredName) {
        int desiredId = 0;
        for (Division division : divisions) {
            if (division.getName().equals(desiredName)) {
                desiredId = division.getDiv_id();
            }
        }

        return desiredId;
    }

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
}
