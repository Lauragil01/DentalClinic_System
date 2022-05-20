package dentalClinic.ifaces;

import java.util.List;
import dentalClinic.pojos.Role;
import dentalClinic.pojos.User;

public interface UserManager {
	public void disconnect();
	public void newUser(User u);
	public Role getRole(int id);
	public List<Role> getRoles();
	public User checkPassword(String username, String password);
	public Role getRoleByName(String name);
	public void deleteUser(User u);
	public User getUser(int id);
	public void updateUser(User u, byte[] password);
	/**
	 * 
	 * @param email
	 * @param passwd
	 * @return A User if there is a match, null if there isn't
	 */

}
