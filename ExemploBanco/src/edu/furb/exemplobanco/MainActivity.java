package edu.furb.exemplobanco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnItemClickListener,
		OnClickListener {

	private DatabaseHelper helper;
	private String selecionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		helper = new DatabaseHelper(this);
		getListView().setOnItemClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		updateAdapter();
	}

	private void updateAdapter() {
		String[] from = { "nome", "telefone" };
		int[] to = { android.R.id.text1, android.R.id.text2 };
		SimpleAdapter adapter = new SimpleAdapter(this, listarClientes(),
				android.R.layout.simple_list_item_2, from, to);

		setListAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		helper.close();
		super.onDestroy();
	}

	private List<Map<String, String>> listarClientes() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT _id, nome, telefone FROM cliente ORDER BY nome", null);

		cursor.moveToFirst();
		List<Map<String, String>> dados = new ArrayList<Map<String, String>>();

		for (int i = 0; i < cursor.getCount(); i++) {
			Map<String, String> rec = new HashMap<String, String>();
			rec.put("_id", cursor.getString(0));
			rec.put("nome", cursor.getString(1));
			rec.put("telefone", cursor.getString(2));

			dados.add(rec);

			cursor.moveToNext();
		}

		return dados;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.novo_cliente) {
			Intent i = new Intent(this, ClienteForm.class);
			startActivity(i);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		Map<String, String> registro = (Map<String, String>) adapter
				.getItemAtPosition(position);
		selecionado = registro.get("_id");
		criaAlertDialog().show();
	}

	private AlertDialog criaAlertDialog() {
		CharSequence[] itens = { getString(R.string.dialog_editar),
				getString(R.string.dialog_excluir) };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialog_titulo));
		builder.setItems(itens,	this);
		return builder.create();
	}
	
	private AlertDialog criaConfirmtDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialog_confirm_title));
		builder.setPositiveButton(getString(R.string.dialog_yes), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				excluirItem();
			}
		});
		builder.setNegativeButton(getString(R.string.dialog_no), null);
		return builder.create();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int item) {
		switch (item) {
		case 0: // Editar
			Intent i = new Intent(this, ClienteForm.class);
			i.putExtra("_id", selecionado);
			startActivity(i);
			break;

		case 1: // Excluir
			criaConfirmtDialog().show();
			break;
		}
	}

	private void excluirItem() {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("cliente", "_id = ?", new String[] {selecionado});
		db.close();
		
		updateAdapter();
	}
}
