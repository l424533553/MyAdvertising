package com.luofx.tools;

import android.util.Log;

import java.io.*;

public class STSerialPort {
	private static final String TAG = "SerialPort";

	/*
	 * Do not remove or rename the field mFd: it is used by native method
	 * close();
	 */
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;

	public STSerialPort(File device, int baudrate, int flags)
			throws SecurityException, IOException {

		/* Check access permission */
		if (!device.canRead() || !device.canWrite()) {
			// try {
			// /* Missing read/write permission, trying to chmod the file */
			// Process su;
			// su = Runtime.getRuntime().exec("/system/bin/su");
			// String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
			// + "exit\n";
			// su.getOutputStream().write(cmd.getBytes());
			// if ((su.waitFor() != 0) || !device.canRead()
			// || !device.canWrite()) {
			// throw new SecurityException();
			// }
			// } catch (Exception e) {
			// e.printStackTrace();
			// throw new SecurityException();
			// }
		} else {

			mFd = open(device.getAbsolutePath(), baudrate, flags);
			if (mFd == null) {
				Log.e(TAG, "native open returns null");
				throw new IOException();
			}
			mFileInputStream = new FileInputStream(mFd);
			mFileOutputStream = new FileOutputStream(mFd);
			if (mFileInputStream == null) {
				l("mFileInputStream == null---");
			} else {
				l("mFileInputStream != null---");
			}
			if (mFileOutputStream == null) {
				l("mFileOutputStream == null---");
			} else {
				l("mFileOutputStream != null---");
			}
		}
	}

	// Getters and setters
	public InputStream getInputStream() {
		return mFileInputStream;
	}

	public OutputStream getOutputStream() {
		return mFileOutputStream;
	}

	// JNI
	private native static FileDescriptor open(String path, int baudrate,
											  int flags);

	public native void close();

	static {
		System.loadLibrary("serial_port");
	}

	public void l(String str) {
		Log.e("stSerialPort", str + "          ---------");
	}
}