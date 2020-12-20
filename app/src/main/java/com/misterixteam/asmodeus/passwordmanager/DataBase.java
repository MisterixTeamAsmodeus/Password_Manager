package com.misterixteam.asmodeus.passwordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PasswordDataBase";
    public static final String TABLE_PASSWORD = "Password";
    public static final String ID = "_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String WEB_SITE = "webSite";
    public static final String URI_TO_IMAGE = "uriToImage";
    private final String keyToCrypt;

    public DataBase(@Nullable Context context, String keyToCrypt) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.keyToCrypt = keyToCrypt;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PASSWORD +
                "(" + ID + " INTEGER PRIMARY KEY, " +
                LOGIN + " TEXT, " +
                PASSWORD + " TEXT, " +
                WEB_SITE + " TEXT, " +
                URI_TO_IMAGE + " Text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_PASSWORD);
        onCreate(sqLiteDatabase);
    }

    public void updatePasswordContainer(PasswordContainer passwordContainer) {
        SQLiteDatabase database = getWritableDatabase();
        database.update(TABLE_PASSWORD, getContentValues(passwordContainer), ID + " = " + passwordContainer.getId(), null);
    }

    public void createPasswordContainer(PasswordContainer passwordContainer) {
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_PASSWORD, null, getContentValues(passwordContainer));
    }

    public void deletePasswordContainer(PasswordContainer passwordContainer) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_PASSWORD, ID + "=" + passwordContainer.getId(), null);
    }

    private ContentValues getContentValues(PasswordContainer passwordContainer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, passwordContainer.getId());
        contentValues.put(LOGIN, passwordContainer.getLogin(keyToCrypt));
        contentValues.put(PASSWORD, passwordContainer.getPassword(keyToCrypt));
        contentValues.put(WEB_SITE, passwordContainer.getWebSite());
        contentValues.put(URI_TO_IMAGE, passwordContainer.getUriToImage());
        return contentValues;
    }

    public ArrayList<PasswordContainer> getAllPasswordContainer() {
        ArrayList<PasswordContainer> passwordContainerList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * FROM " + TABLE_PASSWORD, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int loginIndex = cursor.getColumnIndex(LOGIN);
            int passwordIndex = cursor.getColumnIndex(PASSWORD);
            int webSiteIndex = cursor.getColumnIndex(WEB_SITE);
            int uriToImageIndex = cursor.getColumnIndex(URI_TO_IMAGE);
            do {
                passwordContainerList.add(new PasswordContainer(
                        cursor.getInt(idIndex),
                        cursor.getString(uriToImageIndex),
                        cursor.getString(webSiteIndex),
                        cursor.getString(loginIndex),
                        cursor.getString(passwordIndex)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return passwordContainerList;
    }

    public PasswordContainer getPasswordContainer(int id) {
        PasswordContainer passwordContainer = new PasswordContainer();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * FROM " + TABLE_PASSWORD + " WHERE " + ID + " = ?", new String[]{String.valueOf(id)});
        int idIndex = cursor.getColumnIndex(ID);
        int loginIndex = cursor.getColumnIndex(LOGIN);
        int passwordIndex = cursor.getColumnIndex(PASSWORD);
        int webSiteIndex = cursor.getColumnIndex(WEB_SITE);
        int uriToImageIndex = cursor.getColumnIndex(URI_TO_IMAGE);
        if (cursor.moveToFirst()) {
            passwordContainer = new PasswordContainer(
                    cursor.getInt(idIndex),
                    cursor.getString(uriToImageIndex),
                    cursor.getString(webSiteIndex),
                    cursor.getString(loginIndex),
                    cursor.getString(passwordIndex)
            );
        }
        cursor.close();
        return passwordContainer;
    }
}