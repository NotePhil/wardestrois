package cmr.back.commun;

import cmr.back.commun.dto.ReponseMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
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

    //confJoueurs.get(this.getAbstractJeu8().getIdIA().toString())

    /**
     * méthode permettant de determiner si au prochain coup l'idPlayer gagnera
     * @param idPlayer
     * @return
     */
    protected String canIWinNextStep(String idPlayer){
       String position = null;
        //on cherche toutes les positions de l'IA
        Predicate<Map.Entry<String, String>> pred = m -> m.getValue().equalsIgnoreCase(confJoueurs.get(idPlayer));
        Object[] sumObj = this.getAbstractJeu8().matPaws.entrySet().stream().filter(pred).map(x -> x.getKey()).sorted(String::compareTo).toArray();
        logs.info("[canIWinNextStep] positions IA trouvées");
        Arrays.stream(sumObj).forEach(s -> logs.info(s.toString()));
       //si c'est le 3eme pion  à positionner
        //on recherche dans la liste des posibilités gagnates celle qui se rapproche de la notre et on extrait la position à completer
        if(sumObj.length == 2){
           //pour chaque combinaison gagnante, on extrait l'éventuelle position gagnante en supprimant les positions occupées
           String pos = listWin.stream().map(x -> {
               String item = x.replaceFirst(sumObj[0].toString(), "");
               item = item.replaceFirst(sumObj[1].toString(), "");

               return item;
           }).filter(a -> a.length()==1 && this.abstractJeu8.isFree(a)).findFirst().orElse("");
           position = pos;
        }
        else  if(sumObj.length == 3) {
            String pos = listWin.stream().map(x -> {
                String[] obj = x.split("");
                String resultat = new String();
                int diff=0;
                int iso=0;
                //on parcoure les deux chaines en parallèle
                //recherche des positions différentes entre les combinaisons gagnantes et les positions occupées par le joueur
                for (int i = 0; i < obj.length; i++) {
                    if(!obj[i].equalsIgnoreCase(sumObj[i].toString())){
                        if(this.abstractJeu8.isFree(obj[i])  && this.abstractJeu8.isLegalMove(obj[i], sumObj[i].toString())){
                            resultat= obj[i];
                            diff++;
                        }
                    }
                    else {
                        iso++;
                    }
                }
                //on ne doit avoir qu'une position différente et deux identiques.
                if(diff==1 && iso==2){
                    return resultat;
                }
                return "";
            }).filter(a -> !a.isBlank()).findFirst().orElse("");
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
