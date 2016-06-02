package co.insecurity.iiam.persistence.service;


import co.insecurity.iiam.model.UserRole;
import co.insecurity.iiam.event.users.*;
import co.insecurity.iiam.persistence.domain.User;
import co.insecurity.iiam.persistence.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


public class UserPersistenceEventHandler implements UserPersistenceService {

	private static final Logger LOG = 
			LoggerFactory.getLogger(UserPersistenceEventHandler.class);

	private final UserRepository userRepository;
	
	public UserPersistenceEventHandler(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserCreatedEvent createUser(CreateUserEvent createUserEvent) {
		UserInfo userInfo = createUserEvent.getUserInfo();
		if(userRepository.findByUsername(userInfo.getUsername()) != null) {
			LOG.info("Failed to create user: user with username '{}' already exists", 
					userInfo.getUsername());
			return UserCreatedEvent.usernameAlreadyExists(userInfo);
		}
		else {
			User user = User.fromUserInfo(userInfo);
			user.setEnabled(true);
			if (!user.getRoles().contains(UserRole.USER)) 
				user.addRole(UserRole.USER);
			LOG.info("Created user with username '{}'", user.getUsername());
			return new UserCreatedEvent(userRepository.save(user).toUserInfo());
		}
	}

	@Override
	public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent) {
		UserInfo userInfo = deleteUserEvent.getUserInfo();
		User user = userRepository.findByUsername(userInfo.getUsername());
		if(user == null) {
			LOG.info("Failed to delete user: user with username '{}' does not exist",
					userInfo.getUsername());
			return UserDeletedEvent.notFound(userInfo);
		} else {
			LOG.info("Deleting user with username '{}'", userInfo.getUsername());
			userRepository.delete(user);
			return new UserDeletedEvent(userInfo);
		}
	}
	
	@Override
	public UserUpdatedEvent updateUser(UpdateUserEvent updateUserEvent) {
		UserInfo userInfo = updateUserEvent.getUserInfo();
		User user = userRepository.findByUsername(userInfo.getUsername());
		if (user == null) {
			LOG.info("Failed to update user: user with username '{}' does not exist",
					userInfo.getUsername());
			return UserUpdatedEvent.notFound(userInfo);
		} else {
			LOG.info("Updating user with username '{}'", userInfo.getUsername());
			user = userRepository.save(User.fromUserInfo(userInfo));
			return new UserUpdatedEvent(user.toUserInfo());
		}
	}

	@Override
	@Transactional(readOnly=true)
	public UserInfoEvent requestUserInfo(
			RequestUserInfoEvent requestUserInfoEvent) {
		String username = requestUserInfoEvent.getUsername();
		User user = userRepository.findOne(username);
		if (user == null) {
			LOG.debug("Could not find user with username: {}", username);
			return UserInfoEvent.notFound(username);
		}
		else {
			LOG.debug("Found user: {}", user.toString());
			return new UserInfoEvent(username, user.toUserInfo());
		}
	}

	@Override
	@Transactional(readOnly=true)
	public AllUsersEvent requestAllUsers(
			RequestAllUsersEvent requestAllUsersEvent) {
		LOG.debug("Getting all users from User repository.");
		List<UserInfo> allUserDetails = new ArrayList<UserInfo>();
		for(User user : userRepository.findAll())
			allUserDetails.add(user.toUserInfo());
		return new AllUsersEvent(allUserDetails);
	}

	
	@Override
	@Transactional(readOnly=true)
	public User loadUserByUsername(
			String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			LOG.error("Failed to load user with username: {}", username);
			throw new UsernameNotFoundException(String.format(
					"The user with the username '%s' could not be found.", username));
		}
		else {
			LOG.debug("Loaded user: {}", user.toString());
			return user;
		}
	}
}