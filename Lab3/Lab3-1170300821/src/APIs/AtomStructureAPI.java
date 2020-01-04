package APIs;

import circularOrbit.AtomStructureOrbit;
import circularOrbit.CircularOrbit;
import ortherAPIs.AtomStructureDifference;
import ortherAPIs.Difference;
import physicalObject.Electron;
import track.AtomStructureTrack;

import java.util.Iterator;

public class AtomStructureAPI extends CircularOrbitAPIs {
    /**
     * 计算轨道分布的熵值
     * @param c
     * @return
     */
    @Override
    public double getObjectDistributionEntropy(CircularOrbit c) {
        AtomStructureOrbit C1 = (AtomStructureOrbit) c;
        if (C1.electronTrackNumber <= 0)
            return 0;
        double max = C1.electronTrackNumber;
        double min = 1;
        if (max == min) {
            return 0;
        }
        double[] list = new double[C1.electronTrackNumber];
        for (int i = 0; i < C1.electronTrackNumber; i++) {
            list[i] = ((i + 1) - min) / (max - min + 1);
        }

        double sum = 0;
        int sumOfObjects = C1.objectSet.size();
        for (int i = 0; i < list.length; i++) {
            sum += list[i] * C1.electronTracks[i];
        }

        for(int i = 0; i < list.length; i++){
            list[i] = list[i]/sum;
        }

        double k = 1/Math.log(sumOfObjects);
        double result = 0;
        for(int i = 0;i<list.length;i++){
            result += -k*list[i]*Math.log(list[i]);
        }

        return result;
    }

    public int getLogicalDistance(CircularOrbit c, int t1, int t2) {
        return Math.abs(t1 - t2);
    }

    public AtomStructureOrbit  removeElectronic(AtomStructureOrbit C, int i) {
        C.electronTracks[i]--;
        Iterator<Electron> it = C.objectSet.iterator();
        while (it.hasNext()) {
            it.remove();
            break;
        }
        return C;
    }

    @Override
    public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
        AtomStructureOrbit C1 = (AtomStructureOrbit) c1;
        AtomStructureOrbit C2 = (AtomStructureOrbit) c2;
        AtomStructureDifference difference = new AtomStructureDifference();
        difference.Dname = C1.atomName + "-" + C2.atomName;
        difference.DobjectsNum = C1.objectSet.size() - C2.objectSet.size();
        difference.DtracksNum = C1.trackSet.size() - C2.trackSet.size();

        int size = Math.max(C1.trackSet.size(), C2.trackSet.size());
        difference.Ddistributed = new int[size];
        for (int i = 0; i < size; i++) {
            difference.Ddistributed[i] = C1.electronTracks[i] - C2.electronTracks[i];
        }
        return super.getDifference(c1, c2);
    }

    public AtomStructureOrbit  electronictransition(AtomStructureOrbit C1, int t1, int t2) {
        if (C1.electronTracks[t1] > 0 && C1.electronTracks[t2] > 0) {
            C1.electronTracks[t1]--;
            C1.electronTracks[t2]++;
        }
        return C1;
    }
}
