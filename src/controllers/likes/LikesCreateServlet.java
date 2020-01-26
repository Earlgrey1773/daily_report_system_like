package controllers.likes;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class LikesCreateServlet
 */
@WebServlet("/likes")
public class LikesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String _like = (String)request.getParameter("_like");
        if(_like != null && _like.equals("1")) {

            EntityManager em = DBUtil.createEntityManager();

            Like l = new Like();


            l.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
            //getParemeter(report_id)
            //report table em.find
            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));
            l.setReport(r);


            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            l.setCreated_at(currentTime);
            l.setUpdated_at(currentTime);

            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/reports/index");

        }
    }
}
