package com.ulrich.matthiae.spring.clouddemo.flight.model;

public enum Location {
    JOHANNESBURG(com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Location.JOHANNESBURG),
    DURBAN(com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Location.DURBAN),
    CAPE_TOWN(com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Location.CAPE_TOWN),
    GEORGE(com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Location.GEORGE),
    PORT_ELIZABETH(com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Location.PORT_ELIZABETH);

    private com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Location costLocation;

    Location(com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Location costLocation) {
        this.costLocation = costLocation;
    }

    public com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Location getCostLocation() {
        return costLocation;
    }
}
