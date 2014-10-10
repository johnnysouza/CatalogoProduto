package com.example.catalagoprodutos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "produtos.sqlite";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		
		//Tabela de Categoria
		sb.append("CREATE TABLE categoria ( _id INTEGER PRIMARY KEY, ");
		sb.append("				descricao TEXT, ");
		sb.append("				cor TEXT)");
		db.execSQL(sb.toString());

		//Tabela de Produto
		sb = new StringBuilder();
		sb.append("CREATE TABLE produto ( _id INTEGER PRIMARY KEY, ");
		sb.append("				codigo TEXT, ");
		sb.append("				nome TEXT, ");
		sb.append("				preco REAL(12,2), ");
		sb.append("				estoque REAL(12,2), ");
		sb.append("				categoriaID INTEGER,");
		sb.append("				FOREIGN KEY(categoriaID) REFERENCES categoria(_id))");
		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
