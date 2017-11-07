package com.internshaala.internshalaworkshops;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "IS Workshops";
    private static final int DATABASE_VERSION = 1;

    private static final String WORKSHOPS_TABLE_NAME = "workshops";
    private static final String WORKSHOPS_COLUMN_ID = "key_workshop_id";
    private static final String WORKSHOPS_COLUMN_WORKSHOP_NAME = "key_workshop_name";
    private static final String WORKSHOPS_COLUMN_WORKSHOP_COMPANY = "key_workshop_company";
    private static final String WORKSHOPS_COLUMN_WORKSHOP_LOCATION = "key_workshop_location";
    private static final String WORKSHOPS_COLUMN_WORKSHOP_DURATION = "key_workshop_duration";
    private static final String WORKSHOPS_COLUMN_WORKSHOP_COST = "key_workshop_cost";
    private static final String WORKSHOPS_COLUMN_APPLY_BY = "key_workshop_apply_by";


    private static final String PERSONS_TABLE_NAME = "persons";
    private static final String PERSONS_COLUMN_ID = "key_person_id";
    private static final String PERSONS_COLUMN_PERSON_NAME = "key_person_name";
    private static final String PERSONS_COLUMN_PERSON_EMAIL = "key_person_email";
    private static final String PERSONS_COLUMN_PERSON_PASSWORD = "key_person_password";
    private static final String PERSONS_COLUMN_PERSON_WORKSHOPS = "key_person_workshops";



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_WORKSHOPS_TABLE = "CREATE TABLE " + WORKSHOPS_TABLE_NAME + " ( " + WORKSHOPS_COLUMN_ID + " TEXT PRIMARY KEY, " + WORKSHOPS_COLUMN_WORKSHOP_NAME + " TEXT, "
                + WORKSHOPS_COLUMN_WORKSHOP_COMPANY + " TEXT, " + WORKSHOPS_COLUMN_WORKSHOP_LOCATION + " TEXT, " + WORKSHOPS_COLUMN_WORKSHOP_DURATION + " TEXT , "
                + WORKSHOPS_COLUMN_WORKSHOP_COST + " TEXT , " + WORKSHOPS_COLUMN_APPLY_BY + " TEXT )";


        String CREATE_PERSONS_TABLE = "CREATE TABLE " + PERSONS_TABLE_NAME + " ( " + PERSONS_COLUMN_ID + " TEXT PRIMARY KEY, " + PERSONS_COLUMN_PERSON_NAME + " TEXT, "
                + PERSONS_COLUMN_PERSON_EMAIL + " TEXT, " + PERSONS_COLUMN_PERSON_PASSWORD + " TEXT, " + PERSONS_COLUMN_PERSON_WORKSHOPS + " TEXT )";


        db.execSQL(CREATE_WORKSHOPS_TABLE);
        db.execSQL(CREATE_PERSONS_TABLE);


        addWorkshops(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORKSHOPS_TABLE_NAME);
        onCreate(db);
    }

    private ArrayList<Workshop> workshopsList = new ArrayList<>();
    public  void addWorkshops(SQLiteDatabase db){

        Workshop workshop1 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "Campaign Design Workshop","NIHT Infosolution Private Limited",
                "Kolkota","14-24th Nov'17","10000","11th Nov'17");
        workshopsList.add(workshop1);

        Workshop workshop2 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "Digital Marketing","Horizon Infotech",
                "Pune","15-19th Nov'17","3000","12th Nov'17");
        workshopsList.add(workshop2);

        Workshop workshop3 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "Operations","Zennacle",
                "Bangalore","12-15th Nov'17","2000","9th Nov'17");
        workshopsList.add(workshop3);

        Workshop workshop4 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "Marketing","InventIvy Technologies",
                "Hyderabad","14-16th Nov'17","1500","12th Nov'17");
        workshopsList.add(workshop4);

        Workshop workshop5 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "Human Resources(HR)","Betterstep Consultancy",
                "Mysore","17-20th Nov'17","5500","14th Nov'17");
        workshopsList.add(workshop5);

        Workshop workshop6 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "App Development","Learngage",
                "Kolkota","14-21th Nov'17","7000","11th Nov'17");
        workshopsList.add(workshop1);

        Workshop workshop7 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                " Design Workshop","Xinix Limited",
                "Dhanbad","12-16th Nov'17","4500","10th Nov'17");
        workshopsList.add(workshop7);

        Workshop workshop8 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "Web Development Workshop","Unic Limited",
                "Vijayawada","14-16th Nov'17","10000","11th Nov'17");
        workshopsList.add(workshop1);

        Workshop workshop9 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "Machine Learning Workshop","Mojito Labs",
                "Puri","13-19th Nov'17","8000","9th Nov'17");
        workshopsList.add(workshop9);

        Workshop workshop10 = new Workshop("workshop"+ UUID.randomUUID().toString(),
                "Cyber Security Workshop","Cyber Cinil Limited",
                "Delhi","14-16th Nov'17","9000","11th Nov'17");
        workshopsList.add(workshop10);

          addWorkshopsList(db);



    }



    boolean addWorkshopsList(SQLiteDatabase db){

        for(Workshop workshop : workshopsList) {
            ContentValues values = new ContentValues();
            values.put(WORKSHOPS_COLUMN_ID, workshop.getWorkshopId());
            values.put(WORKSHOPS_COLUMN_WORKSHOP_NAME, workshop.getTitle());
            values.put(WORKSHOPS_COLUMN_WORKSHOP_COMPANY, workshop.getCompany());
            values.put(WORKSHOPS_COLUMN_WORKSHOP_LOCATION, workshop.getLocation());
            values.put(WORKSHOPS_COLUMN_WORKSHOP_DURATION, workshop.getDuration());
            values.put(WORKSHOPS_COLUMN_WORKSHOP_COST, workshop.getCost());
            values.put(WORKSHOPS_COLUMN_APPLY_BY, workshop.getApplyby());

            db.insert(WORKSHOPS_TABLE_NAME, null, values);
        }
        return true;
    }


    ArrayList<Workshop> getWorkshops() {
        ArrayList<Workshop> workshopArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from "+WORKSHOPS_TABLE_NAME, null );

        if (cursor.moveToFirst()) {
            do {
                Workshop model = new Workshop();
                model.setWorkshopId(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_ID)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_NAME)));
                model.setCompany(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_COMPANY)));
                model.setLocation(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_LOCATION)));
                model.setDuration(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_DURATION)));
                model.setCost(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_COST)));
                model.setApplyby(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_APPLY_BY)));

                workshopArrayList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return workshopArrayList;
    }



    Workshop getWorkshop(String workshopId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(WORKSHOPS_TABLE_NAME,null,WORKSHOPS_COLUMN_ID + "=?",new String[]{workshopId},null,null,null);

        Workshop model = new Workshop();
        if (cursor.moveToFirst()) {
            do {
                model.setWorkshopId(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_ID)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_NAME)));
                model.setCompany(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_COMPANY)));
                model.setLocation(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_LOCATION)));
                model.setDuration(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_DURATION)));
                model.setCost(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_WORKSHOP_COST)));
                model.setApplyby(cursor.getString(cursor.getColumnIndex(WORKSHOPS_COLUMN_APPLY_BY)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return model;
    }


    public boolean isValid(String email , String password){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(PERSONS_TABLE_NAME,null,PERSONS_COLUMN_PERSON_EMAIL + "=? AND "+
                PERSONS_COLUMN_PERSON_PASSWORD+ "=?",new String[]{email,password},null,null,null);

        Workshop model = new Workshop();
        if (cursor!=null && cursor.getCount()>0 ) {
            cursor.close();
            return true;
        }else {
            return false;
        }

    }

    public String getName(String email ){
        String name="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(PERSONS_TABLE_NAME,null,PERSONS_COLUMN_PERSON_EMAIL + "=?",
                new String[]{email},null,null,null);

        Workshop model = new Workshop();
        if (cursor!=null && cursor.moveToFirst() ) {
            name = cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_NAME));
        }else {

        }

        Log.d("getname",email+" " + name);
        return  name;
    }


    public boolean registerUser(Person person){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PERSONS_COLUMN_ID, person.getPersonId());
        values.put(PERSONS_COLUMN_PERSON_NAME, person.getName());
        values.put(PERSONS_COLUMN_PERSON_EMAIL, person.getEmail());
        values.put(PERSONS_COLUMN_PERSON_PASSWORD, person.getPassword());
        values.put(PERSONS_COLUMN_PERSON_WORKSHOPS, person.getWorkshops());

        db.insert(PERSONS_TABLE_NAME, null, values);



        return  true;

    }

    public boolean addWorkshop(String username , String email ,String workshopId){

        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.query(PERSONS_TABLE_NAME,null,PERSONS_COLUMN_PERSON_EMAIL + "=? AND "+
                PERSONS_COLUMN_PERSON_NAME+ "=?",new String[]{email,username},null,null,null);

        String workshops="";
        if (cursor!=null && cursor.moveToFirst()) {
            workshops=(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_WORKSHOPS)));
        }else {
        }

        SQLiteDatabase db2 = getWritableDatabase();
        ContentValues values = new ContentValues();
        workshopId = workshops+","+workshopId;
        Log.d("Add Workshops",username + " : " + email+" : " + workshopId);
        values.put(PERSONS_COLUMN_PERSON_WORKSHOPS,workshopId);

        db2.update(PERSONS_TABLE_NAME,values,PERSONS_COLUMN_PERSON_EMAIL + "=? AND "+
                PERSONS_COLUMN_PERSON_NAME+ "=?",new String[]{email,username});

        return  true;
    };


    public ArrayList<Workshop> getUserWorkshops(String username,String email ){
        ArrayList<Workshop> workshopArrayList = new ArrayList<>();

        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.query(PERSONS_TABLE_NAME,null,PERSONS_COLUMN_PERSON_EMAIL + "=? AND "+
                PERSONS_COLUMN_PERSON_NAME+ "=?",new String[]{email,username},null,null,null);

        String workshops="";
        if (cursor!=null && cursor.moveToFirst()) {
            workshops=(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_WORKSHOPS)));
            Log.d("Workshops",workshops);
        }else {
        }

        if(workshops!=null){
            String[] workshopIds= workshops.split(",");
            for(int i=0;i<workshopIds.length;i++) {
                Workshop workshop = getWorkshop(workshopIds[i].trim());
                if (workshop != null) {
                    Log.d("Workshop"+i,workshopIds[i]);
                    workshop.setApplied(true);
                    if(!(workshop.getTitle().equalsIgnoreCase("")))
                        workshopArrayList.add(workshop);
                }

            }
        }


        return  workshopArrayList;

    }


}

