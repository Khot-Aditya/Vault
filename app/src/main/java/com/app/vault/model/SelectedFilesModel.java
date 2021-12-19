package com.app.vault.model;

import com.app.vault.utils.CommonVariables;

public class SelectedFilesModel extends CommonVariables {

    private String fileName;
    private String fileSize;
    private String fileType;
    private String originalPath;
    private String movedPath;

    public SelectedFilesModel(){

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getMovedPath() {
        return movedPath;
    }

    public void setMovedPath(String movedPath) {
        this.movedPath = movedPath;
    }
}
