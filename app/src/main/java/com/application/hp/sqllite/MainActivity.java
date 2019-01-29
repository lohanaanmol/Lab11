package com.application.hp.sqllite;
import android.app.NotificationChannel;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
EditText editsearch,editempname,editempmail,editage;
SQLiteDatabase sqLitedb;
Button Add,Delete,Update,Searchall,Search;
String name,mailid,age,search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLitedb=openOrCreateDatabase("EmployeeDB",Context.MODE_PRIVATE,null);
sqLitedb.execSQL("Create table if not exists EmpRegistration(EmpId INTEGER PRIMARY KEY AUTOINCREMENT,EmpName VARCHAR(255),EmpMail VARCHAR(255),EmpAge VARCHAR(255));");
editsearch=(EditText) findViewById(R.id.editText2);
editempname= (EditText) findViewById(R.id.editText3);
editempmail=(EditText) findViewById(R.id.editText4);
editage=(EditText) findViewById(R.id.editText5);
Add=(Button)findViewById(R.id.button);
Delete=(Button)findViewById(R.id.button3);
Update=(Button)findViewById(R.id.button4);
Searchall=(Button) findViewById(R.id.button5);
Search= (Button) findViewById(R.id.button6);
Add.setOnClickListener((View.OnClickListener) this);
Delete.setOnClickListener((View.OnClickListener) this);
Update.setOnClickListener((View.OnClickListener) this);
Searchall.setOnClickListener((View.OnClickListener) this);
Search.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View view){
        if (view.getId()==R.id.button)
        {
            name=editempname.getText().toString().trim();
            mailid=editempmail.getText().toString().trim();
            age=editage.getText().toString().trim();
            if(name.equals("")||mailid.equals("")||age.equals(""))
            {
                Toast.makeText(this,"Fields can not be empty",Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                sqLitedb.execSQL("Insert into EmpRegistration(EmpName,EmpMail,EmpAge)VALUES('"+name+"','"+mailid+"','"+age+"');");
                Toast.makeText(this,"Record Saved",Toast.LENGTH_SHORT).show();
            }
        }
        else if (view.getId()==R.id.button5)
        {
            Cursor c=sqLitedb.rawQuery("Select * From EmpRegistration",null);
            if ((c.getCount()==0)) {
                Toast.makeText(this,"Database is Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while (c.moveToNext())
            {
                buffer.append("Employee Name: "+c.getString(1)+"\n");
                buffer.append("Employee Mail: "+c.getString(2)+"\n");
                buffer.append("Employee id: "+c.getString(3)+"\n");
        }
        Toast.makeText(this,buffer.toString(),Toast.LENGTH_LONG).show();
        }
        else if(view.getId()==R.id.button6){
            search=editsearch.getText().toString().trim();
            if(search.equals("")){
                Toast.makeText(this,"Enter Employee Name",Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor c=sqLitedb.rawQuery("Select * From EmpRegistration where EmpName='"+search+"'",null);
            if (c.moveToFirst())
            {
                editempname.setText(c.getString(1));
                editempmail.setText((c.getString(2)));
                editage.setText(c.getString(3));
            }
            else
            {
                Toast.makeText(this,"Data Not Found", Toast.LENGTH_SHORT).show();
            }

        }
        else if (view.getId()==R.id.button4)
        {
            search=editsearch.getText().toString().trim();
            name=editempname.getText().toString().trim();
            mailid=editempmail.getText().toString().trim();
            age=editage.getText().toString().trim();
            if (search.equals(""))
            {
                Toast.makeText(this,"Enter Employee Name To Update", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor cursorupdate=sqLitedb.rawQuery("Select * From EmpRegistration where EmpName='"+search+"'",null);
            if(cursorupdate.moveToFirst())
            {
                if (name.equals("")||mailid.equals("")||age.equals(""))
                {
                    Toast.makeText(this,"Fields can not be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    sqLitedb.execSQL("Update EmpRegistration set EmpName='"+name+" ', Empmail='"+mailid+"', EmpAge='"+age+"' where EmpName='"+search+"'");
                    Toast.makeText(this,"Record Modified",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this,"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        }
        else if (view.getId()==R.id.button3)
        {
            search=editsearch.getText().toString().trim();
            if (search.equals(""))
            {
                Toast.makeText(this,"Enter Employee Nmae To Delete",Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor cursordel=sqLitedb.rawQuery("Select * From EmpRegistration where EmpName='"+search+"'",null);
            if (cursordel.moveToFirst())
            {
                sqLitedb.execSQL("Delete From EmpRegistration where EmpName='"+search+"'");
                Toast.makeText(this,"Record Deleted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Data Not Found",Toast.LENGTH_SHORT).show();
            }

        }

}}
