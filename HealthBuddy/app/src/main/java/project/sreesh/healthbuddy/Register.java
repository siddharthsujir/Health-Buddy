package project.sreesh.healthbuddy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;


public class Register extends ActionBarActivity implements View.OnClickListener {

    EditText USER_NAME, USER_PASSWORD, CONFIRM_PASSWORD, MAIL_ID, QUESTION, ANSWER;
    String name, pass, cpass, mail, quest, ans;
    Button REGISTER;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        USER_NAME = (EditText)findViewById(R.id.name);
        USER_PASSWORD = (EditText)findViewById(R.id.password);
        CONFIRM_PASSWORD = (EditText)findViewById(R.id.retypepass);
        MAIL_ID = (EditText)findViewById(R.id.mailid);
        QUESTION = (EditText)findViewById(R.id.editText1);
        ANSWER = (EditText)findViewById(R.id.editText2);
        REGISTER = (Button)findViewById(R.id.button11);


        REGISTER.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.button11:
                name = USER_NAME.getText().toString();
                pass = USER_PASSWORD.getText().toString();
                cpass = CONFIRM_PASSWORD.getText().toString();
                mail = MAIL_ID.getText().toString();
                quest = QUESTION.getText().toString();
                ans = ANSWER.getText().toString();

                if(!pass.equals(cpass))
                {
                    Toast.makeText(getBaseContext(), "Password not matching", Toast.LENGTH_SHORT).show();
                    USER_NAME.setText("");
                    USER_PASSWORD.setText("");
                    CONFIRM_PASSWORD.setText("");

                }
                else
                {
                    DBedit DB = new DBedit(context);
                    DB.insert(DB,name,pass,mail,quest,ans);
                    Toast.makeText(getBaseContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }



    }
}
