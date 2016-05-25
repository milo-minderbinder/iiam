package co.insecurity.iiam.event.users;

import co.insecurity.iiam.event.UpdateEvent;

public class UpdateUserEvent extends UpdateEvent {
	
	private UserInfo userInfo;
	
	public UpdateUserEvent(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}
}