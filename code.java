package com.pr6_writeintent;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class FourActivity extends Activity {

	
	 ArrayAdapter<String> AdapterSpinnerTime;
	 final String LOG_TAG = "myLogs";
	 
	    String[] SSpinnerMaster = {"Панько Оксана", "Черняк Анатолій", "Гурик Ірина", "Красько Андрій", "Твердун Катерина"};
	    
		String SEditName;
		String SEditTelephone;
		String SEditDate;
		String IntentView;
		DBHelper dbHelper;	 
		  
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);	    
		    setContentView(R.layout.four);
		    	    
		    
		    dbHelper = new DBHelper(this);
		    final Button ButtonWrite = (Button)findViewById(R.id.buttonWrite);
			  

		    final EditText EditName = (EditText)findViewById(R.id.editName);
		    final EditText EditTelephone = (EditText)findViewById(R.id.editTelepnone);
		    final EditText EditDate = (EditText)findViewById(R.id.editDate);
		    
		    
	 
	  	  	final List<String> SSpinnerTime = new ArrayList<String>();
	 
	  	    ArrayAdapter<String> AdapterSpinnerMaster = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SSpinnerMaster);
	        AdapterSpinnerMaster.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        
	        final Spinner SpinnerMaster = (Spinner)findViewById(R.id.spinnerMaster);
	        SpinnerMaster.setAdapter(AdapterSpinnerMaster);
	        SpinnerMaster.setPrompt("Master");
	        SpinnerMaster.setSelection(0);
	       
	        SpinnerMaster.setOnItemSelectedListener(new OnItemSelectedListener() {    	 
	        
	        	 @Override
	        	 	public void onItemSelected(AdapterView<?> parent, View view,
	                 int position, long id) {
	        	
	        		SSpinnerTime.clear();
	        		String date =  EditDate.getText().toString();	        		
	        		String master = SpinnerMaster.getSelectedItem().toString();
	        		 
	        		SQLiteDatabase db = dbHelper.getWritableDatabase();
	        		String whereClause = "master = ? AND date = ? AND time = ?";
	        		
	        		 for(int i = 9; i < 18; i++)
	        		 {
	        			 String time = Integer.toString(i)+":00";
	        			 String[] whereArgs = new String[] {
	    	        		     master,
	    	        		     date,
	    	        		     time
	    	        		 };	
	        		Cursor c = db.query("mytable1", null, whereClause, whereArgs, null, null, null);
         		  	
         		  	if (c.moveToFirst())
				 		{
         		  		
         		  		int idColIndex = c.getColumnIndex("id");
			 			int intentViewColIndex = c.getColumnIndex("IntentView");
			 			int nameColIndex = c.getColumnIndex("name");
			 			int dateColIndex = c.getColumnIndex("date");
			 			int masterColIndex = c.getColumnIndex("master");
			 			int timeColIndex = c.getColumnIndex("time");
			 			int telephoneColIndex = c.getColumnIndex("telephone");
			 			boolean check = false;
			 		   
			 			do {
			 				 check = true;
			 					
			 					Log.d(LOG_TAG,
			 							"ID = " + c.getInt(idColIndex) +  
			 							", name = " + c.getString(nameColIndex) +
			 							", telephone = " + c.getString(telephoneColIndex) +
			 							", date = " + c.getString(dateColIndex) +
			 							", master = " + c.getString(masterColIndex) +
			 							", time = " + c.getString(timeColIndex) +
			 							", IntentView = " + c.getString(intentViewColIndex));
			 			   } while (c.moveToNext()); 
			 				
			 				if(!check)
			 					{
			 					SSpinnerTime.add(time);
			 					
			 					}
				 			c.close();	        
				 		}
         		  	else
         		  		SSpinnerTime.add(time);
         		  		//counter++;
         		  	
	        		 }
	        		
	        		 Spinner spnrTime = (Spinner)findViewById(R.id.spinnerTime);
	        		 
	        		AdapterSpinnerTime = new ArrayAdapter<String>(FourActivity.this, android.R.layout.simple_spinner_item, SSpinnerTime);
	     	       
	     	        spnrTime.setAdapter(AdapterSpinnerTime);
	                }
	             @Override
	             public void onNothingSelected(AdapterView<?> arg0) {
	             }
			});
	        
	        
	      
	        final Spinner SpinnerTime = (Spinner)findViewById(R.id.spinnerTime);  
	        SpinnerTime.setAdapter(AdapterSpinnerTime); 
	        SpinnerTime.setPrompt("Time");
	        SpinnerTime.setSelection(0);
	       
	     
	        
	        SpinnerTime.setOnItemSelectedListener(new OnItemSelectedListener() {
	        	
	       	 @Override
	       	 	public void onItemSelected(AdapterView<?> parent, View view,
	                int position, long id) {
	       		
	               }
	            @Override
	            public void onNothingSelected(AdapterView<?> arg0) {
	            }
	            
	            
			});  
	 
	
		    
		    
			   ImageButton imgBack = (ImageButton)findViewById(R.id.imageBack);
			   final Intent intentBack = new Intent(this, MainActivity.class);
			   
			   imgBack.setOnClickListener(new OnClickListener() {		   
				@Override
				public void onClick(View v) {
					
			  		startActivity(intentBack);	
				}
			});
		    
			  
			   ///запис в бд
			   ButtonWrite.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {	
							
					EditDate.setBackgroundColor(Color.WHITE);
					EditTelephone.setBackgroundColor(Color.WHITE);
			        EditName.setBackgroundColor(Color.WHITE);
			            	
					Intent intent = getIntent();			    
				    IntentView = intent.getStringExtra("intentView");
					
					ContentValues cv = new ContentValues();
					
					SEditDate = EditDate.getText().toString();
					SEditTelephone = EditTelephone.getText().toString();
					SEditName = EditName.getText().toString();
					String SEditMaster = SpinnerMaster.getSelectedItem().toString();
					String SEditTime = SpinnerTime.getSelectedItem().toString();
					
					boolean bDate = false;
					boolean bTelephone = false;
					boolean bName = false;
					
					
					if(SEditDate.length() == 10)
						bDate = true;
					///	bDate = parserDate(SEditDate);
						else bDate = false;
					
							
				//	EditText edate = (EditText)findViewById(R.id.editDate);
				//	String sedate = edate.getText().toString();
				//			s.matches("(\\d\\d\\.\\d+\\.\\d");
						
					EditText nedit = (EditText)findViewById(R.id.editName);
					String sname = nedit.getText().toString();	
					
					if(sname != null)
					bName = parser(sname);
					else bName = false;
							
					EditText tedit = (EditText)findViewById(R.id.editTelepnone);
					String stelephone = tedit.getText().toString();	
					if(stelephone != null)
					bTelephone = parserTelephone(stelephone);
					else bTelephone = false;
					
					
						if((bDate) && (bTelephone) && (bName) )
					{
					
							
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					
					//додаємо
					if(true)
					{				
						Log.d(LOG_TAG, "--- Insert in mytable1: ---");
			    							
						cv.put("name", SEditName);
			    		cv.put("IntentView", IntentView);
			    	
			    		cv.put("date", SEditDate );
			    		cv.put("master", SEditMaster);
			    		
			    		cv.put("time", SEditTime);
			    		cv.put("telephone", SEditTelephone);
			    		
			    		long rowID = db.insert("mytable1", null, cv);
			    		Log.d(LOG_TAG, "row inserted, ID = " + rowID);
					}
		    
					
					///виведення
				 	if(true)
				 	{
				 		Log.d(LOG_TAG, "--- Rows in mytable1: ---");
				 		Cursor c = db.query("mytable1", null, null, null, null, null, null);

				 		if (c.moveToFirst())
				 		{				 			
				 			int idColIndex = c.getColumnIndex("id");
				 			int intentViewColIndex = c.getColumnIndex("IntentView");
				 			int nameColIndex = c.getColumnIndex("name");
				 			int dateColIndex = c.getColumnIndex("date");
				 			int masterColIndex = c.getColumnIndex("master");
				 			int timeColIndex = c.getColumnIndex("time");
				 			int telephoneColIndex = c.getColumnIndex("telephone");
				 		    
				 			do {				 					
				 					Log.d(LOG_TAG,
				 							"ID = " + c.getInt(idColIndex) +  
				 							", name = " + c.getString(nameColIndex) +
				 							", telephone = " + c.getString(telephoneColIndex) +
				 							", date = " + c.getString(dateColIndex) +
				 							", master = " + c.getString(masterColIndex) +
				 							", time = " + c.getString(timeColIndex) +
				 							", IntentView = " + c.getString(intentViewColIndex));
				 					
				 				} while (c.moveToNext());
				 		} else
				 			Log.d(LOG_TAG, "0 rows");
				 			c.close();
				 			
				 	EditDate.setText("");
				 	EditName.setText("");
				 	EditTelephone.setText("");
				 	
				   
				            
				     
		   	      	
				 	}//if
				 	
				 	
				 	
				 	 // видалення
				/* 	if(false)
				 	{
		  	    	  Log.d(LOG_TAG, "--- Clear mytable1: ---");
		  	          // удаляем все записи
		  	          int clearCount = db.delete("mytable1", null, null);
		  	          Log.d(LOG_TAG, "deleted rows count = " + clearCount);
				 	}
				 */	
				 	
				 	
				 	 dbHelper.close();  
				 	Intent intentNext = new Intent(FourActivity.this, FiveActivity.class);
			     	startActivity(intentNext);	
					 
				}//if date,name,telephone
				 
					else 
					{
						if(bName != true)
							{
								EditName.setBackgroundColor(Color.RED);
								Toast toast = Toast.makeText(getApplicationContext(), 								
										"Будь ласка, перевірте введені дані: \n"
										+ "1. Прізвище та ім'я повинні починатись з великих літер \n"
										+ "2. Мінімальна довжина прізвища та іменні 2 символи\n"
										+ "3. Прізвище та ім'я повинен розділяти лише 1 пробіл",
									Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}					
						if(bTelephone != true)
							{
								EditTelephone.setBackgroundColor(Color.RED);
								Toast toast = Toast.makeText(getApplicationContext(), 								
										"Будь ласка, перевірте номер телефону: \n"
										+ "1. Довжина повинна складати 10 символів \n"
										+ "2. Перша цифра номеру повинна починатись з 0\n",
									Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
						if(bDate != true)
							{
								EditDate.setBackgroundColor(Color.RED);
								Toast toast = Toast.makeText(getApplicationContext(), 								
										"Будь ласка, перевірте дату: \n"
										+ "Заразок: 01.02.2014 \n",
									Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
						
						
					}
				
				}

				/*private boolean parserDate(String sEditDate) {
					
					if((sEditDate.length() == 10)&&(sEditDate.charAt(2) == '.')&&
							(sEditDate.charAt(5) == '.'))
					{
						if(((sEditDate.charAt(0) > -1) && (sEditDate.charAt(0) < 4))
							&&((sEditDate.charAt(1) > -1) && (sEditDate.charAt(1) < 10))	
							&&((sEditDate.charAt(3) > -1) && (sEditDate.charAt(3) < 2))	
							&&((sEditDate.charAt(4) > -1) && (sEditDate.charAt(4) < 10))
							&&((sEditDate.charAt(6) == 2) && (sEditDate.charAt(7) < 0))	
							&&((sEditDate.charAt(8) == 1) && 
							((sEditDate.charAt(9) > 3) && (sEditDate.charAt(9) < 10)))	)
						{
							return true;
						}
						else
							return false;
						
					//return true;
					}
					else 					
					return false;
				}
*/
	
					
					
				
				
				
					
				
			});
			   
			   
			   
			   
			   
			   
			  }//oncreate
			
			
		  //===========================================
		  boolean parserTelephone(String s)
			{
		//	  boolean bl = false;
			  	//перевірка на довжину номеру та наявність першою цифрою 0
			  if((s.length() == 10) && (s.charAt(0) == '0'))
			  {
				 /* for(int i = 1; i < s.length(); i++ )
				  {
					  int ascii = (int) s.charAt(i);
						 if (!  ((ascii < 58) && (ascii > 47)) )
						 {						
							 bl = true;
							 break;
						 }  
				  }
				  */
				  return true;//coment
			  }			
			  else return false; //coment
			 /* if(bl)
					return false;
				else
			  
					return true;*/
			  
			}
		  //============================================
		  boolean parser(String s)
			{
			  	
				boolean bOne = false;//перша буква велика//
				boolean bTwo = false;//тільки дві великі літери//				
				boolean bThree = false;//друга велика літера перед пробілом//				
				boolean bFour = false;//один пробіл //				
				boolean bFive = false;//довжина мінімум 6 символів//
				
				boolean bSix = true;//символи від az i AZ
									
				/*
				кількість пробілів
				 */
				int position = 0;//позиція пробілу
				int counter = 0;//кількість пробілів
				for(int i = 0; i < s.length(); i++)
				{
					if(s.charAt(i) == ' ')
					{
						position = i;
						counter++;
					}
				}
				
				//якщо один пробіл
				if(counter == 1)
					bFour = true;
				
				final String ABC = "QWERTYUIOPASDFGHJKLZXCVBNM";
				
				/*
				кількість великих літер
				 */
				int counterABC = 0;
				for(int i = 0; i < s.length(); i++)
				{
					for(int j = 0; j < ABC.length(); j ++)
					{
						if(s.charAt(i) == ABC.charAt(j))
						{
							counterABC++;
						}
					}						
				}
				//якщо дві великі літери
				if(counterABC == 2)
					bTwo = true;
				
				/*
				 Перевірка на регіст першої букви
				 */
				
				for(int j = 0; j < ABC.length(); j ++)
				{
					if(s.charAt(0) == ABC.charAt(j))
					{
						bOne = true;
					}
				}
				
				
				/*
				 Перевірка на регіст букви після пробілу
				 */
				
				for(int j = 0; j < ABC.length(); j ++)
				{
					if(s.charAt(position + 1) == ABC.charAt(j))
					{
						bThree = true;
					}
				}
				
				/*
				 довжина мінімум 6 символів
				 */
				if(s.length() > 5)
					bFive = true;
				
				/*
				Перевірка кожної літери
				 */
			   List<String> tempLength = new ArrayList<String>();
				 tempLength.clear();
				 
				 for(int i = 0; i < s.length(); i++)
				 {
					 if(s.charAt(i) != ' ')
					 {
						 int ascii = (int) s.charAt(i);
						 if (! (((ascii < 91) && (ascii > 64)) || ((ascii < 123) && (ascii > 96))) )
						 {						
							 bSix = false;
							 break;
						 }
					 }
				 }	
				
				
				
				
				if(bOne && bTwo && bThree && bFour && bFive && bSix)
					return true;
				else
			  
					return false;
			}
		}


		

		class DBHelper extends SQLiteOpenHelper {

		  //  private static final String LOG_TAG = null;

			public DBHelper(Context context) {
		      // конструктор суперкласса
		      super(context, "myDB", null, 1);
		    }

		    @Override
		    public void onCreate(SQLiteDatabase db) {
		 /*   Log.d(LOG_TAG, "--- onCreate database ---");
		      // создаем таблицу с полями
		      db.execSQL("create table mytable ("
		          + "id integer primary key autoincrement," 
		          + "name text," 
		          + "telephone text," 
		          + "date text," 
		          + "master text," 
		          + "time text," 
		          + "IntentView text" + ");");*/
		          
		          
		    }

		    @Override
		    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		    }
		    
		    
		    
		  }

		    
		    
