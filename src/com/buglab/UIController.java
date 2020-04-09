package com.buglab;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.File;
//https://www.w3cschool.cn/java/javafx-filechooser.html

public class UIController {

    private FileChooser fileChooser;
    private Stage fileStage=null;
    private String codeql_excute_path="",codeql_database_path="",codeql_source_path="";
    @FXML
    private Button codeql_excute_button,codeql_database_button,codeql_source_button;
    @FXML
    private Button codeql_excute_query,codeql_excute_upgrade,codeql_excute_create;
    @FXML
    private ListView mainListView;
    private ObservableList<String> listViewData = FXCollections.observableArrayList();

    @FXML
    private  void initialize(){
        //构建一个文件选择器
        fileChooser=new FileChooser();

        codeql_excute_button.setOnAction(event -> {
            fileChooser.setTitle("Select CodeQl ExcutePath:");
            File selectedFile=fileChooser.showOpenDialog(fileStage);
            codeql_excute_path=selectedFile.getPath();
            if(codeql_excute_path!=""){
                codeql_excute_button.setText(codeql_excute_path);
            }
        });

        codeql_database_button.setOnAction(event -> {
            DirectoryChooser folderChooser = new DirectoryChooser();
            folderChooser.setTitle("Select CodeQl DataBasePath:");
            File selectedDirectory = folderChooser.showDialog(fileStage);
            codeql_database_path=selectedDirectory.getPath();
            if(codeql_database_path!=""){
                codeql_database_button.setText(codeql_database_path);
                //罗列出数据库目录下 的所有数据库
                File[] files=codeql_database_path.listFiles();
                for(File file:files){
                    //判断是否是文件
                    if(file.isDirectory())
                        listViewData.add(file.getPath());
                }
                mainListView.setItems(listViewData);
            }
            
        });

        codeql_source_button.setOnAction(event -> {
            DirectoryChooser folderChooser = new DirectoryChooser();
            folderChooser.setTitle("Select CodeQl SourcePath:");
            File selectedDirectory = folderChooser.showDialog(fileStage);
            codeql_source_path=selectedDirectory.getPath();
            if(codeql_source_path!=""){
                codeql_source_button.setText(codeql_source_path);
            }
        });

        codeql_excute_query.setOnAction(event ->
        {
            System.out.println(codeql_excute_path+":"+codeql_database_path+"::"+codeql_source_path);
            if(codeql_excute_path!=null && codeql_database_path!="" && codeql_source_path!="")
            {
                

            }else
            {

                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("CodeQL参数未配置");
                alert.showAndWait();

            }

        });
    }

}
