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
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.control.TreeItem;

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
            //System.out.println(codeql_ql_path.getPath());
            if (codeql_ql_path!=null){
                codeql_ql_button.setText(codeql_ql_path.getPath());
                //SearchFile(qlListView, qlListData, codeql_ql_path, codeql_ql_button);
                //https://blog.csdn.net/luckGeek/article/details/88944089
                //create root
                //TreeItem<File> rootItem = new TreeItem<>(codeql_ql_path);
                if (codeql_ql_path.listFiles() != null) {
                    Path filepath= Paths.get(codeql_ql_path.toURI());
                    PathItem pathItem=new PathItem(filepath);
                    qlTreeview.setRoot(createNode(pathItem));
                    ContextMenu menu = new ContextMenu();
                    MenuItem addItem = new MenuItem("Run");
                    addItem.setOnAction(e ->{
                        //createNode(pathItem).getChildren().add(new TreeItem<>("节点:" + treeItem.getChildren().size()));
                        TreeItem<PathItem> selectItem=(TreeItem<PathItem>) qlTreeview.getSelectionModel().getSelectedItem();
                        //System.out.println(selectItem.getValue().getPath());
                        Path selectPath=selectItem.getValue().getPath();

                        //如果是ql
                        Boolean isQl=selectPath.getFileName().toString().endsWith("ql");

                        if(isQl&&selectPath!=null){

                            String[] cmd = new String[]{codeql_excute_path.getPath(), "query", "run", "-d=" + codeql_database_path.getPath(),selectPath.toString()};
                            System.out.println(cmd);
                            ExcuteCmd(cmd);
                        }else
                        {
                            ///Users/tao/Desktop/codeql/codeql database  run-queries /Users/tao/Downloads/codeql-case/java-sec /Users/tao/Downloads/codeql-case/codeql/java/ql/src/Security
                            String[] cmd = new String[]{codeql_excute_path.getPath(), "database", "run-queries", codeql_database_path.getPath(),selectPath.toString()};
                            System.out.println(cmd);

                            ExcuteCmd(cmd);
                        }
                        

                    });
                    menu.getItems().add(addItem);
                    qlTreeview.setContextMenu(menu);

                }
            }else {
                System.exit(1);
            }

        });

        codeql_excute_query.setOnAction(event ->
        {
            System.out.println(codeql_excute_path + ":" + codeql_database_path + "::" + codeql_source_path);
            if (codeql_excute_path != null && codeql_database_path != null) {
                String[] cmd = new String[]{codeql_excute_path.getPath(), "query", "run", "-d=" + codeql_database_path.getPath(), "/Users/tao/Downloads/codeql-case/codeql/java/ql/src/java-sec/commandi/taint/find_taint_Runtimecommand.ql"};
                ExcuteCmd(cmd);

            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("CodeQL参数未配置");
                alert.showAndWait();

            }

        });
    }

    private TreeItem<PathItem> createNode(PathItem pathItem){
        return PathTreeItem.createNode(pathItem);
    }

    private void ExcuteCmd(String[] cmd){

        try {

            InputStream inputStream = RuntimeUtils.runExec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                exeute_result.appendText(line + "\r\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
