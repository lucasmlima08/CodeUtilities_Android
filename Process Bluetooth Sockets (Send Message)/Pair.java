package com.myllenno.adapterbluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import java.util.ArrayList;
import java.util.Set;

public class Pair extends Activity {

    public int BT_ATIVAR = 1;
    public int BT_VISIVEL = 1;
    public int BT_TEMPO = 300;
    private Boolean bluetoothActivation = false;

    private BluetoothAdapter bluetoothAdapter;

    //- Verifica se o dispositivo suporta bluetooth.
    public Boolean supportBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            return false;
        }
        return true;
    }

    //- Pede permissão para ativar o bluetoth.
    public void activationBluetooth(){
        if (!bluetoothAdapter.isEnabled()){
            Intent bluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetooth, BT_ATIVAR);
            Log.i("Permissão", "Pergunta se o usuário quer ativar o bluetooth!");
        }
    }

    //- Verifica a resposta do usuário.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // requestCode = verifica se o bluetooth foi ativado.
        // resultCode = verifica se o tempo é igual ao requisitado.
        if (BT_ATIVAR == requestCode){
            //if (BT_RESULTADO != resultCode){
            if (BT_TEMPO == resultCode){
                Log.i("Ativação", "Ativou o Bluetooth!");
                bluetoothActivation = true;
            } else {
                Log.i("Ativação", "Não ativou o Bluetooth!");
                bluetoothActivation = false;
            }
        }
    }

    private Set<BluetoothDevice> setDevices;
    private ArrayList<BluetoothDevice> devices;

    //- Lê os dispositivos pareados e envia para o array.
    public void devicesPaired(){
        setDevices = bluetoothAdapter.getBondedDevices();
        devices = new ArrayList<BluetoothDevice>();
        if (setDevices.size() > 0){
            for (BluetoothDevice device: setDevices){
                devices.add(device);
                Log.i("Pareamento", "Dispositivo: " + device.getName() + "\n" + device.getAddress());
            }
            Log.i("Pareamento", "Leu todos os dispositivos pareados!");
        } else {
            Log.i("Pareamento", "Não encontrou dispositivos pareados!");
        }
    }

    //- Faz a requisição de visibilidade por um determinado tempo definido.
    public void activationTime(){
        if (!bluetoothAdapter.isEnabled()){
            Intent visibilidade = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            visibilidade.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, BT_TEMPO);
            startActivityForResult(visibilidade, BT_VISIVEL);
        }
    }

    public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        // Descobrindo dispositivos para parear e incluir no array.
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);
            }
        }
    };

    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    public void registerBroadcast() {
        registerReceiver(broadcastReceiver, filter);
    }

    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
    public void discoverDevicesNotPaired(){
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }
}
