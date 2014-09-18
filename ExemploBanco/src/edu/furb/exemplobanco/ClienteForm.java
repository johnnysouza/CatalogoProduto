package edu.furb.exemplobanco;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClienteForm extends Activity {

	private TextView txtNome;
	private TextView txtDocumento;
	private TextView txtTelefone;
	private TextView txtEmail;
	private Button btnCadastrar;

	private DatabaseHelper helperDB;

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
		btnCadastrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				cadastrar();
			}
		});
	}

	protected void cadastrar() {
		SQLiteDatabase db = helperDB.getReadableDatabase();
		ContentValues values = new ContentValues();

		values.put("nome", txtNome.getText().toString());
		values.put("documento", txtDocumento.getText().toString());
		values.put("telefone", txtTelefone.getText().toString());
		values.put("email", txtEmail.getText().toString());

		db.insert("cliente", null, values);
		Toast.makeText(ClienteForm.this, "Cliente cadastrado com sucesso!",
				Toast.LENGTH_SHORT).show();

		finish();
	}

}
