package com.tt1000.settlementplatform.feature;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.paydevice.smartpos.sdk.SmartPosException;
import com.paydevice.smartpos.sdk.printer.Printer;
import com.paydevice.smartpos.sdk.printer.PrinterManager;
import com.paydevice.smartpos.sdk.printer.SerialPortPrinter;

import static com.tt1000.settlementplatform.utils.MyConstant.TAG;

public class BuiltInPrinter {

    public PrintCallback mPrintCallback;
    public PowerManager.WakeLock lock;

	private Printer mPrinter = null;
	private PrinterManager mPrinterManager = null;

	public BuiltInPrinter(Context context) {
        PowerManager pm = (PowerManager) context.getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
		// 创建唤醒锁
        lock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, TAG);
		//58mm serialport printer


		// TODO: 2019/12/13   语音播报错误注释
		mPrinter = new SerialPortPrinter();
		mPrinter.selectBuiltInPrinter();
		mPrinterManager = new PrinterManager(mPrinter, PrinterManager.TYPE_PAPER_WIDTH_58MM);


	}

	public void powerOn() {
		try {
			Log.d(TAG,"powerOn");
			mPrinterManager.connect();
			mPrinterManager.cmdSetHeatingParam(15, 100, 20);
			mPrinterManager.setStringEncoding("GB18030");
			mPrinterManager.cmdSetPrinterLanguage(PrinterManager.CODE_PAGE_GB18030);
		} catch (SmartPosException e) {
			Log.e(TAG,"power on fail");
		}
	}

	public void powerOff() {
		try {
			Log.d(TAG,"powerOff");
			mPrinterManager.disconnect();
		} catch (SmartPosException e) {
		}
	}

	public boolean havePaper() {
		try {
			Log.d(TAG,"havePaper");
			mPrinterManager.checkPaper();
		} catch (SmartPosException e) {
			return false;
		}
		return true;
	}

	public void sendData(String str) throws SmartPosException {
		mPrinterManager.sendData(str);
	}

	public void lineFeed(int n) throws SmartPosException {
		mPrinterManager.cmdLineFeed(n);
	}

	public interface PrintCallback {
		void print(BuiltInPrinter printer);
	}

    public void setPrintCallback(PrintCallback cb) {
        this.mPrintCallback = cb;
    }

	private void print() {
		if (mPrinterManager != null) {
			mPrintCallback.print(this);
		} else {
			Log.e(TAG,"mPrinterManager is null");
		}
	}


    public class WriteThread implements Runnable {
        int  action_code;

        public WriteThread(int  code) {
            action_code = code;
        }

        public void run() {
            powerOn();
            if(!havePaper()) {
                powerOff();
                return;
            }
            lock.acquire();
            try {
                switch(action_code) {
                    case 0:
						print();
                        break;
                    default:
                        break;
                }

            } finally {
                lock.release();
                //powerOff();
            }
        }
    }
}
