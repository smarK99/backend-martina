package com.example.inicial1.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        // Al usar Mailtrap, el "From" puede ser cualquier cosa que quieras inventar
        mensaje.setFrom("no-reply@martinasandwichs.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        mailSender.send(mensaje);
        System.out.println("¡Email enviado con éxito a Mailtrap!");
    }
}