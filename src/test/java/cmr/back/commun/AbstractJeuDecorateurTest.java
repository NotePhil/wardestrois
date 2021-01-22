package cmr.back.commun;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

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
        //la position 7 est attendu
        String position = jeuSolo.canIWinNextStep();
        Assert.assertEquals(position,"7");
    }

    @Test
    public void testcanIWinNextStepFin(){
        //mettre le joueur 1 en position de gagnant au prochain tour.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("7",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5",jeuSolo.confJoueurs.get("1"));

        //joueur 2 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("2"));
        //la position 2 est attendu
        String position = jeuSolo.canIWinNextStep();
        Assert.assertEquals(position,"2");
    }

    @Test
    public void testcanIWinNextStepNoPosition(){
        //mettre le joueur 1 en position de gagnant au prochain tour.
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("3",jeuSolo.confJoueurs.get("1"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("5",jeuSolo.confJoueurs.get("1"));

        //joueur 2 perdant
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("0",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("1",jeuSolo.confJoueurs.get("2"));
        jeuSolo.getAbstractJeu8().matPaws.putIfAbsent("4",jeuSolo.confJoueurs.get("2"));
        //la position 2 est attendu
        String position = jeuSolo.canIWinNextStep();
        Assert.assertTrue(position.isBlank());
    }

}