package com.dev.pos.dto;

public class ProductDetailsJoinDto {

    private int code;
    private String description;
    private BatchDto batchDto;

    public ProductDetailsJoinDto() {
    }

    public ProductDetailsJoinDto(int code, String description, BatchDto batchDto) {
        this.code = code;
        this.description = description;
        this.batchDto = batchDto;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BatchDto getBatchDto() {
        return batchDto;
    }

    public void setBatchDto(BatchDto batchDto) {
        this.batchDto = batchDto;
    }
}
