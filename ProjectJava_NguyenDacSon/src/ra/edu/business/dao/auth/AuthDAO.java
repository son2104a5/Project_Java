package ra.edu.business.dao.auth;

import ra.edu.business.model.Admin;

public interface AuthDAO {
    Admin getByUsernameAndPassword(String username, String password);
}
