package com.dev.pos.dto.tm;

import javafx.scene.control.Button;

public class ProductTm {

    private int code;
    private String description;
    private Button showMoreBtn;
    private Button deleteBtn;

    public ProductTm() {
    }

    public ProductTm(int code, String description, Button showMoreBtn, Button deleteBtn) {
        this.code = code;
        this.description = description;
        this.showMoreBtn = showMoreBtn;
        this.deleteBtn = deleteBtn;
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

    public Button getShowMoreBtn() {
        return showMoreBtn;
    }

    public void setShowMoreBtn(Button showMoreBtn) {
        this.showMoreBtn = showMoreBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }
}
