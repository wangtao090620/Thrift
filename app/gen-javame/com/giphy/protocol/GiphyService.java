/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.giphy.protocol;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.Enumeration;

import org.json.*;

import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

public class GiphyService {

  public interface Iface {

    public SearchResponse search(SearchRequest req) throws TException;

  }

  public static class Client implements TServiceClient, Iface {
    public Client(TProtocol prot)
    {
      this(prot, prot);
    }

    public Client(TProtocol iprot, TProtocol oprot)
    {
      iprot_ = iprot;
      oprot_ = oprot;
    }

    protected TProtocol iprot_;
    protected TProtocol oprot_;

    protected int seqid_;

    public TProtocol getInputProtocol()
    {
      return this.iprot_;
    }

    public TProtocol getOutputProtocol()
    {
      return this.oprot_;
    }

    public SearchResponse search(SearchRequest req) throws TException
    {
      send_search(req);
      return recv_search();
    }

    public void send_search(SearchRequest req) throws TException
    {
      oprot_.writeMessageBegin(new TMessage("search", TMessageType.CALL, ++seqid_));
      search_args args = new search_args();
      args.setReq(req);
      args.write(oprot_);
      oprot_.writeMessageEnd();
      oprot_.getTransport().flush();
    }

    public SearchResponse recv_search() throws TException
    {
      TMessage msg = iprot_.readMessageBegin();
      if (msg.type == TMessageType.EXCEPTION) {
        TApplicationException x = TApplicationException.read(iprot_);
        iprot_.readMessageEnd();
        throw x;
      }
      // if (msg.seqid != seqid_) {
      //   throw new TApplicationException(TApplicationException.BAD_SEQUENCE_ID, "search failed: out of sequence response");
      // }
      search_result result = new search_result();
      result.read(iprot_);
      iprot_.readMessageEnd();
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new TApplicationException(TApplicationException.MISSING_RESULT, "search failed: unknown result");
    }

  }

  public static class JsonClient implements Iface {
    protected TTransport trans_;

    public JsonClient(TTransport trans)
    {
      this.trans_ = trans;
    }

    public SearchResponse search(SearchRequest req) throws TException
    {
      try {
        JSONObject objReq = new JSONObject();
        if (req != null) req.write(objReq);
        String strReq = objReq.toString();
        trans_.write(strReq.getBytes("utf-8"));
        trans_.flush();
        TByteArrayOutputStream os = new TByteArrayOutputStream(4096);
        IOUtil.readAll(trans_, os);
        String strRes = new String(os.get(), 0, os.len(), "utf-8");
        JSONObject objRes = new JSONObject(strRes);
        SearchResponse res = new SearchResponse();
        res.read(objRes);
        return res;
      } catch (Exception e) {
        throw new TException(e);
      }
    }

  }

  public static class Processor implements TProcessor {
    public Processor(Iface iface)
    {
      iface_ = iface;
      processMap_.put("search", new search());
    }

    protected static interface ProcessFunction {
      public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException;
    }

    private Iface iface_;
    protected final Hashtable processMap_ = new Hashtable();

    public boolean process(TProtocol iprot, TProtocol oprot) throws TException
    {
      TMessage msg = iprot.readMessageBegin();
      ProcessFunction fn = (ProcessFunction)processMap_.get(msg.name);
      if (fn == null) {
        TProtocolUtil.skip(iprot, TType.STRUCT);
        iprot.readMessageEnd();
        TApplicationException x = new TApplicationException(TApplicationException.UNKNOWN_METHOD, "Invalid method name: '"+msg.name+"'");
        oprot.writeMessageBegin(new TMessage(msg.name, TMessageType.EXCEPTION, msg.seqid));
        x.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
        return true;
      }
      fn.process(msg.seqid, iprot, oprot);
      return true;
    }

    private class search implements ProcessFunction {
      public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException
      {
        search_args args = new search_args();
        try {
          args.read(iprot);
        } catch (TProtocolException e) {
          iprot.readMessageEnd();
          TApplicationException x = new TApplicationException(TApplicationException.PROTOCOL_ERROR, e.getMessage());
          oprot.writeMessageBegin(new TMessage("search", TMessageType.EXCEPTION, seqid));
          x.write(oprot);
          oprot.writeMessageEnd();
          oprot.getTransport().flush();
          return;
        }
        iprot.readMessageEnd();
        search_result result = new search_result();
        result.success = iface_.search(args.req);
        oprot.writeMessageBegin(new TMessage("search", TMessageType.REPLY, seqid));
        result.write(oprot);
        oprot.writeMessageEnd();
        oprot.getTransport().flush();
      }

    }

  }

  public static class search_args implements TBase   {
    private static final TStruct STRUCT_DESC = new TStruct("");

    private static final TField REQ_FIELD_DESC = new TField("req", TType.STRUCT, (short)1);

    private SearchRequest req;

    // isset id assignments

    public search_args() {
    }

    public search_args(
      SearchRequest req)
    {
      this();
      this.req = req;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public search_args(search_args other) {
      if (other.isSetReq()) {
        this.req = new SearchRequest(other.req);
      }
    }

    public search_args deepCopy() {
      return new search_args(this);
    }

    public void clear() {
      this.req = null;
    }

    public SearchRequest getReq() {
      return this.req;
    }

    public void setReq(SearchRequest req) {
      this.req = req;
    }

    public void unsetReq() {
      this.req = null;
    }

    /** Returns true if field req is set (has been assigned a value) and false otherwise */
    public boolean isSetReq() {
      return this.req != null;
    }

    public void setReqIsSet(boolean value) {
      if (!value) {
        this.req = null;
      }
    }

    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof search_args)
        return this.equals((search_args)that);
      return false;
    }

    public boolean equals(search_args that) {
      if (that == null)
        return false;

      boolean this_present_req = true && this.isSetReq();
      boolean that_present_req = true && that.isSetReq();
      if (this_present_req || that_present_req) {
        if (!(this_present_req && that_present_req))
          return false;
        if (!this.req.equals(that.req))
          return false;
      }

      return true;
    }

    public int hashCode() {
      return 0;
    }

    public int compareTo(Object otherObject) {
      if (!getClass().equals(otherObject.getClass())) {
        return getClass().getName().compareTo(otherObject.getClass().getName());
      }

      search_args other = (search_args)otherObject;      int lastComparison = 0;

      lastComparison = TBaseHelper.compareTo(isSetReq(), other.isSetReq());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetReq()) {
        lastComparison = this.req.compareTo(other.req);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public void read(TProtocol iprot) throws TException {
      TField field;
      iprot.readStructBegin();
      while (true)
      {
        field = iprot.readFieldBegin();
        if (field.type == TType.STOP) { 
          break;
        }
        switch (field.id) {
          case 1: // REQ
            if (field.type == TType.STRUCT) {
              this.req = new SearchRequest();
              this.req.read(iprot);
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          default:
            TProtocolUtil.skip(iprot, field.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      validate();
    }

    public void write(TProtocol oprot) throws TException {
      validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (this.req != null) {
        oprot.writeFieldBegin(REQ_FIELD_DESC);
        this.req.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    public void validate() throws TException {
      // check for required fields
    }

    public void read(JSONObject obj) throws TException {
      validate();

      try {
        if (obj.has(REQ_FIELD_DESC.name())) {
          this.req = new SearchRequest();
          this.req.read(obj.optJSONObject(REQ_FIELD_DESC.name()));
        }
      } catch (Exception e) {
            throw new TException(e);
      }
    }

    public void write(JSONObject obj) throws TException {
      validate();

      try {
        if (this.req != null) {
          JSONObject v_req = new JSONObject();
          this.req.write(v_req);
          obj.put(REQ_FIELD_DESC.name(), v_req);
        }
      } catch (Exception e) {
            throw new TException(e);
      }
    }

  }

  public static class search_result implements TBase   {
    private static final TStruct STRUCT_DESC = new TStruct("");

    private static final TField SUCCESS_FIELD_DESC = new TField("success", TType.STRUCT, (short)0);

    private SearchResponse success;

    // isset id assignments

    public search_result() {
    }

    public search_result(
      SearchResponse success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public search_result(search_result other) {
      if (other.isSetSuccess()) {
        this.success = new SearchResponse(other.success);
      }
    }

    public search_result deepCopy() {
      return new search_result(this);
    }

    public void clear() {
      this.success = null;
    }

    public SearchResponse getSuccess() {
      return this.success;
    }

    public void setSuccess(SearchResponse success) {
      this.success = success;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof search_result)
        return this.equals((search_result)that);
      return false;
    }

    public boolean equals(search_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    public int hashCode() {
      return 0;
    }

    public int compareTo(Object otherObject) {
      if (!getClass().equals(otherObject.getClass())) {
        return getClass().getName().compareTo(otherObject.getClass().getName());
      }

      search_result other = (search_result)otherObject;      int lastComparison = 0;

      lastComparison = TBaseHelper.compareTo(isSetSuccess(), other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = this.success.compareTo(other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public void read(TProtocol iprot) throws TException {
      TField field;
      iprot.readStructBegin();
      while (true)
      {
        field = iprot.readFieldBegin();
        if (field.type == TType.STOP) { 
          break;
        }
        switch (field.id) {
          case 0: // SUCCESS
            if (field.type == TType.STRUCT) {
              this.success = new SearchResponse();
              this.success.read(iprot);
            } else { 
              TProtocolUtil.skip(iprot, field.type);
            }
            break;
          default:
            TProtocolUtil.skip(iprot, field.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      validate();
    }

    public void write(TProtocol oprot) throws TException {
      oprot.writeStructBegin(STRUCT_DESC);

      if (this.isSetSuccess()) {
        oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
        this.success.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

    public void validate() throws TException {
      // check for required fields
    }

    public void read(JSONObject obj) throws TException {
      validate();

      try {
        if (obj.has(SUCCESS_FIELD_DESC.name())) {
          this.success = new SearchResponse();
          this.success.read(obj.optJSONObject(SUCCESS_FIELD_DESC.name()));
        }
      } catch (Exception e) {
            throw new TException(e);
      }
    }

    public void write(JSONObject obj) throws TException {
      validate();

      try {
        if (this.success != null) {
          JSONObject v_success = new JSONObject();
          this.success.write(v_success);
          obj.put(SUCCESS_FIELD_DESC.name(), v_success);
        }
      } catch (Exception e) {
            throw new TException(e);
      }
    }

  }

}
