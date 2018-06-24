package ua.nure.Hladush.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.Hladush.SummaryTask4.Path;
import ua.nure.Hladush.SummaryTask4.db.DBManager;
import ua.nure.Hladush.SummaryTask4.db.entity.Staff;
import ua.nure.Hladush.SummaryTask4.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class ListStaffCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger.getLogger(ListStaffCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		
		// get menu items list
		List<Staff> staffpersons = DBManager.getInstance().findStaff();
		LOG.trace("Found in DB: menuItemsList --> " + staffpersons);
		
		// sort menu by category
		Collections.sort(staffpersons, new Comparator<Staff>() {
			public int compare(Staff o1, Staff o2) {
				return (int)(o1.getRoleId() - o2.getRoleId());
			}
		});		
		
		// put menu items list to the request
		request.setAttribute("staffpersons", staffpersons);
		LOG.trace("Set the request attribute: staffpersons --> " + staffpersons);
		
		LOG.debug("Command finished");
		return Path.PAGE_STAFF;
	}


}