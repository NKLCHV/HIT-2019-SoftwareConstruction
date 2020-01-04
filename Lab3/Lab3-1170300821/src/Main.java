import APIs.TrackGameAPI;
import applications.AtomStructure;
import applications.SocialNetworkCircle;
import applications.TrackGame;
import centralObject.SocialNetworkCentralObject;
import circularOrbit.AtomStructureOrbit;
import circularOrbit.SocialNetworkCircleOrbit;
import circularOrbit.TrackGameOrbit;
import physicalObject.Athlete;
import physicalObject.Friend;
import track.GameTrack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        TrackGameOrbit C1 = new TrackGameOrbit();
        C1.readFiles("src\\text\\TrackGame.txt");
        System.out.println(C1.objectSet.size());
        System.out.println(C1.gameType);
        TrackGameAPI API = new TrackGameAPI();
        C1 = API.randomSort(C1);
        System.out.println(C1.gameGroup.size());
        System.out.println(C1.gameGroup.get(2).keySet().size());

    }

    public static void menu() {

    }

}
