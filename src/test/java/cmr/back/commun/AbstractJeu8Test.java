package cmr.back.commun;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractJeu8Test {

    @InjectMocks
    JeuDuo abstractJeu8;

    @Before
    public void init(){

    }

    @Test
    public void testCalculIsTerminatedPlayer1(){
        //mettre le joueur 1 en position de gagnant
        abstractJeu8.matPaws.putIfAbsent("0",abstractJeu8.confJoueurs.get("1"));
        abstractJeu8.matPaws.putIfAbsent("3",abstractJeu8.confJoueurs.get("1"));
        abstractJeu8.matPaws.putIfAbsent("6",abstractJeu8.confJoueurs.get("1"));
        //joueur 2 perdant
        abstractJeu8.matPaws.putIfAbsent("1",abstractJeu8.confJoueurs.get("2"));
        abstractJeu8.matPaws.putIfAbsent("5",abstractJeu8.confJoueurs.get("2"));
        abstractJeu8.matPaws.putIfAbsent("2",abstractJeu8.confJoueurs.get("2"));

        String winner = abstractJeu8.isTerminated();
        Assert.assertEquals(winner,"1");
    }

    @Test
    public void testCalculIsTerminatedPlayer2(){
        //mettre le joueur 2 en position de gagnant
        abstractJeu8.matPaws.putIfAbsent("3",abstractJeu8.confJoueurs.get("2"));
        abstractJeu8.matPaws.putIfAbsent("4",abstractJeu8.confJoueurs.get("2"));
        abstractJeu8.matPaws.putIfAbsent("5",abstractJeu8.confJoueurs.get("2"));
        //joueur 1 perdant
        abstractJeu8.matPaws.putIfAbsent("1",abstractJeu8.confJoueurs.get("1"));
        abstractJeu8.matPaws.putIfAbsent("7",abstractJeu8.confJoueurs.get("1"));
        abstractJeu8.matPaws.putIfAbsent("2",abstractJeu8.confJoueurs.get("1"));

        String winner = abstractJeu8.isTerminated();
        Assert.assertEquals(winner,"2");
    }

    @Test
    public void testCalculIsTerminatedNoPlayer(){
        //mettre le joueur 2 en position neutre
        abstractJeu8.matPaws.putIfAbsent("8",abstractJeu8.confJoueurs.get("2"));
        abstractJeu8.matPaws.putIfAbsent("4",abstractJeu8.confJoueurs.get("2"));
        abstractJeu8.matPaws.putIfAbsent("5",abstractJeu8.confJoueurs.get("2"));
        //joueur 1 neutre
        abstractJeu8.matPaws.putIfAbsent("1",abstractJeu8.confJoueurs.get("1"));
        abstractJeu8.matPaws.putIfAbsent("7",abstractJeu8.confJoueurs.get("1"));
        abstractJeu8.matPaws.putIfAbsent("2",abstractJeu8.confJoueurs.get("1"));

        String winner = abstractJeu8.isTerminated();
        Assert.assertEquals(winner,"-1");
    }
}
