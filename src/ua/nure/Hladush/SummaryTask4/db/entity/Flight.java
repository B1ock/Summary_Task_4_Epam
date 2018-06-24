package ua.nure.Hladush.SummaryTask4.db.entity;




public class Flight extends Entity {

    private String name;

    private long number;

    private String whence;

    private String where;

    private String departuredate;

    private String status;

    public String getName() {
        return name;
    }

    public long getNumber() {
        return number;
    }

    public String getWhence() {
        return whence;
    }

    public String getWhere() {
        return where;
    }

    public String getDepartureDate() {
        return departuredate;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setWhence(String whence) {
        this.whence = whence;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public void setDepartureDate(String departuredate) {
        this.departuredate = departuredate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
