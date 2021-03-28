package cmr.back.commun;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class AbstractJeuDecorateurTest {

    @InjectMocks
    JeuSolo jeuSolo;

    JeuSimple jeuSimple = new JeuSimple("id", 1,0, 0,1);

    @Before
    public void initJeuSolo(){
        jeuSolo.setAbstractJeu8(jeuSimple);
        jeuSolo.getAbstractJeu8().setNbePawn(0);
        jeuSolo.getAbstractJeu8().setLevel(0);
    }

    @Test
    public void testcanIWinNextStepDebut(){
        //mettre le joueur 1 en position de gagnant au prochain tour.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("2",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5",jeuSolo.confJoueurs.get("1"));

        //joueur 2 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("2"));
        //la position 8 est attendu
        String position = jeuSolo.canIWinNextStep("1");
        Assert.assertEquals(position,"8");
    }

    @Test
    public void testcanIWinNextStepFin(){
        //mettre le joueur 1 en position de gagnant au prochain tour.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("8",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5",jeuSolo.confJoueurs.get("1"));

        //joueur 2 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("2"));
        //la position 2 est attendu
        String position = jeuSolo.canIWinNextStep("1");
        Assert.assertEquals(position,"2");
    }

    @Test
    public void testcanIWinNextStepNoPosition(){
        //mettre le joueur 1 en position de gagnant au prochain tour.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("2",jeuSolo.confJoueurs.get("1"));

        //joueur 2 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("2"));
        //Aucune position gagnante
        String position = jeuSolo.canIWinNextStep("1");
        Assert.assertTrue(position.isBlank());
    }

    @Test
    public void testcanIWinNextStep4(){
        //mettre le joueur 1 en position de gagnant au prochain tour à la position 4.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("7",jeuSolo.confJoueurs.get("1"));

        //joueur 2 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5",jeuSolo.confJoueurs.get("2"));
        //la position 4 est attendu
        String position = jeuSolo.canIWinNextStep("1");
        Assert.assertEquals(position,"4");
    }

    @Test
    public void testcanIWinNextStep2ButBusy(){
        //la position renvoyé comme gagnante est occupée. donc on renvoie vide finalement
        //mettre le joueur 1 en position de gagnant au prochain tour.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("8",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5",jeuSolo.confJoueurs.get("1"));

        //joueur 2 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("2",jeuSolo.confJoueurs.get("2"));
        //la position 2 est attendu
        String position = jeuSolo.canIWinNextStep("1");
        Assert.assertTrue(position.isBlank());
    }

    @Test
    public void testcanIWinNextStepWith3PawnPos0(){
        //mettre le joueur 1 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("2",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5",jeuSolo.confJoueurs.get("1"));

        //joueur 2  en position de gagnant au prochain tour.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("6",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("2"));
        //la position 0 est attendu
        String position = jeuSolo.canIWinNextStep("2");
        Assert.assertEquals(position,"0");
    }

    @Test
    public void testcanIWinNextStepWith3PawnNoPos0Busy(){
        //mettre le joueur 1 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5",jeuSolo.confJoueurs.get("1"));

        //joueur 2  en position de gagnant au prochain tour.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("6",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("2"));
        //la position 0 est attendu mais la position occupé dc pas gagnant
        String position = jeuSolo.canIWinNextStep("2");
        Assert.assertTrue(position.isBlank());
    }

    @Test
    public void testcanIWinNextStepWith3PawnPos4(){
        //mettre le joueur 1
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("8",jeuSolo.confJoueurs.get("1"));

        //joueur 2
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("6",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("2",jeuSolo.confJoueurs.get("2"));
        //position 4 gagnante
        String position = jeuSolo.canIWinNextStep("2");
        Assert.assertEquals(position,"4");
        //position 4 gagnante
        position = jeuSolo.canIWinNextStep("1");
        Assert.assertEquals(position,"4");
    }

    @Test
    public void testcanIWinNextStepWith3PawnPos7With2Possibilities(){
        //mettre le joueur 1
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("8",jeuSolo.confJoueurs.get("1"));

        //joueur 2
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("6",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("2",jeuSolo.confJoueurs.get("2"));
        //position 4 gagnante
        String position = jeuSolo.canIWinNextStep("2");
        Assert.assertTrue(position.isBlank());
        //position 4 gagnante
        position = jeuSolo.canIWinNextStep("1");
        Assert.assertEquals(position,"7");
    }

    @Test
    public void testPostionnerPion(){

    }
}
