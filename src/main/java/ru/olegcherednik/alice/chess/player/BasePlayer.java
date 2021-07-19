package ru.olegcherednik.alice.chess.player;

import lombok.Getter;
import ru.olegcherednik.alice.chess.piece.Piece;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 18.07.2021
 */
abstract class BasePlayer implements Player {

    @Getter
    protected final Color color;
    protected final Map<Piece.PieceId, Piece> pieces;

    protected BasePlayer(Color color) {
        this.color = color;
        pieces = createPieces(color);
    }

    @Override
    public final Piece getPiece(Piece.PieceId id) {
        return pieces.getOrDefault(id, Piece.NULL);
    }

    private static Map<Piece.PieceId, Piece> createPieces(Color color) {
        Map<Piece.PieceId, Piece> pieces = new EnumMap<>(Piece.PieceId.class);

        for (Piece.PieceId id : Piece.PieceId.values())
            pieces.put(id, id.create(color));

        return pieces;
    }

}
