package com.example.myfxapp;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailHandler {
    private String senha= DadosSenhas.get().getSenhaEmail();
    private String remetente=DadosSenhas.get().getEmail();
    public String codigo=codigoSeguranca();

    public void enviarEmail(String destinatario){
        Properties properties=new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust","*");
    /**/
        Session session= Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });
        session.setDebug(true);

        try{
            Message message= new MimeMessage(session);
            Address[] address=InternetAddress.parse(destinatario);
            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(Message.RecipientType.TO,address);
            message.setSubject("Codigo de seguranca para mudanca de senha");
            message.setText("Para resetar sua senha digite esse codigo\nCodigo: "+codigo);

            Transport.send(message);
        }catch(MessagingException e){
            e.printStackTrace();
        }

    }

    private String codigoSeguranca() {
        Random random= new Random();
        char a = (char) (random.nextInt(26) + 'A');
        String codigo=""+a+"-"+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);

        return codigo;
    }

}
