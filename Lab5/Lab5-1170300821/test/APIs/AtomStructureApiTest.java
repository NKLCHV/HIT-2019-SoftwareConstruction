package apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import circularorbit.AtomStructureOrbit;
import org.junit.jupiter.api.Test;
import ortherapis.AtomStructureDifference;

public class AtomStructureApiTest {
  @Test
  public void test1() {
    AtomStructureApi API = new AtomStructureApi();
    AtomStructureOrbit C1 = new AtomStructureOrbit();
    C1.readFiles("text/AtomicStructure.txt");

    assertEquals(0.11047871138209722, API.getObjectDistributionEntropy(C1));

    assertEquals(1, API.getLogicalDistance(C1, 1, 2));
    assertEquals(Integer.MAX_VALUE, API.getLogicalDistance(C1, 1, 8));

    API.electronictransition(C1, 4, 5);
    assertEquals(7, C1.electronTracks[4]);
    assertEquals(2, C1.electronTracks[5]);
    API.electronictransition(C1, 5, 4);
    assertEquals(8, C1.electronTracks[4]);
    assertEquals(1, C1.electronTracks[5]);

    AtomStructureOrbit C2 = new AtomStructureOrbit();
    C2.readFiles("test\\text\\AtomicStructure_Medium.txt");
    AtomStructureDifference difference = (AtomStructureDifference) API.getDifference(C1, C2);
    assertEquals("Rb-Er", difference.dname);

    C1 = API.removeElectronic(C1, 5);
    assertEquals(0, C1.electronTracks[5]);
  }
}
