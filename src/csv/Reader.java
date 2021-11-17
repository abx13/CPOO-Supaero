package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import consommateurs.*;
import producteurs.*;


public class Reader {
    
    
    public static ArrayList<Consommateur> readAppareilConstant(String filename){
        ArrayList<Consommateur> appareils = new ArrayList<>(); 
        try{
            Scanner sc = new Scanner(new File(filename)); 
            
            while (sc.hasNext()) {  
                double puissMax = Double.parseDouble(sc.next());
                appareils.add(new AppareilConstant(puissMax));
            }   
            sc.close(); 
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
            e.printStackTrace();
        }  
        return appareils; 
    }

    public static ArrayList<Consommateur> readAppareilFrequentiel(String filename){
        ArrayList<Consommateur> appareils = new ArrayList<>(); 
        try{
            Scanner sc = new Scanner(new File(filename)); 
            
            while (sc.hasNext()) {  
                String res = sc.next();
                String[] s = res.split(";");
                double puissMax = Double.parseDouble(s[0]);
                double frequence = Double.parseDouble(s[1]);
                double tempsUtilisation = Double.parseDouble(s[2]);
                Integer[] plageUtilisation = new Integer[]{Integer.parseInt(s[3]), Integer.parseInt(s[4])};
                appareils.add(new AppareilFrequentiel(puissMax, frequence, tempsUtilisation, plageUtilisation));
            }   
            sc.close(); 
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
            e.printStackTrace();
        }  
        return appareils; 
    }


    public static ArrayList<Consommateur> readAppareilCyclique(String filename){
        ArrayList<Consommateur> appareils = new ArrayList<>(); 
        try{
            Scanner sc = new Scanner(new File(filename)); 
            
            while (sc.hasNext()) {  
                String res = sc.next();
                String[] s = res.split(";");
                Double[] d = new Double[s.length];
                for(int i = 0; i<s.length; i++){
                    d[i] = Double.parseDouble(s[i]);
                }
                double puissMax = d[0];
                HashMap<String, Double> moisActif = new HashMap<>(Map.ofEntries(Map.entry("winter", d[1] ), Map.entry("spring", d[2]), Map.entry("summer", d[3]),
                  Map.entry("fall", d[4])));
                Double[][] heureActif = new Double[][] { { d[5], d[6], d[7], d[8], d[9], d[10], d[11], d[12], d[13], d[14], d[15], d[16] },
                  { d[17], d[18], d[19], d[20], d[21], d[22], d[23], d[24], d[25], d[26], d[27], d[28] } };
               
                appareils.add(new AppareilCyclique(puissMax, moisActif, heureActif));
            }   
            sc.close(); 
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
            e.printStackTrace();
        }  
        return appareils; 
    }

    public static ArrayList<Producteur> readProducteurControle(String filename){
        ArrayList<Producteur> producteurs = new ArrayList<>(); 
        try{
            Scanner sc = new Scanner(new File(filename)); 
            
            while (sc.hasNext()) {  
                double puissMax = Double.parseDouble(sc.next());
                producteurs.add(new ProducteurControle(puissMax));
            }   
            sc.close(); 
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
            e.printStackTrace();
        }  
        return producteurs; 
    }

    public static ArrayList<Producteur> readProducteurCondExt(String filename){
        ArrayList<Producteur> producteurs = new ArrayList<>(); 
        try{
            Scanner sc = new Scanner(new File(filename)); 
            
            while (sc.hasNext()) {  
                String res = sc.next();
                String[] s = res.split(";");
                Double[] d = new Double[s.length];
                for(int i = 0; i<s.length; i++){
                    d[i] = Double.parseDouble(s[i]);
                }
                double puissMax = d[0];
                HashMap<String, Double> moisActif = new HashMap<>(Map.ofEntries(Map.entry("winter", d[1] ), Map.entry("spring", d[2]), Map.entry("summer", d[3]),
                  Map.entry("fall", d[4])));
                Double[][] heureActif = new Double[][] { { d[5], d[6], d[7], d[8], d[9], d[10], d[11], d[12], d[13], d[14], d[15], d[16] },
                  { d[17], d[18], d[19], d[20], d[21], d[22], d[23], d[24], d[25], d[26], d[27], d[28] } };
               
                producteurs.add(new ProducteurCondExt(puissMax, moisActif, heureActif));
            }   
            sc.close(); 
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
            e.printStackTrace();
        }  
        return producteurs; 
    }

    
    

}

