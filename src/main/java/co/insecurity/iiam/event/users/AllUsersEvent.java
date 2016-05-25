package co.insecurity.iiam.event.users;

import co.insecurity.iiam.event.ReadEvent;

import java.util.List;

public class AllUsersEvent extends ReadEvent {
	
	private List<UserInfo> allUserInfo;
	
	public AllUsersEvent(List<UserInfo> allUserInfo) {
		this.allUserInfo = allUserInfo;
	}
	
	public List<UserInfo> getAllUserInfo() {
		return allUserInfo;
	}
}