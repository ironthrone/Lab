package com.guo.lab.storage;

class FileInfo {
    public String path;
    public long size;
    public String title;
    public String displayName;

    @Override
    public String toString() {
        return "FileInfo{" +
                "path='" + path + '\'' +
                ", size=" + size +
                ", title='" + title + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}