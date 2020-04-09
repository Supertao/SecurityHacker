package com.buglab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
//https://www.w3cschool.cn/java/javafx-filechooser.html

public class UIController {

    private FileChooser fileChooser;
    private Stage fileStage = null;
    private File codeql_excute_path, codeql_database_path, codeql_source_path;
    @FXML
    private Button codeql_excute_button, codeql_database_button, codeql_source_button;
    @FXML
    private Button codeql_excute_query, codeql_excute_upgrade, codeql_excute_create;
    @FXML
    private ListView mainListView;
    private ObservableList<String> listViewData = FXCollections.observableArrayList();
    @FXML
    private TextArea exeute_result;

    private Boolean NotEquals(String source, String database) {
        if (source.equals(database))
            return Boolean.FALSE;
        else
            return Boolean.TRUE;

    }


    @FXML
    private void initialize() {
        //构建一个文件选择器
        fileChooser = new FileChooser();

        codeql_excute_button.setOnAction(event -> {
            fileChooser.setTitle("Select CodeQl ExcutePath:");
            codeql_excute_path = fileChooser.showOpenDialog(fileStage);

            if (codeql_excute_path.getPath() != "") {
                codeql_excute_button.setText(codeql_excute_path.getPath());
            }
        });

        codeql_database_button.setOnAction(event -> {
            DirectoryChooser folderChooser = new DirectoryChooser();
            folderChooser.setTitle("Select CodeQl DataBasePath:");
            codeql_database_path = folderChooser.showDialog(fileStage);
            //一定要将listview的数据给清空
            listViewData.clear();
            if (codeql_database_path.getPath() != "") {
                codeql_database_button.setText(codeql_database_path.getPath());

                //罗列出数据库目录下 的所有数据库
                File[] files = codeql_database_path.listFiles();
                for (File file : files) {
                    //判断是否是文件
                    if (file.isDirectory()) {
                        String temp = file.getPath();
                        temp = temp.substring(temp.lastIndexOf(File.separator) + 1);
                        listViewData.add(temp);
                    }

                }
                mainListView.setItems(listViewData);
            }

        });

        codeql_source_button.setOnAction(event -> {
            DirectoryChooser folderChooser = new DirectoryChooser();
            folderChooser.setTitle("Select CodeQl SourcePath:");
            codeql_source_path = folderChooser.showDialog(fileStage);
            if (codeql_source_path.getPath() != "") {
                codeql_source_button.setText(codeql_source_path.getPath());
            }
        });

        codeql_excute_query.setOnAction(event ->
        {
            System.out.println(codeql_excute_path + ":" + codeql_database_path + "::" + codeql_source_path);
            if (codeql_excute_path != null && codeql_database_path != null) {
                String[] cmd=new String[]{codeql_excute_path.getPath(),"version"};
                try {

                    InputStream inputStream = RuntimeUtils.runExec(cmd);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        exeute_result.appendText(line+"\r\n");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("CodeQL参数未配置");
                alert.showAndWait();

            }

        });
    }

}
