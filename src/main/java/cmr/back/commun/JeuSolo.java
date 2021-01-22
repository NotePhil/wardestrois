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
     * positionne un pion en vérifiant si les règles le permettent
     * @param actifPlayer joueur actif
     * @param newPosition position souhaitée
     * @return ReponseMove
     */
    public ReponseMove placePawn(Integer actifPlayer, String newPosition){
        logger.info("[JeuSolo] placePawn actifPlayer={}, newPosition={}",actifPlayer,newPosition);
        ReponseMove reponseMove = new ReponseMove();
        if( this.getAbstractJeu8().getNbePawn()< AbstractJeu8.TOTAL_PAWN && this.getAbstractJeu8().getActifPlayer() == actifPlayer){
            reponseMove = this.getAbstractJeu8().placePawn(newPosition,actifPlayer);
        }
        else {
            reponseMove.setOkToMove(false);
        }
        logger.info("[JeuSolo] placePawn reponse {}", reponseMove);
        return reponseMove;
    }

    /**
     * Déplacement un pion en vérifiant si les règles le permettent
     * @param actifPlayer joueur actif
     * @param newPosition position souhaitée
     * @param oldPosition ancienne position
     * @return ReponseMove
     */
    public ReponseMove movePawn( Integer actifPlayer, String newPosition, String oldPosition){
        logger.info("[JeuSolo] movePawn actifPlayer={}, newPosition={}, oldPosition={}",actifPlayer,newPosition,oldPosition);
        ReponseMove reponseMove = new ReponseMove();
        if(this.getAbstractJeu8().getActifPlayer() == actifPlayer){
            reponseMove = this.getAbstractJeu8().movePawn(newPosition,oldPosition,actifPlayer);
        }
        else {
            reponseMove.setOkToMove(false);
        }
        logger.info("[JeuSolo] movePawn reponse {}", reponseMove);
        return reponseMove;
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
