package com.aws.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.io.Serializable;

@DynamoDBTable(tableName = "equipment")
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String UUID;
    private String equipmentName;
    private String IP;
    private boolean blocked;

    public Equipment() {
    }

    @DynamoDBHashKey(attributeName = "equipmentId")
    @DynamoDBAutoGeneratedKey
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    @DynamoDBAttribute
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    @DynamoDBAttribute
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    @DynamoDBAttribute
    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "Equipments{" +
                "UUID='" + UUID + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", IP='" + IP + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
