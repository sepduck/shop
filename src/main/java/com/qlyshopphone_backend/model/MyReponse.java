package com.qlyshopphone_backend.model;

import lombok.Data;

@Data
public class MyReponse {
    public Integer status;
    public String message;
    public Object data;
}
