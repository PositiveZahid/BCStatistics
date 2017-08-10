package iboxbd.broadband.statistics.ip;


/**
 * Created by User on 8/10/2017.
 */

public class IPConverter {
    public static String convert(String ipAddress) {

        // ipAddressInArray[0] = 192
        String[] ipAddressInArray = ipAddress.split("\\.");

        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);

            // 1. 192 * 256^3
            // 2. 168 * 256^2
            // 3. 1 * 256^1
            // 4. 2 * 256^0
            result += ip * Math.pow(256, power);
        }
        return String.valueOf(result);
    }

    public static String revert(String ipAddress) {
        long ip = Long.parseLong(ipAddress);
        StringBuilder sb = new StringBuilder(15);

        for (int i = 0; i < 4; i++) {

            // 1. 2
            // 2. 1
            // 3. 168
            // 4. 192
            sb.insert(0, Long.toString(ip & 0xff));

            if (i < 3) {
                sb.insert(0, '.');
            }

            // 1. 192.168.1.2
            // 2. 192.168.1
            // 3. 192.168
            // 4. 192
            ip = ip >> 8;

        }
        return sb.toString();
    }
}
