package cmr.back.commun;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class JeuSimple extends AbstractJeu8 {

    public JeuSimple(){}

    public JeuSimple(String id, Integer actifPlayer, Integer level, Integer mode, Integer idIA) {
        this.id = id;
        /*if(!confJoueurs.containsKey(actifPlayer)){
            throw new Exception("Problème d'initialisation du jeu");
        }*/
        this.actifPlayer = actifPlayer;
        this.level = level;
        this.mode = mode;
        this.idIA = idIA;
        this.matPaws = new LinkedHashMap<>();
        Integer[] matEta = new Integer[9];
        Arrays.fill(matEta,0);
        //tabEtat.put(0,matEta);
    }

}
