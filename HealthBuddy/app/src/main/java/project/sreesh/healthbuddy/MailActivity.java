package project.sreesh.healthbuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Properties;

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
public class MailActivity extends Activity {
    private static final String username = "sid.asucis@gmail.com";
    private static final String password = "(s601249)";
    private static String emailid = "smohan18@asu.edu";
    private static final String subject = "Daily Calorie Update";
    private static String message = "";
    Context CTX = this;
    int calories;

    private Multipart multipart = new MimeMultipart();
    private MimeBodyPart messageBodyPart = new MimeBodyPart();
    public File mediaFile;
    String name;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Session session = createSessionObject();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name= bundle.getString("username");

        DBedit d = new DBedit(CTX);
        Cursor CR = d.pullc(d);
        Cursor CR2=d.pull(d);
        String date = Calendar.DATE + "";
try {
//    if (CR2 != null && CR2.moveToFirst()) {
//        do {
//
//            if (name.equals(CR2.getString(0))) {
//                emailid = CR2.getString(2);
//            }
//
//        } while (CR2.moveToNext());
//        CR2.close();
//    } else {
//        message = "No Calorie Update Today";
//    }

    if (CR != null && CR.moveToFirst()) {
        Log.d("Mail method", "Mail Sent ");
        do {

            if (date.equals(CR.getString(0))) {
                calories = Integer.parseInt(CR.getString(1));
                message = "The Calories you Burned today:" + calories;
            }
            else {
                message = "No Calorie Update Today";
            }

        } while (CR.moveToNext());
        CR.close();
    }
}
catch(Exception e)
{

}
        try {
            Message message1 = createMessage(emailid, subject, message, session);
            new SendMailTask().execute(message1);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
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
            progressDialog = ProgressDialog.show(MailActivity.this, "Please wait", "Sending mail", true, false);
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