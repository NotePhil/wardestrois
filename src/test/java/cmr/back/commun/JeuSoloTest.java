package cmr.back.commun;

import cmr.back.commun.dto.ReponseMove;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class JeuSoloTest {

    JeuSimple jeuSimple = new JeuSimple("id", 1,0, 0,1);

    @InjectMocks
    JeuSolo jeuSolo;

    @Before
    public void initJeuSolo(){
        jeuSolo.setAbstractJeu8(jeuSimple);
        jeuSolo.getAbstractJeu8().setNbePawn(0);
        jeuSolo.getAbstractJeu8().setLevel(0);
    }

    @Test
    public void place1erPawnTo9thPosition(){
        //forcer le pion à se place à la position 9 en occupant les 8 autres afin de s'assurer qu'on occupe toujours une place vide
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("2", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("6", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("7", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("8", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));

        ReponseMove pos = jeuSolo.placePawnAI();
        Assert.assertTrue("9".equalsIgnoreCase(pos.getNextPosition()));
    }

    @Test
    public void placeMon1erPawnTo9thPosition(){
        //forcer le pion à se place à la position 0 en occupant les 8 autres afin de s'assurer qu'on occupe toujours une place vide
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("2", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("6", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("7", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("8", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("9", jeuSolo.getAbstractJeu8().confJoueurs.get("1"));
        ReponseMove pos = jeuSolo.placePawnAI();
        Assert.assertTrue("0".equalsIgnoreCase(pos.getNextPosition()));
    }
}
