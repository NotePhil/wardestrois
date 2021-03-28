package cmr.back.commun;

import cmr.back.commun.dto.ReponseMove;


/**
 * implémentation du comportement en mode solo
 */
public class JeuDuo extends AbstractJeuDecorateur {

    public JeuDuo(AbstractJeu8 jeu){
        this.setAbstractJeu8(jeu);
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
