package co.insecurity.iiam.model;

public class Authentication {
    private User user;
    private Session session;

    public Authentication(User user, Session session) {
        this.user = user;
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "user=" + user +
                ", session=" + session +
                '}';
    }
}
