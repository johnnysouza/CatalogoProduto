package com.example.catalagoprodutos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProdutoActivity extends Activity implements OnClickListener {

	private DatabaseHelper helperDB;
	private TextView edCodigo;
	private TextView edNome;
	private TextView edPreco;
	private TextView edEstoque;
	private Spinner spCategoria;
	private Button btnSalvar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produto);

		helperDB = new DatabaseHelper(this);

		edCodigo = (TextView) findViewById(R.id.edCodigo);
		edNome = (TextView) findViewById(R.id.edNome);
		edPreco = (TextView) findViewById(R.id.edPreco);
		edEstoque = (TextView) findViewById(R.id.edEstoque);
		
		spCategoria = (Spinner) findViewById(R.id.spCategoria);

		btnSalvar = (Button) findViewById(R.id.btnSalvar);
		btnSalvar.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.produto, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.addCategoria) {
			Intent intent = new Intent(this, CategoriaActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		updateAdapter();
	}
	
	@Override
	public void onClick(View v) {
		salvar();
	}

	private void salvar() {
		SQLiteDatabase db = helperDB.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("codigo", edCodigo.getText().toString());
		values.put("nome", edNome.getText().toString());
		values.put("preco", edPreco.getText().toString());
		values.put("estoque", edEstoque.getText().toString());
		values.put("categoriaID", spCategoria.getSelectedItem().toString());
		
		db.insert("produto", null, values);
		Toast.makeText(this, "Produto cadastrado com sucesso!",
				Toast.LENGTH_SHORT).show();
		
		edCodigo.setText("");
		edNome.setText("");
		edPreco.setText("");
		edEstoque.setText("");
		spCategoria.setSelection(0);
		
		db.close();
	}
	
	private void updateAdapter() {
		SQLiteDatabase db = helperDB.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT _id, descricao, cor FROM categoria ORDER BY descricao", null);

		cursor.moveToFirst();
		Categoria[] categorias = new Categoria[cursor.getCount()];

		for (int i = 0; i < cursor.getCount(); i++) {
			Categoria categoria = new Categoria(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
			categorias[i] = categoria;

			cursor.moveToNext();
		}
		
		ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, categorias);

		spCategoria.setAdapter(adapter);
	}
	
}
