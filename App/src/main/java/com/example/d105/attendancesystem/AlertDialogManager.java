package com.example.d105.attendancesystem;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class AlertDialogManager {
	boolean flag=false;
	/**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    @SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
            Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
 
        if(status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.s : R.drawable.f);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
    
   /*
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    @SuppressWarnings("deprecation")
	public boolean showAlertDialogForRecommend(final Context context, String title, String message) {
        
    	
    	AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);        
 
        // Setting alert dialog icon
        alertDialog.setIcon(R.drawable.f); 
        
        // Setting Positive "Yes" Button
     	alertDialog.setButton("YES", new DialogInterface.OnClickListener() {
     		public void onClick(DialogInterface dialog,int which) {
     		flag = true;
     		dialog.dismiss();
     		// Write your code here to invoke YES event
     		Toast.makeText(context, "You clicked on YES", Toast.LENGTH_SHORT).show();
     		}
     	});

     	// Setting Negative "NO" Button
     	alertDialog.setButton("NO", new DialogInterface.OnClickListener() {
     		public void onClick(DialogInterface dialog,	int which) {
     		flag = false;
     		// Write your code here to invoke NO event
     		Toast.makeText(context, "You clicked on NO", Toast.LENGTH_SHORT).show();
     			dialog.cancel();
     			dialog.dismiss();
     		}
     	});
 
        // Showing Alert Message
        alertDialog.show();
        return flag;
    }
}