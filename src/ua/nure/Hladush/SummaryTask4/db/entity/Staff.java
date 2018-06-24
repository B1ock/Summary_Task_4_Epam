package ua.nure.Hladush.SummaryTask4.db.entity;




public class Staff extends Entity {

    private String firstname;

    private String lastname;

    private long roleId;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setRoleId(long role) {
        this.roleId = role;
    }
}
