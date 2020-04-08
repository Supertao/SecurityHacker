package com.buglab;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
//https://www.w3cschool.cn/java/javafx-filechooser.html

public class UIController {
    @FXML
    private Button codeql_excute_bt,codeql_database_bt,codeql_source_bt;
    private FileChooser fileChooser;
    private Stage fileStage=null;

    @FXML
    public void codeql_excute_click()
    {
        codeql_excute_bt=new Button();
        //构建一个文件选择器
        fileChooser=new FileChooser();
        fileChooser.setTitle("Select CodeQl ExcutePath:");
        File selectedFile=fileChooser.showOpenDialog(fileStage);
        String path=selectedFile.getPath();
        System.out.println(path);
        codeql_excute_bt.setLabel(path);

    }

    @FXML
    public void codeql_database_click()
    {

        DirectoryChooser folderChooser = new DirectoryChooser();
        folderChooser.setTitle("Choose Folder");
        File selectedDirectory = folderChooser.showDialog(fileStage);

        System.out.println(selectedDirectory);
    }




}
