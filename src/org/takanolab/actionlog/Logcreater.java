package org.takanolab.actionlog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class Logcreater {
	private boolean filecheck;
	private File oldLog;
	private String log="",tmp = "";
	private Date date;
	String path = "";
	PrintWriter pw;
	BufferedReader br;
	
	// 日付のフォーマット
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd','HH:mm:ss");
	// ログファイル(.txt)のパス
	private String basePath = Environment.getExternalStorageDirectory().getPath() + "/ActionLog/";


	public Logcreater(){
		path = basePath + "UserActionLog.txt";
		logCreate();
	}
	public Logcreater(String name){
		path = basePath + name + ".txt";
		logCreate();
	}
	
	public void logCreate() {
		// TODO 自動生成されたコンストラクター・スタブ
		oldLog = new File(path);
		/* ファイルが存在するか */
		if (oldLog.exists()){
			/* ファイルが存在するとき */
			filecheck = true;
			try{
				// リーダとライタを作成
				br = new BufferedReader(new FileReader(oldLog));
				pw = new PrintWriter(new BufferedWriter(new FileWriter(oldLog,false)));
			}catch(Exception e){
				// エラー処理
				Log.e("error",e.toString());
			}
		}else{
			/* ファイルが存在しないとき */
			filecheck = false;
			try{
				// ファイルを作成
				new File(oldLog.getParent()).mkdirs();
				oldLog.createNewFile();
				Log.d("debug","ファイル作成成功");
				// リーダとライタを作成
				pw = new PrintWriter(new BufferedWriter(new FileWriter(oldLog,false)));
				br = new BufferedReader(new FileReader(oldLog));
				// 作成日時を書き込み
//				pw.print("'" + path + "' logfile create\n");
			}catch(Exception e){
				// エラー処理
				Log.e("error","ファイルが見つかりません");
				Log.e("error",e.toString());
			}
		}
	}
	
	/** ファイルが存在したかを返す。今は使われていない。
	 * 
	 * @since 1.0
	 * @return 存在するか
	 */
	public boolean checkFile(){
			return filecheck;
	}
	
	/** ログファイルに書き込む。
	 *  
	 * 
	 * @since 1.1
	 * @param addlog
	 * @return 書き込まれる文字列
	 */
	public String addLog(String addlog){
		// 日付と結合
		tmp = setDate() + "," + addlog;
		// ライタに送る
		pw.println(tmp);
		return tmp + "\n";
	}
	/** ログファイルに書き込む。
	 *  
	 * 
	 * @since 1.1
	 * @param addlog
	 * @return 書き込まれる文字列
	 */
	public String addLog(String action,String model){
		// 日付と結合
		tmp = setDate() + "," + action + "," + model;
		// ライタに送る
		pw.println(tmp);
		return tmp + "\n";
	}
	/** 読み込んだログファイルの内容を読み取る。
	 * 
	 *  @since 1.0
	 *  @return 読み取ったログ
	 */
	public String getLog(){
		try{
			// 最後の行まで読み込む
			while((tmp = br.readLine()) != null){
				log += tmp + "\n";
			}
			return log;
		}catch(Exception e){
			// エラー処理
			Log.e("error",e.toString());
			return "false";
		}
	}
	
	/** 現在時間を整形して返す。
	 * 
	 * @since 1.0
	 * @return 整形された日時
	 */
	private String setDate(){
		// 現在時間を作成
		date = new Date();
		// フォーマットを整えて返す
		return dateformat.format(date);
	}
	
	/** リーダを閉じる。ファイルにはこのとき書き込まれる。
	 * 
	 * @since 1.0
	 */
	public void close(){
		try{
			addLog("finish");
			/* リーダを閉じる */
			pw.close();
			br.close();
		}catch(Exception e){
			// エラー処理
			Log.e("error",e.toString());
		}
	}
}
