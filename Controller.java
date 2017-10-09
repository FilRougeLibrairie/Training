package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ControllerMain", urlPatterns = {"/ControllerMain"})
public class ControllerMain extends HttpServlet {

    private Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String section = request.getParameter("section");
        String page = "/WEB-INF/home.jsp";
        /////// DO NOT MODIFY ABOVE THIS LINE ///////

        /**
         *
         *
         * HERE CODE FOR EACH SECTION
         *
         *
         *
         */
        if ("menu-main".equals(section)) {
            page = "/WEB-INF/includes/menu-main.jsp";
			 for (int i = 0; i < 100; i++) {
                try {
                    Connection cnt = ds.getConnection();
                    String requete ="select s.Pays from iso3166 s where s.A2 ='AD' ";
                    Statement stm = cnt.createStatement();
                    ResultSet rs = stm.executeQuery(requete);
                    if(rs.next()){
                        String pays = rs.getString("Pays");
                        out.println(i+") "+pays+"<br />");
                    }
                } catch (SQLException ex) {
                    System.out.println("====> ERREUR 02 <=====" + ex.getMessage());
                    ex.printStackTrace();
                }
        }

		// Ajout G
        if ("catalog".equals(section)) {
            page = "/WEB-INF/catalog.jsp";
			if (!InputsControls.isCreditCardOk(tfPayCardNumber.getText())) {
                jOptionPane.showMessageDialog(null, "Le numéro de carte bancaire est erroné (entre 15 et 19 chiffres)", "Information", JOptionPane.WARNING_MESSAGE);
            } else if (!InputsControls.isCreditCardSecurityNumberOk(tfPaySecurityNumber.getText())) {
                jOptionPane.showMessageDialog(null, "Le numéro de sécurité est erroné", "Information", JOptionPane.WARNING_MESSAGE);
            } else if (creditCardExpirationDate == null || creditCardExpirationDate.before(today)) {
                jOptionPane.showMessageDialog(null, "La carte bancaire est expirée", "Information", JOptionPane.WARNING_MESSAGE);

            } else {

                int bankValidation = jOptionPane.showConfirmDialog(null, "Bonjour la Banque, validez-vous la transaction ? ", "Validation Banque",
                        jOptionPane.YES_NO_OPTION, jOptionPane.INFORMATION_MESSAGE);
                if (bankValidation == JOptionPane.NO_OPTION || bankValidation == JOptionPane.CANCEL_OPTION || bankValidation == JOptionPane.CLOSED_OPTION) {
                    jOptionPane.showMessageDialog(null, "La transaction n'a pas été acceptée\nLa commande n'est pas transmise", "Information", JOptionPane.WARNING_MESSAGE);
                } else if (bankValidation == JOptionPane.YES_OPTION) {
                    currentPurchaseStatusCode = ORDER_STATUS_PAYED;
                    java.util.Date date = new Date();
                    Object purchaseDate = new java.sql.Timestamp(date.getTime());
                    currentPurchase.setShippingDate(purchaseDate.toString());

                    Payment payment = new Payment();
                    payment.setPayChoice(PAYEMENT_TYPE_CB);
                    payment.setPayValidate(true);
                    payment.setPurId(currentPurchase);
                    payment.setPayDate(date.toString());
                    payment.setPayCardType(comboPayCardType.getSelectedItem().toString());
                    if (!tfPayOwner.getText().isEmpty()) {
                        payment.setPayOwnerName(tfPayOwner.getText());
                    }
                    

        }
		
		

        /////// DO NOT MODIFY BELOW THIS LINE ///////
        System.out.println("--------->>> page : " + page); // DEBUG : recursive calling if displayed twice
        page = response.encodeURL(page);
        getServletContext().getRequestDispatcher(page).include(request, response);
		
		
		
		
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
