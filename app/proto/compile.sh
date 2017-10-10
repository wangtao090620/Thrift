#!/usr/bin/env bash
thrift -r -o ../ --gen javame ./giphy.thrift
thrift -r -o ../ --gen javame ./proto.thrift
