package com.omar.time.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.omar.time.model.audit.DateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users", uniqueConstraints = { //define unique columns
    @UniqueConstraint(columnNames = {
        "username"
    }),
    @UniqueConstraint(columnNames = {
        "email"
    })
})
public class User extends DateAudit {

	private static final long serialVersionUID = 527585354298775504L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 40)
    private String firstName;
    
    @Size(max = 40)
    private String lastName;

    @NotBlank
    @Size(max = 15)
    private String username;
    
    @Size(max = 20)
    private String mobile;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Project.class)
    @JoinTable(name = "project_editors",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<User> projects = new LinkedList<>();
    
    @NotNull
    private boolean isActivatedMail;
    
    @NotNull
    private boolean deleted;

	public User(String firstName, String lastName, String username, String mobile, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
	}
    
}