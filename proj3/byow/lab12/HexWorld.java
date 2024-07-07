package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static class Position {
        int x;
        int y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Position shift(int dx, int dy) {
            return new Position(x + dx, y + dy);
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        return switch (tileNum) {
            case 0 -> Tileset.GRASS;
            case 1 -> Tileset.FLOWER;
            case 2 -> Tileset.SAND;
            case 3 -> Tileset.MOUNTAIN;
            case 4 -> Tileset.TREE;
            default -> Tileset.NOTHING;
        };
    }

    /**
     * Draw a row of tiles to the board, anchored at a given position.
     */
    private static void drawRow(TETile[][] board, Position p, TETile tile, int length) {
        for (int dx = 0; dx < length; dx++) {
            board[p.x + dx][p.y] = tile;
        }
    }

    /**
     * Helper function for addHexagon.
     */
    private static void addHexagonHelper(TETile[][] board, Position p, TETile tile, int b, int t) {
        Position startOfRow = p.shift(b, 0);
        drawRow(board, startOfRow, tile, t);
        if (b > 0) {
            addHexagonHelper(board, p.shift(0, -1), tile, b - 1, t + 2);
        }
        Position startOfReflection = startOfRow.shift(0, -2 * b - 1);
        drawRow(board, startOfReflection, tile, t);
    }

    /**
     * Add a hexagon to the board at position P of size SIZE.
     */
    private static void addHexagon(TETile[][] board, Position p, TETile tile, int size) {
        if (size < 2) {
            return;
        }
        addHexagonHelper(board, p, tile, size - 1, size);
    }

    /**
     * Draw one column of hexagons, with the position of the first one at P.
     */
    private static void addOneColumnOfHexagons(TETile[][] board, Position p, int size, int n) {
        for (int i = 0; i < n; i++) {
            Position startPos = p.shift(0, -2 * i * size);
            addHexagon(board, startPos, randomTile(), size);
        }
    }

    private static void addColumnsOfHexagonsHelper(TETile[][] board, Position p, int size, int b, int n) {
        if (b > 0) {
            Position startOfReflection = p.shift(b * (3 * size - 2) + b * size, 0);
            addOneColumnOfHexagons(board, p, size, n);
            addColumnsOfHexagonsHelper(board, p.shift(2 * size - 1, size), size, b - 1, n + 1);
            addOneColumnOfHexagons(board, startOfReflection, size, n);
        } else {
            addOneColumnOfHexagons(board, p, size, n);
        }
    }

    private static void addColumnsOfHexagons(TETile[][] board, Position p, int size, int length) {
        addColumnsOfHexagonsHelper(board, p, size, length - 1, length);
    }

    /**
     * Fills the given 2D array of tiles with blank tiles.
     * @param tiles
     */
    public static void fillBoardWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void drawWorld(TETile[][] board) {
        fillBoardWithNothing(board);
        Position p = new Position(6, 35);
        // addHexagon(board, p, randomTile(), 5);
        // addOneColumnOfHexagons(board, p, 4, 3);
        addColumnsOfHexagons(board, p, 3, 3);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] board = new TETile[WIDTH][HEIGHT];
        drawWorld(board);

        ter.renderFrame(board);
    }
}
