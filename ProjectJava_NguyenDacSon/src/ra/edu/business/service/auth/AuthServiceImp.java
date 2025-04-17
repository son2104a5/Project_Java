package ra.edu.business.service.auth;

import ra.edu.business.dao.auth.AuthDAO;
import ra.edu.business.dao.auth.AuthDAOImp;
import ra.edu.business.model.Admin;

public class AuthServiceImp implements AuthService {
    private AuthDAO authDAO = new AuthDAOImp();

    @Override
    public boolean login(String username, String password) {
        Admin admin = authDAO.getByUsernameAndPassword(username, password);
        return admin != null;
    }
}
