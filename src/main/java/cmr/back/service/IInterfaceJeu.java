package cmr.back.service;

import cmr.back.commun.dto.ReponseMove;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IInterfaceJeu {
    @GetMapping(value = "/warof3/{actifPlayer}/{level}/{mode}/{idIA}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String initJeu(@PathVariable(name = "actifPlayer", required =true) Integer actifPlayer, @PathVariable(name = "level", required =true) Integer level,@PathVariable(name = "mode", required =true) Integer mode, @PathVariable(name = "idIA", required =true) Integer idIA) throws Exception;

    @GetMapping(value = "/warof3/{actifPlayer}/{level}/{mode}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ReponseMove deplacerPion(@RequestHeader(name = "idJeu") String idJeu, @PathVariable(name = "actifPlayer", required =true) Integer actifPlayer, @PathVariable(name = "newPosition", required =true) String newPosition, @PathVariable(name = "oldPosition") String oldPosition);
}
