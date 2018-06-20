package ua.nure.Hladush.SummaryTask4.db.bean;



public class UserFlightBrigadeBean {

    private static final long serialVersionUID = -5654982557199337483L;

    private long flightId;

    private String flightNum;

    private int brigadeId;

    private String statusFlight;

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public void setBrigadeId(int brigadeId) {
        this.brigadeId = brigadeId;
    }

    public void setStatusFlight(String statusFlight) {
        this.statusFlight = statusFlight;
    }


    public long getFlightId() {
        return flightId;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public int getBrigadeId() {
        return brigadeId;
    }

    public String getStatusFlight() {
        return statusFlight;
    }


}
