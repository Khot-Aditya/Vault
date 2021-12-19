package com.app.vault.model;

public class DocumentsModel extends SelectedFilesModel {

    private String documentContents;
    private String files;
    private int tag;

    public DocumentsModel(){

    }

    public String getDocumentContent() {
        return documentContents;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContents = documentContent;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
