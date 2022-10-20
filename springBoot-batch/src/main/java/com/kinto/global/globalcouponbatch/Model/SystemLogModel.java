package com.kinto.global.globalcouponbatch.Model;

/**
 * @author YangLiming
 */
public class SystemLogModel {
    private String id;
    private String operation_type;

    public SystemLogModel() {
    }

    public SystemLogModel(String id, String operation_type) {
        this.id = id;
        this.operation_type = operation_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    @Override
    public String toString() {
        return "id: " + id + ", operation_type: " + operation_type;
    }
}
