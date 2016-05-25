package co.insecurity.iiam.web.controller;

import co.insecurity.iiam.event.users.RequestUserInfoEvent;
import co.insecurity.iiam.event.users.UserInfo;
import co.insecurity.iiam.event.users.UserInfoEvent;
import co.insecurity.iiam.persistence.service.UserPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;


@Controller
class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected UserPersistenceService userService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * Tries to retrieve the user from the {@link UserPersistenceService} with
     * the username of the currently authenticated user and returns the user as
     * a {@link UserInfo} object.
     *
     * If a user with a username matching the current principal cannot be found
     * (for example if that user does not exist, or if the principal is
     * authenticated anonymously), then this method will return null.
     *
     * @return {@link UserInfo} for the current principal, otherwise return null
     * if the current principal could not be found by the {@link UserPersistenceService}
     */
    protected UserInfo getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserInfoEvent userInfoEvent = userService.requestUserInfo(new RequestUserInfoEvent(auth.getName()));
        if (!userInfoEvent.isEntityFound()) {
            LOG.warn("Could not find user info for user with username: {}", userInfoEvent.getUsername());
            return null;
        }
        else {
            UserInfo userInfo = userInfoEvent.getUserInfo();
            LOG.debug("Retrieved authenticated user: {}", userInfo);
            return userInfo;
        }
    }

    /**
     * Checks whether the current user is authenticated anonymously, meaning
     * the that the user has not signed into the application.
     *
     * @return true if the user has not signed in, or false if the user is
     * logged in
     */
    protected boolean isAuthenticatedAnonymously() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null &&
                (auth instanceof AnonymousAuthenticationToken) &&
                auth.isAuthenticated());
    }
}
