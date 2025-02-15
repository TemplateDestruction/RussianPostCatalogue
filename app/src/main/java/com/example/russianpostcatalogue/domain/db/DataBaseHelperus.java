package com.example.russianpostcatalogue.domain.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelperus extends SQLiteOpenHelper {

    // путь к базе данных вашего приложения
    private static String DB_PATH = "/data/data/com.example.russianpostcatalogue/databases/";
    private static String DB_NAME = "db.db";
    private SQLiteDatabase myDataBase;
    private final Context mContext;

    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     * @param context
     */
    public DataBaseHelperus(Context context) {
    	super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    /**
     * Создает пустую базу данных и перезаписывает ее нашей собственной базой
     * */
    public void createDataBase() throws IOException {
    	boolean dbExist = checkDataBase();

    	if(dbExist){
			Log.e("DBHelper", "db already exist");

			//ничего не делать - база уже есть
    	}else{
    		//вызывая этот метод создаем пустую базу, позже она будет перезаписана
        	this.getReadableDatabase();
			Log.e("DBHelper", "empty db was created");

        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		Log.e("ERROR", e.getLocalizedMessage());
        		throw new Error("Error copying database");
        	}
    	}
    }

    /**
     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
     * @return true если существует, false если не существует
     */
//    private boolean checkDataBase(){
//    	SQLiteDatabase checkDB = null;
//
//    	try{
//    		String myPath = DB_PATH + DB_NAME;
//    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//    	}catch(SQLiteException e){
//    		//база еще не существует
//    	}
//    	if(checkDB != null){
//    		checkDB.close();
//    	}
//    	return checkDB != null ? true : false;
//    }

	private boolean checkDataBase() {
		File databasePath = mContext.getDatabasePath(DB_NAME);
		return databasePath.exists();
	}

    /**
     * Копирует базу из папки assets заместо созданной локальной БД
     * Выполняется путем копирования потока байтов.
     * */
    private void copyDataBase() throws IOException{
    	//Открываем локальную БД как входящий поток
    	InputStream myInput = mContext.getAssets().open(DB_NAME);
//		InputStream myInput = mContext.getDatabasePath(DB_NAME);

    	//Путь ко вновь созданной БД
    	String outFileName = DB_PATH + DB_NAME;

    	//Открываем пустую базу данных как исходящий поток
    	OutputStream myOutput = new FileOutputStream(outFileName);

    	//перемещаем байты из входящего файла в исходящий
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}

    	//закрываем потоки
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }

    public void openDataBase() throws SQLException {
    	//открываем БД
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

    // Здесь можно добавить вспомогательные методы для доступа и получения данных из БД
    // вы можете возвращать курсоры через "return myDataBase.query(....)", это облегчит их использование
    // в создании адаптеров для ваших view
}