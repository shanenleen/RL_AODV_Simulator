package simulator.noderelated;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 1, 2006
 * Time: 5:52:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class IPAddress {

    int[] IP = new int[4];

    public IPAddress(String IP) {

        if (IP.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")) {
            String[] ips = IP.split("\\.");
            for (int i = 0; i < ips.length; i++) {
                this.IP[i] = Integer.parseInt(ips[i]);
                if (this.IP[i] > 254 || this.IP[i] < 1) {
                    throw new IPFormatExcepiton();
                }
            }
        } else {
            throw new IPFormatExcepiton();
        }

    }

    public static IPAddress createNext(IPAddress ipA) {

        if (ipA.IP[3] < 254) {
            return new IPAddress(ipA.IP[0] + "." + ipA.IP[1] + "." + ipA.IP[2] + "." + (ipA.IP[3] + 1));
        } else if (ipA.IP[2] < 254) {
            return new IPAddress(ipA.IP[0] + "." + ipA.IP[1] + "." + (ipA.IP[2] + 1) + "." + 1);
        } else if (ipA.IP[1] < 254) {
            return new IPAddress(ipA.IP[0] + "." + (ipA.IP[1] + 1) + "." + 0 + "." + 1);
        } else if (ipA.IP[0] < 254) {
            return new IPAddress((ipA.IP[0] + 1) + "." + 0 + "." + 0 + "." + 1);
        } else {
            return new IPAddress(1 + "." + 0 + "." + 0 + "." + 1);
        }
    }

    public boolean equals(Object obj) {

        IPAddress objIP = (IPAddress) obj;

        for (int i = 0; i < IP.length; i++) {
            if (IP[i] != objIP.IP[i]) {
                return false;
            }
        }

        return true;
    }

    public String toString() {

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < IP.length; i++) {
            result.append(IP[i]);
            result.append(".");
        }

        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}

class IPFormatExcepiton extends RuntimeException {

    public String getMessage() {
        return super.getMessage() + " Error in IP Format";
    }
}
