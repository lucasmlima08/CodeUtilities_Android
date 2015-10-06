package com.myllenno.adapterbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class Server {

	private BluetoothAdapter bluetoothAdapter;
    private BluetoothServerSocket serverSocket = null;
    
    private UUID uuid;
    private String PIN;

    public Server(){
        uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        PIN = "1234";
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void start(){
        try {
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(PIN, uuid);
        } catch (IOException e){
            Log.e("Erro", e.getMessage());
        }
    }

    public void connection(){
        try {
            BluetoothSocket bluetoothSocket = serverSocket.accept();
            Log.e("Conectado", "Cliente conectado ao servidor!");
            InputStream inputStream = bluetoothSocket.getInputStream();
            byte[] bytes;
            while (bluetoothSocket.isConnected()){
                bytes = new byte[1024];
                int read = inputStream.read(bytes);
                if (read > 0){
                    String message = new String(bytes,0,read);
                    Log.e("Messagem", "Mensagem Lida: " + String.format("message: %s",message));
                }
            }
            inputStream.close();
        } catch(Exception e){
            Log.e("Erro", e.getMessage());
        }
    }
}
