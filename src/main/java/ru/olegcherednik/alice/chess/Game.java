package ru.olegcherednik.alice.chess;

import lombok.Builder;
import lombok.NonNull;
import ru.olegcherednik.alice.chess.exceptions.ChessException;
import ru.olegcherednik.alice.chess.move.Ply;
import ru.olegcherednik.alice.chess.move.Processor;
import ru.olegcherednik.alice.chess.player.Player;
import ru.olegcherednik.alice.chess.visualization.BoardPrintStrategy;
import ru.olegcherednik.alice.chess.visualization.ascii.AsciiBoardPrintStrategy;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Oleg Cherednik
 * @since 18.07.2021
 */
public final class Game implements GameContext {

    private final InitialContext context;
    private final Scanner scan;
    private final Player playerWhite;
    private final Player playerBlack;
    private final Board board;
    private final Processor processor;

    public Game(InitialContext context) {
        this.context = context;
        scan = new Scanner(context.in);
        playerWhite = context.createWhitePlayer();
        playerBlack = context.createBlackPlayer();
        board = new Board(playerBlack, playerWhite);
        processor = new Processor(playerWhite);
    }

    public void start() throws InterruptedException {
        context.out.println("LET'S PLAY CHESS!!!");
        context.out.println("Player 1 (White) vs Player 2 (Black)");
        context.out.println();

        while (true) {
            try {
                print();

                Ply ply = processor.doNextPly(this);
                Board.Cell cellFrom = board.getCell(ply.getFromCellId());
                Board.Cell cellTo = board.getCell(ply.getToCellId());

                cellTo.setPiece(cellFrom.getPiece());
                cellFrom.clear();



                processor.switchToPlayer(playerWhite == processor.getPlayer() ? playerBlack : playerWhite);
            } catch (ChessException e) {
                context.err.println(e.getMessage());
                Thread.sleep(200);
            }
        }
    }

    private void print() {
        context.boardPrintStrategy.print(board, context.out);
        context.out.println();
    }


    @Override
    public PrintStream out() {
        return context.out;
    }

    @Override
    public Scanner scan() {
        return scan;
    }

    @Override
    public Board.Cell cell(String cellId) {
        return board.getCell(cellId);
    }

    @Override
    public Player.Color player() {
        return processor.getPlayer().getColor();
    }

    @Builder
    public static final class InitialContext {

        @NonNull
        @Builder.Default
        private final BoardPrintStrategy boardPrintStrategy = AsciiBoardPrintStrategy.INSTANCE;
        @NonNull
        @Builder.Default
        private final Player.Type playerWhiteType = Player.Type.HUMAN;
        @NonNull
        @Builder.Default
        private final Player.Type playerBlackType = Player.Type.HUMAN;
        @NonNull
        @Builder.Default
        private final InputStream in = System.in;
        @NonNull
        @Builder.Default
        private final PrintStream out = System.out;
        @NonNull
        @Builder.Default
        private final PrintStream err = System.err;

        public Player createWhitePlayer() {
            return playerWhiteType.create(Player.Color.WHITE);
        }

        public Player createBlackPlayer() {
            return playerBlackType.create(Player.Color.BLACK);
        }

    }

}
