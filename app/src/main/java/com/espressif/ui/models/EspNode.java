// Copyright 2020 Espressif Systems (Shanghai) PTE LTD
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.espressif.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class EspNode implements Parcelable {

    private String nodeId;
    private String configVersion;
    private String nodeName;
    private String fwVersion;
    private String nodeType;
    private boolean isOnline;
    private long timeStampOfStatus; // timestamp of connectivity status
    private ArrayList<Device> devices;
    private ArrayList<Param> attributes;
    private ArrayList<Service> services;

    public EspNode(String id) {
        nodeId = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getFwVersion() {
        return fwVersion;
    }

    public void setFwVersion(String fwVersion) {
        this.fwVersion = fwVersion;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public long getTimeStampOfStatus() {
        return timeStampOfStatus;
    }

    public void setTimeStampOfStatus(long timeStampOfStatus) {
        this.timeStampOfStatus = timeStampOfStatus;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public ArrayList<Param> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Param> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    protected EspNode(Parcel in) {

        nodeId = in.readString();
        configVersion = in.readString();
        nodeName = in.readString();
        fwVersion = in.readString();
        nodeType = in.readString();
        isOnline = in.readByte() != 0;
        timeStampOfStatus = in.readLong();
        devices = in.createTypedArrayList(Device.CREATOR);
        attributes = in.createTypedArrayList(Param.CREATOR);
        services = in.createTypedArrayList(Service.CREATOR);
    }

    public static final Creator<EspNode> CREATOR = new Creator<EspNode>() {
        @Override
        public EspNode createFromParcel(Parcel in) {
            return new EspNode(in);
        }

        @Override
        public EspNode[] newArray(int size) {
            return new EspNode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nodeId);
        dest.writeString(configVersion);
        dest.writeString(nodeName);
        dest.writeString(fwVersion);
        dest.writeString(nodeType);
        dest.writeByte((byte) (isOnline ? 1 : 0));
        dest.writeLong(timeStampOfStatus);
        dest.writeTypedList(devices);
        dest.writeTypedList(attributes);
        dest.writeTypedList(services);
    }
}
