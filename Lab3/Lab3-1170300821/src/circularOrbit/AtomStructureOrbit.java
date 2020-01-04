package circularOrbit;

import centralObject.AtomCentralObject;
import physicalObject.Electron;
import track.AtomStructureTrack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtomStructureOrbit extends ConcreteCircularOrbit {
    public String atomName = new String();
    public int electronTrackNumber = 0;//电子轨道数量
    public int[] electronTracks = new int[20];//记录每个轨道上电子数
    public Set<Electron> objectSet = new HashSet<>();//轨道上物体的集合
    public Set<AtomCentralObject> centralObjectSet = new HashSet<>();
    public Set<AtomStructureTrack> trackSet = new HashSet<>();//轨道的集合
    // public Map<AtomStructureTrack, Set<Electron>> relationMap = new HashMap<>();//轨道和物体的对应关系


    @Override
    public void readFiles(String filename) {
        try {
            BufferedReader bfReader = new BufferedReader(new FileReader(filename));
            Pattern pattern1 = Pattern.compile("ElementName\\s::=\\s([A-Z][a-z]?)");
            Pattern pattern2 = Pattern.compile("NumberOfTracks\\s::=\\s([\\d]+)");
            Pattern pattern3 = Pattern.compile("NumberOfElectron\\s::=\\s(\\d/\\d+;?)+");
            String temp = null;
            while ((temp = bfReader.readLine()) != null) {
                Matcher matcher1 = pattern1.matcher(temp);
                Matcher matcher2 = pattern2.matcher(temp);
                Matcher matcher3 = pattern3.matcher(temp);
                if (matcher1.matches())
                    atomName = matcher1.group(1);

                if (matcher2.matches()) {
                    electronTrackNumber = Integer.valueOf(matcher2.group(1));
                }

                if (matcher3.matches()) {
                    String[] temp1 = matcher3.group().split(" ");
                    String[] temp2 = temp1[temp1.length - 1].split(";");//取出电子分布的数据

                    for (int i = 0; i < temp2.length; i++) {
                        String[] temp3 = temp2[i].split("/");
                        electronTracks[Integer.valueOf(temp3[0])] = Integer.valueOf(temp3[1]);
                        for (int j = 0; j < Integer.valueOf(temp3[1]); j++)
                            objectSet.add(new Electron(atomName));
                    }
                }
            }
            for (int i = 1; i <= electronTrackNumber; i++) {
                AtomStructureTrack track = new AtomStructureTrack(i, atomName);
                trackSet.add(track);
            }
            bfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
