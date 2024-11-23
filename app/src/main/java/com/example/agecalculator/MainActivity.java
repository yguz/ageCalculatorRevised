package com.example.agecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private EditText dateOfBirthEditText;
    private EditText currentDateEditText;
    private TextView ageTextView;
    private Button calculateButton;
    private SimpleDateFormat formatForDate = new SimpleDateFormat("MM/dd/yyyy");
    private Button nextBirthdaysButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize elements
        dateOfBirthEditText = findViewById(R.id.editTextDateOfBirth);
        currentDateEditText = findViewById(R.id.editTextCurrentDate);
        ageTextView = findViewById(R.id.resultTextView);
        calculateButton = findViewById(R.id.calculateButton);
        nextBirthdaysButton = findViewById(R.id.nextBirthdaysButton);


        currentDateEditText.setText(formatForDate.format(Calendar.getInstance().getTime()));

        // on click event for calculating age
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAge();
            }
        });

        // on click event for opening next birthday activity
        nextBirthdaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextBirthdaysActivity();
            }
        });
    }

    private void calculateAge() {
        String dateOfBirth = dateOfBirthEditText.getText().toString();
        String currentDate = currentDateEditText.getText().toString();

        if (dateOfBirth.isEmpty()) {
            dateOfBirthEditText.setError("Enter your birthday.");
            return;
        }

        try {
            Date birthDate = formatForDate.parse(dateOfBirth);
            Date todayDate = formatForDate.parse(currentDate);

            // calculate age and handle exceptions so app does not break
            int age = calculateAgeFromDates(birthDate, todayDate);
            ageTextView.setText(String.format("You are %d years old.", age));
        } catch (ParseException e) {
            ageTextView.setText("Invalid date format.");
        }
    }


    private int calculateAgeFromDates(Date birthDate, Date todayDate) {
        // create calendar instance using today's date using input
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(todayDate);

        // create calendar instance using entered birthday's year, month, and day
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        int year = todayCalendar.get(Calendar.YEAR);
        int month = todayCalendar.get(Calendar.MONTH);
        int day = todayCalendar.get(Calendar.DAY_OF_MONTH);

        int birthYear = birthCalendar.get(Calendar.YEAR);
        int birthMonth = birthCalendar.get(Calendar.MONTH);
        int birthDay = birthCalendar.get(Calendar.DAY_OF_MONTH);

        // initial age
        int age = year - birthYear;

        // if birthday hasn't happened yet, then subtract age by 1
        if (birthMonth > month || (birthMonth == month && birthDay > day)) {
            age--;
        }

        return age;
    }


    private void openNextBirthdaysActivity() {
        String dateOfBirth = dateOfBirthEditText.getText().toString();

        if (dateOfBirth.isEmpty()) {
            dateOfBirthEditText.setError("Enter your birthday.");
            return;
        }

        Intent intent = new Intent(this, NextBirthdays.class);
        intent.putExtra("birthday", dateOfBirth);
        startActivity(intent);
    }
}
