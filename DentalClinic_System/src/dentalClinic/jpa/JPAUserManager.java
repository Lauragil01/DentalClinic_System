package dentalClinic.jpa;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dentalClinic.ifaces.UserManager;
import dentalClinic.pojos.Role;
import dentalClinic.pojos.User;

public class JPAUserManager implements UserManager{
	private EntityManager em;
	
	
	public JPAUserManager() {
		this.connect();
	}
	
	private void connect() {
		em = Persistence.createEntityManagerFactory("dentalClinic-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		// Insert the roles needed only if they are not there already
		if (this.getRoles().isEmpty()) {
			Role patient = new Role("patient");
			Role dentist = new Role("dentist");
			this.newRole(patient);
			this.newRole(dentist);
		}
	}
	@Override
	public void disconnect() {
		em.close();
	}

	@Override
	public void newUser(User u) {
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
	}
	
	private void newRole(Role r) {
		em.getTransaction().begin();
		em.persist(r);
		em.getTransaction().commit();
	}

	@Override
	public Role getRole(int id) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE id = ?", Role.class);
		q.setParameter(1, id);
		return (Role) q.getSingleResult();
	}
	
	@Override
	public List<Role> getRoles() {
		Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
		List<Role> roles = q.getResultList();
		return roles;
	}
	
	@Override
	public User checkEmail(String email) {	
		User u = null;
		Query q = em.createNativeQuery("SELECT * FROM users WHERE email = ?", User.class);
		q.setParameter(1, email);
		try {
			u = (User) q.getSingleResult();
		}
		catch (NoResultException e) {
			u = null;
		}
		return u;
	}

	@Override
	public User checkPassword(String email, String passwd) {
		User u = null;
		Query q = em.createNativeQuery("SELECT * FROM users WHERE email = ? AND password = ?", User.class);
		q.setParameter(1, email);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(passwd.getBytes());
			byte[] digest = md.digest();
			q.setParameter(2, digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			u = (User) q.getSingleResult();
		} catch (NoResultException e) {}
		return u;
	}
	
	@Override
	public Role getRoleByName(String name) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE ROLE = ?", Role.class);
		q.setParameter(1, name);
		Role role = (Role) q.getSingleResult();
		return role;
	}

	@Override
	public void deleteUser(User u) {
		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
		
	}

	@Override
	public void updateUser(User u, byte[] password) {
		Query q = em.createNativeQuery("SELECT * FROM users WHERE id = ?", User.class);
		q.setParameter(1, u.getId());
		User userToUpdate = (User) q.getSingleResult();
		em.getTransaction().begin();
		userToUpdate.setEmail(u.getEmail());
		userToUpdate.setPassword(password);
		userToUpdate.setRole(u.getRole());
		em.getTransaction().commit();
		
	}

	@Override
	public User getUser(int id) {
		Query q = em.createNativeQuery("SELECT * FROM users WHERE id = ?", User.class);
		q.setParameter(1, id);
		return (User) q.getSingleResult();
	}

	

}
