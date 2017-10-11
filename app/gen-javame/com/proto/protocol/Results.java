/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.proto.protocol;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.Enumeration;

import org.json.*;

import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

public class Results implements TBase {
  private static final TStruct STRUCT_DESC = new TStruct("");

  private static final TField CREATED_AT_FIELD_DESC = new TField("createdAt", TType.STRING, (short)1);
  private static final TField TYPE_FIELD_DESC = new TField("type", TType.STRING, (short)2);
  private static final TField URL_FIELD_DESC = new TField("url", TType.STRING, (short)3);
  private static final TField WHO_FIELD_DESC = new TField("who", TType.STRING, (short)4);

  private String createdAt;
  private String type;
  private String url;
  private String who;

  // isset id assignments

  public Results() {
  }

  public Results(
    String createdAt,
    String type,
    String url,
    String who)
  {
    this();
    this.createdAt = createdAt;
    this.type = type;
    this.url = url;
    this.who = who;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Results(Results other) {
    if (other.isSetCreatedAt()) {
      this.createdAt = other.createdAt;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetUrl()) {
      this.url = other.url;
    }
    if (other.isSetWho()) {
      this.who = other.who;
    }
  }

  public Results deepCopy() {
    return new Results(this);
  }

  public void clear() {
    this.createdAt = null;
    this.type = null;
    this.url = null;
    this.who = null;
  }

  public String getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public void unsetCreatedAt() {
    this.createdAt = null;
  }

  /** Returns true if field createdAt is set (has been assigned a value) and false otherwise */
  public boolean isSetCreatedAt() {
    return this.createdAt != null;
  }

  public void setCreatedAtIsSet(boolean value) {
    if (!value) {
      this.createdAt = null;
    }
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void unsetUrl() {
    this.url = null;
  }

  /** Returns true if field url is set (has been assigned a value) and false otherwise */
  public boolean isSetUrl() {
    return this.url != null;
  }

  public void setUrlIsSet(boolean value) {
    if (!value) {
      this.url = null;
    }
  }

  public String getWho() {
    return this.who;
  }

  public void setWho(String who) {
    this.who = who;
  }

  public void unsetWho() {
    this.who = null;
  }

  /** Returns true if field who is set (has been assigned a value) and false otherwise */
  public boolean isSetWho() {
    return this.who != null;
  }

  public void setWhoIsSet(boolean value) {
    if (!value) {
      this.who = null;
    }
  }

  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Results)
      return this.equals((Results)that);
    return false;
  }

  public boolean equals(Results that) {
    if (that == null)
      return false;

    boolean this_present_createdAt = true && this.isSetCreatedAt();
    boolean that_present_createdAt = true && that.isSetCreatedAt();
    if (this_present_createdAt || that_present_createdAt) {
      if (!(this_present_createdAt && that_present_createdAt))
        return false;
      if (!this.createdAt.equals(that.createdAt))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_url = true && this.isSetUrl();
    boolean that_present_url = true && that.isSetUrl();
    if (this_present_url || that_present_url) {
      if (!(this_present_url && that_present_url))
        return false;
      if (!this.url.equals(that.url))
        return false;
    }

    boolean this_present_who = true && this.isSetWho();
    boolean that_present_who = true && that.isSetWho();
    if (this_present_who || that_present_who) {
      if (!(this_present_who && that_present_who))
        return false;
      if (!this.who.equals(that.who))
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

    Results other = (Results)otherObject;    int lastComparison = 0;

    lastComparison = TBaseHelper.compareTo(isSetCreatedAt(), other.isSetCreatedAt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreatedAt()) {
      lastComparison = TBaseHelper.compareTo(this.createdAt, other.createdAt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = TBaseHelper.compareTo(isSetType(), other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = TBaseHelper.compareTo(isSetUrl(), other.isSetUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUrl()) {
      lastComparison = TBaseHelper.compareTo(this.url, other.url);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = TBaseHelper.compareTo(isSetWho(), other.isSetWho());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWho()) {
      lastComparison = TBaseHelper.compareTo(this.who, other.who);
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
        case 1: // CREATED_AT
          if (field.type == TType.STRING) {
            this.createdAt = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // TYPE
          if (field.type == TType.STRING) {
            this.type = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // URL
          if (field.type == TType.STRING) {
            this.url = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // WHO
          if (field.type == TType.STRING) {
            this.who = iprot.readString();
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
    if (this.createdAt != null) {
      oprot.writeFieldBegin(CREATED_AT_FIELD_DESC);
      oprot.writeString(this.createdAt);
      oprot.writeFieldEnd();
    }
    if (this.type != null) {
      oprot.writeFieldBegin(TYPE_FIELD_DESC);
      oprot.writeString(this.type);
      oprot.writeFieldEnd();
    }
    if (this.url != null) {
      oprot.writeFieldBegin(URL_FIELD_DESC);
      oprot.writeString(this.url);
      oprot.writeFieldEnd();
    }
    if (this.who != null) {
      oprot.writeFieldBegin(WHO_FIELD_DESC);
      oprot.writeString(this.who);
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
      if (obj.has(CREATED_AT_FIELD_DESC.name())) {
        this.createdAt = obj.optString(CREATED_AT_FIELD_DESC.name());
      }
      if (obj.has(TYPE_FIELD_DESC.name())) {
        this.type = obj.optString(TYPE_FIELD_DESC.name());
      }
      if (obj.has(URL_FIELD_DESC.name())) {
        this.url = obj.optString(URL_FIELD_DESC.name());
      }
      if (obj.has(WHO_FIELD_DESC.name())) {
        this.who = obj.optString(WHO_FIELD_DESC.name());
      }
    } catch (Exception e) {
        throw new TException(e);
    }
  }

  public void write(JSONObject obj) throws TException {
    validate();

    try {
      if (this.createdAt != null) {
        Object v_createdAt = this.createdAt;
        obj.put(CREATED_AT_FIELD_DESC.name(), v_createdAt);
      }
      if (this.type != null) {
        Object v_type = this.type;
        obj.put(TYPE_FIELD_DESC.name(), v_type);
      }
      if (this.url != null) {
        Object v_url = this.url;
        obj.put(URL_FIELD_DESC.name(), v_url);
      }
      if (this.who != null) {
        Object v_who = this.who;
        obj.put(WHO_FIELD_DESC.name(), v_who);
      }
    } catch (Exception e) {
        throw new TException(e);
    }
  }

}
