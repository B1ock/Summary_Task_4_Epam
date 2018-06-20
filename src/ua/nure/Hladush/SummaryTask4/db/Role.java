package ua.nure.Hladush.SummaryTask4.db;


import ua.nure.Hladush.SummaryTask4.db.entity.User;



public enum Role {
    ADMIN, DISPATCHER, USER ;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}