package edu.furb.exemplobanco;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClienteForm extends Activity implements OnClickListener {

	private TextView txtNome;
	private TextView txtDocumento;
	private TextView txtTelefone;
	private TextView txtEmail;
	private Button btnCadastrar;

	private DatabaseHelper helperDB;

	private long id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cliente_form);

		helperDB = new DatabaseHelper(this);

		txtNome = (TextView) findViewById(R.id.txtNome);
		txtDocumento = (TextView) findViewById(R.id.txtDocumento);
		txtTelefone = (TextView) findViewById(R.id.txtTelefone);
		txtEmail = (TextView) findViewById(R.id.txtEmail);

		btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
		btnCadastrar.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		helperDB.close();
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();

		String id = getIntent().getStringExtra("_id");
		if (id == null) {
			this.id = -1;
		} else {
			this.id = Long.parseLong(id);
			loaddata();
		}

	}

	private void loaddata() {
		SQLiteDatabase db = helperDB.getReadableDatabase();

		Cursor cursor = db
				.rawQuery(
						"SELECT nome, documento, telefone, email FROM cliente WHERE _id = ?",
						new String[] { String.valueOf(this.id) });

		cursor.moveToFirst();
		txtNome.setText(cursor.getString(0));
		txtDocumento.setText(cursor.getString(1));
		txtTelefone.setText(cursor.getString(2));
		txtEmail.setText(cursor.getString(3));
		cursor.close();
	}

	protected void cadastrar() {
		SQLiteDatabase db = helperDB.getReadableDatabase();
		ContentValues values = new ContentValues();

		values.put("nome", txtNome.getText().toString());
		values.put("documento", txtDocumento.getText().toString());
		values.put("telefone", txtTelefone.getText().toString());
		values.put("email", txtEmail.getText().toString());

		if (this.id < 0) {
			db.insert("cliente", null, values);
			Toast.makeText(this, "Cliente cadastrado com sucesso!",
					Toast.LENGTH_SHORT).show();
		} else {
			String[] whereArgs = new String[] { String.valueOf(this.id) };
			db.update("cliente", values, "_id = ?", whereArgs);
			Toast.makeText(this, "Cliente atualizado com sucesso!",
					Toast.LENGTH_SHORT).show();
		}

		finish();
	}

	@Override
	public void onClick(View arg0) {
		cadastrar();
	}

}
