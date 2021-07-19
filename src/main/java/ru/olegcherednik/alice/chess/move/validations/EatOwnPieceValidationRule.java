package ru.olegcherednik.alice.chess.move.validations;

import ru.olegcherednik.alice.chess.GameContext;
import ru.olegcherednik.alice.chess.exceptions.ChessException;
import ru.olegcherednik.alice.chess.move.Ply;
import ru.olegcherednik.alice.chess.piece.IPiece;

/**
 * @author Oleg Cherednik
 * @since 19.07.2021
 */
final class EatOwnPieceValidationRule implements ValidationRule {

    @Override
    public void validate(String strPly, GameContext context) {
        String fromCellId = Ply.getFromCellId(strPly);
        String toCellId = Ply.getToCellId(strPly);
        IPiece piece = context.getBoard().getCell(toCellId).getPiece();

        if (piece.getColor() == context.getCurrentPlayer())
            throw new ChessException(String.format("Piece '%s' on cell '%s' can't eat an own piece on cell '%s'",
                    piece.getType().getTitle(), fromCellId, toCellId));
    }

}
