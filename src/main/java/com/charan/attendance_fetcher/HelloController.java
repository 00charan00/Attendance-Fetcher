package com.charan.attendance_fetcher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HelloController {




    @FXML
    private TableView<ModelClass> att_table;
    @FXML private Label pcount,acount;


    Runtime rt;


    @FXML
    protected void onHelloButtonClick() {


        Map<String,String> macList=new HashMap<>();
        macList.put("c2:8a:20:34:fd:48","Charan");
        macList.put("58:20:59:a7:5e:aa","Bineesh");
        macList.put("64:dd:e9:be:20:51","Bhuvanesh");
        macList.put("02:bf:d3:b7:db:2f","Sai Karthik");
        macList.put("46:5a:99:60:bd:27","Dindu Mahidar Sai");
        macList.put("46:5a:99:60:bd:28","Harry Potter");
        macList.put("46:5a:99:60:bd:29","Ron Weasley");
        macList.put("a2:83:53:06:f2:77","Haroon Rashid");
        macList.put("3a:b9:c4:2d:18:ea","Ilamathi");


        att_table.getColumns().clear();
        att_table.refresh();


        TableColumn<ModelClass, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ModelClass, Integer> ageColumn = new TableColumn<>("Status");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        att_table.getColumns().addAll(nameColumn, ageColumn);


        List<ModelClass> lit=new ArrayList<>();
        List<String> litTer=new ArrayList<>();




        rt=Runtime.getRuntime();
        try {
            Process p = rt.exec(new String[]{"arp","-a"});
            BufferedReader bf=new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while((line=bf.readLine())!=null){


                String regexPattern = "([0-9A-Fa-f]{2}-){5}[0-9A-Fa-f]{2}";

                Pattern pattern = Pattern.compile(regexPattern);
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String macAddress1 = matcher.group();
                    String macAddress=macAddress1.replace("-",":");
                    litTer.add(macAddress);

                }

            }

            Set<String> macSet= macList.keySet();
            for(String s:macSet){
                if(litTer.contains(s)){
                    lit.add(new ModelClass(macList.get(s),"PRESENT"));
                }else{
                    lit.add(new ModelClass(macList.get(s),"ABSENT"));
                }

            }
            int psize=lit.stream().filter(t->t.getStatus().equals("PRESENT")).toList().size();
            int asize=lit.stream().filter(t->t.getStatus().equals("ABSENT")).toList().size();
            pcount.setText("Present:"+psize);
            acount.setText("Absent:"+asize);




            ObservableList<ModelClass> data = FXCollections.observableArrayList(

                    lit
            );

            att_table.setItems(data);
            att_table.refresh();
            bf.close();


        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }




}