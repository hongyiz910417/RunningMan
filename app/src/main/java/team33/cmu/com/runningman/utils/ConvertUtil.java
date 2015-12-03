package team33.cmu.com.runningman.utils;

import com.google.android.gms.maps.model.LatLng;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 12/2/15.
 */
public class ConvertUtil {
    public static byte[] doubleToBytes(double val){
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(val);
        return bytes;
    }

    public static byte[] LatLngListToBytes(List<LatLng> list) {
        byte[] bytes = new byte[list.size() * 8 * 2];
        for (int i = 0; i < list.size(); i++) {
            byte[] lattmp = doubleToBytes(list.get(i).latitude);
            byte[] lngtmp = doubleToBytes(list.get(i).longitude);
            for (int j = 0; j < 8; j++) {
                bytes[i * 16 + j] = lattmp[j];
                bytes[i * 16 + 8 + j] = lngtmp[j];
            }
        }
        return bytes;
    }

    public static List<LatLng> BytesToLatLngList(byte[] bytes){
        List<LatLng> list = new ArrayList<LatLng>();
        int size = bytes.length / 16;
        for(int i = 0; i < size; i++){
            byte[] latBytes = new byte[8];
            byte[] lngBytes = new byte[8];
            for(int j = 0; j < 8; j++){
                latBytes[j] = bytes[i * 16 + j];
                lngBytes[j] = bytes[i * 16 + 8 + j];
            }
            double lat = ByteBuffer.wrap(latBytes).getDouble();
            double lng = ByteBuffer.wrap(lngBytes).getDouble();
            list.add(new LatLng(lat, lng));
        }
        return list;
    }
}
