package co.insecurity.iiam.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private Set<UserRole> roles;

    public User() {
        this(-1, null);
    }

    public User(long id, String username) {
        this(id, username, null, null, Stream.of(UserRole.USER).collect(Collectors.toCollection(HashSet::new)));
    }

    public User(long id, String username, String firstName, String lastName, Set<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public boolean addRole(UserRole role) {
        return this.roles.add(role);
    }

    public boolean removeRole(UserRole role) {
        return this.roles.remove(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
