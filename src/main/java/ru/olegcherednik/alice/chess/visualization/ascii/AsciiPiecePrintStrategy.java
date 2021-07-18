package ru.olegcherednik.alice.chess.visualization.ascii;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.olegcherednik.alice.chess.Game;
import ru.olegcherednik.alice.chess.Piece;
import ru.olegcherednik.alice.chess.visualization.PiecePrintStrategy;

import java.io.PrintStream;

/**
 * @author Oleg Cherednik
 * @since 18.07.2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AsciiPiecePrintStrategy implements PiecePrintStrategy, AsciiPrintStrategy {

    public static final AsciiPiecePrintStrategy INSTANCE = new AsciiPiecePrintStrategy();

    @Override
    public void print(Piece piece, PrintStream out) {
        if (piece == Piece.EMPTY)
            out.print(' ');
        else if (piece.getTeam() == Game.Team.BLACK)
            out.print(Character.toUpperCase(piece.getType().getAscii()));
        else if (piece.getTeam() == Game.Team.WHITE)
            out.print(Character.toLowerCase(piece.getType().getAscii()));
        else
            out.print(' ');
    }
}
