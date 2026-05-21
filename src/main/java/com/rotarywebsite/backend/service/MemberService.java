package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.dto.MemberDTO;
import com.rotarywebsite.backend.model.Member;
import com.rotarywebsite.backend.model.MembershipStatus;
import com.rotarywebsite.backend.model.User;
import com.rotarywebsite.backend.model.UserRole;
import com.rotarywebsite.backend.repository.MemberRepository;
import com.rotarywebsite.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository,
                         UserRepository userRepository,
                         PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member createMember(String name, String phone, String occupation, String email, String password) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRol(UserRole.MEMBER);
        user.setActivo(true);
        user.setFechaRegistro(LocalDateTime.now());
        user = userRepository.save(user);

        Member member = new Member();
        member.setNombre(name);
        member.setTelefono(phone);
        member.setOcupacion(occupation);
        member.setUsuario(user);
        member.setFechaIngreso(LocalDate.now());
        member.setFechaRenovacion(LocalDate.now().plusYears(1));
        member.setEstadoMembresia(MembershipStatus.ACTIVE);

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Member> getById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getByEmail(String email) {
        return memberRepository.findByUsuarioEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Member> searchByName(String name) {
        return memberRepository.findByNombreContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Member> getByStatus(MembershipStatus status) {
        return memberRepository.findByEstadoMembresia(status);
    }

    @Transactional
    public Member renewMembership(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        member.setEstadoMembresia(MembershipStatus.ACTIVE);
        member.setFechaRenovacion(LocalDate.now().plusYears(1));

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<Member> getPendingRenewal() {
        LocalDate today = LocalDate.now();
        LocalDate deadline = today.plusDays(30);

        return memberRepository.findByEstadoMembresiaAndFechaRenovacionBetween(
                MembershipStatus.ACTIVE,
                today,
                deadline
        );
    }

    @Transactional(readOnly = true)
    public long countByStatus(MembershipStatus status) {
        return memberRepository.countByEstadoMembresia(status);
    }

    @Transactional
    public Member updateMember(Long memberId, MemberDTO dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        member.setNombre(dto.getName());
        member.setTelefono(dto.getPhone());
        member.setOcupacion(dto.getOccupation());
        member.setDireccion(dto.getAddress());

        // Mantiene la actualización segura del correo electrónico de la cuenta asociada
        if (dto.getEmail() != null && member.getUsuario() != null) {
            String normalizedEmail = dto.getEmail().trim().toLowerCase();
            User currentUser = member.getUsuario();

            if (!normalizedEmail.equalsIgnoreCase(currentUser.getEmail())
                    && userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
                throw new RuntimeException("El email ya está registrado");
            }

            currentUser.setEmail(normalizedEmail);
            userRepository.save(currentUser);
        }

        if (dto.getMembershipStatus() != null) {
            member.setEstadoMembresia(dto.getMembershipStatus());
        }

        if (dto.getRenewalDate() != null) {
            member.setFechaRenovacion(dto.getRenewalDate());
        }

        return memberRepository.save(member);
    }
}