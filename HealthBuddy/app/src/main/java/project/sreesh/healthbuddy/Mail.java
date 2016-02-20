package project.sreesh.healthbuddy;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Properties;
import javax.activation.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * Created by user on 11/8/2015.
 */
public class Mail extends Activity {
    private static final String username = "sid.asucis@gmail.com";
    private static final String password = "(s601249)";
    private static final String emailid = "smohan18@asu.edu";
    private static final String subject = "Photo";
    private static final String message = "Hello";

    private Multipart multipart = new MimeMultipart();
    private MimeBodyPart messageBodyPart = new MimeBodyPart();
    public File mediaFile;

    protected void onCreate(Bundle savedInstanceState) {


        Session session = createSessionObject();

        try {
            Message message1 = createMessage(emailid, subject, message, session);
            new SendMailTask().execute(message1);
        }
        catch (AddressException e)
        {
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


    private void sendMail(String email, String subject, String messageBody)
    {
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        }
        catch (AddressException e)
        {
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    //Session session = Session.getDefaultInstance(properties);

    private Message createMessage(String email, String subject, String messageBody, Session session) throws

            MessagingException, UnsupportedEncodingException
    {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("sid.asucis@gmail.com", "Siddharth Sujir"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }


    public class SendMailTask extends AsyncTask<Message, Void, Void>
    {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Mail.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        protected Void doInBackground(javax.mail.Message... messages)
        {
            try
            {
                Transport.send(messages[0]);
            } catch (MessagingException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

}
