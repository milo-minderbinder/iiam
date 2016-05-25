package co.insecurity.iiam.event.users;

import co.insecurity.iiam.event.CreateEvent;

public class CreateUserEvent extends CreateEvent {
	
	UserInfo userInfo;
	
	public CreateUserEvent(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
}