package cmr.back.commun;

import cmr.back.commun.dto.ReponseMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * implémentation du comportement en mode solo
 */
public class JeuSolo extends AbstractJeuDecorateur {

    Logger logger = LoggerFactory.getLogger(JeuSolo.class);

    public JeuSolo(AbstractJeu8 jeu){
        this.setAbstractJeu8(jeu);
    }


    /**
     * Pour le niveau débutant
     * Positionnement du 1er pion aléatoirement
     * @return
     */
    protected String place1erPawn(){
        //niveau debutant
        return this.getAbstractJeu8().giveRandomFreePosition();
    }

    /**
     * Pour le niveau débutant
     * Positionnement du 1er pion de l'IA apres son adversaire aléatoirement
     * @return
     */
    protected String placeMon1erPawn(){
        return this.getAbstractJeu8().giveRandomFreePosition();
    }

    /**
     * Pour le niveau débutant
     * Positionnement du 2eme pion aléatoirement
     * @return
     */
    protected String place2emePawn(){
        return this.getAbstractJeu8().giveRandomFreePosition();
    }

    /**
     * Pour le niveau débutant
     * Positionnement du 2eme pion de l'IA apres son adversaire aléatoirement
     * @return
     */
    protected String placeMon2emePawn(){
        return this.getAbstractJeu8().giveRandomFreePosition();
    }

    /**
     * Pour le niveau débutant
     * Positionnement du 3eme pion aléatoirement
     * @return
     */
    protected String place3emePawn(){
        return this.getAbstractJeu8().giveRandomFreePosition();
    }

    /**
     * Pour le niveau débutant
     * Positionnement du 3eme pion de l'IA apres son adversaire aléatoirement
     * @return
     */
    protected String placeMon3emePawn(){
        return this.getAbstractJeu8().giveRandomFreePosition();
    }

}
