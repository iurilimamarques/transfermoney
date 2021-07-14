package com.iurilima.transfermoney.util.handlerfieldvalidator;

import java.io.Serializable;

public class FieldErrorDTO implements Serializable {

    private final String objectName;

    private final String field;

    private final String message;

    public FieldErrorDTO(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
