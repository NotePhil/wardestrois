package cmr.back.commun;

import cmr.back.commun.dto.ReponseMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractJeuDecorateur extends AbstractJeu8 {

    Logger logs = LoggerFactory.getLogger(AbstractJeuDecorateur.class);

    protected AbstractJeu8 abstractJeu8;

    /* On force les classes à redéfinir  ces méthodes */
    public abstract ReponseMove placePawn(Integer actifPlayer, String newPosition);

    public abstract ReponseMove movePawn( Integer actifPlayer, String newPosition, String oldPosition);

    /**
     * Positionnement des  3 pions par IA
     * @return
     */
    public ReponseMove placePawnAI(){
        String position ;
        ReponseMove reponseMove = new ReponseMove();
        int nbePion = this.getAbstractJeu8().getNbePawn();
        if ( nbePion == 0) {
            /* 1er pion sur le tableau*/
            position = place1erPawn();
            reponseMove.setOkToMove(true);
            reponseMove.setNextPosition(position);
        }
        else if ( nbePion == 1) {
            /* 1er pion de l'IA sur le tableau, 2eme à jouer*/
            position = placeMon1erPawn();
            reponseMove.setOkToMove(true);
            reponseMove.setNextPosition(position);
        }
        else if ( nbePion == 2) {
            /* 3eme pion sur le tableau, 2ème de l'IA */
            position = place2emePawn();
            reponseMove.setOkToMove(true);
            reponseMove.setNextPosition(position);
        }
        else if ( nbePion == 3) {
            /* 4eme pion sur le tableau, 2ème de l'IA car 2eme à jouer*/
            position = placeMon2emePawn();
            reponseMove.setOkToMove(true);
            reponseMove.setNextPosition(position);
        }
        else if ( nbePion == 4) {
            /* 5eme pion sur le tableau, 3eme de l'IA car 2eme à jouer*/
            position = place3emePawn();
            reponseMove.setOkToMove(true);
            reponseMove.setNextPosition(position);
        }
        else if ( nbePion == 5) {
            /* 5eme pion sur le tableau, 3eme de l'IA car 2eme à jouer*/
            position = placeMon3emePawn();
            reponseMove.setOkToMove(true);
            reponseMove.setNextPosition(position);
        }
        return reponseMove;
    }

    protected String canIWinNextStep(){
       String position = null;
        //on cherche toutes les positions de l'IA
        Predicate<Map.Entry<String, String>> pred = m -> m.getValue().equalsIgnoreCase(confJoueurs.get(this.getAbstractJeu8().getIdIA().toString()));
        String sum = this.getAbstractJeu8().matPaws.entrySet().stream().filter(pred).map(x -> x.getKey()).sorted(String::compareTo).collect(Collectors.joining());
        logs.info("[canIWinNextStep] positions IA trouvées {}",sum);
       //si c'est le 3eme pion de l'ia à positionner
        //on recherche dans la liste des posibilités gagnates celle qui se rapproche de la notre et on extrait la position à completer
        if(sum.length() == 2){
            String pos = listWin.stream().filter(x->x.contains(sum)).map(y-> y.split(sum)).map(z->
                    Arrays.stream(z).filter(a -> !a.contains(sum)).collect(Collectors.joining())).collect(Collectors.joining());

            position = pos;
        }
        logs.info("[canIWinNextStep] position retournée {}",position);
        return position;
    }

    protected abstract String place1erPawn();
    protected abstract String placeMon1erPawn();
    protected abstract String place2emePawn();
    protected abstract String placeMon2emePawn();
    protected abstract String place3emePawn();
    protected abstract String placeMon3emePawn();

    public AbstractJeu8 getAbstractJeu8() {
        return abstractJeu8;
    }

    public void setAbstractJeu8(AbstractJeu8 abstractJeu8) {
        this.abstractJeu8 = abstractJeu8;
    }
}
