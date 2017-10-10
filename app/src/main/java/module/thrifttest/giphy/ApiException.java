package main.java.module.thrifttest.giphy;


import com.proto.protocol.Code;

import org.apache.thrift.TException;

public class ApiException extends TException {

    private static final long serialVersionUID = 1L;

    public final int code;

    public ApiException() {
        super();

        this.code = Code.Unknown.getValue();
    }

    public ApiException(String detailMessage) {
        super(detailMessage);

        this.code = Code.Unknown.getValue();
    }

    public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);

        this.code = Code.Unknown.getValue();
    }

    public ApiException(Throwable throwable) {
        super(throwable);

        this.code = Code.Unknown.getValue();
    }

    public ApiException(String detailMessage, int code) {
        super(detailMessage);

        this.code = code;
    }
}
