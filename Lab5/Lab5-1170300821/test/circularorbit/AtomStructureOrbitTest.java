package circularorbit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AtomStructureOrbitTest {

  private AtomStructureOrbit C1 = new AtomStructureOrbit();

  @Test
  public void test1() {
    C1.readFiles("test\\text\\AtomicStructure.txt");
    assertEquals("Rb", C1.atomName);
    assertEquals(5, C1.electronTrackNumber);
    assertEquals(5, C1.trackSet.size());
    assertEquals(0, C1.electronTracks[0]);
    assertEquals(2, C1.electronTracks[1]);
    assertEquals(8, C1.electronTracks[2]);
    assertEquals(18, C1.electronTracks[3]);
    assertEquals(8, C1.electronTracks[4]);
    assertEquals(1, C1.electronTracks[5]);
    assertEquals(0, C1.electronTracks[6]);

    assertEquals(5, C1.trackSet.size());
    assertEquals(37, C1.objectSet.size());
  }
}
