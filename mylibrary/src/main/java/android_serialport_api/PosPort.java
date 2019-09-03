package android_serialport_api;///*
// * Copyright 2009 Cedric Priscal
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package android_serialport_api;
//
//import android.util.Log;
//
//import java.io.File;
//import java.io.FileDescriptor;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class PosPort {
//
//	private static final String TAG = "PosPort";
//
//	private FileInputStream mFileInputStream;
//	private FileOutputStream mFileOutputStream;
//
//	public PosPort(File device, int baudrate, int flags) throws SecurityException, IOException {
//
//		/* Check access permission */
//		if (!device.canRead() || !device.canWrite()) {
//			try {
//				/* Missing read/write permission, trying to chmod the file */
//				Process su;
//				su = Runtime.getRuntime().exec("/system/bin/su");
//				String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
//						+ "exit\n";
//				su.getOutputStream().write(cmd.getBytes());
//				if ((su.waitFor() != 0) || !device.canRead()
//						|| !device.canWrite()) {
//					throw new SecurityException();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new SecurityException();
//			}
//		}
//
//		/*
//		 * Do not remove or rename the field mFd: it is used by native method close();
//		 */
//		FileDescriptor mFd = open(device.getAbsolutePath(), baudrate, 8, 1, 'n');
//		if (mFd == null) {
//			Log.e(TAG, "native open returns null");
//			throw new IOException();
//		}
//		mFileInputStream = new FileInputStream(mFd);
//		mFileOutputStream = new FileOutputStream(mFd);
//	}
//
//	// Getters and setters
//	public InputStream getInputStream() {
//		return mFileInputStream;
//	}
//
//	public OutputStream getOutputStream() {
//		return mFileOutputStream;
//	}
//
//	// JNI
//	private native static FileDescriptor open(String path, int baudrate, int databits,int stopbits,char parity);
//	public native void close();
//	static {
//		System.loadLibrary("pos_port");
//	}
//}
