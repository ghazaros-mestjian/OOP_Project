package core;

public abstract class Player {

    private int wallCount = 10;
    private Piece piece;

    public boolean make(Step st) {
        if(st.isWall)
            Board.setWall(st.x, st.y, st.isVertical);
        else
            piece.move(st.direction);
    }
}
