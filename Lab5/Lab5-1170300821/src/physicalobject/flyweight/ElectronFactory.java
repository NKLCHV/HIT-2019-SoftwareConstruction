package physicalobject.flyweight;

import java.util.HashMap;
import physicalobject.Electron;
import physicalobject.PhysicalObject;

public class ElectronFactory {

  private static final HashMap<String, PhysicalObject> electronMap = new HashMap<>();

  public static PhysicalObject getElectron(String atomName) {
    Electron electron = (Electron) electronMap.get(atomName);

    if (electron == null) {
      electron = new Electron(atomName);
      electronMap.put(atomName, electron);
    }
    return electron;
  }
}
