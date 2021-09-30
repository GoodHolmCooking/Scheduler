package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DivisionList {
    private ObservableList<Division> divisions = FXCollections.observableArrayList();

    public void addDivision(Division division){
        divisions.add(division);
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
