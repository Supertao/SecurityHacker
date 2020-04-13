package com.buglab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
//https://www.w3cschool.cn/java/javafx-filechooser.html

public class UIController {

    private FileChooser fileChooser;
    private Stage fileStage = null;
    private File codeql_excute_path, codeql_database_path, codeql_source_path, codeql_ql_path;
    @FXML
    private Button codeql_excute_button, codeql_database_button, codeql_source_button, codeql_ql_button;
    @FXML
    private Button codeql_excute_query, codeql_excute_upgrade, codeql_excute_create;
    @FXML
    private ListView mainListView;
    @FXML
    private TreeView qlTreeview;
    private ObservableList<String> mainListData = FXCollections.observableArrayList();
    private ObservableList<String> qlListData = FXCollections.observableArrayList();
    @FXML
    private TextArea exeute_result;

    private Boolean NotEquals(String source, String database) {
        if (source.equals(database))
            return Boolean.FALSE;
        else
            return Boolean.TRUE;

    }

    public void SearchFile(ListView listView, ObservableList<String> listData, File path, Button button) {
        //一定要将listview的数据给清空
        listData.clear();
        if (path.getPath() != "") {
            button.setText(path.getPath());
            //罗列出数据库目录下 的所有数据库
            File[] files = path.listFiles();
            for (File file : files) {
                //判断是否是文件
                if (file.isDirectory()) {
                    String temp = file.getPath();
                    temp = temp.substring(temp.lastIndexOf(File.separator) + 1);
                    listData.add(temp);
                }

            }
            listView.setItems(listData);
        }

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
            SearchFile(mainListView, mainListData, codeql_database_path, codeql_database_button);

        });

        codeql_source_button.setOnAction(event -> {
            DirectoryChooser folderChooser = new DirectoryChooser();
            folderChooser.setTitle("Select CodeQl SourcePath:");
            codeql_source_path = folderChooser.showDialog(fileStage);
            if (codeql_source_path.getPath() != "") {
                codeql_source_button.setText(codeql_source_path.getPath());
            }
        });

        codeql_ql_button.setOnAction(event -> {
            DirectoryChooser folderChooser = new DirectoryChooser();
            folderChooser.setTitle("Select CodeQl QLPath:");
            codeql_ql_path = folderChooser.showDialog(fileStage);
            if (codeql_ql_path.getPath()!=""){
                codeql_ql_button.setText(codeql_ql_path.getPath());
                //SearchFile(qlListView, qlListData, codeql_ql_path, codeql_ql_button);
                //https://blog.csdn.net/luckGeek/article/details/88944089
                //create root
                //TreeItem<File> rootItem = new TreeItem<>(codeql_ql_path);
                if (codeql_ql_path.listFiles() != null) {
                    FileTreeItem fileTreeItem=new FileTreeItem(codeql_ql_path);
                    qlTreeview.setRoot(fileTreeItem.getNodesForDirectory(codeql_ql_path));
                }
            }

        });

        codeql_excute_query.setOnAction(event ->
        {
            System.out.println(codeql_excute_path + ":" + codeql_database_path + "::" + codeql_source_path);
            if (codeql_excute_path != null && codeql_database_path != null) {
                String[] cmd = new String[]{codeql_excute_path.getPath(), "query", "run", "-d=" + codeql_database_path.getPath(), "/Users/tao/Downloads/codeql-case/codeql/java/ql/src/java-sec/commandi/taint/find_taint_Runtimecommand.ql"};
                try {

                    InputStream inputStream = RuntimeUtils.runExec(cmd);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        exeute_result.appendText(line + "\r\n");

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
