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
   
    private FileChooser fileChooser;
    private Stage fileStage=null;

    @FXML
    private Button codeql_excute_button;

    @FXML
    private  void initialize(){

        codeql_excute_button.setOnAction(event -> {
            //构建一个文件选择器
            fileChooser=new FileChooser();
            fileChooser.setTitle("Select CodeQl ExcutePath:");
            File selectedFile=fileChooser.showOpenDialog(fileStage);
            String path=selectedFile.getPath();
            if(path!=""){
                codeql_excute_button.setText(path);
            }

        });
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
