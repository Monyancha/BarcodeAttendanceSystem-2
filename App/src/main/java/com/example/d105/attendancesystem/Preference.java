package com.example.d105.attendancesystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preference {
	private static Preference preference;
	private static SharedPreferences sharedPreferences;
	
	
	public static Preference getInstance(Context context){
		if (preference==null) {
			preference = new Preference();
			sharedPreferences = context.getSharedPreferences("AttendanceSystem", Context.MODE_PRIVATE);
		}
		return preference;
	}
	
	public boolean getBoolean(String key){
		return sharedPreferences.getBoolean(key, false);
	}
	
	public void putBoolean(String key, boolean value){
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public String getString(String key){
		return sharedPreferences.getString(key, "");
	}
	
	public void putString(String key, String value){
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}


	public void putInt(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public int getInt(String key){
		return sharedPreferences.getInt(key, 0);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return sharedPreferences.getBoolean(key, defaultValue);
	}
	
	public void putLong(String key, long value){
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public long getLong(String key){
		return sharedPreferences.getLong(key, 0);
	}
}
