package com.android.networkscanner;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.text.format.Formatter;

import java.net.UnknownHostException;

//networkinfo
public class dhcpInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhcp_info);
        WifiManager wifi = (WifiManager)getApplication().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        if(dhcp == null) {
            System.out.println("No DHCP");
        }else{
            TextView tv= (TextView) findViewById(R.id.networkinfo);
            tv.setText("DNS1: "+Formatter.formatIpAddress(dhcp.dns1).toString()+
                    "\nDNS2: "+Formatter.formatIpAddress(dhcp.dns2).toString()+
                    "\nDefault Gateway: "+Formatter.formatIpAddress(dhcp.gateway).toString()+
                    "\nIP Address: "+Formatter.formatIpAddress(dhcp.ipAddress).toString()+
                    "\nLease Duration: "+dhcp.leaseDuration +
                    "\nNet Mask: "+Formatter.formatIpAddress(dhcp.netmask).toString()+
                    "\nServer IP: "+Formatter.formatIpAddress(dhcp.serverAddress).toString()+
                    "\nSSID: "+wifi.getConnectionInfo().getSSID()+
                    "\nBSSID: "+wifi.getConnectionInfo().getBSSID()+
                    "\nRSSI: "+wifi.getConnectionInfo().getRssi()+
                    "\nLink Speed: "+wifi.getConnectionInfo().getLinkSpeed()+
                    "\nFrequency: "+wifi.getConnectionInfo().getFrequency());
        }
    }
}
