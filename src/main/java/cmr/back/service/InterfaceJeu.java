package cmr.back.service;

import cmr.back.commun.AbstractJeu8;
import cmr.back.commun.IJeu;
import cmr.back.commun.JeuDuo;
import cmr.back.commun.JeuSimple;
import cmr.back.commun.dto.ReponseMove;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class InterfaceJeu implements IInterfaceJeu {

    private static LoadingCache<String, AbstractJeu8> localCache = CacheBuilder.newBuilder().maximumSize(45000).expireAfterWrite(2, TimeUnit.DAYS).build(
            new CacheLoader<String, AbstractJeu8>() {
                @Override
                public JeuDuo load(String s) throws Exception {
                    //utiliser pour charger les précédents jeux depuis la bd
                    return null;
                }
            }
    );

    @Override
    public String initJeu(Integer actifPlayer, Integer level, Integer mode, Integer idIA) throws Exception {
       //génération de l'id du jeu
        String id = UUID.randomUUID().toString();
        AbstractJeu8 jeuDuo = new JeuSimple(id, actifPlayer,level, mode, idIA);
        jeuDuo = new JeuDuo(jeuDuo);
        localCache.put(id, jeuDuo);
        return id;
    }

    @Override
    public ReponseMove deplacerPion(String idJeu, Integer actifPlayer, String newPosition, String oldPosition) {
        AbstractJeu8 jeuDuo = localCache.getUnchecked(idJeu);
        if(jeuDuo != null){

        }
        return null;
    }

}
