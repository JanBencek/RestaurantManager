package bencit.com.restaurantmanager.entities;

import bencit.com.restaurantmanager.enums.TableLocations;
import bencit.com.restaurantmanager.enums.TableTypes;

public class Table {
    private Integer id;
    private TableTypes type;
    private Integer numberOfSeats;
    private Boolean tableAvailable;
    private TableLocations tableLocation;

    public Table(Integer id, TableTypes type, Integer numberOfSeats, Boolean tableAvailable, TableLocations tableLocation) {
        this.id = id;
        this.type = type;
        this.numberOfSeats = numberOfSeats;
        this.tableAvailable = tableAvailable;
        this.tableLocation = tableLocation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TableTypes getType() {
        return type;
    }

    public void setType(TableTypes type) {
        this.type = type;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Boolean getTableAvailable() {
        return tableAvailable;
    }

    public void setTableAvailable(Boolean tableAvailable) {
        this.tableAvailable = tableAvailable;
    }

    public TableLocations getTableLocation() {
        return tableLocation;
    }

    public void setTableLocation(TableLocations tableLocation) {
        this.tableLocation = tableLocation;
    }
}
