package com.hongyewell.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	private static Toast toast;
	
	public static void show(Context context, CharSequence text, int duration) {
		if(toast == null) {
			toast = Toast.makeText(context, text, duration);
		}else {
			toast.setText(text);
		}
		toast.show();
	}
	
	
}
