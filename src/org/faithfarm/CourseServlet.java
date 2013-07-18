/*     */package org.faithfarm;

/*     */
/*     *//*     */import java.io.IOException;
/*     */
import java.util.ArrayList;
/*     */
import java.util.Date;
import java.util.Iterator;
/*     */
import java.util.logging.Logger;

/*     */
import javax.servlet.ServletException;
/*     */
import javax.servlet.http.HttpServletRequest;
/*     */
import javax.servlet.http.HttpServletResponse;

/*     */
import org.faithfarm.dataobjects.SearchParameter;
/*     */
import org.faithfarm.datawriters.ApplicationDao;
/*     */
import org.faithfarm.datawriters.CourseDao;
/*     */
import org.faithfarm.datawriters.PersonDao;
/*     */
import org.faithfarm.utilities.HTMLBuilder;
/*     */
import org.faithfarm.utilities.XMLParser;
/*     */
import org.w3c.dom.NodeList;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
/*     */
import com.google.appengine.api.users.User;
/*     */
import com.google.appengine.api.users.UserService;
/*     */
import com.google.appengine.api.users.UserServiceFactory;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */public class CourseServlet extends BaseServlet
/*     */{
	/* 28 */private static final Logger logger = Logger
			.getLogger(CustomerServlet.class.getCanonicalName());
	/* 29 */private static final HTMLBuilder html = new HTMLBuilder();
	/* 30 */private static final XMLParser xml = new XMLParser();
	/* 31 */private static final CourseDao dao = new CourseDao();
	/* 32 */private static final PersonDao pDao = new PersonDao();

	/*     */
	/*     */protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	/*     */{
		/* 36 */super.doGet(req, resp);
		/*     */
		/* 38 */UserService userService = UserServiceFactory.getUserService();
		/* 39 */User user = userService.getCurrentUser();
		/*     */
		/* 44 */String url = "/jsp/course/results.jsp";
		/* 45 */String action = (String) req.getAttribute("action");
		/* 46 */String entity = (String) req.getAttribute("entity");
		/*     */
		/* 48 */if (action == null) {
			/* 49 */action = req.getParameter("action");
			/*     */}
		/* 51 */if ("Search".equals(action)) {
			/* 52 */String title = req.getParameter("title");
			/* 53 */String number = req.getParameter("number");
			/* 54 */String description = req.getParameter("description");
			/* 55 */String farmBase = req.getParameter("farmBase");
			/* 56 */String instructor = req.getParameter("instructor");
			/* 57 */String status = req.getParameter("status");
			/* 58 */String sequence = req.getParameter("sequence");
			/* 59 */String assistantInstructor = req
					.getParameter("assistantInstructor");
			/*     */
			/* 61 */if ((farmBase == null) || ("".equals(farmBase))) {
				/* 62 */farmBase = (String) req.getSession().getAttribute(
						"farmBase");
				/*     */}
			/* 64 */Iterable resultsList = CourseDao.getCourses(title, number,
					description, instructor, assistantInstructor, status,
					sequence, farmBase);
			/* 65 */req.setAttribute("size",
					Integer.valueOf(CourseDao.getSize(resultsList)));
			/* 66 */req.getSession().setAttribute("results", resultsList);
			/*     */
			/* 68 */int index = 0;
			/* 69 */// for (Entity Course : results) {
			for (Iterator i = resultsList.iterator(); i.hasNext();) {
				Entity Course = (Entity) i.next();

				/* 70 */Iterable roster = PersonDao.getAllPersons(null, null,
						null, "Resident", "Active", null, (String) Course
								.getProperty("sequence"), (String) req
								.getSession().getAttribute("farmBase"));
				/* 71 */int count = 0;
				/* 72 */// for (Entity Person : roster)
				for (Iterator it = roster.iterator(); it.hasNext();) {
					Entity Person = (Entity) it.next();

					/* 73 */count++;
					/* 74 */req.setAttribute("enrollment_" + index, count);
					/* 75 */index++;
					/*     */}
				/* 77 */url = "/jsp/course/results.jsp";
				/*     */}
			/* 79 */if ("View".equals(action)) {
				/* 80 */String id = req.getParameter("id");
				/* 81 */Entity Course = CourseDao.getCourse(id);
				/* 82 */NodeList xmlCourse = xml.getEntityProperties("Course",
						req);
				/*     */
				/* 84 */req.setAttribute("Course", Course);
				/* 85 */req.getSession().setAttribute("xmlCourse", xmlCourse);
				/*     */
				/* 87 */url = "/jsp/course/view.jsp";
				/*     */}
			/* 89 */if ("Add".equals(action)) {
				/* 90 */NodeList xmlCourse = xml.getEntityProperties("Course",
						req);
				/* 91 */Entity Course = xml.xmlToEntity("Course", req);
				/* 92 */req.setAttribute("Course", Course);
				/* 93 */req.setAttribute("xmlCourse", xmlCourse);
				/* 94 */url = "/jsp/course/add.jsp";
				/*     */}
			/* 96 */if ("Save Changes".equals(action)) {
				/* 97 */String id = req.getParameter("id");
				/* 98 */Entity Course = CourseDao.getCourse(id);
				/* 99 */dao.createUpdateEntity(Course, user, req);
				/* 100 */Iterable results = CourseDao.getCourses(null, null,
						null, null, null, null, null, null);
				/* 101 */req.setAttribute("size",
						Integer.valueOf(CourseDao.getSize(results)));
				/* 102 */req.getSession().setAttribute("results", results);
				/* 103 */url = "/jsp/course/results.jsp";
				/*     */}
			/* 105 */if ("Save".equals(action)) {
				/* 106 */Entity Course = new Entity("Course");
				/* 107 */Course.setProperty("createdBy", user.getEmail());
				/* 108 */Course.setProperty("creationDate", new Date());
				/* 109 */Course.setProperty("status", "Active");
				/* 110 */ArrayList roster = new ArrayList();
				/* 111 */Course.setProperty("roster", roster);
				/* 112 */Course.setProperty("roster_next", roster);
				/* 113 */dao.createUpdateEntity(Course, user, req);
				/* 114 */req.setAttribute("Course", Course);
				/* 115 */Iterable results = CourseDao.getCourses(null, null,
						null, null, null, null, null, null);
				/* 116 */req.setAttribute("size",
						Integer.valueOf(CourseDao.getSize(results)));
				/* 117 */req.getSession().setAttribute("results", results);
				/*     */
				/* 119 */url = "/jsp/course/results.jsp";
				/*     */}
			/* 121 */if ("Edit".equals(action)) {
				/* 122 */String id = req.getParameter("id");
				/* 123 */Entity Course = CourseDao.getCourse(id);
				/* 124 */NodeList xmlCourse = xml.getEntityProperties("Course",
						req);
				/* 125 */req.setAttribute("xmlCourse", xmlCourse);
				/* 126 */req.setAttribute("Course", Course);
				/* 127 */url = "/jsp/course/add.jsp?action=edit";
				/*     */}
			/* 129 */if ("Delete".equals(action)) {
				/* 130 */String id = req.getParameter("id");
				/* 131 */Entity Course = CourseDao.getCourse(id);
				/* 132 */CourseDao.deleteCourse(Course);
				/* 133 */Iterable results = CourseDao.getCourses(null, null,
						null, null, null, null, null, null);
				/* 134 */req.setAttribute("size",
						Integer.valueOf(CourseDao.getSize(results)));
				/* 135 */req.getSession().setAttribute("results", results);
				/* 136 */url = "/jsp/course/results.jsp";
				/*     */}
			/*     */Iterable results;
			/* 138 */if ("Roster".equals(action)) {
				/* 139 */String seq = req.getParameter("seq");
				/*     */
				/* 142 */farmBase = (String) req.getSession().getAttribute(
						"farmBase");
				/* 143 */if (farmBase == null)
					farmBase = "Fort Lauderdale";
				/*     */
				/* 145 */Entity Course = CourseDao.getCourse(null, null, null,
						null, null, null, seq, farmBase);
				/* 146 */req.setAttribute("course", Course);
				/* 147 */results = PersonDao.getAllPersons(null, null, null,
						"Resident", "Active", null, seq, farmBase);
				/* 148 */req.getSession().setAttribute("results", results);
				/* 149 */url = "/jsp/course/roster.jsp";
				/*     */}
			/* 151 */if ("MasterRotate".equals(action))
			/*     */{
				/* 153 */Iterable list = PersonDao.getAllPersons(null, null,
						null, "Resident", "Active", null, null, (String) req
								.getSession().getAttribute("farmBase"));
				/* 154 */int i = 0;
				/*     */
				/* 156 */// for (Entity Person : list)
				for (Iterator it = list.iterator(); it.hasNext();) {
					Entity Person = (Entity) it.next();

					/*     */{
						/* 158 */String id = new String("id_"
								+ Person.getKey().getId());
						/* 159 */if ("Y".equals(req.getParameter(id))) {
							/* 160 */String sSeq = (String) Person
									.getProperty("currentCourse");
							/* 161 */if (sSeq != null) {
								/* 162 */int seq = Integer.parseInt(sSeq);
								/* 163 */if (seq == 6) {
									/* 164 */Person.setProperty(
											"currentCourse", "");
									/* 165 */Person.setProperty("personType",
											"Graduate");
									/*     */}
								/* 167 */if (seq < 6) {
									/* 168 */seq++;
									/* 169 */Person.setProperty(
											"currentCourse", seq);
									/*     */}
								/*     */
								/* 173 */PersonDao.createPerson(Person);
								/*     */}
							/*     */}
						/* 176 */i++;
						/*     */}
					/*     */
					/* 180 */ArrayList parameters = new ArrayList();
					/* 181 */parameters.add(new SearchParameter("personStatus",
							"Active", Query.FilterOperator.EQUAL));
					/* 182 */parameters.add(new SearchParameter("personType",
							"Resident", Query.FilterOperator.EQUAL));
					/* 183 *///ArrayList results = new ArrayList();
					/* 184 */results = ApplicationDao.getEntities("Person",
							parameters, "currentCourse", null, (String) req
									.getSession().getAttribute("farmBase"));
					/*     */
					/* 187 */req.getSession().setAttribute("results", results);
					/*     */
					/* 189 */url = "/jsp/course/rotate.jsp";
					/*     */}
				/*     */
				/* 192 */if ("Rotate".equals(action)) {
					/* 193 */ArrayList parameters = new ArrayList();
					/* 194 */parameters.add(new SearchParameter("personStatus",
							"Active", Query.FilterOperator.EQUAL));
					/* 195 */parameters.add(new SearchParameter("personType",
							"Resident", Query.FilterOperator.EQUAL));
					/* 196 *///ArrayList results = new ArrayList();
					/* 197 */results = ApplicationDao.getEntities("Person",
							parameters, "currentCourse", null, (String) req
									.getSession().getAttribute("farmBase"));
					/*     */
					/* 200 */req.getSession().setAttribute("results", results);
					/* 201 */url = "/jsp/course/rotate.jsp";
					/*     */}
				/* 203 */if ("OldRotate".equals(action)) {
					/* 204 */String id = req.getParameter("courseId");
					/* 205 */Entity Course = CourseDao.getCourse(id);
					/* 206 */int studentCount = Integer.parseInt(req
							.getParameter("studentCount"));
					/* 207 */ArrayList newStudentList = new ArrayList();
					/* 208 */ArrayList oldStudentList = (ArrayList) Course
							.getProperty("roster");
					/* 209 */ArrayList repStudentList = new ArrayList();
					/*     */
					/* 211 */boolean match = false;
					/* 212 */for (int h = 0; h < oldStudentList.size(); h++) {
						/* 213 */String studentId = (String) oldStudentList
								.get(h);
						/* 214 */System.out.println("checking..." + studentId);
						/* 215 */System.out.println("studentCount..."
								+ studentCount);
						/* 216 */for (i = 0; i < studentCount; i++) {
							/* 217 */String value = req.getParameter("student_"
									+ i);
							/* 218 */System.out.println("..value=" + value);
							/* 219 */if (studentId.equals(value)) {
								/* 220 */newStudentList.add(value);
								/* 221 */match = true;
								/*     */}
							/*     */}
						/*     */
						/* 225 */if (!match) {
							/* 226 */repStudentList.add(studentId);
							/*     */}
						/* 228 */match = false;
						/*     */}
					/* 230 */Course.setProperty("roster", repStudentList);
					/* 231 */Course.setProperty("roster_next", newStudentList);
					/* 232 */CourseDao.createCourse(Course);
					/*     */
					/* 234 */url = "/jsp/course/index.jsp";
					/*     */}
				/*     */
				/* 237 */req.getRequestDispatcher(req.getContextPath() + url)
						.forward(req, resp);
			}
		}
		/*     */}

	/*     */
	/*     */protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	/*     */{
		/* 242 */doGet(req, resp);
		/*     */}
	/*     */
}

/*
 * Location: C:\development\workspace\faithfarmoss\WEB-INF\classes\ Qualified
 * Name: org.faithfarm.CourseServlet JD-Core Version: 0.6.2
 */