package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> textFile= readFile();
        HashMap<String, Vector<Vector<Integer>>> rawData = new HashMap<>();
        Vector<Integer> weeklyMin = new Vector<>();
        Vector<Integer> weeklyMax = new Vector<>();
        String cityname="";
        Vector<Vector<Integer>>  innerData= new Vector<>();
        for( String line:textFile){
            if(line.startsWith( "city" )){
                String city[] = line.split( ":" );
                cityname=city[1];
            }
            else if(line.startsWith( "min" ) || line.startsWith( "max" )){
                String minmax[]=line.split( " , " );
                String min[]=minmax[0].split( ":" );
                String max[]=minmax[1].split(":");
                weeklyMin.add(Integer.valueOf(min[1]) );
                weeklyMax.add(Integer.valueOf(max[1]));


            }
            else if (line.equals( "" )){
                innerData.add(weeklyMin);
                innerData.add( weeklyMax );
                rawData.put(cityname,innerData);
                weeklyMin = new Vector<>();
                weeklyMax = new Vector<>();
                innerData=new Vector<>();
            }

        }

       // System.out.println( rawData );

	while(true){
        System.out.println("MENU...");
        System.out.println("1.Display Weather");
        System.out.println("2.Hottest City");
        System.out.println("3.Coldest City");
        System.out.println("4.Exit");
        Scanner in = new Scanner( System.in );
        System.out.println(">>> ");
        int command = in.nextInt();
        String days[] = {"MONDAY", "TUESDAY", "WEDENESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

        switch (command){
            case 1:
                for(Map.Entry entry:rawData.entrySet()) {

                    System.out.println( "=============================" );
                    System.out.println( "City : " + entry.getKey() );
                    System.out.println( "=============================" );
                    System.out.println("");
                    Vector<Vector<Integer>> x = (Vector<Vector<Integer>>) entry.getValue();
                    Vector<Integer> mins = x.get( 0 );
                    Vector<Integer> maxs = x.get( 1 );
                    for (int i = 0; i < mins.size(); i++){
                        System.out.println(days[i] + " : " + "min = " + mins.get( i ) + "  " + "max = " + maxs.get( i ));
                    }
                    System.out.println("");
                }
                break;
            case 2:
                int hottest=0;
                String hday = "";
                String city = "";
                for(Map.Entry entry:rawData.entrySet()) {

                    Vector<Vector<Integer>> x = (Vector<Vector<Integer>>) entry.getValue();
                    Vector<Integer> maxs = x.get( 1 );
                    for (int i = 0; i < maxs.size(); i++){
                        if (maxs.get( i ) > hottest){
                            hottest = maxs.get( i );
                            hday = days[i];
                            city = (String) entry.getKey();
                        }
                    }

                }
                System.out.println("The hottest city is : "+ city + " on " + hday +" with temp of : " + hottest);
                break;
            case 3:
                int coldest=100;
                String cday = "";
                String ccity = "";
                for(Map.Entry entry:rawData.entrySet()) {

                    Vector<Vector<Integer>> x = (Vector<Vector<Integer>>) entry.getValue();
                    Vector<Integer> mins = x.get( 0 );
                    for (int i = 0; i < mins.size(); i++){
                        if (mins.get( i ) < coldest){
                            coldest = mins.get( i );
                            cday = days[i];
                            ccity = (String) entry.getKey();
                        }
                    }

                }
                System.out.println("The coldest city is : "+ ccity + " on " + cday + " with temp of : " + coldest);
                break;
            case 4:
                System.exit( 0 );
            default:
                break;
        }
    }
    }
private static ArrayList<String> readFile() {
        ArrayList<String > textFile = new ArrayList<>();
        try{
            FileInputStream fstream=new FileInputStream( "weather.txt" );
            BufferedReader br = new BufferedReader( new InputStreamReader( fstream ) );
            String strLine;
            while ((strLine=br.readLine()) != null){
                textFile.add( strLine );
            }
            fstream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return textFile;
 }
}
