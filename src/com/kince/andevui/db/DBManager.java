package com.kince.andevui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	
	private static DBManager mInstance;
	private Context mContext;
	private SQLHelper mSQLHelp;
	private SQLiteDatabase mSQLiteDatabase;

	private DBManager(Context context) {
		mContext = context;
		mSQLHelp = new SQLHelper(mContext);
		mSQLiteDatabase = mSQLHelp.getWritableDatabase();
	}
	/**
	 * 初始化数据库操作DBUtil类
	 */
	public static DBManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBManager(context);
		}
		return mInstance;
	}
	/**
	 * 关闭数据库
	 */
	public void close() {
		mSQLHelp.close();
		mSQLHelp = null;
		mSQLiteDatabase.close();
		mSQLiteDatabase = null;
		mInstance = null;
	}

	/**
	 * 添加数据
	 */
	public void insertData(ContentValues values) {
		mSQLiteDatabase.insert(SQLHelper.TABLE_CHANNEL, null, values);
	}

	/**
	 * 添加数据
	 */
	public void insertData(String tableName,ContentValues values) {
		mSQLiteDatabase.insert(tableName, null, values);
	}
	/**
	 * 更新数据
	 * 
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public void updateData(ContentValues values, String whereClause,
			String[] whereArgs) {
		mSQLiteDatabase.update(SQLHelper.TABLE_CHANNEL, values, whereClause,
				whereArgs);
	}

	/**
	 * 删除数据
	 * 
	 * @param whereClause
	 * @param whereArgs
	 */
	public void deleteData(String whereClause, String[] whereArgs) {
		mSQLiteDatabase
				.delete(SQLHelper.TABLE_CHANNEL, whereClause, whereArgs);
	}

	/**
	 * 查询数据
	 * 
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor selectData(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		Cursor cursor = mSQLiteDatabase.query(SQLHelper.TABLE_CHANNEL,columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;
	}
	
}