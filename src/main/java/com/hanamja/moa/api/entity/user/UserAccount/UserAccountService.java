package com.hanamja.moa.api.entity.user.UserAccount;


import com.hanamja.moa.api.entity.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAccountService implements UserDetailsService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        Long userId = claims.getBody().get("user_id", Long.class);
        String studentId = claims.getBody().get("student_id", String.class);
        Long departmentId = claims.getBody().get("department_id", Long.class);
        Role role = Role.valueOf(claims.getBody().get("role", String.class));

        return new UserAccount(userId, studentId, departmentId, role);
    }
}
