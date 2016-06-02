package co.insecurity.iiam.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
	ANONYMOUS("ANONYMOUS"),
	USER("USER"),
	ADMIN("ADMIN");
	
	private final String roleName;
	
	UserRole(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	/**
	 * Constructs a {@link SimpleGrantedAuthority} representing this
	 * {@link UserRole}.
	 * 
	 * Note that the {@link SimpleGrantedAuthority} is constructed with
	 * the String representation of this {@link UserRole} prefixed by
	 * the String {@code "ROLE_"}.
	 * 
	 * @return the {@link SimpleGrantedAuthority} representing this
	 * {@link UserRole}
	 */
	public SimpleGrantedAuthority toSimpleGrantedAuthority() {
		return new SimpleGrantedAuthority("ROLE_" + roleName);
	}
	
	@Override
	public String toString() { 
		return roleName; 
	}
}