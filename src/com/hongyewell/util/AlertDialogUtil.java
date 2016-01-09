package com.hongyewell.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogUtil {
	
	private static AlertDialogUtil dialogUtil = null;

	private AlertDialog.Builder builder;
	
	private AlertDialogUtil(Context context) {
		builder = new Builder(context);
	}
	
	/**
	 * 获取AlertDialogUtil的实例
	 * @param context
	 * @return
	 */
	public static AlertDialogUtil getInstance(Context context) {
		if(dialogUtil == null) {
			dialogUtil = new AlertDialogUtil(context);
		}
		return dialogUtil;
	}

	/**
	 * 设置标题
	 * @param title
	 * @return
	 */
	public AlertDialogUtil setTitle(String title) {
		builder.setTitle(title)
			.setCancelable(false);
		return this;
	}

	/**
	 * 设置提示信息
	 * @param message
	 * @return
	 */
	public AlertDialogUtil setMessage(String message) {
		builder.setMessage(message);
		return this;
	}
	
	/**
	 * 设置positive按钮响应事件
	 * @param text
	 * @param listener
	 */
	public AlertDialogUtil setPositiveButton(String text, DialogInterface.OnClickListener listener) {
		builder.setPositiveButton(text, listener);
		return this;
	}
	
	/**
	 * 设置negative按钮响应事件
	 * @param text
	 * @param listener
	 * @return
	 */
	public AlertDialogUtil setNegativeButton (String text, DialogInterface.OnClickListener listener) {
		builder.setNegativeButton(text, listener);
		return this;
	}
	
	/**
	 * 展示dialog框
	 */
	public void display() {
		builder.create();
		builder.show();
	}
	
}
