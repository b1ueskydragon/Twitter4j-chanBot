package jp.tools;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketTool {

    // テスト
    public static void main(String [] args) throws Exception {

        Socket sock = new Socket("SOME_URL", 80);
        InputStream is = sock.getInputStream();
        OutputStream os = sock.getOutputStream();

        os.write("GET/SOME_PARAM".getBytes());
        os.flush();

        InputStreamReader isr = new InputStreamReader(is);
        int i = isr.read();
        while(i != -1) {
            System.out.println((char) i);
            i = isr.read();
        }

        sock.close();
    }
}
