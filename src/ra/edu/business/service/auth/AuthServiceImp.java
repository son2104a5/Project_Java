package ra.edu.business.service.auth;

import ra.edu.business.dao.auth.AuthDAO;
import ra.edu.business.dao.auth.AuthDAOImp;
import ra.edu.business.model.Admin;

public class AuthServiceImp implements AuthService {
    public static Admin admin;
    private final AuthDAO authDAO = new AuthDAOImp();

    @Override
    public boolean login(String username, String password) {
        admin = authDAO.login(username, password);
        return admin != null;
    }
}
