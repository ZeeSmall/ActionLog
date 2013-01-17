package org.takanolab.actionlog;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener, OnEditorActionListener{
	
	Button movebtn, rotatebtn, scalebtn, capturebtn, changebtn;
	TextView console;
	EditText edit;
	Spinner models;
	Logcreater log = null;
	
	boolean mode;
	String selectModel = null;
	String eText = "UserActionLog";
	CharSequence resumeText = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        console = (TextView)findViewById(R.id.console);
        console.append("\n");
        edit = (EditText)findViewById(R.id.filename);
        edit.setOnEditorActionListener(this);
        models = (Spinner)findViewById(R.id.modelspr);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Stringrs.modelNamesL);
        models.setAdapter(adapter);
        models.setOnItemSelectedListener(this);
        
        movebtn = (Button)findViewById(R.id.movebtn);
        movebtn.setOnClickListener(this);
        rotatebtn = (Button)findViewById(R.id.rotatebtn);
        rotatebtn.setOnClickListener(this);
        scalebtn = (Button)findViewById(R.id.scalebtn);
        scalebtn.setOnClickListener(this);
        capturebtn = (Button)findViewById(R.id.capture);
        capturebtn.setOnClickListener(this);
        changebtn = (Button)findViewById(R.id.change);
        changebtn.setOnClickListener(this);
        
        log = new Logcreater();
        mode = true;
        log.addLog("Start3DCGMode");
        console.append("Start3DCGMode\n");
    }
	
    @Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		try{
			log.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
		resumeText = console.getText();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		if(resumeText != null){
			console.setText(resumeText);
		}
	}

	@Override
	protected void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    

	@Override
	public void onClick(View v) {
		if(selectModel == null) return;
		switch(v.getId()){
		case R.id.movebtn :
			console.append("Position," + selectModel + "\n");
			log.addLog("Position", selectModel);
			break;
		case R.id.rotatebtn :
			console.append("Rotate," + selectModel + "\n");
			log.addLog("Rotate", selectModel);
			break;
		case R.id.scalebtn :
			console.append("Scale," + selectModel + "\n");
			log.addLog("Scale", selectModel);
			break;
		case R.id.capture :
			console.append("ScreenCapture," + selectModel + "\n");
			log.addLog("ScreenCapture", selectModel);
			break;
			
		case R.id.change :
			if(mode){
				console.append("StartFreeMode\n");
				log.addLog("StartFreeMode");
				mode = false;
			}else{
				console.append("Start3DCGMode\n");
				log.addLog("Start3DCGMode");
				mode = true;
			}
			break;
			
		default :
			break;
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		selectModel = Stringrs.modelNames[arg2];
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		EditText tmp = (EditText)v;
		String txt = v.getText().toString();
		
		if(!eText.equals(txt)){
			eText = txt;
			console.setText("Log:\n");
			if(log != null){
				log.close();
				log = null;
			}
			log = new Logcreater(txt);
			mode = true;
			log.addLog("Start3DCGMode");
			console.append("Start3DCGMode\n");
			return true;
		}
		return false;
	}
}
