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

public class Pagination implements TBase {
  private static final TStruct STRUCT_DESC = new TStruct("");

  private static final TField TOTAL_COUNT_FIELD_DESC = new TField("total_count", TType.I32, (short)1);
  private static final TField COUNT_FIELD_DESC = new TField("count", TType.I32, (short)2);
  private static final TField OFFSET_FIELD_DESC = new TField("offset", TType.I32, (short)3);

  private int total_count;
  private int count;
  private int offset;

  // isset id assignments
  private static final int __TOTAL_COUNT_ISSET_ID = 0;
  private static final int __COUNT_ISSET_ID = 1;
  private static final int __OFFSET_ISSET_ID = 2;
  private boolean[] __isset_vector = new boolean[3];

  public Pagination() {
  }

  public Pagination(
    int total_count,
    int count,
    int offset)
  {
    this();
    this.total_count = total_count;
    setTotal_countIsSet(true);
    this.count = count;
    setCountIsSet(true);
    this.offset = offset;
    setOffsetIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Pagination(Pagination other) {
    System.arraycopy(other.__isset_vector, 0, __isset_vector, 0, other.__isset_vector.length);
    this.total_count = other.total_count;
    this.count = other.count;
    this.offset = other.offset;
  }

  public Pagination deepCopy() {
    return new Pagination(this);
  }

  public void clear() {
    setTotal_countIsSet(false);
    this.total_count = 0;
    setCountIsSet(false);
    this.count = 0;
    setOffsetIsSet(false);
    this.offset = 0;
  }

  public int getTotal_count() {
    return this.total_count;
  }

  public void setTotal_count(int total_count) {
    this.total_count = total_count;
    setTotal_countIsSet(true);
  }

  public void unsetTotal_count() {
    __isset_vector[__TOTAL_COUNT_ISSET_ID] = false;
  }

  /** Returns true if field total_count is set (has been assigned a value) and false otherwise */
  public boolean isSetTotal_count() {
    return __isset_vector[__TOTAL_COUNT_ISSET_ID];
  }

  public void setTotal_countIsSet(boolean value) {
    __isset_vector[__TOTAL_COUNT_ISSET_ID] = value;
  }

  public int getCount() {
    return this.count;
  }

  public void setCount(int count) {
    this.count = count;
    setCountIsSet(true);
  }

  public void unsetCount() {
    __isset_vector[__COUNT_ISSET_ID] = false;
  }

  /** Returns true if field count is set (has been assigned a value) and false otherwise */
  public boolean isSetCount() {
    return __isset_vector[__COUNT_ISSET_ID];
  }

  public void setCountIsSet(boolean value) {
    __isset_vector[__COUNT_ISSET_ID] = value;
  }

  public int getOffset() {
    return this.offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
    setOffsetIsSet(true);
  }

  public void unsetOffset() {
    __isset_vector[__OFFSET_ISSET_ID] = false;
  }

  /** Returns true if field offset is set (has been assigned a value) and false otherwise */
  public boolean isSetOffset() {
    return __isset_vector[__OFFSET_ISSET_ID];
  }

  public void setOffsetIsSet(boolean value) {
    __isset_vector[__OFFSET_ISSET_ID] = value;
  }

  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Pagination)
      return this.equals((Pagination)that);
    return false;
  }

  public boolean equals(Pagination that) {
    if (that == null)
      return false;

    boolean this_present_total_count = true;
    boolean that_present_total_count = true;
    if (this_present_total_count || that_present_total_count) {
      if (!(this_present_total_count && that_present_total_count))
        return false;
      if (this.total_count != that.total_count)
        return false;
    }

    boolean this_present_count = true;
    boolean that_present_count = true;
    if (this_present_count || that_present_count) {
      if (!(this_present_count && that_present_count))
        return false;
      if (this.count != that.count)
        return false;
    }

    boolean this_present_offset = true;
    boolean that_present_offset = true;
    if (this_present_offset || that_present_offset) {
      if (!(this_present_offset && that_present_offset))
        return false;
      if (this.offset != that.offset)
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

    Pagination other = (Pagination)otherObject;    int lastComparison = 0;

    lastComparison = TBaseHelper.compareTo(isSetTotal_count(), other.isSetTotal_count());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotal_count()) {
      lastComparison = TBaseHelper.compareTo(this.total_count, other.total_count);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = TBaseHelper.compareTo(isSetCount(), other.isSetCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCount()) {
      lastComparison = TBaseHelper.compareTo(this.count, other.count);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = TBaseHelper.compareTo(isSetOffset(), other.isSetOffset());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOffset()) {
      lastComparison = TBaseHelper.compareTo(this.offset, other.offset);
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
        case 1: // TOTAL_COUNT
          if (field.type == TType.I32) {
            this.total_count = iprot.readI32();
            setTotal_countIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // COUNT
          if (field.type == TType.I32) {
            this.count = iprot.readI32();
            setCountIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // OFFSET
          if (field.type == TType.I32) {
            this.offset = iprot.readI32();
            setOffsetIsSet(true);
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
    oprot.writeFieldBegin(TOTAL_COUNT_FIELD_DESC);
    oprot.writeI32(this.total_count);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(COUNT_FIELD_DESC);
    oprot.writeI32(this.count);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(OFFSET_FIELD_DESC);
    oprot.writeI32(this.offset);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  public void validate() throws TException {
    // check for required fields
  }

  public void read(JSONObject obj) throws TException {
    validate();

    try {
      if (obj.has(TOTAL_COUNT_FIELD_DESC.name())) {
        this.total_count = obj.optInt(TOTAL_COUNT_FIELD_DESC.name());
        setTotal_countIsSet(true);
      }
      if (obj.has(COUNT_FIELD_DESC.name())) {
        this.count = obj.optInt(COUNT_FIELD_DESC.name());
        setCountIsSet(true);
      }
      if (obj.has(OFFSET_FIELD_DESC.name())) {
        this.offset = obj.optInt(OFFSET_FIELD_DESC.name());
        setOffsetIsSet(true);
      }
    } catch (Exception e) {
        throw new TException(e);
    }
  }

  public void write(JSONObject obj) throws TException {
    validate();

    try {
      Object v_total_count = this.total_count;
      obj.put(TOTAL_COUNT_FIELD_DESC.name(), v_total_count);
      Object v_count = this.count;
      obj.put(COUNT_FIELD_DESC.name(), v_count);
      Object v_offset = this.offset;
      obj.put(OFFSET_FIELD_DESC.name(), v_offset);
    } catch (Exception e) {
        throw new TException(e);
    }
  }

}

