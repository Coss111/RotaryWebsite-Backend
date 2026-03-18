package com.rotarywebsite.backend.service;

import org.springframework.transaction.annotation.Transactional;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import com.rotarywebsite.backend.model.MembershipStatus;
import com.rotarywebsite.backend.repository.MemberRepository;
import com.rotarywebsite.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Member createMember(String name, String phone, String occupation, String email, String password) {
        // 1. Create User first
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRol(UserRole.MEMBER);
        user.setActivo(true);
        user.setFechaRegistro(LocalDateTime.now());
        user = userRepository.save(user);

        // 2. Create Member linked to that User
        Member member = new Member();
        member.setNombre(name);
        member.setTelefono(phone);
        member.setOcupacion(occupation);
        member.setUsuario(user); 
        member.setFechaIngreso(LocalDate.now()); // Seteamos fechas iniciales
        member.setFechaRenovacion(LocalDate.now().plusYears(1));
        
        return memberRepository.save(member);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> getById(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> searchByName(String name) {
        return memberRepository.findByNombreContainingIgnoreCase(name);
    }

    public List<Member> getByStatus(MembershipStatus status) {
        return memberRepository.findByEstadoMembresia(status);
    }

    public Member renewMembership(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        
        member.setEstadoMembresia(MembershipStatus.ACTIVE);
        member.setFechaRenovacion(LocalDate.now().plusYears(1));
        
        // CORREGIDO: memberRepository (antes decía membrRepository)
        return memberRepository.save(member);
    }

    public List<Member> getPendingRenewal() {
        LocalDate deadline = LocalDate.now().plusDays(30);
        // CORREGIDO: memberRepository
        return memberRepository.findByFechaRenovacionLessThan(deadline);
    }

    public long countByStatus(MembershipStatus status) {
        // CORREGIDO: memberRepository
        return memberRepository.countByEstadoMembresia(status);
    }
}