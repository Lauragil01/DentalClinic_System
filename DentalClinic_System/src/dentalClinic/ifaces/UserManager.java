package dentalClinic.ifaces;

import java.util.List;
import dentalClinic.pojos.Role;
import dentalClinic.pojos.User;

public interface UserManager {
	public void disconnect();
	public void newUser(User u);
	public Role getRole(String name);
	public List<Role> getRoles();
	/**
	 * 
	 * @param email
	 * @param passwd
	 * @return A User if there is a match, null if there isn't
	 */
	public User checkPassword(String email, String passwd);

}
