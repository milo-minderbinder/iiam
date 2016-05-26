package co.insecurity.iiam.model;

import java.util.Date;

public class Session {
    private String sessionId;
    private Date createDate;
    private Date expireDate;

    public Session() {
        this(null);
    }

    public Session(String sessionId) {
        this.sessionId = sessionId;
        this.createDate = new Date();
        this.expireDate = new Date((1000 * 60 * 60) + this.createDate.getTime());
    }

    public Session(String sessionId, Date createDate, Date expireDate) {
        this.sessionId = sessionId;
        this.createDate = createDate;
        this.expireDate = expireDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId='" + sessionId + '\'' +
                ", createDate=" + createDate +
                ", expireDate=" + expireDate +
                '}';
    }
}
