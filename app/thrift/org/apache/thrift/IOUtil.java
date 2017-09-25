package org.apache.thrift;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.io.OutputStream;

public class IOUtil {

    public static void readAll(TTransport trans, OutputStream os) throws TTransportException, IOException {
        try {
            byte[] buf = new byte[4096];
            int read;
            while ((read = trans.read(buf, 0, buf.length)) > 0)
                os.write(buf, 0, read);
        } catch (TTransportException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
