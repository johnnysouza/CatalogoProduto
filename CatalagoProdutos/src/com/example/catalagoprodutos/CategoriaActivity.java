package com.example.catalagoprodutos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CategoriaActivity extends Activity {

	private TextView edCategoria;
	private TextView edCor;
	private Button btnSalvarCat;

	private DatabaseHelper helperDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria);

		helperDB = new DatabaseHelper(this);

		edCategoria = (TextView) findViewById(R.id.edCategoria);
		edCor = (TextView) findViewById(R.id.edCor);

		btnSalvarCat = (Button) findViewById(R.id.btnSalvarCat);
		btnSalvarCat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				salvarCategoria();
			}
		});
	}

	private void salvarCategoria() {
		SQLiteDatabase db = helperDB.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("descricao", edCategoria.getText().toString());
		values.put("cor", edCor.getText().toString());
		
		db.insert("categoria", null, values);
		
		db.close();
		finish();
	}
}
