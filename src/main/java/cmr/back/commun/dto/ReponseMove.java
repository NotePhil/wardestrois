package cmr.back.commun.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ReponseMove {
    private boolean okToMove;
    private boolean partyIsOver;
    private Integer winner;
    private String nextPosition;

    public ReponseMove(){}

    public boolean isOkToMove() {
        return okToMove;
    }

    public void setOkToMove(boolean okToMove) {
        this.okToMove = okToMove;
    }

    public boolean isPartyIsOver() {
        return partyIsOver;
    }

    public void setPartyIsOver(boolean partyIsOver) {
        this.partyIsOver = partyIsOver;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public String getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(String nextPosition) {
        this.nextPosition = nextPosition;
    }

    @Override
    public int  hashCode() { return HashCodeBuilder.reflectionHashCode(this);}

    @Override
    public boolean equals(Object obj){ return EqualsBuilder.reflectionEquals(this,obj); }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
