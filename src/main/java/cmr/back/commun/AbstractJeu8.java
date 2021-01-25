package cmr.back.commun;

import cmr.back.commun.dto.ReponseMove;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public abstract class AbstractJeu8 implements IJeu {

    public static int TOTAL_PAWN = 6;

    protected String id ;
    //niveau du joueur
    protected Integer level = 0;
    //solo ou duo
    protected Integer mode = 0;

    /* liste des combinaisons de déplacements possibles   */
    protected static Map<String, String[]> availableMoveTab = Map.ofEntries(
            entry("0", new String[]{"1","3"}),
            entry("1", new String[]{"0", "2"}),
            entry("2", new String[]{"1","5"}),
            entry("3", new String[]{"0", "6"}),
            entry("5", new String[]{"2", "8"}),
            entry("6", new String[]{"3", "7"}),
            entry("7", new String[]{"6", "8"}),
            entry("8", new String[]{"5", "7"})
    );

    public static Map<String, String> confJoueurs = Map.ofEntries(
            entry("1", "1"),
            entry("2", "4")
    );

    /* liste des combinaisons positions gagnantes */
    List<String> listWin = Arrays.asList("012","345","678","036","147","258","048","246");

    /* matrice bidimensionnel représentant l'état du jeu */
    protected Map<String, String> matPaws;
    protected Integer nbePawn = 0;

    //à l'initialisation du jeu, la valeur est toujours 1. Pour signifier que c'est le joueur qui s'est proposé premier qui joue
    protected Integer actifPlayer = 1;

    //précise si l'IA sera le premier joueur ou le deuxième
    protected Integer idIA;

    /* renvoie le prochain jour actif */
    protected IntFunction whoIsNext = (act) -> {
        Object[] tab = confJoueurs.keySet().toArray();
        return act == Integer.parseInt(tab[0].toString()) ? Integer.parseInt(tab[1].toString()) : Integer.parseInt(tab[0].toString()); };

    /* précise sur une cellule de la matrice est libre */
    public boolean isFree(String pos){
        return  (matPaws.get(pos) == null || matPaws.get(pos).isBlank())  ?  true : false;
    }



    /**
     * determine si un jour a aligné ses trois pions
     * @return renvoie l'id du gagnant
     * 1- filtre et retient uniquement les positions d'un joueur. les concatènent dans une chaine
     * 2- regarde si dans une liste de positions gagnantes on retrouve la chaine
     */
    private String calculIsTerminated(){
        Predicate<Map.Entry<String, String>> pred1 = m -> m.getValue().equalsIgnoreCase(confJoueurs.get("1"));
        Predicate<Map.Entry<String, String>> pred2 = m -> m.getValue().equalsIgnoreCase(confJoueurs.get("2"));
        String sum = this.matPaws.entrySet().stream().filter(pred1).map(x -> x.getKey()).sorted(String::compareTo).collect(Collectors.joining());
        if(listWin.contains(sum)){
            return confJoueurs.get("1");
        }

        sum = this.matPaws.entrySet().stream().filter(pred2).map(x -> x.getKey()).sorted(String::compareTo).collect(Collectors.joining());
        if(listWin.contains(sum)){
            return confJoueurs.get("2");
        }

        return "-1";
    }

    /* renvoie l'identifiant du jour qui a gagné */
    protected String isTerminated(){
        String i = calculIsTerminated();
        if(i == confJoueurs.get("1"))
            return "1";
        else if (i == confJoueurs.get("2"))
            return "2";
        else
            return i;
    }

    /* indique si un déplacement est autorisé */
    protected boolean isLegalMove(String newPos, String oldPos){
        if(oldPos == newPos){
            return false;
        }

        /*si on part / va du centre tout est possibe*/
        if (oldPos == "4" || newPos == "4") {
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

    /**
     * permet de placer un pion dans la matrice
     * @param pos position voulue
     * @param player id du joueur désirant la position
     * @return true si l'attribution a bien eu lieu
     */
    public ReponseMove placePawn(String pos, Integer player){
        ReponseMove reponseMove = new ReponseMove();
        if(getNbePawn()< AbstractJeu8.TOTAL_PAWN && this.isFree(pos)){
            this.matPaws.put(pos, confJoueurs.get(player));
        }
        else{
            reponseMove.setOkToMove(false);
            return reponseMove;
        }

        this.setNbePawn(this.getNbePawn() + 1);

        this.actifPlayer =  Integer.valueOf(whoIsNext.apply(actifPlayer).toString());
        reponseMove.setOkToMove(true);
        //vérifie s'il y a un gagnant
        if(this.getNbePawn()>= (TOTAL_PAWN-1)){
            String winner = this.isTerminated();
            if("1".equalsIgnoreCase(winner) || "2".equalsIgnoreCase(winner)){
                reponseMove.setPartyIsOver(true);
                reponseMove.setWinner(Integer.valueOf(winner));
            }
        }
        return reponseMove;

    }

    /**
     * permet de déplacer un pion dans la matrice
     * @param newPos position voulue
     * @param oldPos position de départ
     * @param player id du joueur désirant la position
     * @return true si l'attribution a bien eu lieu
     */
    public ReponseMove movePawn(String newPos, String oldPos, Integer player){
        ReponseMove reponseMove = new ReponseMove();
        if(this.isFree(newPos) && this.isLegalMove(newPos, oldPos) && (player == this.actifPlayer)){
            this.matPaws.put(newPos,confJoueurs.get(player));
            this.matPaws.put(oldPos,"");
            this.actifPlayer =  Integer.valueOf(whoIsNext.apply(actifPlayer).toString());
            reponseMove.setOkToMove(true);

            //vérifie s'il y a un gagnant
            if(this.getNbePawn()== (TOTAL_PAWN)){
                String winner = this.isTerminated();
                if("1".equalsIgnoreCase(winner) || "2".equalsIgnoreCase(winner)){
                    reponseMove.setPartyIsOver(true);
                    reponseMove.setWinner(Integer.valueOf(winner));
                }
            }
        }
        else{
            reponseMove.setOkToMove(false);
        }
        return reponseMove;

    }

    /* renvoie les pions et la position occupée */
    protected String pawnPosition(){
        String tabPos = this.matPaws.entrySet().stream().map(e -> {
            StringBuilder sb = new StringBuilder();
            if (e.getValue() != null && !e.getValue().isBlank()) {
                sb.append(e.getKey().concat(" :")).append(e.getValue());
            }
            return sb.toString();
        }).collect(Collectors.joining(" ,"));
        return tabPos;
    }

    /**
     *  Renvoie une position vide aléatoirement
     * @return
     */
    protected String giveRandomFreePosition(){
        String reponse = null;
        Random random = new Random();
        int pos = random.nextInt(10);
        int i = 0; //eviter de boucler à l'infini
        while(!this.isFree(String.valueOf(pos))){
            //TODO trouver un classe qui génére un entier et se souvient des précédents tirages
            pos = random.nextInt(10);
            if(++i >= 50) break;
        }
        if(i<50)
            reponse = String.valueOf(i);

        return reponse;
    }

    public Integer getNbePawn() {
        return nbePawn;
    }

    public void setNbePawn(Integer nbePawn) {
        this.nbePawn = nbePawn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getActifPlayer() {
        return actifPlayer;
    }

    public void setActifPlayer(int actifPlayer) {
        this.actifPlayer = actifPlayer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Integer getIdIA() {
        return idIA;
    }

    public void setIdIA(Integer idIA) {
        this.idIA = idIA;
    }
}
