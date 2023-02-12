package algonquin.cst2355.torunse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import algonquin.cst2355.torunse.databinding.ActivityMainBinding;

/**
 * @author steph
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the edit text where the user puts their password **/
   private  EditText thePasswordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads buttons / text on screen
        setContentView(binding.getRoot());
       thePasswordText = binding.editText;

       Button btn = binding.button;

       btn.setOnClickListener( clk -> {

           String password = thePasswordText.getText().toString();
           verifyPassword(password);

           if(verifyPassword(password))
           {
               //password has an uppercase character
              binding.textView4.setText("Your password meets the requirements");
           }else
               binding.textView4.setText("You shall not pass!");



       });






    }

    /**
     * check if the  #$%^&*!@?
     *
     * //return false otherwise
     * @param c
     * @return
     */

    private boolean isSpecialCharacter(char c) {

        switch(c){
            case'#':
            case '?':
            case'*':
            case '%':
            case '$':
            case '&':
            case'@':
            case '^':
                return true;
            default:
                return false;
        }


    }

    /** This function scans the string to see if it is complex enough
     *
     * @param pw The string to verify
     * @return True if pw has uppercase, otherwise false
     */
    public boolean verifyPassword(String pw){
        boolean isComplexEnough = false;


        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;



        for(int i = 0; i < pw.length(); i++)
        {
            char c = pw.charAt(i);

            if(Character.isUpperCase(c))
               // if(Charcter)
                foundUpperCase = true;

            if(Character.isLowerCase(c))
                // if(Charcter)
                foundLowerCase = true;

            if(Character.isDigit(c))
                // if(Charcter)
                foundNumber = true;

            if(isSpecialCharacter(c))
                foundSpecial = true;
        }
        if(!foundUpperCase)
        {

            Toast.makeText(this,"Your password does not have an upper case letter",Toast.LENGTH_SHORT).show();

            return false;

        }

        else if( ! foundLowerCase)
        {
            Toast.makeText(this,"Your password does not have a lower case letter",Toast.LENGTH_SHORT).show();

            return false;

        }

        else if( ! foundNumber) {
            Toast.makeText(this,"Your password does not have a number",Toast.LENGTH_SHORT).show();

            return false;
        }

        else if(! foundSpecial) {
            Toast.makeText(this,"Your password does not have a special symbol",Toast.LENGTH_SHORT).show();
            return false;
        }

        else

            return true; //only get here if they're all true
    }

}