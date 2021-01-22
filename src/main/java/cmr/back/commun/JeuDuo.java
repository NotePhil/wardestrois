package cmr.back.commun;

import cmr.back.commun.dto.ReponseMove;


/**
 * implémentation du comportement en mode solo
 */
public class JeuDuo extends AbstractJeuDecorateur {

    public JeuDuo(AbstractJeu8 jeu){
        this.setAbstractJeu8(jeu);
    }

    /**
     * positionne un pion en vérifiant si les règles le permettent
     * @param actifPlayer joueur actif
     * @param newPosition position souhaitée
     * @return ReponseMove
     */
    @Override
    public ReponseMove placePawn(Integer actifPlayer, String newPosition) {
        ReponseMove reponseMove = new ReponseMove();
        if( this.getAbstractJeu8().getNbePawn()< AbstractJeu8.TOTAL_PAWN && this.getAbstractJeu8().getActifPlayer() == actifPlayer){
            reponseMove = this.getAbstractJeu8().placePawn(newPosition,actifPlayer);
        }
        else {
            reponseMove.setOkToMove(false);
        }
        return reponseMove;
    }

    @Override
    public ReponseMove movePawn(Integer actifPlayer, String newPosition, String oldPosition) {
        return null;
    }

    @Override
    protected String place1erPawn() {
        return null;
    }

    @Override
    protected String placeMon1erPawn() {
        return null;
    }

    @Override
    protected String place2emePawn() {
        return null;
    }

    @Override
    protected String placeMon2emePawn() {
        return null;
    }

    @Override
    protected String place3emePawn() {
        return null;
    }

    @Override
    protected String placeMon3emePawn() {
        return null;
    }



   /* public LinkedHashMap<Integer, Integer[]> getTabEtat() {
        return tabEtat;
    }

    public void setTabEtat(LinkedHashMap<Integer, Integer[]> tabEtat) {
        this.tabEtat = tabEtat;
    }*/
}
