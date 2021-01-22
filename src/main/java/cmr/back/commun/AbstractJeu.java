package cmr.back.commun;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public abstract class AbstractJeu {
    protected static Map<Integer, Integer[]> availableMoveTab = Map.ofEntries(
            entry(0, new Integer[]{1,3}),
            entry(1, new Integer[]{0, 2}),
            entry(2, new Integer[]{1, 5}),
            entry(3, new Integer[]{0, 6}),
            entry(5, new Integer[]{2, 8}),
            entry(6, new Integer[]{3, 7}),
            entry(7, new Integer[]{6, 8}),
            entry(8, new Integer[]{5, 7})
    );

    protected static Map<Integer, Integer> confJoueurs = Map.ofEntries(
            entry(1, 1),
            entry(2, 4)
    );

    protected Integer[] matPaws;
    protected Integer nbePawn;
    protected Integer actifPlayer;

    protected IntFunction whoIsNext = (act) -> {
        Object[] tab = confJoueurs.keySet().toArray();
        return act == Integer.parseInt(tab[0].toString()) ? Integer.parseInt(tab[1].toString()) : Integer.parseInt(tab[0].toString()); };

    public boolean isFree(int pos){
       return  (matPaws[pos]==0) ?  true : false;
    }


    private Integer calculIsTerminated(){
        Integer sum = (this.matPaws[0] + this.matPaws[4] + this.matPaws[8])/3;
        if(sum ==  confJoueurs.get(1) || sum == confJoueurs.get(2)){
            return sum;
        }
        sum = (this.matPaws[2] + this.matPaws[4] + this.matPaws[6])/3;
        if(sum ==  confJoueurs.get(1) || sum == confJoueurs.get(2)){
            return sum;
        }
        for (int i = 0; i < 9; i = i + 3) {
            sum = (this.matPaws[i] + this.matPaws[i + 1] + this.matPaws[i + 2])/3;
            if(sum ==  confJoueurs.get(1) || sum == confJoueurs.get(2)){
                return sum;
            }
        }
        for (int i = 0; i <3; i++) {
            sum = (this.matPaws[i] + this.matPaws[i + 3] + this.matPaws[i + 6])/3;
            if(sum ==  confJoueurs.get(1) || sum == confJoueurs.get(2)){
                return sum;
            }
        }
        return -1;
    }

    protected Integer isTerminated(){
        Integer i = calculIsTerminated();
        if(i == confJoueurs.get(1))
            return 1;
        else if (i == confJoueurs.get(2))
            return 2;
        else
            return i;
    }

    protected boolean isLegalMove(Integer newPos, Integer oldPos){
        if(oldPos == newPos){
            return false;
        }

        /*si on part / va du centre tout est possibe*/
        if (oldPos == 4 || newPos == 4) {
            return true;
        } else {
            /*pour etre compatible a tous les implementations js on utilise pas indexof*/
            if (availableMoveTab.get(newPos)[0] == oldPos || availableMoveTab.get(newPos)[1] == oldPos) {
                return true;
            } else {
                return false;
            }
        }

    }

    protected boolean movePawn(Integer newPos, Integer oldPos, Integer player){
        if(this.isFree(newPos) && this.isLegalMove(newPos, oldPos) && (player == this.actifPlayer)){
            this.matPaws[newPos]=confJoueurs.get(player);
            this.matPaws[oldPos]=0;
            this.actifPlayer =  Integer.valueOf(whoIsNext.apply(actifPlayer).toString());
            return true;
        }
        else{ return false;}

    }

    protected boolean placePawn(int pos, Integer player){
        if(this.isFree(pos)){
            this.matPaws[pos]=confJoueurs.get(player);
        }
        else{ return false;}

        this.nbePawn++;

        this.actifPlayer =  Integer.valueOf(whoIsNext.apply(actifPlayer).toString());
        return true;

    }

    protected String pawnPosition(){
        String tabPos = Arrays.stream(this.matPaws).map(e -> e.toString()).collect(Collectors.joining(";"));
        return tabPos;
    }

}
