package com.rotarywebsite.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom("noreply@rotaryclub.com");
            
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    // Send membership renewal reminder
    public void sendRenewalReminder(String to, String memberName, String renewalDate) {
        String subject = "Recordatorio de Renovación de Membresía - Rotary Club";
        String text = String.format(
            "Estimado/a %s,\n\n" +
            "Le recordamos que su membresía en el Rotary Club vence el %s.\n" +
            "Por favor, renueve su membresía para continuar disfrutando de todos los beneficios.\n\n" +
            "Saludos cordiales,\nEquipo Rotary Club",
            memberName, renewalDate
        );
        
        sendSimpleMessage(to, subject, text);
    }

    // Send project assignment notification
    public void sendProjectAssignment(String to, String memberName, String projectName) {
        String subject = "Has sido asignado a un nuevo proyecto - Rotary Club";
        String text = String.format(
            "Estimado/a %s,\n\n" +
            "Has sido asignado como responsable del proyecto: %s.\n" +
            "Por favor, accede al sistema para ver los detalles del proyecto.\n\n" +
            "Saludos cordiales,\nEquipo Rotary Club",
            memberName, projectName
        );
        
        sendSimpleMessage(to, subject, text);
    }

    // Send welcome email to new members
    public void sendWelcomeEmail(String to, String memberName) {
        String subject = "Bienvenido al Rotary Club";
        String text = String.format(
            "Estimado/a %s,\n\n" +
            "¡Bienvenido al Rotary Club!\n\n" +
            "Su cuenta ha sido creada exitosamente. Ahora puede acceder al sistema " +
            "y participar en nuestros proyectos y actividades.\n\n" +
            "Saludos cordiales,\nEquipo Rotary Club",
            memberName
        );
        
        sendSimpleMessage(to, subject, text);
    }
}