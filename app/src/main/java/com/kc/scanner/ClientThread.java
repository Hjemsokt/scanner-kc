package com.kc.scanner;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kc.scanner.model.Record;
import com.kc.scanner.model.StockType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientThread implements Runnable {
	private static final String TAG = ClientThread.class.getSimpleName();
	// 定义向UI线程发送消息的Handler对象
	Handler handler;
	// 定义接收UI线程的Handler对象
	Handler revHandler;
	// 该线程处理Socket所对用的输入输出流
	BufferedReader br = null;
	OutputStream os = null;
	ObjectMapper mapper;
	private Socket s;


	public ClientThread(Handler handler) {
		this.handler = handler;
		mapper = new ObjectMapper();
	}

	@Override
	public void run() {
		s = new Socket();
		try {
			s.connect(new InetSocketAddress("192.168.1.120", 1989), 5000);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = s.getOutputStream();
			// 启动一条子线程来读取服务器相应的数据
			new Thread() {

				@Override
				public void run() {
					String content = null;
					// 不断的读取Socket输入流的内容
					try {
						while ((content = br.readLine()) != null) {
							// 每当读取到来自服务器的数据之后，发送的消息通知程序
							// 界面显示该数据
							Message msg = new Message();
							msg.what = 0x123;
							msg.obj = content;
							handler.sendMessage(msg);
						}
					} catch (IOException io) {
						io.printStackTrace();
					}
				}

			}.start();
			// 为当前线程初始化Looper
			Looper.prepare();
			// 创建revHandler对象
			revHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					// 接收到UI线程的中用户输入的数据
					try {

						String string = (String) msg.obj;
						Log.d(TAG, "string msg " + string);
						String[] str = string.split("\\^");
						Record record = new Record();

						record.setId(Long.valueOf(str[0]));
						record.setName(str[1]);
						record.setSize(str[2]);
						record.setType(str[3]);
						record.setValue(Double.valueOf(str[4]));
						record.setSupplierID(Long.valueOf(str[5]));
						if (msg.what == 0x345) {
							// 将用户在扫描的内容写入网络

							record.setStockType(StockType.In);
							record.setRepositoryID(1005l);
							Log.d(TAG, "after transfet msg " + mapper.writeValueAsString(record));

							os.write(mapper.writeValueAsBytes(record));

						} else if (msg.what == 0x456) {

							record.setStockType(StockType.Out);
							record.setRepositoryID(1005l);
							record.setCustomerID(1215l);
							Log.d(TAG, "after transfet msg " + mapper.writeValueAsString(record));

							os.write(mapper.writeValueAsBytes(record));

						}
					} catch (Exception e) {
						Message m = new Message();
						msg.what = 0x123;
						msg.obj = e.getMessage();
						handler.sendMessage(m);

					}
				}

			};
			// 启动Looper
			Looper.loop();

		} catch (SocketTimeoutException e) {
			Message msg = new Message();
			msg.what = 0x123;
			msg.obj = "网络连接超时！";
			handler.sendMessage(msg);
		} catch (IOException io) {
			io.printStackTrace();
		}

	}
}