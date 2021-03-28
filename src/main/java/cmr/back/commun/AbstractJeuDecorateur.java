package cmr.back.commun;

import cmr.back.commun.dto.ReponseMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractJeuDecorateur extends AbstractJeu8 {

    Logger logs = LoggerFactory.getLogger(AbstractJeuDecorateur.class);

    protected AbstractJeu8 abstractJeu8;

    /**
     * positionne un pion en vérifiant si les règles le permettent
     * en mode Solo, on détermine la position à joueur de l'IA
     * @param actifPlayer joueur actif
     * @param newPosition position souhaitée
     * @return ReponseMove
     */
    public ReponseMove positionnerPion(Integer actifPlayer, String newPosition){
        logs.info("[JeuSolo] positionnerPion actifPlayer={}, newPosition={}",actifPlayer,newPosition);
        ReponseMove reponseMove = this.getAbstractJeu8().positionnerPion(actifPlayer,newPosition);

        //si le pion autorisé à la position et la partie non gagnée
        if(reponseMove.isOkToMove() && !reponseMove.isPartyIsOver()){
            //proposition de la prochaine position
            ReponseMove reponseMove1 = placePawnAI();
            if(reponseMove1.isOkToMove()){
                //on positionne le pion de l'IA
                logs.debug("[JeuSolo] position de l'IA {}", reponseMove1);
                reponseMove = this.getAbstractJeu8().placePawn(reponseMove1.getNextPosition(),Integer.valueOf(this.getAbstractJeu8().whoIsNext.apply(actifPlayer).toString()));
            }
        }

        logs.info("[JeuSolo] positionnerPion reponse {}", reponseMove);
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
        logs.info("[JeuSolo] movePawn actifPlayer={}, newPosition={}, oldPosition={}",actifPlayer,newPosition,oldPosition);
        ReponseMove reponseMove = new ReponseMove();
        if(this.getAbstractJeu8().getActifPlayer() == actifPlayer){
            reponseMove = this.getAbstractJeu8().movePawn(newPosition,oldPosition,actifPlayer);

            //si le pion autorisé à la position et la partie non gagnée
            if(reponseMove.isOkToMove() && !reponseMove.isPartyIsOver()){
                //proposition de la prochaine position
                ReponseMove reponseMove1 = placePawnAI();
                if(reponseMove1.isOkToMove()){
                    //on positionne le pion de l'IA
                    logs.debug("[JeuSolo] movePawn position de l'IA {}", reponseMove1);
                    reponseMove = this.getAbstractJeu8().movePawn(reponseMove1.getNextPosition(),oldPosition,Integer.valueOf(this.getAbstractJeu8().whoIsNext.apply(actifPlayer).toString()));
                }
            }
        }

        logs.info("[JeuSolo] movePawn reponse {}", reponseMove);
        return reponseMove;
    }



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
     * @return position gagnante
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
           }).filter(a -> a.length()==1 && this.abstractJeu8.isFree(a,this.getAbstractJeu8().matPaws)).findFirst().orElse("");
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
                        if(this.abstractJeu8.isFree(obj[i],this.getAbstractJeu8().matPaws)  && this.abstractJeu8.isLegalMove(obj[i], sumObj[i].toString())){
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

    public String determinerMeilleurePosition(){
        //determiner si on gagne au prochain coup
        String position = canIWinNextStep(this.getIdIA().toString());
        if(position == null){
            //si non determiner si on perd au suivant
            position = canIWinNextStep(this.whoIsNext.apply(this.getIdIA()).toString());
        }
        if(position != null){
            //on retourne à la position indiquée
            return position;
        }else {
            //determiner les postions libres
            List<String> posFree = this.getAbstractJeu8().listFreePosition();
            //si le nombre de pion inférieur à 6, on itére sur les position libres
            posFree.parallelStream().map(x ->
                {
                    ReponseMove reponse =   this.placePawn(x,this.getAbstractJeu8().getIdIA());
                    if(reponse.isPartyIsOver()){
                        return reponse.getNextPosition();
                    }
                    else{
                        
                    }
                }
            )
            //si le nombre supérieur à 6
            //déterminer les positions libres peuvent

            //iterer sur chacune des positions, jusquà gagnant ou perdant (limite 2)
        }

        return null;
    }
}
