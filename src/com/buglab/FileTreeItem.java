package com.buglab;
//https://blog.csdn.net/moakun/article/details/88912740

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;

public class FileTreeItem extends TreeItem<String> {

    private  File filex=null;

    public FileTreeItem(File file) {
        super(FileIcon.getFileName(file), FileIcon.getFileIconToNode(file));
        this.filex=file;
    }



    public File getFile()
    {
        return filex;
    }



    public  TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<>(directory.getName());
        for(File f : directory.listFiles()) {
            //System.out.println("Loading " + f.getName());
            if(f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }
        return root;
    }




}

