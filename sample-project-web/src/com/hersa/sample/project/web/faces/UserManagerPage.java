package com.hersa.sample.project.web.faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.Severity;
import javax.servlet.http.HttpSession;

import com.hersa.sample.project.bom.UserManager;
import com.hersa.sample.project.dao.user.User;

@ManagedBean(name="userManagementBean")
@SessionScoped
public class UserManagerPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User sessionUser;
	private User selectedUser;
	private List<User> allUsersList;
	
	private Map<String, String> userRoleMap;
	
	private UserManager um = new UserManager();
	FacesContext context = FacesContext.getCurrentInstance();
	
	@PostConstruct
	public void init() {
		initializeVariables();
		loadUsers();
		generateMaps();
	}
	public void initializeVariables(){
		sessionUser = new User();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		sessionUser = (User) session.getAttribute("User");
		allUsersList = new ArrayList<User>();
		userRoleMap = new HashMap<String, String>();
	}
	public void generateMaps(){
		userRoleMap.put("System Admin", "sysadmin");
		userRoleMap.put("Admin", "admin");
		userRoleMap.put("User", "user");
	}
	public void loadUsers(){
		allUsersList = new ArrayList<User>();
		allUsersList = um.retrieveAllUsers();
	}
	public void resetUser(){
		try {
			um.resetUser(selectedUser);
			loadUsers();
			this.context.addMessage("Info", new FacesMessage(null, "User has been unlocked successfully.", "Success!"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.context.addMessage("Info", new FacesMessage(FacesMessage.SEVERITY_ERROR, "User could not be unlocked.", "Error!"));
		}
	}
	public void updateUser(){
		try {
			um.updateUser(selectedUser);
			loadUsers();
			this.context.addMessage("Info", new FacesMessage(null, "User has been unlocked successfully.", "Success!"));
		} catch (Exception e) {
			this.context.addMessage("Info", new FacesMessage(FacesMessage.SEVERITY_ERROR, "There was an error while deleting this user.", "Error!"));
		}
	}
	public User getSessionUser() {
		return sessionUser;
	}
	
	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}
	public List<User> getAllUsersList() {
		return allUsersList;
	}
	public void setAllUsersList(List<User> allUsersList) {
		this.allUsersList = allUsersList;
	}
	public Map<String, String> getUserRoleMap() {
		return userRoleMap;
	}
	public void setUserRoleMap(Map<String, String> userRoleMap) {
		this.userRoleMap = userRoleMap;
	}
	public User getSelectedUser() {
		return selectedUser;
	}
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

}
