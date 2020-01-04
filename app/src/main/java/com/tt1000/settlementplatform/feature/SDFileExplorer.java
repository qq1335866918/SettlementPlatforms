package com.tt1000.settlementplatform.feature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tt1000.settlementplatform.R;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.      /dddd
 *
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class SDFileExplorer extends Activity
{
	final String RootDirConst="/";
	final String TAG="SDFileExplorer";
	final String OpenDirConst="/mnt/usb_storage";
	final String OpenDirConst2="/mnt/";
	private Intent intent;
	ListView listView;
	TextView textView;
	static int FileType=0;
	// ��¼��ǰ�ĸ��ļ���
	File currentParent;
	// ��¼��ǰ·���µ������ļ����ļ�����
	File[] currentFiles;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		intent = getIntent();

	}
	void Get_Buf_Sum(byte dataBuf[], int dataLen, byte sumBuf[]) {

		int i;
		long Sum = 0;
		// byte[] byteNum = new byte[8];
		long temp;

		for (i = 0; i < dataLen; i++) {
			if (dataBuf[i] < 0) {
				temp = dataBuf[i] & 0x7f;
				temp |= 0x80L;

			} else {
				temp = dataBuf[i];
			}
			Sum += temp;
			temp = dataBuf[i];

		}

		for (int ix = 0; ix < 4; ++ix) {
			int offset = ix * 8;
			sumBuf[ix] = (byte) ((Sum >> offset) & 0xff);
		}

	}
}