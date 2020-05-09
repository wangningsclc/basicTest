package com.util.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * @Auth wn
 * @Date 2019/12/18
 */
public class MonitorJobFileTransfer {

    static List<String> SERV_A = Arrays.asList(new String[]{"6","7"});
    static List<String> SERV_B = Arrays.asList(new String[]{"4","5"});
    static List<String> SERV_C = Arrays.asList(new String[]{"2","3"});
    static List<String> SERV_D = Arrays.asList(new String[]{"0","1"});
    public static void main(String[] args)  {
        File file = new File("E:\\work_jinshang\\nohup.out");
        try {
            String col1 = "Machine/Time";
            String col_a = "10.1.32.23";
            String col_b = "10.1.32.24";
            String col_c = "10.1.32.25";
            String col_d = "10.1.32.26";
            String col_e = "总处理数";
            printRow(col1, col_a, col_b, col_c, col_d, col_e);
            FileReader fileReader =  new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            Map<String, Long> map = new HashMap<>();
            List<String> all = new ArrayList<>();
            all.addAll(SERV_A);
            all.addAll(SERV_B);
            all.addAll(SERV_C);
            all.addAll(SERV_D);
            while ((line = br.readLine()) != null) {
                if (line.indexOf("TIME") >-1) {
                    if (map.containsKey("0")) {
                        col_a = getSumCun(map, SERV_A);
                        col_b = getSumCun(map, SERV_B);
                        col_c = getSumCun(map, SERV_C);
                        col_d = getSumCun(map, SERV_D);
                        col_e =  getSumCun(map, all);
                        printRow(col1, col_a, col_b, col_c, col_d, col_e);
                    }
                    col1 = line.substring(4);
                } else if (line.contains("处理数")) {
                    Long cnt = Long.valueOf(line.substring(line.indexOf("处理数:") + 4).trim());
                    map.put(line.substring(1, 2), cnt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getSumCun(Map<String, Long> map, List<String> serv) {
        Long cnt = 0l;
        for (String s : serv) {
            cnt += map.get(s);
        }
        return String.valueOf(cnt);
    }

    private static void printRow(String... col) {
        System.out.println();
         for (int i = 0; i<col.length; i++) {
             String row = col[i].trim();
             int blank =  20 - row.length();
             while (blank >0) {
                 row += " ";
                 blank --;
             }
             System.out.print(row);
         }
        System.out.println();
    }
}
