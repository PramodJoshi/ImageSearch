package org.wikipedia.imagesearch.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.wikipedia.imagesearch.R;

public class LicenseDialog extends DialogFragment {
	
	private AlertDialog mDialog = null;
	
	private String mDialogTitle = null;
	private String mDialogText = null;
	
    /* Call this to instantiate a new InfoDialog.
     * @param activity  The activity hosting the dialog
     * @returns A new instance of InfoDialog.
     */
    public static LicenseDialog newInstance(String dialogTitle, String dialogText) {
    	LicenseDialog frag = new LicenseDialog();
        
   	 	// Supply dialog text as an argument.
        Bundle args = new Bundle();
        args.putString("dialog_title", dialogTitle);
        args.putString("dialog_text", dialogText);
        frag.setArguments(args);
        
        return frag;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogTitle = getArguments().getString("dialog_title");
        mDialogText = getArguments().getString("dialog_text");
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	builder.setTitle(mDialogTitle);
    	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            builder.setMessage(Html.fromHtml(mDialogText,Html.FROM_HTML_MODE_LEGACY));
        } else {
            builder.setMessage(Html.fromHtml(mDialogText));
        }
        builder.setCancelable(true);
        builder.setNeutralButton(getString(R.string.close), new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			dialog.dismiss();
    		}
		});
        // Create the AlertDialog object and return it
        mDialog = builder.create();
        return mDialog;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	   // Make the textview clickable. Must be called after show()
        TextView message = (TextView) mDialog.findViewById(android.R.id.message);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            message.setContentDescription(Html.fromHtml(mDialogText,Html.FROM_HTML_MODE_LEGACY));
        } else {
            message.setContentDescription(Html.fromHtml(mDialogText));
        }
    }
}