package ua.nure.Hladush.SummaryTask4.db;

import ua.nure.Hladush.SummaryTask4.db.entity.Flight;
import ua.nure.Hladush.SummaryTask4.db.entity.RoleInFlight;
import ua.nure.Hladush.SummaryTask4.db.entity.Staff;
import ua.nure.Hladush.SummaryTask4.db.entity.User;
import ua.nure.Hladush.SummaryTask4.exception.DBException;
import ua.nure.Hladush.SummaryTask4.exception.Messages;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

//import org.apache.log4j.Logger;




public class DBManager {
//    private static final Logger LOG = Logger.getLogger(DBManager.class);

    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // ST4DB - the name of data source
            ds = (DataSource) envContext.lookup("jdbc/AIRLINE");
//            LOG.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
//            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    private DataSource ds;

    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

    private static final String SQL_FIND_ALL_FLIGHTS = "SELECT * FROM flights";

    private static final String SQL_FIND_ALL_STAFF = "SELECT * FROM staff";

    private static final String SQL_FIND_ALL_ROLES_IN_FLIGHT = "SELECT * FROM roles_in_flight";

    //private static final String SQL_ADD_FLIGHT = "INSERT INTO flights (number, name, whence, destination, departure_date, status) VALUES(";

    private static final String SQL_ADD_FLIGHT = "INSERT INTO flights (number, name, whence, destination, departure_date, status) VALUES( (?), (?), (?), (?), (?), 0 )";

    public Connection getConnection() throws DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
//            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    public boolean  addFlight(int number, String whence, String destination, Date departure_date) throws DBException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        String name = whence + "-" + destination;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_ADD_FLIGHT);
            //StringBuffer SQL = new StringBuffer();
            /*
            SQL.append(SQL_ADD_FLIGHT + number + ", ")
                    .append( name + ", ")
                            .append( whence + ", ")
                                    .append( destination + ", ")
                                            .append(departure_date + ", ")
                                                    .append(+ 0 + ");");
            */
            stmt.setInt(1, number);
            stmt.setString(2, name);
            stmt.setString( 3, whence);
            stmt.setString( 4, destination);
           // stmt.setDate(5, new SimpleDateFormat("yyyy-MM-dd").format(departure_date));
                stmt.setDate(5, new java.sql.Date(departure_date.getTime()));
             stmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, ex);
        } finally {
            close(con, stmt, rs);
        }
        return true;
    }

    public List<Flight> findFlights() throws DBException {
        List<Flight> flightsList = new ArrayList<Flight>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_FLIGHTS);
            while (rs.next()) {
                flightsList.add(extractFlight(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FLIGHTS, ex);
        } finally {
            close(con, stmt, rs);
        }
        return flightsList;
    }

    public User findUserByLogin(String login) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    public List<Staff> findStaff() throws DBException {
        List<Staff> staffpersonsList = new ArrayList<Staff>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_STAFF);
            while (rs.next()) {
                staffpersonsList.add(extractStaff(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
//            LOG.error(Messages.ERR_CANNOT_OBTAIN_STAFF, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_STAFF, ex);
        } finally {
            close(con, stmt, rs);
        }
        return staffpersonsList;
    }

    public List<RoleInFlight> findRolesInFlight() throws DBException {
        List<RoleInFlight> rolesList = new ArrayList<RoleInFlight>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_ROLES_IN_FLIGHT);
            while (rs.next()) {
                rolesList.add(extractRoleInFlight(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
//            LOG.error(Messages.ERR_CANNOT_ROLES_IN_FLIGHT, ex);
            throw new DBException(Messages.ERR_CANNOT_ROLES_IN_FLIGHT, ex);
        } finally {
            close(con, stmt, rs);
        }
        return rolesList;
    }

    private RoleInFlight extractRoleInFlight(ResultSet rs) throws SQLException {
        RoleInFlight roleinflight = new RoleInFlight();
        roleinflight.setId(rs.getLong(Fields.ENTITY_ID));
        roleinflight.setName(rs.getString(Fields.ROLE_IN_FLIGHT_NAME));
        return roleinflight;
    }

    private Staff extractStaff(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setId(rs.getLong(Fields.ENTITY_ID));
        staff.setFirstname(rs.getString(Fields.STAFF_FIRST_NAME));
        staff.setLastname(rs.getString(Fields.STAFF_LAST_NAME));
        staff.setRoleId(rs.getLong(Fields.STAFF_ROLE_ID));
        return staff;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        return user;
    }

    private Flight extractFlight(ResultSet rs) throws SQLException {
        Flight flight = new Flight();
        flight.setId(rs.getLong(Fields.ENTITY_ID));
        flight.setName(rs.getString(Fields.FLIGHT_NAME));
        flight.setNumber(rs.getLong(Fields.FLIGHT_NUMBER));
        flight.setWhence(rs.getString(Fields.FLIGHT_WHENCE));
        flight.setWhere(rs.getString(Fields.FLIGHT_WHERE));
        flight.setDepartureDate(rs.getString(Fields.FLIGHT_DEPARTURE_DATE));
        flight.setStatus(rs.getString(Fields.FLIGHT_STATUS));
        return flight;
    }



    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
//                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }



    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
//                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
//                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
//                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    private static <T> void printList(List<T> list) {
        for (T element : list) {
            System.out.println(element);
        }
    }
/*
    public static void main(String[] args) {
        try {
            DBManager dbManager = DBManager.getInstance();
            printList(dbManager.findFlights());

        } catch (DBException e) {
            e.printStackTrace();
        }
    }
*/
}
