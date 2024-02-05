package com.victor.Airbnb.entity;

import com.victor.Airbnb.enuns.SexType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Integer age;
    private SexType sexType;

    @OneToMany
    private List<Reserve> userReserveList;

    public void setName(String name){
        String[] parts = name.split(" ");
        this.firstName = parts[0];
        this.lastName = parts[1];
    }

    public String getName(){
        return this.firstName + " " + this.lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getPhoneNumber(), user.getPhoneNumber()) && Objects.equals(getAge(), user.getAge()) && getSexType() == user.getSexType() && Objects.equals(getUserReserveList(), user.getUserReserveList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getPassword(), getPhoneNumber(), getAge(), getSexType(), getUserReserveList());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", sexType=" + sexType +
                ", userReserveList=" + userReserveList +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
