package com.myllenno.adapterbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class Client {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
	
    private UUID uuid;
    private OutputStream outputStream;

    public Client(){
        uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public Boolean start(BluetoothDevice device){
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            Log.i("Conectado", "Conexão cliente estabelecida!");
            return true;
        } catch (IOException e) {
            Log.i("Erro", e.getMessage());
            return false;
        }
    }

    public void connection(String messageSend){
        try {
            outputStream = bluetoothSocket.getOutputStream();
            byte[] bytes = messageSend.getBytes();
            outputStream.write(bytes);
            Log.i("Mensagem", "Mensagem Enviada: " + messageSend);
        } catch (IOException e) {
            Log.i("Erro", e.getMessage());
        }
    }
}
