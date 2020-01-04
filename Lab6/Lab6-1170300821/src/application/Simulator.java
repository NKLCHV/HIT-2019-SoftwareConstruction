package application;

import ADT.Ladder;
import ADT.Monkey;
import com.google.common.collect.Lists;
import helper.LadderGenerator;
import helper.MonkeyGenerator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simulator {

  public static List<Ladder> ladders = Lists.newArrayList();
  public static List<Monkey> monkeys = Lists.newArrayList();

  public static void mai() {


    v3();


  }

  public static void v2() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please input the number of ladders:");
    int lnumber = Integer.parseInt(scanner.nextLine());
    System.out.println("Please input the height of ladders:");
    int lheight = Integer.parseInt(scanner.nextLine());
    System.out.println("Please input the number of monkeys:");
    int mnumber = Integer.parseInt(scanner.nextLine());
    System.out.println("Please input the max speed of monkeys:");
    int maxspeed = Integer.parseInt(scanner.nextLine());
    System.out.println("Please input the time interval to generate monkeys:");
    int timeinterval = Integer.parseInt(scanner.nextLine());
    System.out.println("Please input the number of monkeys of each generation:");
    int mpg = Integer.parseInt(scanner.nextLine());
    ladders = LadderGenerator.generateLadders(lnumber, lheight);
    int generations = mnumber / mpg;
    new Gui();
    int i = 0;
    for (i = 0; i < generations; i++) {
      try {
        Thread.sleep(timeinterval * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      List<Monkey> tmpgeneration = MonkeyGenerator.generateMonkeys(i, maxspeed, mpg);
      monkeys.addAll(tmpgeneration);
    }
    if (mnumber % mpg != 0) {
      try {
        Thread.sleep(timeinterval * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      monkeys.addAll(MonkeyGenerator.generateMonkeys(i, maxspeed, mnumber % mpg));
    }
    analysiser(mnumber);
  }

  public static void v3() {
    int lnumber = 4, lheight = 10, mnumber = 0, mpg;

    Scanner scanner = new Scanner(System.in);
    System.out.println("Please input the number of ladders:");
    lnumber = Integer.parseInt(scanner.nextLine());
    System.out.println("Please input the height of ladders:");
    lheight = Integer.parseInt(scanner.nextLine());
    
    Pattern pattern1 = Pattern.compile("n=(\\d+)");
    Pattern pattern2 = Pattern.compile("h=(\\d+)");
    Pattern pattern3 = Pattern.compile("monkey=<(\\d+),(\\d+),(\\S+),(\\d+)>");
    Map<Integer, ArrayList<String>> map = new HashMap<>();
    List<Monkey> monkeyList = new ArrayList<>();

    try {
      BufferedReader bfreader = new BufferedReader(new FileReader("txt/Competition_3.txt"));

      String templine = new String();
      while ((templine = bfreader.readLine()) != null) {
        templine = templine.trim();

        Matcher matcher1 = pattern1.matcher(templine);
        Matcher matcher2 = pattern2.matcher(templine);
        Matcher matcher3 = pattern3.matcher(templine);

        if (matcher1.matches()) {
          lnumber = Integer.valueOf(matcher1.group(1));
        }
        if (matcher2.matches()) {
          lheight = Integer.valueOf(matcher2.group(1));
        }
        //匹配猴子的信息，根据时间先后，添加到储存信息的map中
        if (matcher3.matches()) {
          int x = Integer.valueOf(matcher3.group(1));
          ArrayList<String> list = new ArrayList<>();
          if (map.keySet().contains(x)) {
            map.get(x).add(matcher3.group());
          } else {
            list.add(matcher3.group());
            map.put(x, list);
          }
        }
      }
      bfreader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    ladders = LadderGenerator.generateLadders(lnumber, lheight);

    int timeA = 0;
    int timeB = -1;
    int timeinterval = 0;

    //
    new Gui();

    for (int i = 0; i < 50; i++) {
      if (map.keySet().contains(i)) {
        //根据间隔时间停顿
        timeA = i;
        timeinterval = timeA - timeB;
        try {
          Thread.sleep(timeinterval * 1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        //生成这一批次的猴子
        ArrayList<String> list = map.get(i);
        for (String s : list) {
          Matcher matcher = pattern3.matcher(s);
          if (matcher.matches()) {
            mnumber++;
            int id = Integer.valueOf(matcher.group(2));
            String direcion = matcher.group(3);
            int speed = Integer.valueOf(matcher.group(4));
            //生成猴子，启动线程
            Monkey tempMonkey = new Monkey(id, direcion, speed);
            tempMonkey.setBirthday(System.currentTimeMillis());
            Thread tmpthread = new Thread(tempMonkey);
            tmpthread.start();

            monkeys.add(tempMonkey);
          }
        }
        timeB = timeA;
      }
    }

    analysiser(mnumber);
  }

  private static void analysiser(int mnumber) {
    try {
      Thread.currentThread().sleep(60000);
      System.out.println("All of the monkeys has passed river.");
      long sumtime = 0;
      Iterator<Monkey> mIterator = monkeys.iterator();
      while (mIterator.hasNext()) {
        Monkey monkey = (Monkey) mIterator.next();
        sumtime = sumtime + monkey.getTotalTime();
      }
      double rate = (double) mnumber / (double) sumtime;
      double sumfairness = 0;
      for (Monkey m : monkeys) {
        for (Monkey n : monkeys) {
          if ((n.getBirthday() - m.getBirthday()) * (n.getTotalTime() - m.getTotalTime()) > 0) {
            sumfairness = sumfairness + 1d;
          } else {
            sumfairness = sumfairness + 0d;
          }
        }
      }
      double fairness = (double) (mnumber * (mnumber - 1));
      fairness = fairness / 2;
      fairness = sumfairness / fairness;
      System.out.println("The IOrate is " + rate);
      System.out.println("The fairness is " + fairness);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
