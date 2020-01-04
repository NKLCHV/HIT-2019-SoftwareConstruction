package centralobject.nucleon;

import java.util.Hashtable;

public class NucleonCache {

  private static Hashtable<String, Nucleon> nucleonMap = new Hashtable<>();

  public static Nucleon getNucleon(String id) {
    Nucleon nucleon = nucleonMap.get(id);
    return (Nucleon) nucleon.clone();
  }

  public static void loadmap(String atomName) {
    Proton proton = new Proton();
    proton.setAtomName(atomName);
    nucleonMap.put("1", proton);

    Neutron neutron = new Neutron();
    neutron.setAtomName(atomName);
    nucleonMap.put("0", neutron);
  }
}
