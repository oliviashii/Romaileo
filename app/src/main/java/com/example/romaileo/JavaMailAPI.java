package com.example.romaileo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/*
    Class that is run when the send button is clicked. Executes all email functions using our JavaMail API
 */
public class JavaMailAPI extends AsyncTask<Void,Void,Void>  {
    //information retrieved from MainActivity and necessary for email capabilities
    private Context getContext;
    private Session getSession;
    private String getEmail;
    private String getSubject;
    private String getMessage;
    private ProgressDialog getProgressDialog;
    public JavaMailAPI(Context getContext, String getEmail, String getSubject, String getMessage) {
        this.getContext = getContext;
        this.getEmail = getEmail;
        this.getSubject = getSubject;
        this.getMessage = getMessage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //message that is shown when email is set to be sent
        getProgressDialog = ProgressDialog.show(getContext,"Your mysterious love letter is being delivered", "Please be patient...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //when the email has been sent, the progress dialog will be dismissed
        getProgressDialog.dismiss();
        //message that is shown to show that the email has been sent successfully
        Toast.makeText(getContext,"Your secret admirer has received your email!",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //creating properties for compatibility with GMail
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        //creating a new session
        getSession = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
                    //checks password against the specific romeomailbot email and password we created
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
                    }
                });
        try {
            //using mimemessage to send email
            MimeMessage mime = new MimeMessage(getSession);
            //set romaileobot address
            mime.setFrom(new InternetAddress(Utils.EMAIL));
            //set secret admirer adress
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(getEmail));
            //set subject of message
            mime.setSubject(getSubject);
            //set message
            mime.setText(getMessage);
            //send message
            Transport.send(mime);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}