package co.insecurity.iiam.event.users;

import co.insecurity.iiam.event.DeleteEvent;


public class DeleteUserEvent extends DeleteEvent {
	
	UserInfo userInfo;
	
	public DeleteUserEvent(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}
}