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

public class SearchRequest implements TBase {
  private static final TStruct STRUCT_DESC = new TStruct("");

  private static final TField Q_FIELD_DESC = new TField("q", TType.STRING, (short)1);
  private static final TField API_KEY_FIELD_DESC = new TField("api_key", TType.STRING, (short)2);

  private String q;
  private String api_key;

  // isset id assignments

  public SearchRequest() {
  }

  public SearchRequest(
    String q,
    String api_key)
  {
    this();
    this.q = q;
    this.api_key = api_key;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SearchRequest(SearchRequest other) {
    if (other.isSetQ()) {
      this.q = other.q;
    }
    if (other.isSetApi_key()) {
      this.api_key = other.api_key;
    }
  }

  public SearchRequest deepCopy() {
    return new SearchRequest(this);
  }

  public void clear() {
    this.q = null;
    this.api_key = null;
  }

  public String getQ() {
    return this.q;
  }

  public void setQ(String q) {
    this.q = q;
  }

  public void unsetQ() {
    this.q = null;
  }

  /** Returns true if field q is set (has been assigned a value) and false otherwise */
  public boolean isSetQ() {
    return this.q != null;
  }

  public void setQIsSet(boolean value) {
    if (!value) {
      this.q = null;
    }
  }

  public String getApi_key() {
    return this.api_key;
  }

  public void setApi_key(String api_key) {
    this.api_key = api_key;
  }

  public void unsetApi_key() {
    this.api_key = null;
  }

  /** Returns true if field api_key is set (has been assigned a value) and false otherwise */
  public boolean isSetApi_key() {
    return this.api_key != null;
  }

  public void setApi_keyIsSet(boolean value) {
    if (!value) {
      this.api_key = null;
    }
  }

  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SearchRequest)
      return this.equals((SearchRequest)that);
    return false;
  }

  public boolean equals(SearchRequest that) {
    if (that == null)
      return false;

    boolean this_present_q = true && this.isSetQ();
    boolean that_present_q = true && that.isSetQ();
    if (this_present_q || that_present_q) {
      if (!(this_present_q && that_present_q))
        return false;
      if (!this.q.equals(that.q))
        return false;
    }

    boolean this_present_api_key = true && this.isSetApi_key();
    boolean that_present_api_key = true && that.isSetApi_key();
    if (this_present_api_key || that_present_api_key) {
      if (!(this_present_api_key && that_present_api_key))
        return false;
      if (!this.api_key.equals(that.api_key))
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

    SearchRequest other = (SearchRequest)otherObject;    int lastComparison = 0;

    lastComparison = TBaseHelper.compareTo(isSetQ(), other.isSetQ());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQ()) {
      lastComparison = TBaseHelper.compareTo(this.q, other.q);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = TBaseHelper.compareTo(isSetApi_key(), other.isSetApi_key());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApi_key()) {
      lastComparison = TBaseHelper.compareTo(this.api_key, other.api_key);
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
        case 1: // Q
          if (field.type == TType.STRING) {
            this.q = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // API_KEY
          if (field.type == TType.STRING) {
            this.api_key = iprot.readString();
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
    if (this.q != null) {
      oprot.writeFieldBegin(Q_FIELD_DESC);
      oprot.writeString(this.q);
      oprot.writeFieldEnd();
    }
    if (this.api_key != null) {
      oprot.writeFieldBegin(API_KEY_FIELD_DESC);
      oprot.writeString(this.api_key);
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
      if (obj.has(Q_FIELD_DESC.name())) {
        this.q = obj.optString(Q_FIELD_DESC.name());
      }
      if (obj.has(API_KEY_FIELD_DESC.name())) {
        this.api_key = obj.optString(API_KEY_FIELD_DESC.name());
      }
    } catch (Exception e) {
        throw new TException(e);
    }
  }

  public void write(JSONObject obj) throws TException {
    validate();

    try {
      if (this.q != null) {
        Object v_q = this.q;
        obj.put(Q_FIELD_DESC.name(), v_q);
      }
      if (this.api_key != null) {
        Object v_api_key = this.api_key;
        obj.put(API_KEY_FIELD_DESC.name(), v_api_key);
      }
    } catch (Exception e) {
        throw new TException(e);
    }
  }

}

